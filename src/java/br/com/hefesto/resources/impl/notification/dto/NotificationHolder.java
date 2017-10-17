package br.com.hefesto.resources.impl.notification.dto;

import br.com.wsbasestructure.dto.impl.GenericHolder;

/**
 *
 * @author andrew
 */
public class NotificationHolder extends GenericHolder{
    
    private Long counter;

    public Long getCounter() {
        return counter;
    }

    public void setCounter(Long counter) {
        this.counter = counter;
    }
    
}
