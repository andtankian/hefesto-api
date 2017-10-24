package br.com.hefesto.resources.impl.notification.view;

import br.com.hefesto.domain.impl.Notification;
import br.com.hefesto.domain.impl.User;
import br.com.wsbasestructure.dto.FlowContainer;
import br.com.wsbasestructure.dto.impl.GenericHolder;
import br.com.wsbasestructure.dto.interfaces.IHolder;
import br.com.wsbasestructure.view.abstracts.AbstractViewHelper;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author andrew
 */
public class UpdateSeenStatusBulkNotificationViewHelper extends AbstractViewHelper{

    @Override
    public IHolder getView(FlowContainer fc) {
        super.getView(fc);
        typeRequest = "bulk";
        
        GenericHolder gh = new GenericHolder();
        Notification n = new Notification();
        n.setType(Notification.NOTIFICATION);
        UriInfo uri = fc.getCr().getUriInfo();
        MultivaluedMap<String, String> mvm = uri.getPathParameters();
        User u = new User();
        try {
            u.setId(Long.parseLong(mvm.get("id").get(0)));
        }catch(NullPointerException | NumberFormatException ne){
            u = null;
        } finally {
            n.setUser(u);
        }
        
        gh.getEntities().add(n);
        
        return gh;
    }
    
    
    
}
