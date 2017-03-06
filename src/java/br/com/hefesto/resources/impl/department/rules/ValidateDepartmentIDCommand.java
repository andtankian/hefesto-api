package br.com.hefesto.resources.impl.department.rules;

import br.com.hefesto.domain.impl.Department;
import br.com.wsbasestructure.dto.FlowContainer;
import br.com.wsbasestructure.dto.Message;
import br.com.wsbasestructure.dto.Result;
import br.com.wsbasestructure.dto.interfaces.IHolder;
import br.com.wsbasestructure.rules.interfaces.ICommand;

/**
 *
 * @author Andrew Ribeiro
 */
public class ValidateDepartmentIDCommand implements ICommand {

    @Override
    public IHolder exe(IHolder holder, FlowContainer flowContainer) {
        Department d = (Department)holder.getSm().getEntity();
        Message m = new Message();
        Long id = d.getId();
        
        if(id == null || id == 0){
            m.setError("invalid id");
            flowContainer.getResult().setMessage(m);
            flowContainer.getResult().setStatus(Result.ERROR);
            flowContainer.getFc().setMustContinue(false);
        }
        
        flowContainer.getResult().setHolder(holder);
        
        return holder;
    }

}
