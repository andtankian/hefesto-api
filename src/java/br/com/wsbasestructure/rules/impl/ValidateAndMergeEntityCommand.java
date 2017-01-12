package br.com.wsbasestructure.rules.impl;

import br.com.wsbasestructure.domain.abstracts.Entity;
import br.com.wsbasestructure.dto.FlowContainer;
import br.com.wsbasestructure.dto.Message;
import br.com.wsbasestructure.dto.Result;
import br.com.wsbasestructure.dto.interfaces.IHolder;
import br.com.wsbasestructure.rules.interfaces.ICommand;
import org.hibernate.Session;

/**
 *
 * @author Andrew Ribeiro
 */
public class ValidateAndMergeEntityCommand implements ICommand {

    @Override
    public IHolder exe(IHolder holder, FlowContainer flowContainer) {
        Entity e = (Entity)holder.getEntities().get(0);
        Entity loaded = null;
        Message m = new Message();
        if (e.getId() == null || e.getId() == 0) {
            flowContainer.getResult().setStatus(Result.ERROR);
            m.setError("invalid id");
            flowContainer.getFc().setMustContinue(false);
            flowContainer.getResult().setMessage(m);
        } else {
            Session s = flowContainer.getSession();
            loaded = (Entity) s.get(e.getClass(), e.getId());
            if (loaded != null) {
                loaded.merge(e);
            } else {
                m.setError("not found");
                flowContainer.getResult().setStatus(Result.ERROR);
                flowContainer.getResult().setMessage(m);
                flowContainer.getFc().setMustContinue(false);
            }
        }

        holder.getEntities().set(0, loaded);
        flowContainer.getResult().setHolder(holder);

        return holder;
    }

}
