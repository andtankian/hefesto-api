package br.com.hefesto.resources.impl.user.view;

import br.com.hefesto.domain.impl.User;
import br.com.wsbasestructure.dto.FlowContainer;
import br.com.wsbasestructure.dto.impl.GenericHolder;
import br.com.wsbasestructure.dto.interfaces.IHolder;
import br.com.wsbasestructure.view.abstracts.AbstractViewHelper;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MultivaluedHashMap;

/**
 *
 * @author Andrew Ribeiro
 */
public class NewUserViewHelper extends AbstractViewHelper{

    @Override
    public IHolder getView(FlowContainer fc) {
        super.getView(fc);
        
        GenericHolder gh = new GenericHolder();
        User u = new User();
        Form f = (Form)fc.getCr().readEntity(Form.class);
        MultivaluedHashMap mvhm = (MultivaluedHashMap) f.asMap();
        
        return null;
    }
    
    
    
}
