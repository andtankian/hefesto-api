package br.com.hefesto.resources.impl.rules;

import br.com.wsbasestructure.dto.FlowContainer;
import br.com.wsbasestructure.dto.interfaces.IHolder;
import br.com.wsbasestructure.rules.interfaces.AbstractAcceptAttributesCommand;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Andrew Ribeiro
 */
public class GenericAcceptAttributes extends AbstractAcceptAttributesCommand{

    public GenericAcceptAttributes(String[] accepts, List rejects, String[] all) {
        super(accepts, rejects);
        this.all = all;
    }
    
    protected String[] all;
    

    @Override
    public IHolder exe(IHolder holder, FlowContainer flowContainer) {
        List p = holder.getEntities();

        if (p == null || p.isEmpty()) {
            return holder;
        }
        
        if(all == null || all.length == 0){
            return holder;
        }
        
        List<String> toAccept = Arrays.asList(this.accepts);
        List<String> allAttributes = Arrays.asList(all);
        List<String> missingList = new ArrayList<>(allAttributes);

        missingList.removeAll(toAccept);
        
        this.rejects.addAll(missingList);
        
        

        return holder;
    }
    
}
