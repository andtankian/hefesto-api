package br.com.hefesto.resources.impl.deparment.view;

import br.com.hefesto.domain.impl.Department;
import br.com.hefesto.resources.impl.department.rules.impl.ValidateAndMergeDepartmentCommand;
import br.com.wsbasestructure.dto.FlowContainer;
import br.com.wsbasestructure.dto.impl.GenericHolder;
import br.com.wsbasestructure.dto.interfaces.IHolder;
import br.com.wsbasestructure.view.abstracts.AbstractViewHelper;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author Andrew Ribeiro
 */
public class DeleteDepartmentViewHelper extends AbstractViewHelper{

    @Override
    public IHolder getView(FlowContainer fc) {
        super.getView(fc);
        
        Department d = new Department();
        GenericHolder gh = new GenericHolder();
        d.setStatus("deactivated");
        UriInfo u = fc.getCr().getUriInfo();
        String id = u.getPathParameters().get("id") != null ? u.getPathParameters().get("id").get(0) : null;
        try {
            d.setId(Long.parseLong(id));
        } catch(NumberFormatException n){
            d.setId((long)0);
        }
        gh.getEntities().add(d);
        
        loadBusinessRulesBeforeMainFlow();
        
        return gh;
        
    }

    @Override
    public void loadBusinessRulesBeforeMainFlow() {
        
        this.getRulesBeforeMainFlow().add(new ValidateAndMergeDepartmentCommand());
    }
    
    
    
}
