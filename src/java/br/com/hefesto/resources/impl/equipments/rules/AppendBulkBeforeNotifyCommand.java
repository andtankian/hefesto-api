package br.com.hefesto.resources.impl.equipments.rules;

import br.com.wsbasestructure.dto.FlowContainer;
import br.com.wsbasestructure.dto.interfaces.IHolder;
import br.com.wsbasestructure.rules.interfaces.ICommand;

/**
 *
 * @author Andrew Ribeiro
 */
public class AppendBulkBeforeNotifyCommand implements ICommand{

    @Override
    public IHolder exe(IHolder holder, FlowContainer flowContainer) {
        
        flowContainer.getCr().setMethod(flowContainer.getCr().getMethod() + "bulk");
        
        return holder;
    }
    
}
