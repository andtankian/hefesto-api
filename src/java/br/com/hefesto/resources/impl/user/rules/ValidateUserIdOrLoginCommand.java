package br.com.hefesto.resources.impl.user.rules;

import br.com.hefesto.domain.impl.User;
import br.com.wsbasestructure.dto.FlowContainer;
import br.com.wsbasestructure.dto.Message;
import br.com.wsbasestructure.dto.Result;
import br.com.wsbasestructure.dto.interfaces.IHolder;
import br.com.wsbasestructure.rules.interfaces.ICommand;

/**
 *
 * @author Andrew Ribeiro
 */
public class ValidateUserIdOrLoginCommand implements ICommand{

    @Override
    public IHolder exe(IHolder holder, FlowContainer flowContainer) {
        User u = (User) holder.getEntities().get(0);
        Message m = new Message();
        if(u.getId() == null){
            if(u.getLogin() == null){
                flowContainer.getFc().setMustContinue(false);
                flowContainer.getResult().setStatus(Result.ERROR);
                flowContainer.getResult().setMessage(m);
            }
        }
        
        return holder;
    }
    
}
