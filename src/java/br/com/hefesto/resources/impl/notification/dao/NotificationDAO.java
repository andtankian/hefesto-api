package br.com.hefesto.resources.impl.notification.dao;

import br.com.hefesto.domain.impl.Notification;
import br.com.wsbasestructure.dao.impl.GenericCRUDDAO;
import br.com.wsbasestructure.dto.Result;
import br.com.wsbasestructure.dto.SearchModel;
import br.com.wsbasestructure.dto.interfaces.IHolder;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author andrew
 */
public class NotificationDAO extends GenericCRUDDAO {

    public NotificationDAO(Session session, IHolder holder) {
        super(session, holder);
    }

    @Override
    public Result read() {
        Notification n = (Notification) holder.getEntities().get(0);
        SearchModel sm = holder.getSm();
        Criteria criteria = session.createCriteria(Notification.class);
        criteria.createAlias("user", "u");
        criteria.addOrder(Order.desc("dateReg"));
        criteria.add(Restrictions.eq("u.id", n.getUser().getId()));
        criteria.setMaxResults(sm.getLimit().intValue());
        criteria.setFirstResult(sm.getOffset().intValue());
        holder.setEntities(criteria.list());
        criteria.setMaxResults(0);
        criteria.setFirstResult(0);
        criteria.setProjection(Projections.rowCount());
        holder.setTotalEntities((Long) criteria.uniqueResult());
        
        result.setHolder(holder);
        result.setMessage(message);
        result.setStatus(Result.SUCCESS);

        return result;

    }

}
