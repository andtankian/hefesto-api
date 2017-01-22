package br.com.hefesto.resources.impl.resourcepage.rules;

import br.com.hefesto.domain.impl.ResourcePage;
import br.com.wsbasestructure.dto.FlowContainer;
import br.com.wsbasestructure.dto.Message;
import br.com.wsbasestructure.dto.Result;
import br.com.wsbasestructure.dto.interfaces.IHolder;
import br.com.wsbasestructure.rules.interfaces.ICommand;

/**
 *
 * @author Andrew Ribeiro
 */
public class ValidateNameWhenReadingOneResourcePageCommand implements ICommand {

    @Override
    public IHolder exe(IHolder holder, FlowContainer flowContainer) {
        ResourcePage r = (ResourcePage) holder.getEntities().get(0);
        Message m = new Message();
        String name;

        name = r.getName();

        if (name == null || name.isEmpty()) {
            r.setName(null);
            m.setError("invalid name");
            flowContainer.getFc().setMustContinue(false);
            flowContainer.getResult().setStatus(Result.ERROR);
            flowContainer.getResult().setMessage(m);
        }
        
        return holder;
    }

}
