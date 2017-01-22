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
public class ValidateDataResourcePageCommand implements ICommand{

    @Override
    public IHolder exe(IHolder holder, FlowContainer flowContainer) {
        ResourcePage r = (ResourcePage)holder.getEntities().get(0);
        Message m = new Message();
        String name;
        name = r.getName();
        if(name == null || name.isEmpty()){
            m.setError("empty name");
            flowContainer.getResult().setMessage(m);
            flowContainer.getResult().setStatus(Result.ERROR);
            flowContainer.getResult().setHolder(holder);
            flowContainer.getFc().setMustContinue(false);
        }
        
        return holder;
    }
    
}
