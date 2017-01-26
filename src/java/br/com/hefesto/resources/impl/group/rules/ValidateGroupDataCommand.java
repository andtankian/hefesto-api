package br.com.hefesto.resources.impl.group.rules;

import br.com.hefesto.domain.impl.Groups;
import br.com.hefesto.domain.impl.User;
import br.com.wsbasestructure.dto.FlowContainer;
import br.com.wsbasestructure.dto.Message;
import br.com.wsbasestructure.dto.Result;
import br.com.wsbasestructure.dto.interfaces.IHolder;
import br.com.wsbasestructure.rules.interfaces.ICommand;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.Session;

/**
 *
 * @author Andrew Ribeiro
 */
public class ValidateGroupDataCommand implements ICommand {

    @Override
    public IHolder exe(IHolder holder, FlowContainer flowContainer) {
        Groups p = (Groups) holder.getEntities().get(0);

        String name;
        String description;
        Message m = new Message();

        name = p.getName();
        description = p.getDescription();
        Set users = p.getUsers();
        p.setUsers(new HashSet<>());

        Session s = flowContainer.getSession();

        if (name == null || name.isEmpty()) {
            flowContainer.getFc().setMustContinue(false);
            m.setError("name empty");
            flowContainer.getResult().setStatus(Result.ERROR);
            return holder;
        }

        if (description == null || description.isEmpty()) {
            flowContainer.getFc().setMustContinue(false);
            m.setError("description empty");
            flowContainer.getResult().setStatus(Result.ERROR);
            return holder;
        }

        if (users != null) {
            for (Object user : users) {
                String id = (String) user;
                Long idl;
                try {
                    idl = Long.parseLong(id);
                } catch (NumberFormatException nf) {
                    flowContainer.getFc().setMustContinue(false);
                    m.setError("user id invalid");
                    flowContainer.getResult().setStatus(Result.ERROR);
                    flowContainer.getResult().setMessage(m);
                    flowContainer.getResult().setHolder(holder);
                    return holder;
                }

                User u = (User) s.get(User.class, idl);
                if (u == null) {
                    flowContainer.getFc().setMustContinue(false);
                    m.setError("user doesn't exist");
                    flowContainer.getResult().setStatus(Result.ERROR);
                    flowContainer.getResult().setMessage(m);
                    flowContainer.getResult().setHolder(holder);
                    return holder;
                } else {
                    p.getUsers().add(u);
                }
            }
        }
        flowContainer.getResult().setHolder(holder);
        return holder;
    }

}
