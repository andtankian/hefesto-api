package br.com.hefesto.resources.impl.user.dao;

import br.com.hefesto.domain.impl.User;
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
public class UserDAO extends GenericCRUDDAO{

    public UserDAO(Session session, IHolder holder) {
        super(session, holder);
    }

    @Override
    public Result read() {
        SearchModel sm = holder.getSm();
        if (sm.getEntity() != null
                && sm.getEntity().getId() != null) {
           readOne();
           if(holder.getEntities().get(0) == null){
               message.setError("user not found");
               result.setStatus(Result.ERROR);
               fc.setMustContinue(false);
           }
        } else if (sm.getEntity() != null) {
            User u = (User) sm.getEntity();
            try {
                u.setId(Long.parseLong(sm.getSearch()));
            } catch (NumberFormatException n) {
                u.setId((long) -1);
            }
            Criteria crSearch = session.createCriteria(User.class);
            crSearch.setMaxResults(sm.getLimit().intValue());
            crSearch.setFirstResult(sm.getOffset().intValue());
            crSearch.add(Restrictions.disjunction(
                    Restrictions.ilike("name", u.getFullName(), MatchMode.ANYWHERE),
                    Restrictions.ilike("email", u.getEmail(), MatchMode.ANYWHERE),
                    Restrictions.ilike("login", u.getLogin(), MatchMode.ANYWHERE),
                    Restrictions.sqlRestriction("lower(id) like '%" + String.valueOf(u.getId()) + "%'"),
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
            Criteria c = session.createCriteria(User.class);
            if (sm.getEntity() != null
                    && sm.getEntity().getStatus() != null) {
                c.add(Restrictions.eq("status", sm.getEntity().getStatus()));
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
