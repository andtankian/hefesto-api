package br.com.wsbasestructure.dao.impl;

import br.com.hefesto.domain.impl.User;
import br.com.hefesto.resources.impl.user.dao.UserWidgetsDAO;
import br.com.wsbasestructure.dao.abstracts.AbstractPersistenceCenter;
import br.com.wsbasestructure.dao.interfaces.IDAO;
import br.com.wsbasestructure.domain.abstracts.Entity;
import br.com.wsbasestructure.dto.interfaces.IHolder;
import org.hibernate.Session;

/**
 *
 * @author andrew
 */
public class WidgetCenter extends AbstractPersistenceCenter{

    @Override
    public IDAO create(Session session, IHolder holder) {
        Entity e = (Entity) holder.getEntities().get(0);
        
        if(e instanceof User){
            return new UserWidgetsDAO(session, holder);
        } else {
            return null;
        }
    }
    
}
