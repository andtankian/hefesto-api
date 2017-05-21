package br.com.hefesto.resources.impl.tickets.maintenance.dao;

import br.com.hefesto.domain.impl.Ticket;
import br.com.wsbasestructure.dao.impl.GenericCRUDDAO;
import br.com.wsbasestructure.dto.Result;
import br.com.wsbasestructure.dto.SearchModel;
import br.com.wsbasestructure.dto.interfaces.IHolder;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Andrew Ribeiro
 */
public class TicketMaintenanceDAO extends GenericCRUDDAO {

    public TicketMaintenanceDAO(Session session, IHolder holder) {
        super(session, holder);
    }

    @Override
    public Result read() {
        Ticket t = (Ticket) holder.getEntities().get(0);
        SearchModel sm = holder.getSm();
        Criteria c = session.createCriteria(Ticket.class, "t");
        c.add(Restrictions.eq("type", Ticket.MAINTENANCE));
        c.addOrder(Order.desc("t.dateReg"));
        if (sm != null && sm.getEntity() != null && sm.getEntity().getId() != null && sm.getEntity().getId() > 0) {
            readOne();
        } else if (sm != null && sm.getEntity() != null && sm.getSearch() != null && !sm.getSearch().isEmpty()) {
            c.createAlias("t.responsible", "resp");
            c.createAlias("t.owner", "owner");
            c.createAlias("t.equipment", "equip");
            c.createAlias("t.service", "service");
            c.setMaxResults(sm.getLimit().intValue());
            c.setFirstResult(sm.getOffset().intValue());
            c.add(Restrictions.disjunction(
                    Restrictions.ilike("resp.fullName", sm.getSearch(), MatchMode.ANYWHERE),
                    Restrictions.ilike("resp.login", sm.getSearch(), MatchMode.ANYWHERE),
                    Restrictions.ilike("owner.fullName", sm.getSearch(), MatchMode.ANYWHERE),
                    Restrictions.ilike("owner.login", sm.getSearch(), MatchMode.ANYWHERE),
                    Restrictions.ilike("equip.name", sm.getSearch(), MatchMode.ANYWHERE),
                    Restrictions.ilike("service.name", sm.getSearch(), MatchMode.ANYWHERE),
                    Restrictions.sqlRestriction("lower({alias}.id) like '%" + sm.getSearch() + "%'"),
                    Restrictions.sqlRestriction("lower({alias}.dateReg) like '%" + sm.getSearch() + "%'")
            ));
            
            holder.setEntities(c.list());
            c.setMaxResults(0).setFirstResult(0);
            c.setProjection(Projections.rowCount());
            holder.setTotalEntities((long)c.uniqueResult());
            message.setText("read");
        } else {
            c.setMaxResults(sm.getLimit().intValue());
            c.setFirstResult(sm.getOffset().intValue());
            c.add(Restrictions.ne("status", "deleted"));
            holder.setEntities(c.list());
            c.setMaxResults(0);
            c.setFirstResult(0);
            c.setProjection(Projections.rowCount());
            holder.setTotalEntities((Long) c.uniqueResult());
        }

        result.setHolder(holder);
        result.setMessage(message);
        result.setStatus(Result.SUCCESS);

        return result;
    }

}
