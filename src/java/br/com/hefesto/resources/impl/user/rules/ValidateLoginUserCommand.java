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
public class ValidateLoginUserCommand implements ICommand{

    @Override
    public IHolder exe(IHolder holder, FlowContainer flowContainer) {
        User user = (User)holder.getEntities().get(0);
        Message m = new Message();
        boolean isValid = true;
        String password;
        String login;
        
        password = user.getPassword();
        login = user.getLogin();
        
        if(password == null || password.isEmpty()){
            m.setError("empty password");
            isValid = false;
        } else if(login == null || login.isEmpty()){
            m.setError("empty login");
            isValid = false;
        }
        
        if(!isValid){
            flowContainer.getResult().setStatus(Result.ERROR);
            flowContainer.getResult().setMessage(m);
            flowContainer.getResult().setHolder(holder);
            flowContainer.getFc().setMustContinue(false);
        }
        
        return holder;
    }
    
}
