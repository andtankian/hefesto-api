package br.com.hefesto.resources.impl.permission.view;

import br.com.hefesto.domain.impl.Permission;
import br.com.hefesto.resources.impl.permission.rules.AcceptPermissionAttributes;
import br.com.hefesto.resources.impl.permission.rules.ValidatePermissionDataCommand;
import br.com.wsbasestructure.dto.FlowContainer;
import br.com.wsbasestructure.dto.impl.GenericHolder;
import br.com.wsbasestructure.dto.interfaces.IHolder;
import br.com.wsbasestructure.view.abstracts.AbstractViewHelper;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MultivaluedHashMap;

/**
 *
 * @author Andrew Ribeiro
 */
public class NewPermissionViewHelper extends AbstractViewHelper {

    @Override
    public IHolder getView(FlowContainer fc) {
        super.getView(fc);

        GenericHolder gh = new GenericHolder();
        Permission p = new Permission();
        Form f = fc.getCr().readEntity(Form.class);
        MultivaluedHashMap fields = (MultivaluedHashMap) f.asMap();
        String description;
        String name;
        List users;
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

        users = fields.get("users");

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
        this.getRulesBeforeMainFlow().add(new ValidatePermissionDataCommand());
    }

    @Override
    public void loadBusinessRulesAfterMainFlow() {
        this.getRulesAfterMainFlow().add(new AcceptPermissionAttributes(new String[]{"none"}, this.rejects));
    }

    
}
