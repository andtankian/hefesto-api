package br.com.wsbasestructure.dto;

import br.com.hefesto.domain.impl.Notification;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author andrew
 */
public class NotificationSystem {
    
    private Notification notification;
    private List ids;

    public NotificationSystem() {
        
        ids = new ArrayList();
    }
    
    

    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }

    public List getIds() {
        return ids;
    }

    public void setIds(List ids) {
        this.ids = ids;
    }

   

}
