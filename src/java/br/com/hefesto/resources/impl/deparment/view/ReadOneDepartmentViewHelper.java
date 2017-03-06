package br.com.hefesto.resources.impl.deparment.view;

import br.com.hefesto.domain.impl.Department;
import br.com.hefesto.resources.impl.department.rules.ValidateDepartmentIDCommand;
import br.com.wsbasestructure.dto.FlowContainer;
import br.com.wsbasestructure.dto.SearchModel;
import br.com.wsbasestructure.dto.impl.GenericHolder;
import br.com.wsbasestructure.dto.interfaces.IHolder;
import br.com.wsbasestructure.view.abstracts.AbstractViewHelper;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author Andrew Ribeiro
 */
public class ReadOneDepartmentViewHelper extends AbstractViewHelper {

    @Override
    public IHolder getView(FlowContainer fc) {
        super.getView(fc);

        Department d = new Department();
        GenericHolder gh = new GenericHolder();
        SearchModel sm = gh.getSm();
        UriInfo u = fc.getCr().getUriInfo();
        sm.getBasics(u);

        String id = u.getPathParameters().get("id") != null ? u.getPathParameters().get("id").get(0) : null;

        try {
            d.setId(Long.parseLong(id));
        } catch (NumberFormatException n) {
            d.setId((long) 0);
        }
        
        sm.setEntity(d);
        gh.getEntities().add(d);
        
        loadBusinessRulesBeforeMainFlow();
        return gh;
        
    }

    @Override
    public void loadBusinessRulesBeforeMainFlow() {        
        this.getRulesBeforeMainFlow().add(new ValidateDepartmentIDCommand());
    }
    
    

}
