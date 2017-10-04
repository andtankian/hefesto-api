package br.com.hefesto.resources.impl.tickets.maintenance.rules.notifications;

import br.com.hefesto.domain.impl.Notification;
import br.com.hefesto.domain.impl.Ticket;
import br.com.hefesto.domain.impl.User;
import br.com.wsbasestructure.dto.FlowContainer;
import br.com.wsbasestructure.dto.NotificationSystem;
import br.com.wsbasestructure.dto.NotificationSystemContainer;
import br.com.wsbasestructure.dto.interfaces.IHolder;
import br.com.wsbasestructure.endpoints.sessions.interfaces.WebSocketSessionHandler;
import br.com.wsbasestructure.rules.interfaces.ICommand;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author andrew
 */
public class BuildNotificationNewTicketCommand implements ICommand {

    @Override
    public IHolder exe(IHolder holder, FlowContainer flowContainer) {
        NotificationSystemContainer nsc = new NotificationSystemContainer();
        
        NotificationSystem ns = new NotificationSystem();
        

        Session s = flowContainer.getSession();
        Transaction tr = s.getTransaction().isActive() ? s.getTransaction() : s.beginTransaction();

        Notification n = new Notification();
        n.setDateReg(new Timestamp(Calendar.getInstance().getTimeInMillis()));

        /*Building notification description*/
        StringBuilder no_desc = new StringBuilder();
        no_desc.append("Novo ticket foi aberto no dia ");
        /*Formatting date*/
        SimpleDateFormat sdf = new SimpleDateFormat("d 'de' MMMM 'de' YYYY");
        no_desc.append(sdf.format(n.getDateReg())).append(" por ");
        Ticket t = (Ticket) holder.getEntities().get(0);

        no_desc.append(t.getOwner().getFullName()).append(": ");
        no_desc.append(t.getTitle());
        
          /*Let's add the main notification to the system*/
        ns.setNotification(n);

        /*Let's get all the users that is in the group Administrator*/
        List users = s.createCriteria(User.class).createAlias("groups", "g").add(
                Restrictions.conjunction(
                        Restrictions.eq("g.name", "Administradores")
                )
        ).list();
        
        
        if (users != null && !users.isEmpty()) {
            for (int i = 0; i < users.size(); i++) {
                User u = (User) users.get(0);
                Notification no = new Notification();
                no.setDateReg(n.getDateReg());
                no.setDescription(no_desc.toString());
                no.setPicture(t.getOwner().getUserVisual().getProfile());
                no.setTitle(new StringBuilder(t.getOwner().getFullName()).append(" abriu novo ticket").toString());
                no.setType(Notification.NOTIFICATION);
                no.setUser(u);
                s.save(no);
                if (i % 20 == 0) { //20, same as the JDBC batch size
                    //flush a batch of inserts and release memory:
                    s.flush();
                    s.clear();
                }
                ns.getIds().add(u.getId());
            }
            tr.commit();
        }
        
        nsc.getNotificationSystems().add(ns);
        
        /*FROM HERE TO BELOW WE WILL WORK WITH RESPONSIBLE NOTIFICATION*/
        
        /*BUILD THE STRING AND SEND VIA WEBSOCKET*/
        WebSocketSessionHandler ws = (WebSocketSessionHandler) flowContainer.getHttprequest().getServletContext().getAttribute("contentep");
        ws.notify(nsc);
        
        return holder;

    }

}
