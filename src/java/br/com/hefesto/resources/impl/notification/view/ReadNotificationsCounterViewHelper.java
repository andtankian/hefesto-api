package br.com.hefesto.resources.impl.notification.view;

import br.com.hefesto.domain.impl.Notification;
import br.com.hefesto.domain.impl.User;
import br.com.hefesto.resources.impl.notification.dto.NotificationHolder;
import br.com.wsbasestructure.dto.FlowContainer;
import br.com.wsbasestructure.dto.SearchModel;
import br.com.wsbasestructure.dto.interfaces.IHolder;
import br.com.wsbasestructure.view.abstracts.AbstractViewHelper;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author andrew
 */
public class ReadNotificationsCounterViewHelper extends AbstractViewHelper{

    @Override
    public IHolder getView(FlowContainer fc) {
        
        super.getView(fc);
        
        NotificationHolder nh = new NotificationHolder();
        Notification n = new Notification();
        n.setType(Notification.NOTIFICATION);
        nh.getEntities().add(n);
        
        SearchModel sm = nh.getSm();
        sm.setSearch("**counter**");
        UriInfo uri = fc.getCr().getUriInfo();
        sm.getBasics(uri);
        MultivaluedMap<String, String> mvm = uri.getPathParameters();
        
        User u = new User();
        try {
            u.setId(Long.parseLong(mvm.get("id").get(0)));
        }catch(NullPointerException | NumberFormatException ne){
            u = null;
        }finally {
            n.setUser(u);
        }
        
        return nh;
    }
    
    
    
}
