package br.com.hefesto.resources.impl.tickets.maintenance.rules;

import br.com.hefesto.domain.impl.Interaction;
import br.com.hefesto.domain.impl.Ticket;
import br.com.wsbasestructure.dto.FlowContainer;
import br.com.wsbasestructure.dto.Message;
import br.com.wsbasestructure.dto.Result;
import br.com.wsbasestructure.dto.interfaces.IHolder;
import br.com.wsbasestructure.rules.interfaces.ICommand;

/**
 *
 * @author andrew
 */
public class ValidateInteractionUserWhenGettingRecentTicketsCommand implements ICommand {

    @Override
    public IHolder exe(IHolder holder, FlowContainer flowContainer) {
        Ticket t = (Ticket) holder.getEntities().get(0);
        Interaction inte = null;

        for (Object interaction : t.getInteractions()) {
            inte = (Interaction) interaction;
            break;
        }

        Message m = new Message();

        if (inte.getUser() == null) {
            m.setError("invalid interaction user");
            flowContainer.getFc().setMustContinue(false);
            flowContainer.getResult().setMessage(m);
            flowContainer.getResult().setStatus(Result.ERROR);
        }

        return holder;
    }

}
