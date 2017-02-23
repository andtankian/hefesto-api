package br.com.hefesto.resources.impl.user.view;

import br.com.hefesto.domain.impl.User;
import br.com.hefesto.resources.impl.user.rules.AcceptUserAttributesCommand;
import br.com.hefesto.resources.impl.user.rules.ValidateUserIdOrLoginCommand;
import br.com.wsbasestructure.dto.FlowContainer;
import br.com.wsbasestructure.dto.impl.GenericHolder;
import br.com.wsbasestructure.dto.interfaces.IHolder;
import br.com.wsbasestructure.view.abstracts.AbstractViewHelper;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author Andrew Ribeiro
 */
public class ReadUserViewHelper extends AbstractViewHelper {

    @Override
    public IHolder getView(FlowContainer fc) {
        super.getView(fc);

        User u = new User();
        GenericHolder gh = new GenericHolder();
        UriInfo ui = fc.getCr().getUriInfo();
        MultivaluedMap<String, String> mvm = ui.getPathParameters();
        gh.getSm().getBasics(ui);
        String login;
        String id;

        try {
            id = mvm.get("id").get(0);
        }catch(NullPointerException e){
            id = null;
        }

        Long longId;
        
        try {
            longId = Long.parseLong(id);
        }catch(NumberFormatException e){
            login = id;
            longId = null;
        }
        
        u.setId(longId);
        gh.getSm().setEntity(u);
        gh.getEntities().add(u);
        
        loadBusinessRulesBeforeMainFlow();
        loadBusinessRulesAfterMainFlow();
        return gh;

    }

    @Override
    public void loadBusinessRulesBeforeMainFlow() {
        getRulesBeforeMainFlow().add(new ValidateUserIdOrLoginCommand());
    }

    @Override
    public void loadBusinessRulesAfterMainFlow() {
        getRulesAfterMainFlow().add(new AcceptUserAttributesCommand(new String[]{"department", "groups"}, rejects));
    }
    
    
    
    

}
