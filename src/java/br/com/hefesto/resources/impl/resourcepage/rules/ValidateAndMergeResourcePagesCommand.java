package br.com.hefesto.resources.impl.resourcepage.rules;

import br.com.hefesto.domain.impl.Permission;
import br.com.hefesto.domain.impl.ResourcePage;
import br.com.wsbasestructure.dto.FlowContainer;
import br.com.wsbasestructure.dto.Message;
import br.com.wsbasestructure.dto.Result;
import br.com.wsbasestructure.dto.interfaces.IHolder;
import br.com.wsbasestructure.rules.interfaces.ICommand;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;

/**
 *
 * @author Andrew Ribeiro
 */
public class ValidateAndMergeResourcePagesCommand implements ICommand {

    @Override
    public IHolder exe(IHolder holder, FlowContainer flowContainer) {
        ResourcePage r = (ResourcePage) holder.getEntities().get(0);
        ResourcePage loaded = null;
        Message m = new Message();
        if (r.getId() == null || r.getId() == 0) {
            m.setError("invalid id");
            flowContainer.getResult().setStatus(Result.ERROR);
            flowContainer.getResult().setMessage(m);
            flowContainer.getFc().setMustContinue(false);
        } else {
            Session s = flowContainer.getSession();
            loaded = (ResourcePage) s.get(ResourcePage.class, r.getId());

            if (loaded == null) {
                m.setError("resource page not found");
                flowContainer.getResult().setStatus(Result.ERROR);
                flowContainer.getResult().setMessage(m);
                flowContainer.getFc().setMustContinue(false);
            } else {
                Set read = r.getRead();
                r.setRead(new HashSet<>());
                Set write = r.getWrite();
                r.setWrite(new HashSet<>());

                /*To associate read permissiont to ResourcePage*/
                Permission p;
                for (Object object : read) {
                    String idPerm = (String) object;
                    Long idLongPerm = null;
                    try {
                        idLongPerm = Long.parseLong(idPerm);
                    } catch (NumberFormatException e) {
                        m.setError(new StringBuilder("permission id ").append(idLongPerm).append(" is invalid").toString());
                        flowContainer.getResult().setStatus(Result.ERROR);
                        flowContainer.getResult().setMessage(m);
                        flowContainer.getFc().setMustContinue(false);
                        return holder;
                    }
                    p = (Permission) s.get(Permission.class, idLongPerm);
                    if (p == null) {
                        m.setError(new StringBuilder("permission id ").append(idLongPerm).append(" doesn't exist").toString());
                        flowContainer.getResult().setStatus(Result.ERROR);
                        flowContainer.getResult().setMessage(m);
                        flowContainer.getFc().setMustContinue(false);
                        return holder;
                    } else {
                        r.getRead().add(p);
                    }
                }

                /*To associate write permissions to ResourcePage*/
                for (Object object : write) {
                    String idPerm = (String) object;
                    Long idLongPerm = null;
                    try {
                        idLongPerm = Long.parseLong(idPerm);
                    } catch (NumberFormatException e) {
                        m.setError(new StringBuilder("permission id ").append(idLongPerm).append(" is invalid").toString());
                        flowContainer.getResult().setStatus(Result.ERROR);
                        flowContainer.getResult().setMessage(m);
                        flowContainer.getFc().setMustContinue(false);
                        return holder;
                    }

                    p = (Permission) s.get(Permission.class, idLongPerm);
                    if (p == null) {
                        m.setError(new StringBuilder("permission id ").append(idLongPerm).append(" doesn't exist").toString());
                        flowContainer.getResult().setStatus(Result.ERROR);
                        flowContainer.getResult().setMessage(m);
                        flowContainer.getFc().setMustContinue(false);
                        return holder;
                    } else {
                        r.getWrite().add(p);
                    }

                }

                loaded.merge(r);
                holder.getEntities().set(0, loaded);
            }
        }

        flowContainer.getResult()
                .setHolder(holder);
        return holder;
    }

}
