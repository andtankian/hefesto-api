package br.com.hefesto.resources.impl.deparment.view;

import br.com.hefesto.domain.impl.Department;
import br.com.hefesto.resources.impl.department.rules.impl.ValidateAndMergeDepartmentCommand;
import br.com.wsbasestructure.dto.FlowContainer;
import br.com.wsbasestructure.dto.impl.GenericHolder;
import br.com.wsbasestructure.dto.interfaces.IHolder;
import br.com.wsbasestructure.view.abstracts.AbstractViewHelper;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author Andrew Ribeiro
 */
public class UpdateDepartmentViewHelper extends AbstractViewHelper{

    @Override
    public IHolder getView(FlowContainer fc) {
        
        super.getView(fc); //don't forget
        
        Department d = new Department();
        GenericHolder gh = new GenericHolder();
        UriInfo u = fc.getCr().getUriInfo();
        String id = u.getPathParameters().get("id") != null ? u.getPathParameters().get("id").get(0) : null;
        Form f = fc.getCr().readEntity(Form.class);
        MultivaluedHashMap mvhm = (MultivaluedHashMap) f.asMap();
        try {
            d.setId(Long.parseLong(id));
        } catch(NumberFormatException n){
            d.setId((long)0);
        }
        
        d.setDescription(mvhm.get("description") != null ? (String) mvhm.get("description").get(0) : null);
        d.setName(mvhm.get("name") != null ? (String) mvhm.get("name").get(0) : null);
        d.setStatus(mvhm.get("status") != null ? (String) mvhm.get("status").get(0) : null);
        
        gh.getEntities().add(d);
        
        loadBusinessRulesBeforeMainFlow();
        
        return gh;
        
    }

    @Override
    public void loadBusinessRulesBeforeMainFlow() {
        getRulesBeforeMainFlow().add(new ValidateAndMergeDepartmentCommand());
    }
    
    
    
    
    
}
