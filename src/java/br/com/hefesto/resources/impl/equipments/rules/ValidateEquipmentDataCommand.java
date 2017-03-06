package br.com.hefesto.resources.impl.equipments.rules;

import br.com.hefesto.domain.impl.Equipment;
import br.com.wsbasestructure.dto.FlowContainer;
import br.com.wsbasestructure.dto.Message;
import br.com.wsbasestructure.dto.Result;
import br.com.wsbasestructure.dto.interfaces.IHolder;
import br.com.wsbasestructure.rules.interfaces.ICommand;

/**
 *
 * @author Andrew Ribeiro
 */
public class ValidateEquipmentDataCommand implements ICommand{

    @Override
    public IHolder exe(IHolder holder, FlowContainer flowContainer) {
        Equipment e = (Equipment)holder.getEntities().get(0);
        Message m = new Message();
        boolean isValid = true;
        
        if(e.getDescription() == null || e.getDescription().isEmpty()){
            isValid = false;
            m.setError("empty description");
        } else if(e.getName() == null || e.getName().isEmpty()){
            isValid = false;
            m.setError("empty name");
        } else if(e.getPatrimonial() == null || e.getPatrimonial().isEmpty()){
            isValid = false;
            m.setError("emmpty patrimonial");
        }
        
        if(!isValid){
            flowContainer.getResult().setMessage(m);
            flowContainer.getResult().setStatus(Result.ERROR);
            flowContainer.getResult().setHolder(holder);
            flowContainer.getFc().setMustContinue(false);
        }
        
        return holder;
    }
    
}
