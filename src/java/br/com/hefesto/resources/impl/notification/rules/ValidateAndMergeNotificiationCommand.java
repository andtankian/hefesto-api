package br.com.hefesto.resources.impl.notification.rules;

import br.com.hefesto.domain.impl.Notification;
import br.com.wsbasestructure.dto.FlowContainer;
import br.com.wsbasestructure.dto.Message;
import br.com.wsbasestructure.dto.interfaces.IHolder;
import br.com.wsbasestructure.rules.interfaces.ICommand;
import org.hibernate.Session;

/**
 *
 * @author andrew
 */
public class ValidateAndMergeNotificiationCommand implements ICommand{

    @Override
    public IHolder exe(IHolder holder, FlowContainer flowContainer) {
        Notification n = (Notification)holder.getEntities().get(0);
        
        Message m = new Message();
        
        Session s = (Session)flowContainer.getSession();
        
        Notification loadedN = (Notification) s.get(Notification.class, n.getId());
        
        loadedN.merge(n);
        
        holder.getEntities().set(0, loadedN);
        
        return holder;
    }
    
}
