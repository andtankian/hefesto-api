package br.com.hefesto.resources.impl.user.rules;

import br.com.hefesto.domain.impl.User;
import br.com.wsbasestructure.dto.FlowContainer;
import br.com.wsbasestructure.dto.interfaces.IHolder;
import br.com.wsbasestructure.rules.interfaces.ICommand;
import java.util.UUID;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author andrew
 */
public class GenerateAndMergeNewForgotPasswordTokenCommand implements ICommand{

    @Override
    public IHolder exe(IHolder holder, FlowContainer flowContainer) {
        User u = (User)holder.getEntities().get(0);
        
        u.getUserConfig().setForgotPasswordCurrentToken(BCrypt.hashpw(UUID.randomUUID().toString(), BCrypt.gensalt()));
        
        return holder;
    }
    
}
