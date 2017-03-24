package br.com.hefesto.resources.impl.user.rules;

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
import org.hibernate.Transaction;

/**
 *
 * @author Andrew Ribeiro
 */
public class GroupsAssociationWhenUpdatingCommand implements ICommand {

    Set shouldKeep;

    public GroupsAssociationWhenUpdatingCommand(Set shouldKeep) {

        this.shouldKeep = shouldKeep;
    }

    @Override
    public IHolder exe(IHolder holder, FlowContainer flowContainer) {
        User u = (User) holder.getEntities().get(0);
        Message m = new Message();
        Session s = flowContainer.getSession();
        Transaction t = null;
        Set permissions = u.getGroups();
        Set skClone;
        if (shouldKeep != null) {
            t = s.getTransaction().isActive() ? s.getTransaction() : s.beginTransaction();
            skClone = new HashSet<>();
            for (Object group : shouldKeep) {
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
                    skClone.add(gp);
                    gp.getUsers().add(u);
                } catch (NumberFormatException n) {
                    m.setError("group id " + longG + " invalid");
                    flowContainer.getResult().setMessage(m);
                    flowContainer.getResult().setStatus(Result.ERROR);
                    flowContainer.getResult().setHolder(holder);
                    flowContainer.getFc().setMustContinue(false);
                    return holder;
                }
            }
            Set missing = new HashSet<>(permissions);
            missing.removeAll(skClone);

            /*
            Firstly, let's extract up the users from original Collection.
             */
            for (Object object : missing) {
                Groups g = (Groups) object;
                for (Object object1 : g.getUsers()) {
                    User us = (User) object1;
                    if(us.getId() == u.getId()){
                        g.getUsers().remove(us);
                    }
                }
            }
            t.commit();
        }
        return holder;
    }

}
