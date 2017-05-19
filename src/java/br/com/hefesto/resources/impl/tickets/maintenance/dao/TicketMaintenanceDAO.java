package br.com.hefesto.resources.impl.tickets.maintenance.dao;

import br.com.wsbasestructure.dao.impl.GenericCRUDDAO;
import br.com.wsbasestructure.dto.Result;
import br.com.wsbasestructure.dto.interfaces.IHolder;
import org.hibernate.Session;

/**
 *
 * @author Andrew Ribeiro
 */
public class TicketMaintenanceDAO extends GenericCRUDDAO{

    public TicketMaintenanceDAO(Session session, IHolder holder) {
        super(session, holder);
    }

    @Override
    public Result read() {
        return result;
    }
    
}
