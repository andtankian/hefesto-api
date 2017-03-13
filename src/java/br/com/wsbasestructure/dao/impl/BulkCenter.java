package br.com.wsbasestructure.dao.impl;

import br.com.hefesto.domain.impl.Equipment;
import br.com.hefesto.domain.impl.Groups;
import br.com.hefesto.resources.impl.equipments.dao.EquipmentBulkDAO;
import br.com.wsbasestructure.dao.abstracts.AbstractPersistenceCenter;
import br.com.wsbasestructure.dao.interfaces.IDAO;
import br.com.wsbasestructure.domain.abstracts.Entity;
import br.com.wsbasestructure.dto.interfaces.IHolder;
import org.hibernate.Session;

/**
 *
 * @author Andrew Ribeiro
 */
public class BulkCenter extends AbstractPersistenceCenter {

    @Override
    public IDAO create(Session session, IHolder holder) {
        Entity e = (Entity)holder.getEntities().get(0);
       if(e instanceof Equipment){
           return new EquipmentBulkDAO(session, holder);
       }
       
       return null;
    }

    
}
