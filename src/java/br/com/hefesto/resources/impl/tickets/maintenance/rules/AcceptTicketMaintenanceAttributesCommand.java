package br.com.hefesto.resources.impl.tickets.maintenance.rules;

import br.com.hefesto.domain.impl.Ticket;
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
public class AcceptTicketMaintenanceAttributesCommand extends AbstractAcceptAttributesCommand{

    public AcceptTicketMaintenanceAttributesCommand(String[] accepts, List rejects) {
        super(accepts, rejects);
    }

    @Override
    public IHolder exe(IHolder holder, FlowContainer flowContainer) {
        if(holder.getEntities() == null || holder.getEntities().isEmpty()){
            return holder;
        }
        Ticket t = (Ticket)holder.getEntities().get(0);
        List<String> toAccept = Arrays.asList(this.accepts);
        if(toAccept.contains("service")){
            t.setService(Utils.initializeAndUnproxy(t.getService()));
        } 
        if(toAccept.contains("equipment")){
            t.setEquipment(Utils.initializeAndUnproxy(t.getEquipment()));
        }
        if(toAccept.contains("responsible")){
            t.setResponsible(Utils.initializeAndUnproxy(t.getResponsible()));
        }
        List<String> allAttributes = new ArrayList<>();
        allAttributes.add("service");
        allAttributes.add("equipment");
        allAttributes.add("responsible");
        allAttributes.add("ticket");
        allAttributes.add("groups");
        
        List<String> missingList = new ArrayList<>(allAttributes);

        missingList.removeAll(toAccept);
        
        this.rejects.addAll(missingList);
        
        return holder;
        
    }
    
}
