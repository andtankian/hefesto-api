package br.com.hefesto.resources.impl.group.view;

import br.com.hefesto.domain.impl.Groups;
import br.com.hefesto.resources.impl.group.rules.AcceptGroupAttributes;
import br.com.hefesto.resources.impl.group.rules.ValidateGroupDataCommand;
import br.com.wsbasestructure.dto.FlowContainer;
import br.com.wsbasestructure.dto.impl.GenericHolder;
import br.com.wsbasestructure.dto.interfaces.IHolder;
import br.com.wsbasestructure.view.abstracts.AbstractViewHelper;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MultivaluedHashMap;

/**
 *
 * @author Andrew Ribeiro
 */
public class NewGroupViewHelper extends AbstractViewHelper {

    @Override
    public IHolder getView(FlowContainer fc) {
        super.getView(fc);

        GenericHolder gh = new GenericHolder();
        Groups p = new Groups();
        Form f = fc.getCr().readEntity(Form.class);
        MultivaluedHashMap fields = (MultivaluedHashMap) f.asMap();
        String description;
        String name;
        Set users;
        try {
            description = (String) fields.get("description").get(0);
        } catch (NullPointerException n) {
            description = null;
        }

        try {
            name = (String) fields.get("name").get(0);
        } catch (NullPointerException n) {
            name = null;
        }

        try {
            users = new HashSet<>(fields.get("users"));
        } catch (NullPointerException e) {
            users = null;
        }

        p.setDescription(description);
        p.setName(name);
        p.setDateReg(new Timestamp(Calendar.getInstance().getTimeInMillis()));
        p.setUsers(users);

        gh.getEntities().add(p);

        this.loadBusinessRulesBeforeMainFlow();
        this.loadBusinessRulesAfterMainFlow();

        return gh;

    }

    @Override
    public void loadBusinessRulesBeforeMainFlow() {
        this.getRulesBeforeMainFlow().add(new ValidateGroupDataCommand());
    }

    @Override
    public void loadBusinessRulesAfterMainFlow() {
        this.getRulesAfterMainFlow().add(new AcceptGroupAttributes(new String[]{"none"}, this.rejects));
    }

}
