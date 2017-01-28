package br.com.hefesto.resources.impl.user.rules;

import br.com.hefesto.domain.impl.Groups;
import br.com.hefesto.domain.impl.User;
import br.com.wsbasestructure.dto.FlowContainer;
import br.com.wsbasestructure.dto.Message;
import br.com.wsbasestructure.dto.Result;
import br.com.wsbasestructure.dto.interfaces.IHolder;
import br.com.wsbasestructure.rules.interfaces.ICommand;
import java.util.Set;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Andrew Ribeiro
 */
public class PermissionAssociationsPersistenceHelperCommand implements ICommand {

    @Override
    public IHolder exe(IHolder holder, FlowContainer flowContainer) {
        User u = (User) holder.getEntities().get(0);
        Message m = new Message();
        Set permissions = u.getGroups();
        if (permissions == null || permissions.isEmpty()) {
            m.setError("permissions empty");
            flowContainer.getResult().setMessage(m);
            flowContainer.getResult().setStatus(Result.ERROR);
        } else {
            Session s = flowContainer.getSession();
            Transaction t = s.beginTransaction();

            for (Object permission : u.getGroups()) {
                ((Groups)permission).getUsers().add(u);
            }
            t.commit();
        }

        return holder;
    }

}
