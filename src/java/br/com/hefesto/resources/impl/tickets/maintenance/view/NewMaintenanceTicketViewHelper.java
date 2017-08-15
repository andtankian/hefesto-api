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
import br.com.hefesto.resources.impl.tickets.maintenance.rules.ValidateMaintenanceTicketDataForRegisterCommand;
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
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MultivaluedHashMap;

/**
 *
 * @author Andrew
 */
public class NewMaintenanceTicketViewHelper extends AbstractViewHelper {
    
    @Override
    public IHolder getView(FlowContainer fc) {
        super.getView(fc);
        
        GenericHolder gh = new GenericHolder();
        
        Form f = fc.getCr().readEntity(Form.class);
        MultivaluedHashMap mvhm = (MultivaluedHashMap) f.asMap();
        
        Ticket t = new Ticket();
        
        t.setDateReg(new Timestamp(Calendar.getInstance().getTimeInMillis()));

        /*DESCRIPTION*/
        try {
            t.setDescription((String) mvhm.get("description").get(0));
        } catch (NullPointerException npe) {
            t.setDescription(null);
        }

        /*DIAGNOSIS*/
        try {
            t.setDiagnosis((String) mvhm.get("diagnosis").get(0));
        } catch (NullPointerException npe) {
            t.setDiagnosis(null);
        }

        /*EQUIPMENT*/
        Equipment equipment;
        try {
            equipment = new Equipment();
            equipment.setId(Long.parseLong((String) mvhm.get("equipment").get(0)));
            t.setEquipment(equipment);
        } catch (NullPointerException | NumberFormatException nge) {
            t.setEquipment(null);
        }

        /*--------------------------INTERACTION--------------------------*/
        Interaction interaction = new Interaction();
        interaction.setDateReg(t.getDateReg());
        interaction.setTicket(t);
        interaction.setType(Interaction.OPENING);
        /*INTERACTION CURRENT UPDATE*/
        try {
            interaction.setStringUpdate((String) mvhm.get("interaction-update").get(0));
        } catch (NullPointerException npe) {
            interaction.setStringUpdate(null);
        }
        /*INTERACTION USER*/
        User interactionUser;
        try {
            interactionUser = new User();
            interactionUser.setId(Long.parseLong((String) mvhm.get("interaction-user").get(0)));
            interaction.setUser(interactionUser);
        } catch (NullPointerException | NumberFormatException nge) {
            interaction.setUser(null);
        }
        t.setInteractions(new HashSet());
        t.getInteractions().add(interaction);
        /*--------------------------END-INTERACTION--------------------------*/

 /*--------------------------OWNER--------------------------*/
        try {
            User owner = new User();
            owner.setId(Long.parseLong((String) mvhm.get("owner").get(0)));
            t.setOwner(owner);
        } catch (NullPointerException | NumberFormatException nge) {
            t.setOwner(null);
        }
        /*--------------------------END-OWNER--------------------------*/

 /*PRIORITY*/
        try {
            t.setPriority((String) mvhm.get("priority").get(0));
        } catch (NullPointerException npe) {
            t.setPriority(null);
        }

        /*-------------------------REQUESTED PRODUCTS--------------------------*/
        List products, amounts;
        products = mvhm.get("products");
        amounts = mvhm.get("amounts");
        if ((products == null && amounts != null) 
                || (products != null && amounts == null)
                || (products == null && amounts == null)
                || (products.size() != amounts.size())) {
            t.setRequestedProducts(null);
        } else {
            t.setRequestedProducts(new HashSet());
            RequestedProduct rp;
            for (int i = 0; i < products.size(); i++) {
                rp = new RequestedProduct();
                rp.setDateReg(t.getDateReg());
                rp.setType(RequestedProduct.REQUESTED_COMPONENT);
                rp.setStatusPurchase(RequestedProduct.STATUS_PIPE);
                try {
                    rp.setAmount(Long.parseLong((String) amounts.get(i)));
                } catch (NumberFormatException nfe) {
                    rp.setAmount((long) 0);
                }
                try {
                    rp.setProduct(new Product());
                    rp.getProduct().setId(Long.parseLong((String) products.get(i)));
                } catch (NumberFormatException nfe) {
                    rp.setProduct(null);
                }
                t.getRequestedProducts().add(rp);
            }
        }
        /*-------------------------END-REQUESTED-PRODUCTS-------------------------*/

 /*STATUS*/
        try {
            t.setStatus((String) mvhm.get("status").get(0));
        } catch (NullPointerException npe) {
            t.setStatus(null);
        }

        /*-------------------------RESPONSIBLE-------------------------*/
        try {
            t.setResponsible(new User());
            t.getResponsible().setId(Long.parseLong((String) mvhm.get("responsible").get(0)));
        } catch (NullPointerException | NumberFormatException nge) {
            t.setResponsible(null);
        }
        /*-------------------------END-RESPONSIBLE-------------------------*/

 /*SERVICE*/
        try {
            t.setService(new Service());
            t.getService().setId(Long.parseLong((String) mvhm.get("service").get(0)));
        } catch (NullPointerException | NumberFormatException nge) {
            t.setService(null);
        }

        /*STATUS*/
        try {
            t.setStatus((String) mvhm.get("status").get(0));
        } catch (NullPointerException npe) {
            t.setStatus(Ticket.OPENED);
        }

        /*TITLE*/
        try {
            t.setTitle((String) mvhm.get("title").get(0));
        } catch (NullPointerException npe) {
            t.setTitle(null);
        }
        
        t.setType(Ticket.MAINTENANCE);
        try {
            t.setTypeOfClosing((String)mvhm.get("type-of-closing").get(0));
        }catch(NullPointerException npe){
            t.setTypeOfClosing(null);
        }
        
        loadBusinessRulesBeforeMainFlow();
        loadBusinessRulesAfterMainFlow();
        
        gh.getEntities().add(t);
        
        return gh;
    }

    @Override
    public void loadBusinessRulesBeforeMainFlow() {
        getRulesBeforeMainFlow().add(new ValidateMaintenanceTicketDataForRegisterCommand());
    }

    @Override
    public void loadBusinessRulesAfterMainFlow() {
        getRulesAfterMainFlow().add(new AcceptTicketMaintenanceAttributesCommand(new String[] {"responsible"}, rejects));
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
