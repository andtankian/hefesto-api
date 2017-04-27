package br.com.hefesto.resources.impl.user.rules;

import br.com.hefesto.domain.impl.User;
import br.com.wsbasestructure.dto.FlowContainer;
import br.com.wsbasestructure.dto.interfaces.IHolder;
import br.com.wsbasestructure.rules.interfaces.AbstractAcceptAttributesCommand;
import br.com.wsbasestructure.utils.Utils;
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
        User u = (User) holder.getEntities().get(0);
        if (p == null || p.isEmpty()) {
            return holder;
        }
        
        List<String> toAccept = Arrays.asList(this.accepts);
        if(toAccept.contains("department")){
            u.setDepartment(Utils.initializeAndUnproxy(u.getDepartment()));
        }
        List<String> allAttributes = new ArrayList<>();
        allAttributes.add("department");
        allAttributes.add("groups");
        allAttributes.add("users");
        allAttributes.add("userVisual");
        List<String> missingList = new ArrayList<>(allAttributes);

        missingList.removeAll(toAccept);
        
        this.rejects.addAll(missingList);
        
        return holder;
        
    }
    
}
