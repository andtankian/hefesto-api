package br.com.hefesto.resources.impl.user.view.widgets;

import br.com.hefesto.domain.impl.User;
import br.com.hefesto.resources.impl.user.holder.UserHomeWidgetsHolder;
import br.com.wsbasestructure.dto.FlowContainer;
import br.com.wsbasestructure.dto.interfaces.IHolder;
import br.com.wsbasestructure.view.abstracts.AbstractViewHelper;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author andrew
 */
public class ReadUserRecentInteractionsWidgetViewHelper extends AbstractViewHelper{

    @Override
    public IHolder getView(FlowContainer fc) {
        super.getView(fc);
        typeRequest = "widgets";
        UserHomeWidgetsHolder uhwm = new UserHomeWidgetsHolder();
        
        User u = null;
        UriInfo uri = fc.getCr().getUriInfo();
        MultivaluedMap<String, String> mvm = uri.getPathParameters();
        try {
            u = new User();
            u.setId(Long.parseLong(mvm.get("id").get(0)));
        }catch(NullPointerException | NumberFormatException n){
            u = null; 
        }
        
        uhwm.getEntities().add(u);
        
        uhwm.getSm().setSearch(uhwm.getRecentInteractionsHomeWidget().getClass().getName());
        
        return uhwm;
    }
    
    
    
}
