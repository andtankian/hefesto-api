package br.com.hefesto.resources.impl.tickets.maintenance.view;

import br.com.hefesto.domain.impl.Interaction;
import br.com.hefesto.domain.impl.Ticket;
import br.com.hefesto.domain.impl.User;
import br.com.hefesto.resources.impl.tickets.maintenance.rules.AcceptTicketMaintenanceAttributesCommand;
import br.com.hefesto.resources.impl.tickets.maintenance.rules.ValidateInteractionUserWhenGettingRecentTicketsCommand;
import br.com.wsbasestructure.dto.FlowContainer;
import br.com.wsbasestructure.dto.SearchModel;
import br.com.wsbasestructure.dto.impl.GenericHolder;
import br.com.wsbasestructure.dto.interfaces.IHolder;
import br.com.wsbasestructure.view.abstracts.AbstractViewHelper;
import java.util.HashSet;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author andrew
 */
public class ReadRecentsTicketsByUser extends AbstractViewHelper {

    @Override
    public IHolder getView(FlowContainer fc) {
        super.getView(fc);

        Ticket t = new Ticket();
        t.setType(Ticket.MAINTENANCE);

        GenericHolder gh = new GenericHolder();
        SearchModel sm = gh.getSm();
        UriInfo uri = fc.getCr().getUriInfo();
        sm.getBasics(uri);
        MultivaluedMap<String, String> mvm = uri.getPathParameters();

        t.setInteractions(new HashSet());

        Interaction in = new Interaction();
        User user = new User();

        try {
            user.setId(Long.parseLong(mvm.get("id").get(0)));
        } catch (NullPointerException | NumberFormatException nfe) {
            user = null;
        } finally {
            in.setUser(user);
        }
        
        t.getInteractions().add(in);
        sm.setEntity(in);
        gh.getEntities().add(t);
        
        loadBusinessRulesBeforeMainFlow();
        loadBusinessRulesAfterMainFlow();
        
        return gh;

    }

    @Override
    public void loadBusinessRulesBeforeMainFlow() {
        this.getRulesBeforeMainFlow().add(new ValidateInteractionUserWhenGettingRecentTicketsCommand());
    }

    
    
     @Override
    public void loadBusinessRulesAfterMainFlow() {
        this.getRulesAfterMainFlow().add(new AcceptTicketMaintenanceAttributesCommand(new String[]{"equipment", "responsible"}, rejects));
    }
}
