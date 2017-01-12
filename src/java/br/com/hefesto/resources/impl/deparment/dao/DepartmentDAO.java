package br.com.hefesto.resources.impl.deparment.dao;

import br.com.hefesto.domain.impl.Department;
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
public class DepartmentDAO extends GenericCRUDDAO {

    public DepartmentDAO(Session session, IHolder holder) {
        super(session, holder);
    }

    @Override
    public Result read() {
        SearchModel sm = holder.getSm();
        if (sm.getEntity() != null
                && sm.getEntity().getId() != null) {
            readOne();
        } else if (sm.getEntity() != null) {
            Department d = (Department) sm.getEntity();
            try {
                d.setId(Long.parseLong(sm.getSearch()));
            } catch (NumberFormatException n) {
                d.setId((long) -1);
            }
            Criteria crSearch = session.createCriteria(Department.class);
            crSearch.setMaxResults(sm.getLimit().intValue());
            crSearch.setFirstResult(sm.getOffset().intValue());
            crSearch.add(Restrictions.disjunction(
                    Restrictions.ilike("name", d.getName(), MatchMode.ANYWHERE),
                    Restrictions.ilike("description", d.getDescription(), MatchMode.ANYWHERE),
                    Restrictions.sqlRestriction("lower(id) like '%" + String.valueOf(d.getId()) + "%'"),
                    Restrictions.sqlRestriction("lower(dateReg) like '%" + String.valueOf(sm.getSearch()) + "%'")
            ));

            holder.setEntities(crSearch.list());

            crSearch.setMaxResults(0);
            crSearch.setFirstResult(0);
            crSearch.setProjection(Projections.rowCount());
            holder.setTotalEntities((Long) crSearch.uniqueResult());

            message.setText("read");
            result.setStatus(Result.SUCCESS);
        } else {
            Criteria c = session.createCriteria(Department.class);
            if (((Department)holder.getEntities().get(0)).getStatus() != null) {
                c.add(Restrictions.eq("status", ((Department)holder.getEntities().get(0)).getStatus()));
            } else {
                c.add(Restrictions.isNull("status"));
            }
            c.setMaxResults(sm.getLimit().intValue());
            c.setFirstResult(sm.getOffset().intValue());
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
