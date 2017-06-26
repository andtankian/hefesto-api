package br.com.hefesto.resources.impl.user.rules;

import br.com.hefesto.domain.impl.User;
import br.com.wsbasestructure.dto.FlowContainer;
import br.com.wsbasestructure.dto.Message;
import br.com.wsbasestructure.dto.Result;
import br.com.wsbasestructure.dto.interfaces.IHolder;
import br.com.wsbasestructure.rules.interfaces.ICommand;

/**
 *
 * @author andrew
 */
public class ValidateLoginUserStatusCommand implements ICommand{

    @Override
    public IHolder exe(IHolder holder, FlowContainer flowContainer) {
        User u = (User)holder.getEntities().get(0);
        Message  m = new Message();
        
        if(u.getStatus() != null && u.getStatus().equals("deactivated")) {
            m.setError("user deactivated");
            flowContainer.getResult().setMessage(m);
            flowContainer.getResult().setStatus(Result.ERROR);
            flowContainer.getFc().setMustContinue(false);
            flowContainer.getResult().setHolder(null);
        }
        
        return holder;
    }
    
}
