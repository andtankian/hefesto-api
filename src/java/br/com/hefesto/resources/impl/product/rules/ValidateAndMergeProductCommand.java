package br.com.hefesto.resources.impl.product.rules;

import br.com.hefesto.domain.impl.Product;
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
public class ValidateAndMergeProductCommand implements ICommand {

    @Override
    public IHolder exe(IHolder holder, FlowContainer flowContainer) {
        Product p = (Product) holder.getEntities().get(0);
        Product loaded = null;
        Session s = flowContainer.getSession();
        Message m = new Message();
        boolean isValid = true;
        Long id = p.getId();
        if(id == null || id == 0){
            m.setError("product id invalid");
            isValid = false;
        } else {
           loaded = (Product) s.get(p.getClass(), id);
           
           if(loaded == null){
               m.setError("product doesn't exist");
               isValid = false;
           } else {
               loaded.merge(p);
           }
        }
        
        if(!isValid){
            flowContainer.getResult().setStatus(Result.ERROR);
            flowContainer.getResult().setMessage(m);
            flowContainer.getFc().setMustContinue(false);
            flowContainer.getResult().setHolder(holder);
        }
        
        return holder;
    }

}
