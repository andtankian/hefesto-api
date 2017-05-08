package br.com.hefesto.resources.impl.tickets.view;

import br.com.hefesto.domain.impl.Equipment;
import br.com.hefesto.domain.impl.Interaction;
import br.com.hefesto.domain.impl.Product;
import br.com.hefesto.domain.impl.RequestedProduct;
import br.com.hefesto.domain.impl.Ticket;
import br.com.hefesto.domain.impl.User;
import br.com.hefesto.resources.impl.rules.NotifyContentCommand;
import br.com.hefesto.resources.impl.tickets.rules.ValidatePurchaseTicketDataCommand;
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
 * @author Andrew Ribeiro
 */
public class CreatePurchaseTicketViewHelper extends AbstractViewHelper{

    @Override
    public void loadBusinessRulesAfterMainFlow() {
        
        getRulesAfterMainFlow().add(new NotifyContentCommand(new String[]{"ticket"}));
        
    }

    @Override
    public void loadBusinessRulesBeforeMainFlow() {
        getRulesBeforeMainFlow().add(new ValidatePurchaseTicketDataCommand());
    }

    @Override
    public IHolder getView(FlowContainer fc) {
        super.getView(fc);
        
        Ticket t = new Ticket();
        t.setType(Ticket.PURCHASE);
        Form f = fc.getCr().readEntity(Form.class);
        MultivaluedHashMap mvhm = (MultivaluedHashMap) f.asMap();
        
        t.setDateReg(new Timestamp(Calendar.getInstance().getTimeInMillis()));
        
        Equipment e = new Equipment();
        String eqId;
        Long longEqId;
        
        try {
            eqId = (String) mvhm.get("eqId").get(0);
        }catch(NullPointerException npe){
            eqId = null;
        }
        
        try {
            longEqId = Long.parseLong(eqId);
        }catch(NumberFormatException nfe){
            longEqId = null;
        }
        
        e.setId(longEqId);
        
        t.setInteractions(new HashSet());
        
        Interaction i = new Interaction();
        i.setDateReg(t.getDateReg());
        i.setTicket(t);
        i.setType(Interaction.OPENING);
        User u = new User();
        
        String uId;
        Long longUId;
        
        try {
            uId = (String) mvhm.get("uId").get(0);
        }catch(NullPointerException npe){
            uId = null;
        }
        
        try {
            longUId = Long.parseLong(eqId);
        }catch(NumberFormatException nfe){
            longUId = null;
        }
        
        u.setId(longUId);
        i.setUser(u);
        
        t.getInteractions().add(i);
        
        t.setPriority(Ticket.P5);
        t.setRequestedProducts(new HashSet());
        
        List products = mvhm.get("products");
        List amounts = mvhm.get("amounts");
        
        if(products != null && !products.isEmpty() && amounts != null && !amounts.isEmpty()){
            if(products.size() == amounts.size()){
                for (int j = 0; j < products.size(); j++) {
                    RequestedProduct rp = new RequestedProduct();
                    String pId, aId;
                    Long longPId, longAId;
                    pId = (String) products.get(j);
                    aId = (String) amounts.get(j);                    
                    try {
                        longPId = Long.parseLong(pId);
                        longAId = Long.parseLong(aId);
                    }catch(NumberFormatException nfe){
                        longPId = null;
                        longAId = null;
                    }
                    
                    Product p = new Product();
                    p.setId(longPId);
                    
                    rp.setProduct(p);
                    rp.setAmount(longAId);
                    rp.setDateReg(t.getDateReg());
                    rp.setStatusPurchase("Aguardando");
                    rp.setTicket(t);
                    rp.setType(RequestedProduct.REQUESTED_PRODUCT);
                    t.getRequestedProducts().add(rp);
                }
            }
            
        }
        
        t.setResponsible(u);
        
        GenericHolder gh = new GenericHolder();
        gh.getEntities().add(t);
        
        loadBusinessRulesBeforeMainFlow();
        loadBusinessRulesAfterMainFlow();
        
        return gh;
        
    }
    
    @Override
    public String setView(Result result) {
        GsonBuilder gb = new GsonBuilder();

        gb.addSerializationExclusionStrategy(new GenericExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes fa) {
                return rejects.contains(fa.getName()) || fa.getName().equals("ticket");
            }
        });
        Gson g = gb.create();

        return g.toJson(result);
    }
    
}
