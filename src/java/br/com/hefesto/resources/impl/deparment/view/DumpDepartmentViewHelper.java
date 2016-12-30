package br.com.hefesto.resources.impl.deparment.view;

import br.com.hefesto.domain.impl.Department;
import br.com.wsbasestructure.dto.FlowContainer;
import br.com.wsbasestructure.dto.SearchModel;
import br.com.wsbasestructure.dto.impl.GenericHolder;
import br.com.wsbasestructure.dto.interfaces.IHolder;
import br.com.wsbasestructure.view.abstracts.AbstractViewHelper;

/**
 *
 * @author Andrew Ribeiro
 */
public class DumpDepartmentViewHelper extends AbstractViewHelper {

    @Override
    public IHolder getView(FlowContainer fc) {
        super.getView(fc);
        
        Department d = new Department();
        GenericHolder gh = new GenericHolder();
        SearchModel sm = gh.getSm();
        d.setStatus("deactivated");
        sm.setEntity(d);
        sm.getBasics(fc.getCr().getUriInfo());
        
        return gh;
    }
    
    

}
