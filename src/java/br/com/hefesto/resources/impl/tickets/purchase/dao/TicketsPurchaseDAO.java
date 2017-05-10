package br.com.hefesto.resources.impl.tickets.purchase.dao;

import br.com.hefesto.domain.impl.Ticket;
import br.com.wsbasestructure.dao.impl.GenericCRUDDAO;
import br.com.wsbasestructure.dto.Result;
import br.com.wsbasestructure.dto.SearchModel;
import br.com.wsbasestructure.dto.interfaces.IHolder;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Andrew Ribeiro
 */
public class TicketsPurchaseDAO extends GenericCRUDDAO{

    public TicketsPurchaseDAO(Session session, IHolder holder) {
        super(session, holder);
    }

    @Override
    public Result read() {
        Ticket t = (Ticket) holder.getEntities().get(0);
        SearchModel sm = holder.getSm();
        Criteria c;
        if(sm != null && sm.getEntity() != null && sm.getEntity().getId() != null 
                && sm.getEntity().getId() > 0){
            readOne();
        } else if(sm != null && sm.getEntity() != null && sm.getSearch() != null &&
                !sm.getSearch().isEmpty()) {
            c = session.createCriteria(Ticket.class, "t");
            c.createAlias("t.responsible", "res");
            c.createAlias("t.requestedProducts", "rp");
            c.createAlias("rp.product", "p");
            c.add(Restrictions.disjunction(
                    Restrictions.ilike("res.fullName", sm.getSearch(), MatchMode.ANYWHERE),
                    Restrictions.ilike("t.priority", sm.getSearch(), MatchMode.ANYWHERE),
                    Restrictions.ilike("rp.statusPurchase", sm.getSearch(), MatchMode.ANYWHERE),
                    Restrictions.ilike("p.name", sm.getSearch(), MatchMode.ANYWHERE),
                    Restrictions.sqlRestriction("lower({alias}.id) like '%" + sm.getSearch() + "%'"),
                    Restrictions.sqlRestriction("lower({alias}.dateReg) like '%" + sm.getSearch() + "%'")
            ));
            c.add(Restrictions.isNull("t.status"));
            c.add(Restrictions.eq("t.type", Ticket.PURCHASE));
            holder.setEntities(c.list());
            c.setProjection(Projections.rowCount());
            holder.setTotalEntities((Long) c.uniqueResult());
            message.setText("read");
        } else {
            c = session.createCriteria(Ticket.class, "t");
            c.setFirstResult(sm.getOffset().intValue()).setMaxResults(sm.getLimit().intValue());
            c.add(Restrictions.isNull("status"));
            holder.setEntities(c.list());
            c.setFirstResult(0).setMaxResults(0);
            c.setProjection(Projections.rowCount());
            holder.setTotalEntities((Long) c.uniqueResult());
            message.setText("read");
        }
        
        result.setHolder(holder);
        result.setMessage(message);
        result.setStatus(Result.SUCCESS);
        
        return result;
    }
    
}
