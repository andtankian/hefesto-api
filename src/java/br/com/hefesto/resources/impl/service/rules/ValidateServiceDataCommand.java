package br.com.hefesto.resources.impl.service.rules;

import br.com.hefesto.domain.impl.SLA;
import br.com.hefesto.domain.impl.Service;
import br.com.wsbasestructure.dto.FlowContainer;
import br.com.wsbasestructure.dto.Message;
import br.com.wsbasestructure.dto.Result;
import br.com.wsbasestructure.dto.interfaces.IHolder;
import br.com.wsbasestructure.rules.interfaces.ICommand;

/**
 *
 * @author Andrew Ribeiro
 */
public class ValidateServiceDataCommand implements ICommand{

    @Override
    public IHolder exe(IHolder holder, FlowContainer flowContainer) {
        Service s = (Service)holder.getEntities().get(0);
        SLA sla = s.getSla();
        boolean isValid = true;
        Message m = new Message();
        if(s.getDescription() == null || s.getDescription().isEmpty()){
            m.setError("empty description");
            isValid = false;
        } else if(s.getName() == null || s.getName().isEmpty()){
            m.setError("empty name");
            isValid = false;
        } else if((sla.getDays() == null || sla.getDays() == 0)
                && (sla.getHours() == null || sla.getHours() == 0)
                && (sla.getMinutes() == null || sla.getMinutes() == 0)){
            m.setError("invalid sla");
            isValid = false;
        } else if(s.getType() == null || s.getType().isEmpty()){
            m.setError("empty type");
            isValid = false;
        }
        
        if(!isValid){
            flowContainer.getResult().setMessage(m);
            flowContainer.getFc().setMustContinue(false);
            flowContainer.getResult().setHolder(holder);
            flowContainer.getResult().setStatus(Result.ERROR);
        }
        
        return holder;
        
    }
    
}
