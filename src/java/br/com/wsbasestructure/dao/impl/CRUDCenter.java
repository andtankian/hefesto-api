package br.com.wsbasestructure.dao.impl;

import br.com.hefesto.resources.impl.deparment.dao.DepartmentDAO;
import br.com.hefesto.domain.impl.Department;
import br.com.hefesto.resources.impl.permission.dao.PermissionDAO;
import br.com.wsbasestructure.dao.abstracts.AbstractPersistenceCenter;
import br.com.wsbasestructure.dao.interfaces.IDAO;
import br.com.wsbasestructure.dto.interfaces.IHolder;
import org.hibernate.Session;

/**
 *
 * @author Andrew Ribeiro
 */
public class CRUDCenter extends AbstractPersistenceCenter{

    @Override
    public IDAO create(Session session, IHolder holder) {
       if(holder.getEntities().get(0) instanceof Department){
           return new DepartmentDAO(session, holder);
       } else {
           return new PermissionDAO(session, holder);
       }
    }

    
    
}
