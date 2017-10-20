package br.com.hefesto.resources.impl.notification.view;

import br.com.hefesto.domain.impl.Notification;
import br.com.hefesto.resources.impl.notification.rules.ValidateAndMergeNotificiationCommand;
import br.com.hefesto.resources.impl.rules.GenericAcceptAttributes;
import br.com.wsbasestructure.dto.FlowContainer;
import br.com.wsbasestructure.dto.impl.GenericHolder;
import br.com.wsbasestructure.dto.interfaces.IHolder;
import br.com.wsbasestructure.rules.impl.ValidateIDEntityCommand;
import br.com.wsbasestructure.view.abstracts.AbstractViewHelper;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author andrew
 */
public class UpdateSeenStatusNotificationViewHelper extends AbstractViewHelper{

    @Override
    public IHolder getView(FlowContainer fc) {
        super.getView(fc);
        
        UriInfo uri = fc.getCr().getUriInfo();
        MultivaluedMap<String, String> mvm = uri.getPathParameters();
        Notification n = new Notification();
        n.setType(Notification.NOTIFICATION);
        n.setSeen(true);
        
        try {
            n.setId(Long.parseLong(mvm.get("id").get(0)));
        }catch(NullPointerException | NumberFormatException npe){
            n.setId(null);
        }
        
        GenericHolder gh = new GenericHolder();
        gh.getEntities().add(n);
        
        loadBusinessRulesBeforeMainFlow();
        loadBusinessRulesAfterMainFlow();
        
        return gh;
    }

    @Override
    public void loadBusinessRulesBeforeMainFlow() {
        this.getRulesBeforeMainFlow().add(new ValidateIDEntityCommand());
        this.getRulesBeforeMainFlow().add(new ValidateAndMergeNotificiationCommand());
    }
    
    
    
     @Override
    public void loadBusinessRulesAfterMainFlow() {
        this.getRulesAfterMainFlow().add(new GenericAcceptAttributes(new String[]{"user"}, rejects, new String[]{"ticket", "groups", "user"}));
    }
    
    
    
}
