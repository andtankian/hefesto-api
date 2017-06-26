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
import br.com.hefesto.resources.impl.tickets.maintenance.rules.ValidateAndMergeTicketMaintenanceCommand;
import br.com.wsbasestructure.dto.FlowContainer;
import br.com.wsbasestructure.dto.Result;
import br.com.wsbasestructure.dto.impl.GenericHolder;
import br.com.wsbasestructure.dto.interfaces.IHolder;
import br.com.wsbasestructure.rules.impl.ValidateIDEntityCommand;
import br.com.wsbasestructure.view.abstracts.AbstractViewHelper;
import br.com.wsbasestructure.view.impl.GenericExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author Andrew Ribeiro
 */
public class UpdateTicketMaintenanceViewHelper extends AbstractViewHelper {
    
    @Override
    public IHolder getView(FlowContainer fc) {
        super.getView(fc);
        
        Ticket t = new Ticket();
        t.setType(Ticket.MAINTENANCE);
        GenericHolder gh = new GenericHolder();
        UriInfo uri = fc.getCr().getUriInfo();
        MultivaluedMap<String, String> mvm = uri.getPathParameters();
        Form f = fc.getCr().readEntity(Form.class);
        MultivaluedHashMap mvhm = (MultivaluedHashMap) f.asMap();

        /*TICKET*/
        String ticket;
        try {
            ticket = (String) mvm.get("id").get(0);
        } catch (NullPointerException npe) {
            ticket = null;
        }
        
        try {
            t.setId(Long.parseLong(ticket));
        } catch (NumberFormatException nfe) {
            t.setId(null);
        }

        /*EQUIPMENT*/
        Equipment e = new Equipment();
        String equipment;
        try {
            equipment = (String) mvhm.get("equipment").get(0);
        } catch (NullPointerException npe) {
            equipment = null;
        }
        
        try {
            e.setId(Long.parseLong(equipment));
        } catch (NumberFormatException nfe) {
            e.setId(null);
        }
        
        t.setEquipment(e);

        /*INTERACTIONS*/
        t.setInteractions(new HashSet());
        Interaction i = new Interaction();
        i.setTicket(t);
        try {
            i.setType((String) mvhm.get("interaction-type").get(0));
        } catch (NullPointerException npe) {
            i.setType(Interaction.UPDATING_TICKET);
        }
        /*INTERACTION USER*/
        User u = new User();
        String interactionUser;
        try {
            interactionUser = (String) mvhm.get("interaction-user").get(0);
        }catch(NullPointerException npe){
            interactionUser = null;
        }
        
        try {
            u.setId(Long.parseLong(interactionUser));
        }catch(NumberFormatException nfe){
            u.setId(null);
        }
        
        i.setUser(u);
        
        t.getInteractions().add(i);
        
        
        /*PRIORITY*/
        try {
            t.setPriority((String) mvhm.get("priority").get(0));
        }catch(NullPointerException npe){
            t.setPriority(null);
        }
        
        /*PROBLEM*/
        try {
            t.setProblem((String) mvhm.get("problem").get(0));
        }catch(NullPointerException npe){
            t.setProblem(null);
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
            for (int j = 0; j < products.size(); j++) {
                String pId;
                String amount;
                try {
                    pId = (String) products.get(j);
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
                    amount = (String) amounts.get(j);
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
        } else if (products == null || amounts == null || (products.isEmpty() && !amounts.isEmpty()) || (amounts.isEmpty() && !products.isEmpty()) || products.size() != amounts.size()) {
            t.setRequestedProducts(null);
        }
        
        /*RESOLUTION*/
        try {
            t.setResolution((String) mvhm.get("resolution").get(0));
        }catch(NullPointerException npe){
            t.setResolution(null);
        }
        
        /*RESPONSIBLE*/
        
        User r = new User();
        String responsible;
        
        try {
            responsible = (String) mvhm.get("responsible").get(0);
        }catch(NullPointerException npe){
            responsible = null;
        }
        
        try {
            r.setId(Long.parseLong(responsible));
        }catch(NumberFormatException nfe){
            r.setId(null);
        }
        
        t.setResponsible(r);
        
        /*SERVICE*/
        Service s = new Service();
        String service;
        try {
            service = (String) mvhm.get("service").get(0);
        }catch(NullPointerException npe){
            service = null;
        }
        
        try {
            s.setId(Long.parseLong(service));
        }catch(NumberFormatException nfe){
            s.setId(null);
        }
        
        t.setService(s);
        
        /*STATUS*/
        try {
            t.setStatus((String) mvhm.get("status").get(0));
        }catch(NullPointerException npe){
            t.setStatus(null);
        }
        
        /*TITLE*/
        try {
            t.setTitle((String) mvhm.get("title").get(0));
        }catch(NullPointerException npe){
            t.setTitle(null);
        }
        
        gh.getEntities().add(t);
        
        loadBusinessRulesBeforeMainFlow();
        loadBusinessRulesAfterMainFlow();
        
        return gh;
    }

    @Override
    public void loadBusinessRulesBeforeMainFlow() {
        getRulesBeforeMainFlow().add(new ValidateIDEntityCommand());
        getRulesBeforeMainFlow().add(new ValidateAndMergeTicketMaintenanceCommand());
    }   
   

    @Override
    public void loadBusinessRulesAfterMainFlow() {        
        getRulesAfterMainFlow().add(new AcceptTicketMaintenanceAttributesCommand(new String[]{"none"}, rejects));
        getRulesAfterMainFlow().add(new NotifyContentCommand(new String[]{"ticket", "groups"}));        
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
