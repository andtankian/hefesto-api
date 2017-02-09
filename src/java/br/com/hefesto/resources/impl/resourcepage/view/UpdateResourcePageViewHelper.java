package br.com.hefesto.resources.impl.resourcepage.view;

import br.com.hefesto.domain.impl.ResourcePage;
import br.com.hefesto.resources.impl.resourcepage.rules.ValidateAndMergeResourcePagesCommand;
import br.com.hefesto.resources.impl.rules.GenericAcceptAttributes;
import br.com.wsbasestructure.dto.FlowContainer;
import br.com.wsbasestructure.dto.impl.GenericHolder;
import br.com.wsbasestructure.dto.interfaces.IHolder;
import br.com.wsbasestructure.rules.impl.ValidateAndMergeEntityCommand;
import br.com.wsbasestructure.view.abstracts.AbstractViewHelper;
import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author Andrew Ribeiro
 */
public class UpdateResourcePageViewHelper extends AbstractViewHelper {

    @Override
    public IHolder getView(FlowContainer fc) {
        super.getView(fc);

        ResourcePage r = new ResourcePage();
        GenericHolder gh = new GenericHolder();
        Form f = fc.getCr().readEntity(Form.class);
        UriInfo u = fc.getCr().getUriInfo();
        MultivaluedHashMap mvhm = (MultivaluedHashMap) f.asMap();

        String id;
        Set read;
        Set write;
        try {
            id = u.getPathParameters().get("id").get(0);
        } catch (NullPointerException e) {
            id = null;
        }

        try {
            r.setId(Long.parseLong(id));
        } catch (NumberFormatException e) {
            r.setId((long) 0);
        }

        try {
            read = new HashSet<>(mvhm.get("read"));
        } catch (NullPointerException e) {
            read = new HashSet<>();
        }

        try {
            write = new HashSet<>(mvhm.get("write"));
        } catch (NullPointerException e) {
            write = new HashSet<>();
        }

        r.setRead(read);
        r.setWrite(write);

        gh.getEntities().add(r);

        this.loadBusinessRulesBeforeMainFlow();
        this.loadBusinessRulesAfterMainFlow();

        return gh;
    }   

    @Override
    public void loadBusinessRulesBeforeMainFlow() {
        this.getRulesBeforeMainFlow().add(new ValidateAndMergeResourcePagesCommand());
    }

    @Override
    public void loadBusinessRulesAfterMainFlow() {
        this.getRulesAfterMainFlow().add(new GenericAcceptAttributes(new String[]{"none"}, rejects, new String[]{"read", "wirte", "users", "groups"}));
    }
    
    
    
    

}
