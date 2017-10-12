package br.com.hefesto.resources.impl.notification.view;

import br.com.hefesto.domain.impl.Notification;
import br.com.hefesto.domain.impl.User;
import br.com.hefesto.resources.impl.rules.GenericAcceptAttributes;
import br.com.wsbasestructure.dto.FlowContainer;
import br.com.wsbasestructure.dto.SearchModel;
import br.com.wsbasestructure.dto.impl.GenericHolder;
import br.com.wsbasestructure.dto.interfaces.IHolder;
import br.com.wsbasestructure.view.abstracts.AbstractViewHelper;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author andrew
 */
public class ReadNotificationsViewHelper extends AbstractViewHelper{

    @Override
    public IHolder getView(FlowContainer fc) {
        super.getView(fc);
        
        Notification notification = new Notification();
        notification.setType(Notification.NOTIFICATION);
        
        GenericHolder gh = new GenericHolder();
        SearchModel sm = gh.getSm();
        UriInfo uri = fc.getCr().getUriInfo();
        sm.getBasics(uri);
        MultivaluedMap<String, String> mvm = uri.getPathParameters();
        
        User u = new User();
        try {
            u.setId(Long.parseLong(mvm.get("id").get(0)));
        }catch(NullPointerException | NumberFormatException n){
            u = null;
        }finally {
            notification.setUser(u);
        }
        
        gh.getEntities().add(notification);
    
        loadBusinessRulesAfterMainFlow();
        
        
        return gh;
    }

    @Override
    public void loadBusinessRulesAfterMainFlow() {
        this.getRulesAfterMainFlow().add(new GenericAcceptAttributes(new String[]{"user"}, rejects, new String[]{"ticket", "groups", "user"}));
    }
    
    
    
    
}
