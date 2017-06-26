package br.com.hefesto.resources.impl.user.view;

import br.com.hefesto.domain.impl.User;
import br.com.hefesto.resources.impl.user.rules.AcceptUserAttributesCommand;
import br.com.hefesto.resources.impl.user.rules.ExceptionToLoginUserCommand;
import br.com.hefesto.resources.impl.user.rules.RemoveUserFromPersistenceContextCommand;
import br.com.hefesto.resources.impl.user.rules.ValidateLoginUserCommand;
import br.com.hefesto.resources.impl.user.rules.ValidateLoginUserPasswordCommand;
import br.com.hefesto.resources.impl.user.rules.ValidateLoginUserStatusCommand;
import br.com.wsbasestructure.dto.FlowContainer;
import br.com.wsbasestructure.dto.impl.GenericHolder;
import br.com.wsbasestructure.dto.interfaces.IHolder;
import br.com.wsbasestructure.rules.impl.StopFlowCommand;
import br.com.wsbasestructure.view.abstracts.AbstractViewHelper;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MultivaluedHashMap;

/**
 *
 * @author Andrew Ribeiro
 */
public class LoginUserViewHelper extends AbstractViewHelper{
    
    User userTrying;

    @Override
    public IHolder getView(FlowContainer fc) {
        super.getView(fc);
        
        User user = new User();
        GenericHolder gh = new GenericHolder();
        Form f = fc.getCr().readEntity(Form.class);
        MultivaluedHashMap mvhm = (MultivaluedHashMap) f.asMap();
        String login;
        String pass;
        
        try {
            login = (String) mvhm.get("login").get(0);
        }catch(NullPointerException npe){
            login = null;
        }
        
        try {
            pass = (String) mvhm.get("password").get(0);
        }catch(NullPointerException npe){
            pass = null;
        }
        
        user.setLogin(login);
        user.setPassword(pass);
        
        userTrying = user;
        
        gh.getEntities().add(user);
        
        loadBusinessRulesBeforeMainFlow();
        
        return gh;
    }

    @Override
    public void loadBusinessRulesBeforeMainFlow() {
        getRulesBeforeMainFlow().add(new ValidateLoginUserCommand());
        getRulesBeforeMainFlow().add(new ExceptionToLoginUserCommand());
        getRulesBeforeMainFlow().add(new ValidateLoginUserStatusCommand());
        getRulesBeforeMainFlow().add(new ValidateLoginUserPasswordCommand(userTrying));
        getRulesBeforeMainFlow().add(new AcceptUserAttributesCommand(new String[]{"department", "groups", "userVisual"}, rejects));
        getRulesBeforeMainFlow().add(new RemoveUserFromPersistenceContextCommand());
        getRulesBeforeMainFlow().add(new StopFlowCommand());
    }
}
