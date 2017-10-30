package br.com.hefesto.resources.impl.user.rules;

import br.com.hefesto.domain.impl.User;
import br.com.hefesto.domain.impl.UserConfig;
import br.com.wsbasestructure.dto.FlowContainer;
import br.com.wsbasestructure.dto.Message;
import br.com.wsbasestructure.dto.Result;
import br.com.wsbasestructure.dto.interfaces.IHolder;
import br.com.wsbasestructure.rules.interfaces.ICommand;

/**
 *
 * @author andrew
 */
public class ValidateTokenRedefinePasswordCommand implements ICommand{

    @Override
    public IHolder exe(IHolder holder, FlowContainer flowContainer) {
        User u = (User)holder.getEntities().get(0);
        UserConfig uc = u.getUserConfig();
        
        Message m = new Message();
        if(uc.getForgotPasswordCurrentToken() == null ||
                uc.getForgotPasswordCurrentToken().isEmpty()){
            m.setError("invalid token");
            flowContainer.getFc().setMustContinue(false);
            flowContainer.getResult().setStatus(Result.ERROR);
            flowContainer.getResult().setMessage(m);
        }
        
        return holder;
    }
    
}
