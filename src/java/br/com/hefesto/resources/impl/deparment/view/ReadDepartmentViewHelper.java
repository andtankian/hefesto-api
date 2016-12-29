package br.com.hefesto.resources.impl.deparment.view;

import br.com.hefesto.domain.impl.Department;
import br.com.wsbasestructure.dto.FlowContainer;
import br.com.wsbasestructure.dto.impl.GenericHolder;
import br.com.wsbasestructure.dto.interfaces.IHolder;
import br.com.wsbasestructure.view.abstracts.AbstractViewHelper;

/**
 *
 * @author Andrew Ribeiro
 */
public class ReadDepartmentViewHelper extends AbstractViewHelper{

    @Override
    public IHolder getView(FlowContainer fc) {
        super.getView(fc);
        
        GenericHolder gh = new GenericHolder();
        gh.getEntities().add(new Department());
        gh.getSm().getBasics(fc.getCr().getUriInfo());
        
        return gh;
    }
    
    
    
}
