package br.com.hefesto.resources.impl.user.view;

import br.com.hefesto.domain.impl.User;
import br.com.wsbasestructure.dto.FlowContainer;
import br.com.wsbasestructure.dto.SearchModel;
import br.com.wsbasestructure.dto.impl.GenericHolder;
import br.com.wsbasestructure.dto.interfaces.IHolder;
import br.com.wsbasestructure.view.abstracts.AbstractViewHelper;
import javax.ws.rs.core.MultivaluedMap;

/**
 *
 * @author Andrew Ribeiro
 */
public class ReadUserProfileViewHelper extends AbstractViewHelper{

    @Override
    public IHolder getView(FlowContainer fc) {
        super.getView(fc);
        
        GenericHolder gh = new GenericHolder();
        User u = new User();
        MultivaluedMap<String, String> mvm = fc.getCr().getUriInfo().getPathParameters();
        String login;
        
        try {
            login = mvm.get("login").get(0);
        }catch(NullPointerException npe){
            login = null;
        }
        
        u.setLogin(login);
        
        SearchModel sm = gh.getSm();
        sm.setEntity(u);
        gh.getEntities().add(u);
        
        return gh;
    }
    
}
