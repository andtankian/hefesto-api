package br.com.hefesto.resources.impl.tickets.maintenance.view;

import br.com.hefesto.domain.impl.Ticket;
import br.com.hefesto.resources.impl.tickets.maintenance.rules.AcceptTicketMaintenanceAttributesCommand;
import br.com.wsbasestructure.dto.FlowContainer;
import br.com.wsbasestructure.dto.SearchModel;
import br.com.wsbasestructure.dto.impl.GenericHolder;
import br.com.wsbasestructure.dto.interfaces.IHolder;
import br.com.wsbasestructure.rules.impl.ValidateIDEntityCommand;
import br.com.wsbasestructure.view.abstracts.AbstractViewHelper;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author Andrew
 */
public class ReadMaintenanceTicketViewHelper extends AbstractViewHelper{

    @Override
    public IHolder getView(FlowContainer fc) {
        super.getView(fc);
        GenericHolder gh = new GenericHolder();
        Ticket t = new Ticket();
        t.setType(Ticket.MAINTENANCE);
        SearchModel sm = gh.getSm();
        UriInfo uri = fc.getCr().getUriInfo();
        MultivaluedMap<String, String> mvm = uri.getPathParameters();
        
        try {
            t.setId(Long.parseLong(mvm.get("id").get(0)));
        }catch(NullPointerException npe){
            t.setId(null);
        }
        
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
    public void loadBusinessRulesAfterMainFlow() {
        getRulesAfterMainFlow().add(new AcceptTicketMaintenanceAttributesCommand(new String[]{"service", "equipment", "responsible"}, rejects));
    }

    

}
