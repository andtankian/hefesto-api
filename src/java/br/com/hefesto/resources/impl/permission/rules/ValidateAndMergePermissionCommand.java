package br.com.hefesto.resources.impl.permission.rules;

import br.com.hefesto.domain.impl.Permission;
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
public class ValidateAndMergePermissionCommand implements ICommand {

    @Override
    public IHolder exe(IHolder holder, FlowContainer flowContainer) {
        Permission p = (Permission) holder.getEntities().get(0);
        Session s = flowContainer.getSession();
        Permission loaded = null;
        Message m = new Message();
        if (p.getId() == null || p.getId() == 0) {
            flowContainer.getResult().setStatus(Result.ERROR);
            m.setError("invalid id");
            flowContainer.getFc().setMustContinue(false);
            flowContainer.getResult().setMessage(m);
            flowContainer.getResult().setHolder(holder);
            return holder;
        } else {

            loaded = (Permission) s.get(Permission.class, p.getId());

            if (loaded == null) {
                flowContainer.getResult().setStatus(Result.ERROR);
                m.setError("permission doesn't exist");
                flowContainer.getFc().setMustContinue(false);
                flowContainer.getResult().setMessage(m);
                flowContainer.getResult().setHolder(holder);
                return holder;
            }

            /*Let's validate the users updated*/
            Set users = p.getUsers();
            p.setUsers(null);
            if (users != null) {
                p.setUsers(new HashSet<>());
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

                    User u = (User) s.get(User.class, (Long) idl);
                    if (u == null) {
                        flowContainer.getFc().setMustContinue(false);
                        p.setUsers(null);
                        m.setError(new StringBuilder().append("user ").append(idl).append(" doesn't exist").toString());
                        flowContainer.getResult().setStatus(Result.ERROR);
                        flowContainer.getResult().setMessage(m);
                        flowContainer.getResult().setHolder(holder);
                        return holder;
                    } else {
                        p.getUsers().add(u);
                    }
                }
            }

            loaded.merge(p);
            holder.getEntities().set(0, loaded);
        }

        flowContainer.getResult().setHolder(holder);
        return holder;
    }

}
