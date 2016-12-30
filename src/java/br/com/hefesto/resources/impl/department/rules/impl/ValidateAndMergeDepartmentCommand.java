package br.com.hefesto.resources.impl.department.rules.impl;

import br.com.hefesto.domain.impl.Department;
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
public class ValidateAndMergeDepartmentCommand implements ICommand {

    @Override
    public IHolder exe(IHolder holder, FlowContainer flowContainer) {
        Department d = (Department) holder.getEntities().get(0);
        Department loaded = null;
        Message m = new Message();
        if (d.getId() == null || d.getId() == 0) {
            flowContainer.getResult().setStatus(Result.ERROR);
            m.setError("invalid id");
            flowContainer.getFc().setMustContinue(false);
            flowContainer.getResult().setMessage(m);
        } else {
            Session s = flowContainer.getSession();
            loaded = (Department) s.get(Department.class, d.getId());
            if (loaded != null) {
                loaded.merge(d);
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
