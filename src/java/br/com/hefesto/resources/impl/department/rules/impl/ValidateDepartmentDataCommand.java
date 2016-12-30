package br.com.hefesto.resources.impl.department.rules.impl;

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
public class ValidateDepartmentDataCommand implements ICommand{

    @Override
    public IHolder exe(IHolder holder, FlowContainer flowContainer) {
        Department d = (Department)holder.getEntities().get(0);
        
        Message m = new Message();
        
        String description = d.getDescription();
        String name = d.getName();
        
        if(name == null || name.isEmpty()){
            flowContainer.getResult().setStatus(Result.ERROR);
            m.setError("name empty");
            flowContainer.getFc().setMustContinue(false);
        } else {
            
        }
        
        if(description == null || description.isEmpty()){
            flowContainer.getResult().setStatus(Result.ERROR);
            m.setError("description empty");
            flowContainer.getFc().setMustContinue(false);
        } else {
            
        }
        
        flowContainer.getResult().setMessage(m);
        d.setDescription(description);
        d.setName(name);
        
        return holder;
        
    }
    
}
