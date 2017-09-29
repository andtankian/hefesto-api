package br.com.hefesto.resources.impl.tickets.maintenance.view;

import br.com.hefesto.domain.impl.Interaction;
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
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author Andrew
 */
public class ReopenMaintenanceTicketViewHelper extends AbstractViewHelper {

    @Override
    public IHolder getView(FlowContainer fc) {
        super.getView(fc);

        Ticket t = new Ticket();
        t.setType(Ticket.MAINTENANCE);
        t.setStatus(Ticket.OPENED);
        GenericHolder gh = new UpdatingMaintenanceTicketHolder();
        UriInfo uri = fc.getCr().getUriInfo();
        Form f = fc.getCr().readEntity(Form.class);
        MultivaluedMap<String, String> mvm = uri.getPathParameters();
        MultivaluedHashMap mvhm = (MultivaluedHashMap) f.asMap();

        try {
            t.setId(Long.parseLong(mvm.get("id").get(0)));
        } catch (NullPointerException | NumberFormatException npe) {
            t.setId(null);
        }

        Interaction i = new Interaction();

        i.setDateReg(new Timestamp(Calendar.getInstance().getTimeInMillis()));
        i.setType(Interaction.REOPENING_TICKET);

        /*RELATED TO INTERACTION USER*/
        User interactionUser = new User();

        try {
            interactionUser.setId(Long.parseLong((String) mvhm.get("interaction-user").get(0)));
            i.setUser(interactionUser);
        } catch (NullPointerException | NumberFormatException npe) {
            i.setUser(interactionUser);
        }

        t.setInteractions(new HashSet());
        t.getInteractions().add(i);

        gh.getEntities().add(t);

        loadBusinessRulesBeforeMainFlow();
        loadBusinessRulesAfterMainFlow();
        return gh;
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
