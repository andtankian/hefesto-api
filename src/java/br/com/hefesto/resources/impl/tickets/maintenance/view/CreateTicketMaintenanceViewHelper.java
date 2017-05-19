package br.com.hefesto.resources.impl.tickets.maintenance.view;

import br.com.hefesto.domain.impl.Equipment;
import br.com.hefesto.domain.impl.Interaction;
import br.com.hefesto.domain.impl.Product;
import br.com.hefesto.domain.impl.RequestedProduct;
import br.com.hefesto.domain.impl.Service;
import br.com.hefesto.domain.impl.Ticket;
import br.com.hefesto.domain.impl.User;
import br.com.hefesto.resources.impl.rules.NotifyContentCommand;
import br.com.hefesto.resources.impl.tickets.maintenance.rules.AcceptTicketMaintenanceAttributesCommand;
import br.com.hefesto.resources.impl.tickets.maintenance.rules.ValidateTicketMaintenanceDataCommand;
import br.com.wsbasestructure.dto.FlowContainer;
import br.com.wsbasestructure.dto.Result;
import br.com.wsbasestructure.dto.impl.GenericHolder;
import br.com.wsbasestructure.dto.interfaces.IHolder;
import br.com.wsbasestructure.view.abstracts.AbstractViewHelper;
import br.com.wsbasestructure.view.impl.GenericExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MultivaluedHashMap;

/**
 *
 * @author Andrew Ribeiro
 */
public class CreateTicketMaintenanceViewHelper extends AbstractViewHelper {

    @Override
    public IHolder getView(FlowContainer fc) {
        super.getView(fc);

        GenericHolder gh = new GenericHolder();
        Ticket t = new Ticket();
        Form f = fc.getCr().readEntity(Form.class);
        MultivaluedHashMap mvhm = (MultivaluedHashMap) f.asMap();

        t.setDateReg(new Timestamp(Calendar.getInstance().getTimeInMillis()));

        /*Ticket Equipment*/
        Equipment e = new Equipment();
        t.setEquipment(e);
        String eqId;
        try {
            eqId = (String) mvhm.get("equipment").get(0);
        } catch (NullPointerException npe) {
            eqId = null;
        }

        try {
            e.setId(Long.parseLong(eqId));
        } catch (NumberFormatException nfe) {
            e.setId(null);
        }

        /*Ticket interactions*/
        t.setInteractions(new HashSet());
        Interaction in = new Interaction();
        t.getInteractions().add(in);

        in.setDateReg(t.getDateReg());
        in.setTicket(t);
        in.setType(Interaction.OPENING);
        User u = new User();
        in.setUser(u);
        String usId;
        try {
            usId = (String) mvhm.get("interaction-user").get(0);
        } catch (NullPointerException npe) {
            usId = null;
        }

        try {
            u.setId(Long.parseLong(usId));
        } catch (NumberFormatException nfe) {
            u.setId(null);
        }

        t.setPriority(Ticket.P5);

        try {
            t.setProblem((String) mvhm.get("problem").get(0));
        } catch (NullPointerException npe) {
            t.setProblem(null);
        }

        /*TICKET ONWER*/
        User owner = new User();
        t.setOwner(owner);
        String ownerId;

        try {
            ownerId = (String) mvhm.get("owner").get(0);
        } catch (NullPointerException npe) {
            ownerId = null;
        }

        try {
            owner.setId(Long.parseLong(ownerId));
        } catch (NumberFormatException nfe) {
            owner.setId(null);
        }

        /*REQUESTED PRODUCTS*/
        t.setRequestedProducts(new HashSet());
        List products;
        List amounts;

        products = mvhm.get("product");
        products = products != null && !products.isEmpty() ? products : new ArrayList();

        amounts = mvhm.get("amount");
        amounts = amounts != null && !amounts.isEmpty() ? amounts : new ArrayList();

        if (!products.isEmpty() && !amounts.isEmpty() && products.size() == amounts.size()) {
            for (int i = 0; i < products.size(); i++) {
                String pId;
                String amount;
                try {
                    pId = (String) products.get(i);
                } catch (NullPointerException npe) {
                    pId = null;
                }
                Product p = new Product();
                try {
                    p.setId(Long.parseLong(pId));
                } catch (NumberFormatException nfe) {
                    p.setId(null);
                }

                try {
                    amount = (String) amounts.get(i);
                } catch (NumberFormatException nfe) {
                    amount = null;
                }

                RequestedProduct rp = new RequestedProduct();
                try {
                    rp.setAmount(Long.parseLong(amount));
                } catch (NumberFormatException nfe) {
                    rp.setAmount((long) 0);
                }

                rp.setDateReg(t.getDateReg());
                rp.setProduct(p);
                rp.setType(RequestedProduct.REQUESTED_COMPONENT);
                rp.setStatusPurchase(RequestedProduct.STATUS_PIPE);
                rp.setTicket(t);

                t.getRequestedProducts().add(rp);
            }
        } else if (products == null || amounts == null || products.isEmpty() || amounts.isEmpty() || products.size() != amounts.size()) {
            t.setRequestedProducts(null);
        }
        /*Responsible of the ticket*/
        User resp = new User();
        t.setResponsible(resp);
        String respId;

        try {
            respId = (String) mvhm.get("responsible").get(0);
        } catch (NullPointerException npe) {
            respId = null;
        }

        try {
            resp.setId(Long.parseLong(respId));
        } catch (NumberFormatException nfe) {
            resp.setId(null);
            t.setResponsible(null);
        }

        try {
            t.setResolution((String) mvhm.get("resolution").get(0));
        } catch (NullPointerException npe) {
            t.setResolution(null);
        }

        /*Ticket service*/
        Service s = new Service();
        t.setService(s);
        String serId;

        try {
            serId = (String) mvhm.get("service").get(0);
        } catch (NullPointerException npe) {
            serId = null;
        }

        try {
            s.setId(Long.parseLong(serId));
        } catch (NumberFormatException nfe) {
            s.setId(null);
        }

        t.setStatus(Ticket.PENDING);

        try {
            t.setTitle((String) mvhm.get("title").get(0));
        } catch (NullPointerException npe) {
            t.setTitle(null);
        }

        t.setType(Ticket.MAINTENANCE);
        gh.getEntities().add(t);

        loadBusinessRulesBeforeMainFlow();
        loadBusinessRulesAfterMainFlow();
        return gh;
    }

    @Override
    public void loadBusinessRulesAfterMainFlow() {
        getRulesAfterMainFlow().add(new AcceptTicketMaintenanceAttributesCommand(new String[]{"none"}, rejects));
        getRulesAfterMainFlow().add(new NotifyContentCommand(new String[]{"ticket", "groups"}));
    }

    @Override
    public void loadBusinessRulesBeforeMainFlow() {
        getRulesBeforeMainFlow().add(new ValidateTicketMaintenanceDataCommand());
    }

    @Override
    public String setView(Result result) {
        GsonBuilder gb = new GsonBuilder();

        gb.addSerializationExclusionStrategy(new GenericExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes fa) {
                return rejects.contains(fa.getName()) || fa.getName().equals("ticket") || fa.getName().equals("groups");
            }
        });
        Gson g = gb.create();

        return g.toJson(result);
    }
    
    

}
