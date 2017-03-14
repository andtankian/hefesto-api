package br.com.hefesto.resources.impl.user.rules;

import br.com.hefesto.domain.impl.User;
import br.com.wsbasestructure.dto.FlowContainer;
import br.com.wsbasestructure.dto.Message;
import br.com.wsbasestructure.dto.Result;
import br.com.wsbasestructure.dto.interfaces.IHolder;
import br.com.wsbasestructure.rules.interfaces.ICommand;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Andrew Ribeiro
 */
public class ExceptionToLoginUserCommand implements ICommand{

    @Override
    public IHolder exe(IHolder holder, FlowContainer flowContainer) {
        /*
        This class is an extreme exception where wen can't continue to main flow
        to execute this kind of request.
        (Login informations must be send via POST but actually it is a GET operation...
        */
        User u = (User)holder.getEntities().get(0);
        Message m = new Message();
        Session s = flowContainer.getSession();
        
        Criteria c = s.createCriteria(User.class);
        c.add(Restrictions.eq("login", u.getLogin()));
        
        User loaded = (User) c.uniqueResult();
        
        if(loaded == null){
            m.setError("user doesn't exist");
            flowContainer.getResult().setMessage(m);
            flowContainer.getResult().setHolder(holder);
            flowContainer.getResult().setStatus(Result.ERROR);
            flowContainer.getFc().setMustContinue(false);
        } else {
            flowContainer.getResult().setStatus(Result.SUCCESS);
        }
        
        holder.getEntities().set(0, loaded);
        
        return holder;
    }
    
}
