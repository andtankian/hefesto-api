package br.com.hefesto.resources.impl.deparment.view;

import br.com.hefesto.domain.impl.Department;
import br.com.wsbasestructure.dto.FlowContainer;
import br.com.wsbasestructure.dto.impl.GenericHolder;
import br.com.wsbasestructure.dto.interfaces.IHolder;
import br.com.wsbasestructure.view.abstracts.AbstractViewHelper;
import java.sql.Timestamp;
import java.util.Calendar;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;

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
        FormDataMultiPart fdm = fc.getCr().readEntity(FormDataMultiPart.class);
        
        GenericHolder gh = new GenericHolder();
        
        return gh;
        
    }
    
}
