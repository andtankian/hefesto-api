package br.com.hefesto.resources.impl.user.rules;

import br.com.hefesto.domain.impl.User;
import br.com.wsbasestructure.dto.FlowContainer;
import br.com.wsbasestructure.dto.Message;
import br.com.wsbasestructure.dto.Result;
import br.com.wsbasestructure.dto.interfaces.IHolder;
import br.com.wsbasestructure.rules.interfaces.ICommand;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author andrew
 */
public class ValidateForgotPasswordCommand implements ICommand {

    @Override
    public IHolder exe(IHolder holder, FlowContainer flowContainer) {
        User u = (User) holder.getEntities().get(0);
        Message m = new Message();

        if (u.getEmail() == null || u.getEmail().isEmpty()) {
            m.setError("empty email");
        } else {
            Session s = flowContainer.getSession();

            User loadedUser = (User) s.createCriteria(User.class)
                    .add(Restrictions.eq("email", u.getEmail()))
                    .uniqueResult();

            if (loadedUser == null) {
                m.setError("error getting the user from database");
                flowContainer.getFc().setMustContinue(false);
                flowContainer.getResult().setMessage(m);
                flowContainer.getResult().setStatus(Result.FATAL_ERROR);
            } else {
                holder.getEntities().set(0, loadedUser);
            }
        }
        return holder;

    }

}
