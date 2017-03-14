package br.com.hefesto.resources.impl.user.rules;

import br.com.hefesto.domain.impl.User;
import br.com.wsbasestructure.dto.FlowContainer;
import br.com.wsbasestructure.dto.Message;
import br.com.wsbasestructure.dto.Result;
import br.com.wsbasestructure.dto.interfaces.IHolder;
import br.com.wsbasestructure.rules.interfaces.ICommand;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author Andrew Ribeiro
 */
public class ValidateLoginUserPasswordCommand implements ICommand{

    User userTrying;
    public ValidateLoginUserPasswordCommand(User userTrying) {        
        this.userTrying = userTrying;
    }
    
    

    @Override
    public IHolder exe(IHolder holder, FlowContainer flowContainer) {
        User u = (User)holder.getEntities().get(0);
        Message m = new Message();
        boolean isValid = true;
        
        if(u == null){
            m.setError("user doesn't exist");
            isValid = false;
        } else if(!BCrypt.checkpw(userTrying.getPassword(), u.getPassword())){
            m.setError("invalid password");
            isValid = false;
        }
        
        if(!isValid){
            flowContainer.getResult().setMessage(m);
            flowContainer.getResult().setStatus(Result.ERROR);
            flowContainer.getFc().setMustContinue(false);
        }
        
        flowContainer.getResult().setHolder(holder);
        return holder;
    }
    
}
