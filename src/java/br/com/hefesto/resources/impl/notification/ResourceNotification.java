package br.com.hefesto.resources.impl.notification;

import br.com.hefesto.resources.impl.notification.view.ReadNotificationsCounterViewHelper;
import br.com.hefesto.resources.impl.notification.view.ReadNotificationsViewHelper;
import br.com.hefesto.resources.impl.notification.view.UpdateSeenStatusBulkNotificationViewHelper;
import br.com.hefesto.resources.impl.notification.view.UpdateSeenStatusNotificationViewHelper;
import br.com.wsbasestructure.control.Facade;
import br.com.wsbasestructure.domain.abstracts.AbstractResource;
import br.com.wsbasestructure.dto.FlowContainer;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.hibernate.Session;

/**
 *
 * @author andrew
 */
@Path("notifications")
@Produces(MediaType.APPLICATION_JSON)
public class ResourceNotification extends AbstractResource{
    
    @GET
    @Path("user/{id}")
    public String getNotifications(){
        return new Facade(new FlowContainer(new ReadNotificationsViewHelper(),
                (Session)httpRequest.getAttribute("session"), cr, httpRequest))
                .process();
    }
    
    @GET
    @Path("user/{id}/counter")
    public String getNotificationsCounter(){
        return new Facade(new FlowContainer(new ReadNotificationsCounterViewHelper(),
                (Session)httpRequest.getAttribute("session"), cr, httpRequest))
                .process();
    }
    
    @PUT
    @Path("{id}")
    public String updateSeenStatusNotification(){
        return new Facade(new FlowContainer(new UpdateSeenStatusNotificationViewHelper(),
                (Session)httpRequest.getAttribute("session"), cr, httpRequest))
                .process();
    }
    
    
    @PUT
    @Path("user/{id}/clearall")
    public String updateSeenStatusBulkNotification(){
        return new Facade(new FlowContainer(new UpdateSeenStatusBulkNotificationViewHelper(),
                (Session)httpRequest.getAttribute("session"), cr, httpRequest))
                .process();
    }
}
