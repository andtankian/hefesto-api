package br.com.hefesto.resources.impl.notification.dao;

import br.com.hefesto.domain.impl.Notification;
import br.com.wsbasestructure.dao.impl.GenericBulkDAO;
import br.com.wsbasestructure.dto.Result;
import br.com.wsbasestructure.dto.interfaces.IHolder;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author andrew
 */
public class NotificationBulkDAO extends GenericBulkDAO{

    public NotificationBulkDAO(Session session, IHolder holder) {
        super(session, holder);
    }

    @Override
    public Result read() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Result update() {
        Notification n = (Notification) holder.getEntities().get(0);
        
        Criteria criteria = session.createCriteria(Notification.class);
        criteria.createAlias("user", "u");
        criteria.add(Restrictions.eq("u.id", n.getUser().getId()));
        criteria.add(Restrictions.eq("seen", false));
        
        List ns = criteria.list();
        
        for (Object n1 : ns) {
            Notification iN = (Notification)n1;
            iN.setSeen(true);
        }
        
        session.getTransaction().commit();
        message.setText("notifications updated");
        result.setStatus(Result.SUCCESS);
        
        return result;
    }

    @Override
    public Result delete() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
