package br.com.hefesto.resources.impl.rules;

import br.com.wsbasestructure.dto.FlowContainer;
import br.com.wsbasestructure.dto.interfaces.IHolder;
import br.com.wsbasestructure.rules.interfaces.ICommand;

/**
 *
 * @author Andrew Ribeiro
 */
public class ValidateSearchEntitiesCommand implements ICommand{

    @Override
    public IHolder exe(IHolder holder, FlowContainer flowContainer) {
        if(holder.getSm().getSearch() == null ||
                holder.getSm().getSearch().isEmpty()){
            holder.getSm().setEntity(null);
        }
        
        return holder;
    }
    
}
