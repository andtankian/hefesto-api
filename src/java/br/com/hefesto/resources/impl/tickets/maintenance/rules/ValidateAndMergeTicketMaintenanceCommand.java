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
import java.util.Set;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;

/**
 *
 * @author Andrew Ribeiro
 */
public class ValidateAndMergeTicketMaintenanceCommand implements ICommand {

    @Override
    public IHolder exe(IHolder holder, FlowContainer flowContainer) {
        Ticket t = (Ticket) holder.getEntities().get(0);
        Set rps = t.getRequestedProducts();
        Message m = new Message();
        Session session = flowContainer.getSession();
        boolean isValid = true;

        Ticket loaded = null;
        /*TICKET ITSELF*/
        if (t.getId() == null || t.getId() <= 0) {
            isValid = false;
            m.setError("invalid ticket id");
        } else {
            loaded = (Ticket) session.get(Ticket.class, t.getId());
            if (loaded == null) {
                isValid = false;
                m.setError("ticket doesn't exist");
            }
        }

        if (isValid) {
            /*EQUIPMENT*/
            if (t.getEquipment().getId() != null && t.getEquipment().getId() > 0) {
                t.setEquipment((Equipment) flowContainer.getSession().get(Equipment.class, t.getEquipment().getId()));
                if (t.getEquipment() == null) {
                    m.setError("equipment doesn't exist");
                    isValid = false;
                }
            } else {
                t.setEquipment(null);
            }
        }

        /*STRINGS*/
        if (isValid) {
            if (t.getPriority() != null && t.getPriority().isEmpty()) {
                isValid = false;
                m.setError("empty priority");
            } else if (t.getProblem() != null && t.getProblem().isEmpty()) {
                isValid = false;
                m.setError("empty problem");
            } else if (t.getResolution() != null && t.getResolution().isEmpty()) {
                isValid = false;
                m.setError("empty resolution");
            } else if (t.getStatus() != null && t.getStatus().isEmpty()) {
                isValid = false;
                m.setError("empty status");
            } else if (t.getTitle() != null && t.getTitle().isEmpty()) {
                isValid = false;
                m.setError("empty priority");
            } else if (t.getType() != null && t.getType().isEmpty()) {
                isValid = false;
                m.setError("empty type");
            }
        }

        /*RESPONSIBLE*/
        if (isValid) {
            if (t.getResponsible().getId() != null && t.getResponsible().getId() > 0) {
                t.setResponsible((User) session.get(User.class, t.getResponsible().getId()));
                if (t.getResponsible() == null) {
                    isValid = false;
                    m.setError("responsible doesn't exist");
                }
            } else {
                t.setResponsible(null);
            }
        }

        /*SERVICE*/
        if (isValid) {
            if (t.getService().getId() != null && t.getService().getId() > 0) {
                t.setService((Service) session.get(Service.class, t.getService().getId()));
                if (t.getService() == null) {
                    isValid = false;
                    m.setError("service doesn't exist");
                }
            } else {
                t.setService(null);
            }
        }
        
        /*Validating each product of requested product*/
        if (isValid) {
            if (rps != null && !rps.isEmpty()) {
                for (Object rp : rps) {
                    RequestedProduct current = (RequestedProduct) rp;
                    Product p = new Product();
                    p.setId(current.getProduct().getId());
                    if (p.getId() == null || p.getId() <= 0) {
                        m.setError("invalid product");
                        isValid = false;
                        break;
                    } else {
                        Product loadedProduct;
                        try {
                            loadedProduct = (Product) session.get(Product.class, p.getId());
                        } catch (ObjectNotFoundException one) {
                            loadedProduct = null;
                        }
                        if (loadedProduct == null) {
                            m.setError(new StringBuilder("product ").append(p.getId()).append(" doesn't exist").toString());
                            isValid = false;
                            break;
                        } else {
                            current.setProduct(loadedProduct);
                            current.setTicket(loaded);
                        }
                    }

                    if (current.getAmount() == null || current.getAmount() == 0) {
                        m.setError("invalid amount");
                        isValid = false;
                        break;
                    }
                }
            } else if (rps == null) {
                m.setError("inconsistency in requested products");
                isValid = false;
            }
        }
        
        /*INACTIVATING ORPHAN REQUESTED PRODUCTS*/
        
        for (Object requestedProduct : loaded.getRequestedProducts()) {
            RequestedProduct current = (RequestedProduct)requestedProduct;
            current.setTicket(null);
        }
        
        /*INTERACTIONS*/
        for (Object interaction : t.getInteractions()) {
            Interaction in = (Interaction)interaction;
            User loadedInteractionUser = in.getUser();
            if(loadedInteractionUser.getId() == null || loadedInteractionUser.getId() <= 0){
                isValid = false;
                m.setError("interaction user invalid");
                break;
            } else {
                loadedInteractionUser = (User) session.get(User.class, loadedInteractionUser.getId());
                if(loadedInteractionUser == null){
                    isValid = false;
                    m.setError("interaction user doens't exist");
                    break;
                }
            }
            
            in.setUser(loadedInteractionUser);
            in.setTicket(loaded);
            loaded.getInteractions().add(in);
        }
        
        t.setInteractions(null);
        
        
        
        if(!isValid){
            flowContainer.getResult().setMessage(m);
            flowContainer.getResult().setStatus(Result.ERROR);
            flowContainer.getFc().setMustContinue(false);
        } else {
            loaded.merge(t);
            holder.getEntities().set(0, loaded);
        }
        
        return holder;
    }

}
