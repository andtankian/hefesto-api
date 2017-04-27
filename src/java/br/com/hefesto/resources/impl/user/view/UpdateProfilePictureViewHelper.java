package br.com.hefesto.resources.impl.user.view;

import br.com.hefesto.domain.impl.User;
import br.com.hefesto.resources.impl.user.rules.AcceptUserAttributesCommand;
import br.com.hefesto.resources.impl.user.rules.ValidateAndSaveLSAndProfilePicCommand;
import br.com.wsbasestructure.dto.FlowContainer;
import br.com.wsbasestructure.dto.impl.GenericImageHolder;
import br.com.wsbasestructure.dto.interfaces.IHolder;
import br.com.wsbasestructure.rules.impl.ValidateIDEntityCommand;
import br.com.wsbasestructure.view.abstracts.AbstractViewHelper;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Base64;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MultivaluedHashMap;

/**
 *
 * @author Andrew Ribeiro
 */
public class UpdateProfilePictureViewHelper extends AbstractViewHelper {

    @Override
    public IHolder getView(FlowContainer fc) {
        super.getView(fc);
        
        User user = new User();   
        GenericImageHolder gih = new GenericImageHolder();
        InputStream is;
        String stringIs;
        byte[] byteIs;
        Form f = fc.getCr().readEntity(Form.class);
        MultivaluedHashMap mvhm = (MultivaluedHashMap) f.asMap();
        try {
            stringIs = (String) mvhm.get("pic").get(0);
            byteIs = Base64.getDecoder().decode(stringIs);
        } catch (NullPointerException npe) {
            stringIs = null;
            byteIs = null;
        }
        
        try {
            is = new ByteArrayInputStream(byteIs);
        } catch (NullPointerException npe) {
            is = null;
        }
        String id;
        Long longId;
        
        try {
            id = fc.getCr().getUriInfo().getPathParameters().get("id").get(0);
        }catch(NullPointerException npe){
            id = null;
        }
        
        try {
            longId = Long.parseLong(id);
        }catch(NumberFormatException nfe){
            longId = null;
        }
        
        user.setId(longId);
        
        gih.setIs(is);
        gih.getEntities().add(user);
        
        loadBusinessRulesBeforeMainFlow();
        loadBusinessRulesAfterMainFlow();
        
        return gih;
    }

    @Override
    public void loadBusinessRulesBeforeMainFlow() {
        getRulesBeforeMainFlow().add(new ValidateIDEntityCommand());
        getRulesBeforeMainFlow().add(new ValidateAndSaveLSAndProfilePicCommand());
    }

    @Override
    public void loadBusinessRulesAfterMainFlow() {
        getRulesAfterMainFlow().add(new AcceptUserAttributesCommand(new String[]{"groups", "department", "userVisual"}, rejects));
    }
    
    

    
}
