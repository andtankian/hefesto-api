package br.com.wsbasestructure.test;

import br.com.wsbasestructure.dto.interfaces.IHolder;
import br.com.wsbasestructure.view.abstracts.AbstractViewHelper;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author Andrew Ribeiro
 */
public class TestViewHelper extends AbstractViewHelper{

    @Override
    public IHolder getView(UriInfo uriInfo) {
        super.getView(uriInfo);
        TestEntity e = new TestEntity();
        e.setDateReg(new Timestamp(Calendar.getInstance().getTimeInMillis()));
        
        TestHolder h = new TestHolder();
        h.setEntities(new ArrayList<>());
        h.getEntities().add(e);
        
        return h;
        
    }
    
}
