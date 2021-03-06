package br.com.hefesto.resources.impl.group.view;

import br.com.hefesto.domain.impl.Groups;
import br.com.hefesto.resources.impl.group.rules.AcceptGroupAttributes;
import br.com.hefesto.resources.impl.group.rules.ValidateAndMergeGroupCommand;
import br.com.hefesto.resources.impl.rules.NotifyContentCommand;
import br.com.wsbasestructure.dto.FlowContainer;
import br.com.wsbasestructure.dto.impl.GenericHolder;
import br.com.wsbasestructure.dto.interfaces.IHolder;
import br.com.wsbasestructure.view.abstracts.AbstractViewHelper;
import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author Andrew Ribeiro
 */
public class UpdateGroupViewHelper extends AbstractViewHelper {

    @Override
    public IHolder getView(FlowContainer fc) {
        super.getView(fc); //don't forget

        Groups p = new Groups();
        GenericHolder gh = new GenericHolder();
        UriInfo u = fc.getCr().getUriInfo();
        String id = u.getPathParameters().get("id") != null ? u.getPathParameters().get("id").get(0) : null;
        Form f = fc.getCr().readEntity(Form.class);
        MultivaluedHashMap mvhm = (MultivaluedHashMap) f.asMap();
        try {
            p.setId(Long.parseLong(id));
        } catch (NumberFormatException n) {
            p.setId((long) 0);
        }
        Set users;
        try {
            users = new HashSet<>(mvhm.get("users"));
        } catch (NullPointerException e) {
            users = new HashSet<>();
        }

        p.setDescription(mvhm.get("description") != null ? (String) mvhm.get("description").get(0) : null);
        p.setName(mvhm.get("name") != null ? (String) mvhm.get("name").get(0) : null);
        p.setStatus(mvhm.get("status") != null ? (String) mvhm.get("status").get(0) : null);
        p.setUsers(users);

        gh.getEntities().add(p);

        loadBusinessRulesBeforeMainFlow();
        loadBusinessRulesAfterMainFlow();

        return gh;
    }

    @Override
    public void loadBusinessRulesBeforeMainFlow() {
        this.getRulesBeforeMainFlow().add(new ValidateAndMergeGroupCommand());
    }

    @Override
    public void loadBusinessRulesAfterMainFlow() {
        this.getRulesAfterMainFlow().add(new AcceptGroupAttributes(new String[]{"none"}, this.rejects));
        this.getRulesAfterMainFlow().add(new NotifyContentCommand(new String[]{"groups"}));
    }

}
