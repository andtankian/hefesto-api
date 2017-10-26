package br.com.hefesto.resources.impl.tickets.maintenance.rules;

import br.com.hefesto.domain.impl.Ticket;
import br.com.wsbasestructure.dto.FlowContainer;
import br.com.wsbasestructure.dto.interfaces.IHolder;
import br.com.wsbasestructure.rules.interfaces.ICommand;

/**
 *
 * @author andrew
 */
public class EmailfyNewTicketAllAdmUsersCommand implements ICommand{

    @Override
    public IHolder exe(IHolder holder, FlowContainer flowContainer) {
        Ticket t = (Ticket)holder.getEntities().get(0);
        
        StringBuilder emailBuilder = new StringBuilder();
        
        return holder;        
    }
    
}
