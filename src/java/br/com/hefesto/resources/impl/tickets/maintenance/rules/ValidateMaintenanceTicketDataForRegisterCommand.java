package br.com.hefesto.resources.impl.tickets.maintenance.rules;

import br.com.hefesto.domain.impl.Equipment;
import br.com.hefesto.domain.impl.Interaction;
import br.com.hefesto.domain.impl.Product;
import br.com.hefesto.domain.impl.RequestedProduct;
import br.com.hefesto.domain.impl.Service;
import br.com.hefesto.domain.impl.Ticket;
import br.com.hefesto.domain.impl.User;
import br.com.wsbasestructure.dto.FlowContainer;
import br.com.wsbasestructure.dto.Message;
import br.com.wsbasestructure.dto.Result;
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
        } else if (t.getStatus() == null || t.getStatus().isEmpty()) {
            isValid = false;
            m.setError("empty status");
        } else if (t.getTitle() == null || t.getTitle().isEmpty()) {
            isValid = false;
            m.setError("empty title");
        }

        /*EQUIPMENTS*/
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
        
        /*INTERACTION USER*/
        if(isValid){
            for (Object interaction : t.getInteractions()) {
                Interaction i = (Interaction)interaction;
                if(i.getUser() == null){
                    isValid = false;                    
                    m.setError("invalid interaction user");
                } else {
                    User loadedInteractionUser = (User) s.get(User.class, i.getUser().getId());
                    if(loadedInteractionUser == null){
                        isValid = false;
                        sb.delete(0, sb.length());
                        m.setError(sb.append("interaction user ").append(i.getUser().getId()).append(" doesn't exist").toString());
                    } else {
                        i.setUser(loadedInteractionUser);
                    }
                }
            }
        }

        /*OWNER*/
        if (isValid) {

            if (t.getOwner() == null) {
                isValid = false;
                m.setError("invalid owner");
            } else {
                User loadedOwner = (User) s.get(User.class, t.getOwner().getId());
                if (loadedOwner == null) {
                    isValid = false;
                    sb.delete(0, sb.length());
                    m.setError(sb.append("user ").append(t.getOwner().getId()).append(" doesn't exist").toString());
                } else {
                    t.setOwner(loadedOwner);
                }
            }
        }

        /*REQUESTED PRODUCTS*/
        if (isValid) {
            if (t.getRequestedProducts() != null) {
                for (Object requestedProduct : t.getRequestedProducts()) {
                    RequestedProduct rp = (RequestedProduct) requestedProduct;
                    if (rp.getProduct() == null || rp.getAmount() == null || rp.getAmount().intValue() == 0) {
                        isValid = false;
                        m.setError("inconsistent requested product");
                    } else {
                        Product product = (Product) s.get(Product.class, rp.getProduct().getId());
                        if (product == null) {
                            isValid = false;
                            sb.delete(0, sb.length());
                            m.setError(sb.append("product ").append(rp.getProduct().getId()).append(" doesn't exist").toString());
                        } else {
                            rp.setProduct(product);
                        }
                    }
                }
            }
        }

        /*RESPONSIBLE*/
        if (isValid) {
            if(t.getResponsible() != null){
                User loadedResponsible = (User) s.get(User.class, t.getResponsible().getId());
                if(loadedResponsible == null){
                    isValid = false;
                    sb.delete(0, sb.length());
                    m.setError(sb.append("responsible ").append(t.getResponsible().getId()).append(" doesn't exist").toString());
                } else {
                    t.setResponsible(loadedResponsible);
                }
                
            }
        }
        
        /*SERVICE*/
        if(isValid){
            if(t.getService() != null){
                Service loadedService = (Service) s.get(Service.class, t.getService().getId());
                if(loadedService == null){
                    isValid = false;
                    sb.delete(0, sb.length());
                    m.setError(sb.append("service ").append(t.getService().getId()).append(" doesn't exist").toString());
                } else {
                    t.setService(loadedService);
                }
            }
        }
        
        /*VERIFYING MORE ALTERNATIVES*/
        if(isValid){
            if(t.getStatus().equals(Ticket.CLOSED)){
                sb.delete(0, sb.length());
                for (Object interaction : t.getInteractions()) {
                    Interaction in = (Interaction)interaction;
                    in.setType(Interaction.OPENING_CLOSING_TICKET);
                    sb.append(in.toString()).append("\n");
                }
                t.setResolution(sb.toString());
            }
        }
        
        if(!isValid){
            flowContainer.getResult().setMessage(m);
            flowContainer.getResult().setStatus(Result.ERROR);
            flowContainer.getResult().setHolder(null);
            flowContainer.getFc().setMustContinue(false);
        }

        return holder;
    }

}
