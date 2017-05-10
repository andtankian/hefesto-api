package br.com.hefesto.resources.impl.tickets.purchase.view;

import br.com.hefesto.domain.impl.Ticket;
import br.com.wsbasestructure.dto.FlowContainer;
import br.com.wsbasestructure.dto.Result;
import br.com.wsbasestructure.dto.SearchModel;
import br.com.wsbasestructure.dto.impl.GenericHolder;
import br.com.wsbasestructure.dto.interfaces.IHolder;
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
public class ReadTicketsPurchaseViewHelper extends AbstractViewHelper{

    @Override
    public void loadBusinessRulesAfterMainFlow() {
        super.loadBusinessRulesAfterMainFlow();
    }

    @Override
    public void loadBusinessRulesBeforeMainFlow() {
        super.loadBusinessRulesBeforeMainFlow();
    }

    @Override
    public IHolder getView(FlowContainer fc) {
        super.getView(fc);
        
        Ticket t = new Ticket();
        t.setType(Ticket.PURCHASE);
        GenericHolder gh = new GenericHolder();
        SearchModel sm = gh.getSm();
        UriInfo uri = fc.getCr().getUriInfo();
        sm.getBasics(uri);
        MultivaluedMap<String, String> mvm = uri.getQueryParameters();
        
        try {
            sm.setSearch(mvm.get("search").get(0));
        } catch(NullPointerException npe){
            sm.setSearch(null);
        }
        
        sm.setEntity(t);
        gh.getEntities().add(t);
        
        return gh;
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
