package br.com.hefesto.resources.impl.tickets.purchase.view;

import br.com.hefesto.domain.impl.Ticket;
import br.com.hefesto.resources.impl.rules.GenericAcceptAttributes;
import br.com.wsbasestructure.dto.FlowContainer;
import br.com.wsbasestructure.dto.Result;
import br.com.wsbasestructure.dto.SearchModel;
import br.com.wsbasestructure.dto.impl.GenericHolder;
import br.com.wsbasestructure.dto.interfaces.IHolder;
import br.com.wsbasestructure.rules.impl.ValidateIDEntityCommand;
import br.com.wsbasestructure.view.abstracts.AbstractViewHelper;
import br.com.wsbasestructure.view.impl.GenericExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author Andrew Ribeiro
 */
public class ReadTicketPurchaseViewHelper extends AbstractViewHelper {

    @Override
    public IHolder getView(FlowContainer fc) {
        super.getView(fc);
        
        Ticket t = new Ticket();
        t.setType(Ticket.PURCHASE);
        GenericHolder gh = new GenericHolder();
        SearchModel sm = gh.getSm();
        UriInfo uri = fc.getCr().getUriInfo();
        MultivaluedMap<String, String> mvm = uri.getPathParameters();
        
        String id;
        Long longId;
        try {
            id = mvm.get("id").get(0);
        }catch(NullPointerException npe){
            id = null;
        }
        
        try {
            longId = Long.parseLong(id);
        }catch(NumberFormatException nfe){
            longId = null;
        }
        
        t.setId(longId);
        sm.setEntity(t);
        gh.getEntities().add(t);
        
        loadBusinessRulesBeforeMainFlow();
        loadBusinessRulesAfterMainFlow();
        return gh;
    }

    @Override
    public void loadBusinessRulesBeforeMainFlow() {
        getRulesBeforeMainFlow().add(new ValidateIDEntityCommand());
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
