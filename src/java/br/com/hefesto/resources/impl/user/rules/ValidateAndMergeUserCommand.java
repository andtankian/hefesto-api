package br.com.hefesto.resources.impl.user.rules;

import br.com.hefesto.domain.impl.Department;
import br.com.hefesto.domain.impl.User;
import br.com.wsbasestructure.dto.FlowContainer;
import br.com.wsbasestructure.dto.Message;
import br.com.wsbasestructure.dto.Result;
import br.com.wsbasestructure.dto.interfaces.IHolder;
import br.com.wsbasestructure.rules.interfaces.ICommand;
import java.util.Set;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author Andrew Ribeiro
 */
public class ValidateAndMergeUserCommand implements ICommand {

    @Override
    public IHolder exe(IHolder holder, FlowContainer flowContainer) {
        User u = (User) holder.getEntities().get(0);
        Message m = new Message();
        Session s = (Session) flowContainer.getSession();
        Department d = u.getDepartment();

        if (d != null && d.getId() != null) {
            try {
                u.setDepartment((Department) s.load(Department.class, d.getId()));
            } catch (ObjectNotFoundException o) {
                m.setError("department id " + d.getId() + " doesn't exist");
                flowContainer.getResult().setMessage(m);
                flowContainer.getResult().setStatus(Result.ERROR);
                flowContainer.getResult().setHolder(holder);
                flowContainer.getFc().setMustContinue(false);
                return holder;
            }
        }

        Set groups = u.getGroups();

       /* if (groups != null) {
            u.setGroups(new HashSet<>());
            for (Object group : groups) {
                String g = (String) group;
                Long longG = (long) -1;
                try {
                    longG = Long.parseLong(g);
                    Groups gp = (Groups) s.get(Groups.class, longG);
                    if (gp == null) {
                        m.setError("group id " + longG + " doesn't exist");
                        flowContainer.getResult().setMessage(m);
                        flowContainer.getResult().setStatus(Result.ERROR);
                        flowContainer.getResult().setHolder(holder);
                        flowContainer.getFc().setMustContinue(false);
                        return holder;
                    }
                    u.getGroups().add(gp);
                } catch (NumberFormatException n) {
                    m.setError("group id " + longG + " invalid");
                    flowContainer.getResult().setMessage(m);
                    flowContainer.getResult().setStatus(Result.ERROR);
                    flowContainer.getResult().setHolder(holder);
                    flowContainer.getFc().setMustContinue(false);
                    return holder;
                }
            }
        }*/
       
       if(u.getPassword() != null && !u.getPassword().isEmpty()){
           u.setPassword(BCrypt.hashpw(u.getPassword(), BCrypt.gensalt()));
       }

        User loaded = (User) s.get(User.class, u.getId());
        if (loaded == null) {
            m.setError("user id " + u.getId() + " doesn't exist");
            flowContainer.getResult().setMessage(m);
            flowContainer.getResult().setStatus(Result.ERROR);
            flowContainer.getResult().setHolder(holder);
            flowContainer.getFc().setMustContinue(false);
            return holder;
        } else {
            loaded.merge(u);
            holder.getEntities().set(0, loaded);
        }

        return holder;
    }

}
