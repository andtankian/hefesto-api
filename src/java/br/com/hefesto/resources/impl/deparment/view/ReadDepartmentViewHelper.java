package br.com.hefesto.resources.impl.deparment.view;

import br.com.hefesto.domain.impl.Department;
import br.com.wsbasestructure.dto.SearchModel;
import br.com.wsbasestructure.dto.impl.GenericHolder;
import br.com.wsbasestructure.dto.interfaces.IHolder;
import br.com.wsbasestructure.view.abstracts.AbstractViewHelper;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author Andrew Ribeiro
 */
public class ReadDepartmentViewHelper extends AbstractViewHelper{

    @Override
    public IHolder getView(UriInfo uriInfo, HttpServletRequest httpReq) {
        super.getView(uriInfo, httpReq);
        
        GenericHolder gh = new GenericHolder();
        gh.getEntities().add(new Department());
        gh.getSm().getBasics(uriInfo);
        
        return gh;
    }
    
    
    
}
