package br.com.hefesto.resources.impl.user.rules;

import br.com.hefesto.domain.impl.User;
import br.com.wsbasestructure.dto.FlowContainer;
import br.com.wsbasestructure.dto.interfaces.IHolder;
import br.com.wsbasestructure.rules.interfaces.ICommand;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author Andrew Ribeiro
 */
public class EncryptUserPasswordCommand implements ICommand{

    @Override
    public IHolder exe(IHolder holder, FlowContainer flowContainer) {
        User u = (User)holder.getEntities().get(0);
        
        u.setPassword(BCrypt.hashpw(u.getPassword(), BCrypt.gensalt()));
        
        return holder;     
        
    }
    
}
