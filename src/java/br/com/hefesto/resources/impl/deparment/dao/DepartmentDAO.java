package br.com.hefesto.resources.impl.deparment.dao;

import br.com.hefesto.domain.impl.Department;
import br.com.wsbasestructure.dao.impl.GenericCRUDDAO;
import br.com.wsbasestructure.dto.Result;
import br.com.wsbasestructure.dto.interfaces.IHolder;
import org.hibernate.Criteria;
import org.hibernate.Session;
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
        if (holder.getSm().getEntity() != null
                && holder.getSm().getEntity().getId() != null) {
            holder.getEntities().set(0, session.get(Department.class, holder.getSm().getEntity().getId()));
            if (holder.getEntities().get(0) == null) {
                message.setError("not found");
            } else {
                message.setText("read");
            }
        } else {
            Criteria c = session.createCriteria(Department.class);
            if(holder.getSm().getEntity() != null && 
                    holder.getSm().getEntity().getStatus() != null){
                c.add(Restrictions.eq("status", holder.getSm().getEntity().getStatus()));
            } else {
                c.add(Restrictions.isNull("status"));
            }
            c.setMaxResults(holder.getSm().getLimit().intValue());
            c.setFirstResult(holder.getSm().getOffset().intValue());
            holder.setEntities(c.list());
            message.setText("read");
            result.setStatus(Result.SUCCESS);
        }
        result.setMessage(message);
        result.setHolder(holder);

        return result;
    }

}
