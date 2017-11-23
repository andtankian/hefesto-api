package br.com.hefesto.resources.impl.tickets.maintenance.dao;

import br.com.hefesto.domain.impl.Interaction;
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
import org.hibernate.sql.JoinType;

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
        Criteria c = session.createCriteria(Ticket.class);
        c.add(Restrictions.eq("type", Ticket.MAINTENANCE));
        c.addOrder(Order.desc("lastUpdate"));
        c.addOrder(Order.desc("dateReg"));
        br.com.wsbasestructure.utils.Utils.buildCriteriaFilters(c, sm.getFilters());
        br.com.wsbasestructure.utils.Utils.buildBetweenFilter(c, sm.getBetween());
        if (sm.getEntity() != null && sm.getEntity().getId() != null && sm.getEntity().getId() > 0) {
            readOne();
        } else if (sm.getEntity() != null && sm.getEntity() instanceof Interaction) {
            Interaction inte = null;
            for (Object interaction : t.getInteractions()) {
                inte = (Interaction) interaction;
                break;
            }
            c.createAlias("interactions", "i");
            c.setMaxResults(sm.getLimit().intValue());
            c.setFirstResult(sm.getOffset().intValue());
            c.add(Restrictions.eq("i.user.id", inte.getUser().getId()));
            holder.setEntities(c.list());
            c.setMaxResults(0).setFirstResult(0);
            c.setProjection(Projections.rowCount());
            holder.setTotalEntities((long) c.uniqueResult());
            message.setText("read");
        } else if (sm.getEntity() != null && sm.getSearch() != null && !sm.getSearch().isEmpty()) {
            c.createAlias("responsible", "resp", JoinType.LEFT_OUTER_JOIN);
            c.createAlias("owner", "owner");
            c.createAlias("equipment", "equip", JoinType.LEFT_OUTER_JOIN);
            c.createAlias("service", "service", JoinType.LEFT_OUTER_JOIN);
            c.setMaxResults(sm.getLimit().intValue());
            c.setFirstResult(sm.getOffset().intValue());
            c.add(Restrictions.disjunction(
                    Restrictions.ilike("title", sm.getSearch(), MatchMode.ANYWHERE),
                    Restrictions.ilike("description", sm.getSearch(), MatchMode.ANYWHERE),
                    Restrictions.ilike("resolution", sm.getSearch(), MatchMode.ANYWHERE),
                    Restrictions.ilike("status", sm.getSearch(), MatchMode.ANYWHERE),
                    Restrictions.ilike("priority", sm.getSearch(), MatchMode.ANYWHERE),
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
            holder.setTotalEntities((long) c.uniqueResult());
            message.setText("read");
        } else {
            c.setMaxResults(sm.getLimit().intValue());
            c.setFirstResult(sm.getOffset().intValue());
            c.add(Restrictions.ne("status", "deleted"));
            c.add(Restrictions.ne("status", "Recusado"));
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
