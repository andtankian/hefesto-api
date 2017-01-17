package br.com.wsbasestructure.dao.impl;

import br.com.hefesto.resources.impl.deparment.dao.DepartmentDAO;
import br.com.hefesto.domain.impl.Department;
import br.com.hefesto.domain.impl.Permission;
import br.com.hefesto.domain.impl.User;
import br.com.hefesto.resources.impl.permission.dao.PermissionDAO;
import br.com.hefesto.resources.impl.user.dao.UserDAO;
import br.com.wsbasestructure.dao.abstracts.AbstractPersistenceCenter;
import br.com.wsbasestructure.dao.interfaces.IDAO;
import br.com.wsbasestructure.domain.abstracts.Entity;
import br.com.wsbasestructure.dto.interfaces.IHolder;
import org.hibernate.Session;

/**
 *
 * @author Andrew Ribeiro
 */
public class CRUDCenter extends AbstractPersistenceCenter{

    @Override
    public IDAO create(Session session, IHolder holder) {
        Entity e = (Entity)holder.getEntities().get(0);
       if(e instanceof Department){
           return new DepartmentDAO(session, holder);
       } else if(e instanceof Permission){
           return new PermissionDAO(session, holder);
       } else if(e instanceof User){
           return new UserDAO(session, holder);
       }
       
       return null;
    }

    
    
}
