package br.com.hefesto.resources.impl.tickets.maintenance.rules;

import br.com.hefesto.domain.impl.Equipment;
import br.com.hefesto.domain.impl.Interaction;
import br.com.hefesto.domain.impl.Product;
import br.com.hefesto.domain.impl.RequestedProduct;
import br.com.hefesto.domain.impl.Service;
import br.com.hefesto.domain.impl.Ticket;
import br.com.hefesto.domain.impl.User;
import br.com.hefesto.resources.impl.tickets.maintenance.dao.UpdatingMaintenanceTicketHolder;
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
public class ValidateAndMergeMaintenanceTicketCommand implements ICommand {

    @Override
    public IHolder exe(IHolder holder, FlowContainer flowContainer) {
        Ticket t = (Ticket) holder.getEntities().get(0);
        UpdatingMaintenanceTicketHolder up = (UpdatingMaintenanceTicketHolder) holder;

        boolean isValid = true;
        Message m = new Message();

        Session s = flowContainer.getSession();

        /*RELATED TO EQUIPMENT*/
        if (isValid) {
            if (t.getEquipment() != null) {
                t.setEquipment((Equipment) s.get(Equipment.class, t.getEquipment().getId()));
                if (t.getEquipment() == null) {
                    isValid = false;
                    m.setError("equipment doesn't exist");
                }
            } else if (up.getChangedEquipment() != null && !up.getChangedEquipment().isEmpty()) {
                /*IF THERE WAS CHANGE IN EQUIPMENT AND IT'S NULL
                USER EXPLICITELY REMOVED EQUIPMENT FROM TICKET*/
                t.setEquipment(new Equipment());
                t.getEquipment().setId((long) -1);
            }
        }

        /*RELATED TO OWNER*/
        if (isValid) {
            if (t.getOwner() != null) {
                t.setOwner((User) s.get(User.class, t.getOwner().getId()));
                if (t.getOwner() == null) {
                    isValid = false;
                    m.setError("owner doesn't exist");
                }
            }
        }

        /*RELATED TO RESPONSIBLES*/
        if (isValid) {
            if (t.getResponsible() != null) {
                t.setResponsible((User) s.get(User.class, t.getResponsible().getId()));
                if (t.getResponsible() == null) {
                    isValid = false;
                    m.setError("responsible doesn't exist");
                }
            }
        }

        /*RELATED TO SERVICE*/
        if (isValid) {
            if (t.getService() != null) {
                t.setService((Service) s.get(Service.class, t.getService().getId()));
                if (t.getService() == null) {
                    isValid = false;
                    m.setError("service doesn't exist");
                }
            }
        }

        /*RELATED TO TICKET ITSELF*/
        Ticket loadedTicket = (Ticket) s.get(Ticket.class, t.getId());
        if (loadedTicket == null) {
            isValid = false;
            m.setError("ticket doesn't exist");
        }

        /*RELATED TO REQUESTED PRODUCTS*/
        if (isValid) {
            if (t.getRequestedProducts() != null) {

                for (Object requestedProduct : t.getRequestedProducts()) {
                    RequestedProduct rp = (RequestedProduct) requestedProduct;
                    rp.setTicket(loadedTicket);
                    /*ANALISING PRODUCT ID*/
                    if (rp.getProduct() != null) {
                        rp.setProduct((Product) s.get(Product.class, rp.getProduct().getId()));
                        if (rp.getProduct() == null) {
                            isValid = false;
                            m.setError("product doesn't exist");
                        }
                    }

                    if (isValid) {
                        /*VERIFY IF IT EXISTS*/
                        if (rp.getId() != null) {
                            RequestedProduct loadedRp = (RequestedProduct) s.get(RequestedProduct.class, rp.getId());
                            loadedRp.setTicket(loadedTicket);
                            if (loadedRp == null) {
                                isValid = false;
                                m.setError("requested product failed recovering object");
                            } else {
                                loadedRp.merge(rp);
                            }
                        }
                    }
                }
            }
        }
        /*RELATED TO TICKET INTERACTIONS AND STRUCTURAL CHANGES*/
        if (isValid) {
            StringBuilder sb = new StringBuilder();
            for (Object interaction : t.getInteractions()) {
                Interaction in = (Interaction) interaction;
                in.setTicket(loadedTicket);

                if (in.getUser() == null) {
                    isValid = false;
                    m.setError("interaction user invalid");
                    break;
                } else {
                    in.setUser((User) s.get(User.class, in.getUser().getId()));
                    if (in.getUser() == null) {
                        isValid = false;
                        m.setError("interaction user doesn't exist");
                        break;
                    }
                }
                sb.delete(0, sb.length());
                /*CHANGED DESCRIPTION*/
                if (up.getChangedDescription() != null && !up.getChangedDescription().isEmpty()) {
                    sb.append("\nDescrição alterada de \"")
                            .append(loadedTicket.getDescription())
                            .append("\" para \"")
                            .append(t.getDescription())
                            .append("\".");
                    in.setTicketChanges(sb.toString().replace("null", ""));
                }
                sb.delete(0, sb.length());
                /*CHANGED EQUIPMENT*/
                if (up.getChangedEquipment() != null && !up.getChangedEquipment().isEmpty()) {
                    sb.append(in.getTicketChanges());
                    sb.append("\nEquipamento alterado de \"");
                    if (loadedTicket.getEquipment() != null) {
                        sb.append(loadedTicket.getEquipment().getName());
                    } else {
                        sb.append("nenhum");
                    }
                    sb.append("\" para \"");

                    if (t.getEquipment() != null) {
                        sb.append(t.getEquipment().getName());
                    } else {
                        sb.append("nenhum");
                    }

                    sb.append("\".");
                    in.setTicketChanges(sb.toString().replace("null", ""));
                }
                sb.delete(0, sb.length());
                /*CHANGED OWNER*/
                if (up.getChangedOwner() != null && !up.getChangedOwner().isEmpty()) {
                    sb.append("\nSolicitante alterado de \"")
                            .append(loadedTicket.getOwner().getFullName())
                            .append("\" para \"")
                            .append(t.getOwner().getFullName()).append("\".");

                    in.setTicketChanges(in.getTicketChanges() + sb.toString().replace("null", ""));
                }

                sb.delete(0, sb.length());
                /*CHANGED PRIORITY*/
                if (up.getChangedPriority() != null && !up.getChangedPriority().isEmpty()) {
                    sb.append(in.getTicketChanges());
                    sb.append("\nPrioridade alterada de \"")
                            .append(loadedTicket.getPriority())
                            .append("\" para \"")
                            .append(t.getPriority())
                            .append("\".");
                    in.setTicketChanges(sb.toString().replace("null", ""));
                }

                sb.delete(0, sb.length());
                /*CHANGED RESPONSIBLE*/
                if (up.getChangedResponsible() != null && !up.getChangedResponsible().isEmpty()) {
                    sb.append(in.getTicketChanges());
                    sb.append("\nResponsável alterado de \"");

                    if (loadedTicket.getResponsible() != null) {
                        sb.append(loadedTicket.getResponsible().getFullName());
                    } else {
                        sb.append("nenhum");
                    }

                    sb.append("\" para \"");

                    if (t.getResponsible() != null) {
                        sb.append(t.getResponsible().getFullName());
                    } else {
                        sb.append("nenhum");
                    }

                    sb.append("\".");
                    in.setTicketChanges(sb.toString().replace("null", ""));
                }

                sb.delete(0, sb.length());
                /*CHANGED SERVICE*/
                if (up.getChangedService() != null && !up.getChangedService().isEmpty()) {
                    sb.append(in.getTicketChanges());

                    sb.append("\nServiço alterado de \"");

                    if (loadedTicket.getService() != null) {
                        sb.append(loadedTicket.getService().getName());
                    } else {
                        sb.append("nenhum");
                    }

                    sb.append("\" para \"");

                    if (t.getService() != null) {
                        sb.append(t.getService().getName());
                    } else {
                        sb.append("nenhum");
                    }

                    sb.append("\".");

                    in.setTicketChanges(sb.toString().replace("null", ""));
                }

                sb.delete(0, sb.length());

                /*CHANGED TITLE*/
                if (up.getChangedTitle() != null && !up.getChangedTitle().isEmpty()) {
                    sb.append(in.getTicketChanges());

                    sb.append("\nTítulo alterado de \"")
                            .append(loadedTicket.getTitle())
                            .append("\" para \"")
                            .append(t.getTitle()).append("\".");

                    in.setTicketChanges(sb.toString().replace("null", "").trim());
                }
            }
        }

        /*VALIDATING TO SEE IF ITS A CLOSING AFTER AN EDIT*/
        if (isValid) {
            if (t.getTypeOfClosing() != null && !t.getTypeOfClosing().isEmpty()) {
                /*ITS A CLOSING TICKET, LETS JOIN ALL UPDATES*/
                StringBuilder sb = new StringBuilder();
                int tempIndex = 1;
                for (Object interaction : t.getInteractions()) {
                    Interaction in = (Interaction) interaction;
                    if (in.getStringUpdate() != null && !in.getStringUpdate().isEmpty()) {
                        sb.append("[").append(tempIndex).append("]: ")
                                .append(in.getStringUpdate()).append(" .\n");
                        tempIndex++;
                    }
                }

                for (Object interaction : loadedTicket.getInteractions()) {
                    Interaction in = (Interaction) interaction;
                    if (in.getStringUpdate() != null && !in.getStringUpdate().isEmpty()) {
                        sb.append("[").append(tempIndex).append("]: ")
                                .append(in.getStringUpdate()).append(" .\n");
                        tempIndex++;
                    }
                }
                t.setResolution(sb.toString());

            }
        }

        if (isValid) {
            loadedTicket.merge(t);
            up.getEntities().set(0, loadedTicket);
        } else {
            flowContainer.getResult().setMessage(m);
            flowContainer.getResult().setStatus(Result.ERROR);
            flowContainer.getFc().setMustContinue(false);
        }

        flowContainer.getResult().setHolder(up);

        return up;
    }

}
