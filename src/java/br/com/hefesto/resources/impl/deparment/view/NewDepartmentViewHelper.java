package br.com.hefesto.resources.impl.deparment.view;

import br.com.hefesto.domain.impl.Department;
import br.com.wsbasestructure.dto.impl.GenericHolder;
import br.com.wsbasestructure.dto.interfaces.IHolder;
import br.com.wsbasestructure.view.abstracts.AbstractViewHelper;
import java.sql.Timestamp;
import java.util.Calendar;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author Andrew Ribeiro
 */
public class NewDepartmentViewHelper extends AbstractViewHelper{

    @Override
    public IHolder getView(UriInfo uriInfo, HttpServletRequest httpReq) {
        super.getView(uriInfo, httpReq);
        
        Department d = new Department();
        d.setDateReg(new Timestamp(Calendar.getInstance().getTimeInMillis()));
        d.setDescription(httpReq.getParameter("description"));
        d.setName(httpReq.getParameter("name"));
        
        GenericHolder gh = new GenericHolder();
        
        return gh;
        
    }
    
}
