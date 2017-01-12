package br.com.hefesto.resources.impl.permission.rules;

import br.com.hefesto.domain.impl.Permission;
import br.com.hefesto.domain.impl.User;
import br.com.wsbasestructure.dto.FlowContainer;
import br.com.wsbasestructure.dto.Message;
import br.com.wsbasestructure.dto.Result;
import br.com.wsbasestructure.dto.interfaces.IHolder;
import br.com.wsbasestructure.rules.interfaces.ICommand;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author Andrew Ribeiro
 */
public class ValidatePermissionDataCommand implements ICommand {

    @Override
    public IHolder exe(IHolder holder, FlowContainer flowContainer) {
        Permission p = (Permission) holder.getEntities().get(0);

        String name;
        String description;
        Message m = new Message();

        name = p.getName();
        description = p.getDescription();
        List users = p.getUsers();
        p.setUsers(new ArrayList<>());
        
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
                    return holder;
                }
                
                User u = (User)s.get(User.class, (Long)idl);
                if(u == null){
                    flowContainer.getFc().setMustContinue(false);
                    m.setError("user doesn't exist");
                    flowContainer.getResult().setStatus(Result.ERROR);
                    return holder;
                } else {
                    p.getUsers().add(u);
                }
            }
        }

        return holder;
    }

}
