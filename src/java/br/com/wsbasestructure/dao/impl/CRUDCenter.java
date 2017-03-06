package br.com.wsbasestructure.dao.impl;

import br.com.hefesto.resources.impl.deparment.dao.DepartmentDAO;
import br.com.hefesto.domain.impl.Department;
import br.com.hefesto.domain.impl.Equipment;
import br.com.hefesto.domain.impl.Groups;
import br.com.hefesto.domain.impl.ResourcePage;
import br.com.hefesto.domain.impl.Service;
import br.com.hefesto.domain.impl.User;
import br.com.hefesto.resources.impl.equipments.dao.EquipmentDAO;
import br.com.hefesto.resources.impl.group.dao.GroupDAO;
import br.com.hefesto.resources.impl.resourcepage.dao.ResourceResourcePageDAO;
import br.com.hefesto.resources.impl.service.dao.ServiceDAO;
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
       } else if(e instanceof Groups){
           return new GroupDAO(session, holder);
       } else if(e instanceof User){
           return new UserDAO(session, holder);
       } else if(e instanceof ResourcePage){
           return new ResourceResourcePageDAO(session, holder);
       } else if(e instanceof Service){
           return new ServiceDAO(session, holder);
       } else if(e instanceof Equipment){
           return new EquipmentDAO(session, holder);
       }
       
       return null;
    }

    
    
}
