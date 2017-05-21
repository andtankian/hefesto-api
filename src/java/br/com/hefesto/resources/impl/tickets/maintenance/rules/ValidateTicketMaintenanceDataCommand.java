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
public class ValidateTicketMaintenanceDataCommand implements ICommand {

    @Override
    public IHolder exe(IHolder holder, FlowContainer flowContainer) {
        Ticket t = (Ticket) holder.getEntities().get(0);
        Message m = new Message();
        boolean isValid = true;
        Session session = flowContainer.getSession();
        /*Ticket equipment*/
        Equipment e = t.getEquipment();
        t.setEquipment(null);
        User owner = t.getOwner();
        t.setOwner(null);
        User resp = t.getResponsible();
        t.setResponsible(null);
        Set rps = t.getRequestedProducts();
        Service s = t.getService();
        t.setService(null);
        Set interactions = t.getInteractions();

        if (e == null || e.getId() == null || e.getId() == 0) {
            isValid = false;
            m.setError("invalid equipment");
        } else {
            Equipment loadedEq = (Equipment) session.get(Equipment.class, e.getId());
            if (loadedEq == null) {
                m.setError("equipment doesn't exist");
                isValid = false;
            } else {
                t.setEquipment(loadedEq);
            }
        }

        /*Validating ticket owner*/
        if (isValid) {
            if (owner == null || owner.getId() == null || owner.getId() == 0) {
                isValid = false;
                m.setError("invalid owner");
            } else {
                User loadedOwner = (User) session.get(User.class, owner.getId());
                if (loadedOwner == null) {
                    isValid = false;
                    m.setError("owner doesn't exist");
                } else {
                    t.setOwner(loadedOwner);
                }
            }
        }
        /*Validating ticket responsible*/
        if (isValid) {
            if (resp != null && resp.getId() != null && resp.getId() > 0) {
                /*It will have a responsible*/
                User loadedResp;
                try {
                    loadedResp = (User) session.get(User.class, resp.getId());
                } catch (ObjectNotFoundException ofe) {
                    loadedResp = null;
                }

                if (loadedResp == null || (!loadedResp.getType().equals("Técnico") && !loadedResp.getType().equals("Administrador"))) {
                    m.setError("responsible doesn't exist");
                    isValid = false;
                } else {
                    t.setResponsible(loadedResp);
                }
            }
        }

        /*Validating user interaction*/
        if (isValid) {
            for (Object interaction : interactions) {
                Interaction current = (Interaction) interaction;
                User interactionUser = current.getUser();
                if (interactionUser == null || interactionUser.getId() == null || interactionUser.getId() <= 0) {
                    m.setError("invalid interaction user");
                    isValid = false;
                } else {
                    User loadedInUser = (User) session.get(User.class, interactionUser.getId());

                    if (loadedInUser == null) {
                        m.setError("interaction user doesn't exist");
                        isValid = false;
                    } else {
                        current.setUser(loadedInUser);
                    }
                }
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

        /*Validation ticket service*/
        if (isValid) {
            if (s == null || s.getId() == null || s.getId() <= 0) {
                m.setError("invalid service");
                isValid = false;
            } else {
                Service loadedService = (Service) session.get(Service.class, s.getId());
                if (loadedService == null) {
                    m.setError("service doesn't exist");
                    isValid = false;
                } else {
                    t.setService(loadedService);
                }
            }
        }

        if (isValid) {
            if (t.getProblem() == null || t.getProblem().isEmpty()) {
                m.setError("empty problem");
                isValid = false;
            } else if (t.getTitle() == null || t.getTitle().isEmpty()) {
                m.setError("empty title");
                isValid = false;
            }
        }

        if (!isValid) {
            flowContainer.getResult().setStatus(Result.ERROR);
            flowContainer.getResult().setMessage(m);
            flowContainer.getFc().setMustContinue(false);
        }

        return holder;
    }

}