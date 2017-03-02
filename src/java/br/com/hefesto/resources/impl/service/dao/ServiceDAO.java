package br.com.hefesto.resources.impl.service.dao;

import br.com.hefesto.domain.impl.Service;
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
public class ServiceDAO extends GenericCRUDDAO {

    public ServiceDAO(Session session, IHolder holder) {
        super(session, holder);
    }

    @Override
    public Result read() {
        SearchModel sm = holder.getSm();
        if (sm.getEntity() != null
                && sm.getEntity().getId() != null) {
            readOne();
        } else if (sm.getEntity() != null) {
            //Search
            Service s = (Service) sm.getEntity();
            try {
                s.setId(Long.parseLong(sm.getSearch()));
            } catch (NumberFormatException n) {
                s.setId((long) -1);
            }
            Criteria crSearch = session.createCriteria(Service.class);
            crSearch.setMaxResults(sm.getLimit().intValue());
            crSearch.setFirstResult(sm.getOffset().intValue());
            crSearch.add(Restrictions.disjunction(
                    Restrictions.ilike("name", s.getName(), MatchMode.ANYWHERE),
                    Restrictions.ilike("description", s.getDescription(), MatchMode.ANYWHERE),
                    Restrictions.sqlRestriction("lower(id) like '%" + String.valueOf(s.getId()) + "%'"),
                    Restrictions.sqlRestriction("lower(dateReg) like '%" + String.valueOf(sm.getSearch()) + "%'")
            )).add(Restrictions.isNull("status"));
            
            crSearch.addOrder(Order.desc("dateReg"));

            holder.setEntities(crSearch.list());

            crSearch.setMaxResults(0);
            crSearch.setFirstResult(0);
            crSearch.setProjection(Projections.rowCount());
            holder.setTotalEntities((Long) crSearch.uniqueResult());

            message.setText("read");
            result.setStatus(Result.SUCCESS);
        } else {
            //Regular get or getAll
            Criteria c = session.createCriteria(Service.class);
            c.setFirstResult(sm.getOffset().intValue());
            c.setMaxResults(sm.getLimit().intValue());
            if (sm.getEntity() != null
                    && sm.getEntity().getStatus() != null) {
                c.add(Restrictions.eq("status", sm.getEntity().getStatus()));
            } else {
                c.add(Restrictions.isNull("status"));
            }
            c.addOrder(Order.desc("dateReg"));
            holder.setEntities(c.list());

            c.setMaxResults(0);
            c.setFirstResult(0);
            c.setProjection(Projections.rowCount());
            holder.setTotalEntities((Long) c.uniqueResult());
            message.setText("read");
            result.setStatus(Result.SUCCESS);
        }
        result.setMessage(message);
        result.setHolder(holder);

        return result;
    }

}
