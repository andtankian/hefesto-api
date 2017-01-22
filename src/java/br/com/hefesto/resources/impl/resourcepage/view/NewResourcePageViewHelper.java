package br.com.hefesto.resources.impl.resourcepage.view;

import br.com.hefesto.domain.impl.ResourcePage;
import br.com.hefesto.resources.impl.resourcepage.rules.ValidateDataResourcePageCommand;
import br.com.wsbasestructure.dto.FlowContainer;
import br.com.wsbasestructure.dto.impl.GenericHolder;
import br.com.wsbasestructure.dto.interfaces.IHolder;
import br.com.wsbasestructure.view.abstracts.AbstractViewHelper;
import java.sql.Timestamp;
import java.util.Calendar;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MultivaluedHashMap;

/**
 *
 * @author Andrew Ribeiro
 */
public class NewResourcePageViewHelper extends AbstractViewHelper {

    @Override
    public IHolder getView(FlowContainer fc) {
        super.getView(fc);

        ResourcePage r = new ResourcePage();
        GenericHolder gh = new GenericHolder();
        Form f = fc.getCr().readEntity(Form.class);
        MultivaluedHashMap mvhm = (MultivaluedHashMap) f.asMap();
        String name;

        try {
            name = (String) mvhm.get("name").get(0);
        } catch (NumberFormatException e) {
            name = null;
        }
        r.setName(name);
        r.setDateReg(new Timestamp(Calendar.getInstance().getTimeInMillis()));

        gh.getEntities().add(r);
        this.loadBusinessRulesBeforeMainFlow();
        return gh;
    }

    @Override
    public void loadBusinessRulesBeforeMainFlow() {
        this.getRulesBeforeMainFlow().add(new ValidateDataResourcePageCommand());
    }
    
    

}
