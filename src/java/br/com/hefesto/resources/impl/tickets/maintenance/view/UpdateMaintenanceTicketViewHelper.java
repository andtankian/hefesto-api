package br.com.hefesto.resources.impl.tickets.maintenance.view;

import br.com.hefesto.domain.impl.Equipment;
import br.com.hefesto.domain.impl.Interaction;
import br.com.hefesto.domain.impl.Product;
import br.com.hefesto.domain.impl.RequestedProduct;
import br.com.hefesto.domain.impl.Service;
import br.com.hefesto.domain.impl.Ticket;
import br.com.hefesto.domain.impl.User;
import br.com.hefesto.resources.impl.rules.NotifyContentCommand;
import br.com.hefesto.resources.impl.tickets.maintenance.dao.UpdatingMaintenanceTicketHolder;
import br.com.hefesto.resources.impl.tickets.maintenance.rules.AcceptTicketMaintenanceAttributesCommand;
import br.com.hefesto.resources.impl.tickets.maintenance.rules.ValidateAndMergeMaintenanceTicketCommand;
import br.com.wsbasestructure.dto.FlowContainer;
import br.com.wsbasestructure.dto.impl.GenericHolder;
import br.com.wsbasestructure.dto.interfaces.IHolder;
import br.com.wsbasestructure.rules.impl.ValidateIDEntityCommand;
import br.com.wsbasestructure.view.abstracts.AbstractViewHelper;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author Andrew
 */
public class UpdateMaintenanceTicketViewHelper extends AbstractViewHelper {

    @Override
    public IHolder getView(FlowContainer fc) {
        super.getView(fc);

        GenericHolder gh = new UpdatingMaintenanceTicketHolder();
        Ticket t = new Ticket();
        t.setType(Ticket.MAINTENANCE);
        Form f = fc.getCr().readEntity(Form.class);
        MultivaluedHashMap mvhm = (MultivaluedHashMap) f.asMap();
        UriInfo uri = fc.getCr().getUriInfo();
        MultivaluedMap<String, String> mvm = uri.getPathParameters();

        try {
            t.setDescription((String) mvhm.get("description").get(0));
        } catch (NullPointerException npe) {
            t.setDescription(null);
        }

        /*RELATED TO EQUIPMENTS*/
        Equipment equipment = null;
        try {
            String equipId = (String) mvhm.get("equipment").get(0);
            equipment = new Equipment();
            equipment.setId(Long.parseLong(equipId));
        } catch (NullPointerException | NumberFormatException nfe) {
            equipment = null;
        } finally {
            t.setEquipment(equipment);
        }
        /*RELATED TO TICKET ID*/
        try {
            t.setId(Long.parseLong(mvm.get("id").get(0)));
        } catch (NullPointerException | NumberFormatException nfe) {
            t.setId(null);
        }

        /*RELATED TO INTERACTIONS*/
        t.setInteractions(new HashSet());
        Interaction interaction = new Interaction();
        interaction.setDateReg(new Timestamp(Calendar.getInstance().getTimeInMillis()));

        try {
            interaction.setStringUpdate((String) mvhm.get("interaction-update").get(0));
        } catch (NullPointerException npe) {
            interaction.setStringUpdate(null);
        }

        interaction.setTicket(t);

        try {
            interaction.setType((String) mvhm.get("interaction-type").get(0));
        } catch (NullPointerException npe) {
            interaction.setType(Interaction.UPDATING_TICKET);
        }

        try {
            User interactionUser = new User();
            interactionUser.setId(Long.parseLong((String) mvhm.get("interaction-user").get(0)));
            interaction.setUser(interactionUser);
        } catch (NullPointerException | NumberFormatException nfe) {
            interaction.setUser(null);
        }

        /*ADD INTERACTION TO TICKET*/
        t.getInteractions().add(interaction);

        /*RELATED TO OWNER*/
        try {
            User owner = new User();
            owner.setId(Long.parseLong((String) mvhm.get("owner").get(0)));
            t.setOwner(owner);
        } catch (NullPointerException | NumberFormatException nfe) {
            t.setOwner(null);
        }

        /*RELATED TO PRIORITY*/
        try {
            t.setPriority((String) mvhm.get("priority").get(0));
        } catch (NullPointerException | NumberFormatException nfe) {
            t.setPriority(null);
        }

        /*RELATED TO REQUESTEDPRODUCTS*/
        List rps, products, amounts;
        rps = mvhm.get("rps");
        products = mvhm.get("products");
        amounts = mvhm.get("amounts");

        if (products != null && amounts != null && products.size() == amounts.size()) {
            t.setRequestedProducts(new HashSet());
            for (int i = 0; i < products.size(); i++) {
                RequestedProduct rp1 = new RequestedProduct();

                if (rps != null && rps.size() >= (i + 1)) {
                    try {
                        rp1.setId(Long.parseLong((String) rps.get(i)));
                    } catch (NumberFormatException nfe) {
                        rp1.setId(null);
                    }
                }

                try {
                    rp1.setAmount(Long.parseLong((String) amounts.get(i)));
                } catch (NumberFormatException nfe) {
                    rp1.setAmount((long) 0);
                }
                try {
                    rp1.setProduct(new Product());
                    rp1.getProduct().setId(Long.parseLong((String) products.get(i)));
                } catch (NumberFormatException nfe) {
                    rp1.setProduct(null);
                }

                t.getRequestedProducts().add(rp1);
            }
        }

        if ((products == null && amounts != null)
                || (products != null && amounts == null)
                || (products == null && amounts == null)
                || (products.size() != amounts.size())) {
        } else {

            for (Object requestedProduct : t.getRequestedProducts()) {
                RequestedProduct rp = (RequestedProduct) requestedProduct;
                if (rp.getId() == null) {
                    rp.setDateReg(new Timestamp(Calendar.getInstance().getTimeInMillis()));
                    rp.setType(RequestedProduct.REQUESTED_COMPONENT);
                    rp.setTicket(t);
                    rp.setStatusPurchase(RequestedProduct.STATUS_PIPE);
                }
            }
        }

        /*RELATED TO RESPONSIBLES*/
        try {
            User responsible = new User();
            responsible.setId(Long.parseLong((String) mvhm.get("responsible").get(0)));
            t.setResponsible(responsible);
        } catch (NullPointerException | NumberFormatException nfe) {
            t.setResponsible(null);
        }

        /*RELATED TO SERVICE*/
        Service service;
        try {
            service = new Service();
            service.setId(Long.parseLong((String) mvhm.get("service").get(0)));
        } catch (NullPointerException | NumberFormatException nfe) {
            service = null;
        }
        t.setService(service);

        /*RELATED TO STATUS*/
        try {
            t.setStatus((String) mvhm.get("status").get(0));
        } catch (NullPointerException npe) {
            t.setStatus(null);
        }

        /*RELATED TO TITLE*/
        try {
            t.setTitle((String) mvhm.get("title").get(0));
        } catch (NullPointerException npe) {
            t.setTitle(null);
        }

        /*RELATED TO CLOSING*/
        try {
            t.setTypeOfClosing((String) mvhm.get("type-of-closing").get(0));
        } catch (NullPointerException npe) {
            t.setTypeOfClosing(null);
        }

        /*RELATED TO STRUCTURAL CHANGES*/
        UpdatingMaintenanceTicketHolder up = (UpdatingMaintenanceTicketHolder) gh;
        try {
            up.setChangedDescription((String) mvhm.get("changedDescription").get(0));
        } catch (NullPointerException npe) {
        }
        try {
            up.setChangedEquipment((String) mvhm.get("changedEquipment").get(0));
        } catch (NullPointerException npe) {
        }
        try {
            up.setChangedOwner((String) mvhm.get("changedOwner").get(0));
        } catch (NullPointerException npe) {
        }
        try {
            up.setChangedPriority((String) mvhm.get("changedPriority").get(0));
        } catch (NullPointerException npe) {
        }
        try {
            up.setChangedRequestedProducts((String) mvhm.get("changedRequestedProducts").get(0));
        } catch (NullPointerException npe) {
        }
        try {
            up.setChangedResponsible((String) mvhm.get("changedResponsible").get(0));
        } catch (NullPointerException npe) {
        }
        try {
            up.setChangedService((String) mvhm.get("changedService").get(0));
        } catch (NullPointerException npe) {
        }
        try {
            up.setChangedTitle((String) mvhm.get("changedTitle").get(0));
        } catch (NullPointerException npe) {
        }

        up.getEntities().add(t);

        loadBusinessRulesBeforeMainFlow();
        loadBusinessRulesAfterMainFlow();

        return up;
    }

    @Override
    public void loadBusinessRulesBeforeMainFlow() {
        getRulesBeforeMainFlow().add(new ValidateIDEntityCommand());
        getRulesBeforeMainFlow().add(new ValidateAndMergeMaintenanceTicketCommand());
    }

    @Override
    public void loadBusinessRulesAfterMainFlow() {
        getRulesAfterMainFlow().add(new AcceptTicketMaintenanceAttributesCommand(new String[]{"responsible", "service"}, rejects));
        getRulesAfterMainFlow().add(new NotifyContentCommand(new String[]{"ticket", "groups"}));
    }

}
