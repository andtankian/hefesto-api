package br.com.wsbasestructure.dto;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author andrew
 */
public class NotificationSystemContainer {
    
    private List notificationSystems;

    public NotificationSystemContainer() {
        this.notificationSystems = new ArrayList();
    }
    
    

    public List getNotificationSystems() {
        return notificationSystems;
    }

    public void setNotificationSystems(List notificationSystems) {
        this.notificationSystems = notificationSystems;
    }
    
}
