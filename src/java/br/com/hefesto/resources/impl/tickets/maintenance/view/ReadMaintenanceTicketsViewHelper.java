package br.com.hefesto.resources.impl.tickets.maintenance.view;

import br.com.hefesto.domain.impl.Ticket;
import br.com.hefesto.resources.impl.rules.ValidateSearchEntitiesCommand;
import br.com.hefesto.resources.impl.tickets.maintenance.rules.AcceptTicketMaintenanceAttributesCommand;
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
 * @author Andrew
 */
public class ReadMaintenanceTicketsViewHelper extends AbstractViewHelper {

    @Override
    public IHolder getView(FlowContainer fc) {
        super.getView(fc); //To change body of generated methods, choose Tools | Templates.
        
        Ticket t = new Ticket();
        t.setType(Ticket.MAINTENANCE);
        GenericHolder gh = new GenericHolder();
        SearchModel sm = gh.getSm();
        UriInfo uri = fc.getCr().getUriInfo();
        
        MultivaluedMap<String, String> mvm = uri.getQueryParameters();
        
        sm.getBasics(uri);
        
        try {
            sm.setSearch(mvm.get("search").get(0));
        }catch(NullPointerException npe){
            sm.setSearch(null);
        }
        
        loadBusinessRulesBeforeMainFlow();
        loadBusinessRulesAfterMainFlow();
        
        sm.setEntity(t);
        gh.getEntities().add(t);
        return gh;
    }

    @Override
    public void loadBusinessRulesBeforeMainFlow() {
        this.getRulesBeforeMainFlow().add(new ValidateSearchEntitiesCommand());
    }

    @Override
    public void loadBusinessRulesAfterMainFlow() {
        this.getRulesAfterMainFlow().add(new AcceptTicketMaintenanceAttributesCommand(new String[]{"equipment", "responsible"}, rejects));
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
