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
public class ValidateBulkEquipmentsCommand implements ICommand{

    @Override
    public IHolder exe(IHolder holder, FlowContainer flowContainer) {
        
        Message m = new Message();
        if(holder.getEntities().isEmpty()){
            m.setError("empty equipments");
            flowContainer.getResult().setMessage(m);
            flowContainer.getResult().setStatus(Result.ERROR);
            flowContainer.getFc().setMustContinue(false);
        } else {
            for (Object entity : holder.getEntities()) {
                Equipment eq = (Equipment)entity;
                eq.setDescription(eq.getDescription().trim().replaceAll("\\s+", " "));
                eq.setName(eq.getName().trim().replaceAll("\\s+", " ").toUpperCase());
                eq.setPatrimonial(eq.getPatrimonial().trim().replaceAll("\\s+", " "));
            }
        }
        
        return holder;
    }
    
}
