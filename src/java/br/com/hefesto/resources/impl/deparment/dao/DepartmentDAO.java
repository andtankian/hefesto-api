package br.com.hefesto.resources.impl.deparment.dao;

import br.com.hefesto.domain.impl.Department;
import br.com.wsbasestructure.dao.impl.GenericCRUDDAO;
import br.com.wsbasestructure.dto.Result;
import br.com.wsbasestructure.dto.interfaces.IHolder;
import org.hibernate.Criteria;
import org.hibernate.Session;

/**
 *
 * @author Andrew Ribeiro
 */
public class DepartmentDAO extends GenericCRUDDAO{

    public DepartmentDAO(Session session, IHolder holder) {
        super(session, holder);
    }   

    @Override
    public Result read() {
        Criteria c = session.createCriteria(Department.class);
        c.setMaxResults(holder.getSm().getAmount().intValue());
        c.setFirstResult(holder.getSm().getFrom().intValue());
        holder.setEntities(c.list());
        result.setMessage(message);
        result.setStatus(Result.SUCCESS);
        result.setHolder(holder);

        return result;
    }

}
