package br.com.hefesto.resources.impl.tickets.maintenance.rules;

import br.com.hefesto.domain.impl.Equipment;
import br.com.hefesto.domain.impl.Ticket;
import br.com.hefesto.domain.impl.User;
import br.com.wsbasestructure.dto.FlowContainer;
import br.com.wsbasestructure.dto.Message;
import br.com.wsbasestructure.dto.interfaces.IHolder;
import br.com.wsbasestructure.rules.interfaces.ICommand;
import org.hibernate.Session;

/**
 *
 * @author Andrew
 */
public class ValidateMaintenanceTicketDataForRegisterCommand implements ICommand {

    @Override
    public IHolder exe(IHolder holder, FlowContainer flowContainer) {
        Ticket t = (Ticket) holder.getEntities().get(0);

        Message m = new Message();
        boolean isValid = true;
        Session s = flowContainer.getSession();
        StringBuilder sb = new StringBuilder();
        /*Strings*/
        if (t.getDescription() == null || t.getDescription().isEmpty()) {
            isValid = false;
            m.setError("empty description");
        } else if (t.getPriority() == null || t.getPriority().isEmpty()) {
            isValid = false;
            m.setError("empty priority");
        } else if(t.getStatus() == null || t.getStatus().isEmpty()){
            isValid = false;
            m.setError("empty status");
        } else if(t.getTitle() == null || t.getTitle().isEmpty()){
            isValid = false;
            m.setError("empty title");
        }

        if (isValid) {

            if (t.getEquipment() == null) {
                isValid = false;
                m.setError("invalid equipment");
            } else {
                Equipment loadedEquipment = (Equipment) s.get(Equipment.class, t.getEquipment().getId());
                if (loadedEquipment == null) {
                    isValid = false;
                    m.setError(sb.append("equipment ").append(t.getEquipment().getId()).append(" doesn't exist").toString());
                } else {
                    t.setEquipment(loadedEquipment);
                }
            }
        }

        if (isValid) {

            if (t.getOwner() == null) {
                isValid = false;
                m.setError("invalid owner");
            } else {
                User loadedOwner = (User) s.get(User.class, t.getOwner().getId());
                if (loadedOwner == null) {
                    isValid = false;
                    sb.delete(0, sb.length() - 1);
                    m.setError(sb.append("user ").append(t.getOwner().getId()).append(" doesn't exist").toString());
                } else {
                    t.setOwner(loadedOwner);
                }
            }
        }

        return holder;
    }

}
