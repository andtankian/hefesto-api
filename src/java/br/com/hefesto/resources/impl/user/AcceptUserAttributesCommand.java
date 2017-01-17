package br.com.hefesto.resources.impl.user;

import br.com.wsbasestructure.dto.FlowContainer;
import br.com.wsbasestructure.dto.interfaces.IHolder;
import br.com.wsbasestructure.rules.interfaces.AbstractAcceptAttributesCommand;
import br.com.wsbasestructure.rules.interfaces.ICommand;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Andrew Ribeiro
 */
public class AcceptUserAttributesCommand extends AbstractAcceptAttributesCommand{

    public AcceptUserAttributesCommand(String[] accepts, List rejects) {
        super(accepts, rejects);
    }

    @Override
    public IHolder exe(IHolder holder, FlowContainer flowContainer) {
        List p = holder.getEntities();

        if (p == null || p.isEmpty()) {
            return holder;
        }
        
        List<String> toAccept = Arrays.asList(this.accepts);
        List<String> allAttributes = new ArrayList<>();
        allAttributes.add("department");
        allAttributes.add("permissions");
        List<String> missingList = new ArrayList<>(allAttributes);

        missingList.removeAll(toAccept);
        
        this.rejects.addAll(missingList);
        
        return holder;
        
    }
    
}
