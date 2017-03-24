package br.com.hefesto.resources.impl.product.rules;

import br.com.hefesto.domain.impl.Product;
import br.com.wsbasestructure.dto.FlowContainer;
import br.com.wsbasestructure.dto.Message;
import br.com.wsbasestructure.dto.Result;
import br.com.wsbasestructure.dto.interfaces.IHolder;
import br.com.wsbasestructure.rules.interfaces.ICommand;

/**
 *
 * @author Andrew Ribeiro
 */
public class ValidateProductDataCommand implements ICommand {
    
    @Override
    public IHolder exe(IHolder holder, FlowContainer flowContainer) {
        Product p = (Product) holder.getEntities().get(0);
        Message m = new Message();
        boolean isValid = true;
        if (p.getDescription() == null
                || p.getDescription().isEmpty()) {
            isValid = false;
            m.setError("empty description");
        } else if(p.getLink() == null ||
                p.getLink().isEmpty()){
            isValid = false;
            m.setError("empty link");
        } else if(p.getName() == null ||
                p.getName().isEmpty()){
            isValid = false;
            m.setError("empty name");
        } else if(p.getType() == null ||
                p.getType().isEmpty()){
            isValid = false;
            m.setError("empty type");
        }
        
        /*
        Just verify variable isValid, if not valid interrupt the main control
        */
        if(!isValid){
            flowContainer.getResult().setStatus(Result.ERROR);
            flowContainer.getResult().setMessage(m);
            flowContainer.getResult().setHolder(holder);
            flowContainer.getFc().setMustContinue(false);
        }
        
        return holder;
    }
    
}
