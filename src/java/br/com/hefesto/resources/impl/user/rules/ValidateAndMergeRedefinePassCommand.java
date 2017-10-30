package br.com.hefesto.resources.impl.user.rules;

import br.com.hefesto.domain.impl.User;
import br.com.wsbasestructure.dto.FlowContainer;
import br.com.wsbasestructure.dto.Message;
import br.com.wsbasestructure.dto.interfaces.IHolder;
import br.com.wsbasestructure.rules.interfaces.ICommand;
import org.hibernate.Session;

/**
 *
 * @author andrew
 */
public class ValidateAndMergeRedefinePassCommand implements ICommand{

    @Override
    public IHolder exe(IHolder holder, FlowContainer flowContainer) {
        User u = (User)holder.getEntities().get(0);
        Session s = flowContainer.getSession();
        
        User loadedUser = (User) s.get(User.class, u.getId());
        
        loadedUser.getUserConfig().setForgotPasswordCurrentToken(null);
        
        loadedUser.merge(u);
        
        holder.getEntities().set(0, loadedUser);
        
        return holder;
    }
    
}
