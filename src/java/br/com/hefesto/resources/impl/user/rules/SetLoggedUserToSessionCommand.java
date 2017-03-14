package br.com.hefesto.resources.impl.user.rules;

import br.com.hefesto.domain.impl.User;
import br.com.wsbasestructure.dto.FlowContainer;
import br.com.wsbasestructure.dto.Message;
import br.com.wsbasestructure.dto.Result;
import br.com.wsbasestructure.dto.interfaces.IHolder;
import br.com.wsbasestructure.rules.interfaces.ICommand;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Andrew Ribeiro
 */
public class SetLoggedUserToSessionCommand implements ICommand {

    @Override
    public IHolder exe(IHolder holder, FlowContainer flowContainer) {
        User u = (User) holder.getEntities().get(0);

        if (u != null) {
            HttpSession httpSession = flowContainer.getHttprequest().getSession();
            httpSession.setAttribute("user", u);
        } else {
            Message m = new Message();
            m.setError("user failing");
            flowContainer.getResult().setHolder(holder);
            flowContainer.getResult().setStatus(Result.ERROR);
            flowContainer.getResult().setMessage(m);
        }
        
        flowContainer.getFc().setMustContinue(false);
        return holder;
    }

}
