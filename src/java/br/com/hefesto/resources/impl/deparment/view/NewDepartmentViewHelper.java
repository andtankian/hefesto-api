package br.com.hefesto.resources.impl.deparment.view;

import br.com.hefesto.domain.impl.Department;
import br.com.hefesto.resources.impl.department.rules.impl.ValidateDepartmentDataCommand;
import br.com.hefesto.resources.impl.rules.NotifyContentCommand;
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
public class NewDepartmentViewHelper extends AbstractViewHelper{

    @Override
    public IHolder getView(FlowContainer fc) {
        super.getView(fc);
        
        
        Department d = new Department();
        d.setDateReg(new Timestamp(Calendar.getInstance().getTimeInMillis()));
        Form f = fc.getCr().readEntity(Form.class);
        MultivaluedHashMap mvhm = (MultivaluedHashMap) f.asMap();
        
        d.setDescription(mvhm.get("description") != null ? (String) mvhm.get("description").get(0) : null);
        d.setName(mvhm.get("name") != null ? (String) mvhm.get("name").get(0) : null);
        
        GenericHolder gh = new GenericHolder();
        gh.getEntities().add(d);
        
        loadBusinessRulesBeforeMainFlow();
        loadBusinessRulesAfterMainFlow();
        return gh;
        
    }

    @Override
    public void loadBusinessRulesBeforeMainFlow() {
        super.loadBusinessRulesBeforeMainFlow();
        getRulesBeforeMainFlow().add(new ValidateDepartmentDataCommand());
    }

    @Override
    public void loadBusinessRulesAfterMainFlow() {
        
    }
    
    
    
    
    
}
