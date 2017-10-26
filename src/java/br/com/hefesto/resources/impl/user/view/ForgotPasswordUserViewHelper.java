package br.com.hefesto.resources.impl.user.view;

import br.com.hefesto.domain.impl.User;
import br.com.hefesto.resources.impl.user.rules.EmailfySendRedefintionPasswordCommand;
import br.com.hefesto.resources.impl.user.rules.GenerateAndMergeNewForgotPasswordTokenCommand;
import br.com.hefesto.resources.impl.user.rules.ValidateForgotPasswordCommand;
import br.com.hefesto.resources.impl.user.rules.ValidateUserIdOrLoginCommand;
import br.com.wsbasestructure.dto.FlowContainer;
import br.com.wsbasestructure.dto.impl.GenericHolder;
import br.com.wsbasestructure.dto.interfaces.IHolder;
import br.com.wsbasestructure.view.abstracts.AbstractViewHelper;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MultivaluedHashMap;

/**
 *
 * @author andrew
 */
public class ForgotPasswordUserViewHelper extends AbstractViewHelper{

    @Override
    public IHolder getView(FlowContainer fc) {
        super.getView(fc);
        
        User u = new User();
        GenericHolder gh = new GenericHolder();
        Form f = fc.getCr().readEntity(Form.class);
        MultivaluedHashMap mvhm = (MultivaluedHashMap) f.asMap();
        
        try {
            u.setEmail((String) mvhm.get("email").get(0));
        }catch(NullPointerException npe){
            u.setEmail(null);
        }
        
        gh.getEntities().add(u);
        
        loadBusinessRulesBeforeMainFlow();
        loadBusinessRulesAfterMainFlow();
        
        return gh;
    }

    @Override
    public void loadBusinessRulesBeforeMainFlow() {
        this.getRulesBeforeMainFlow().add(new ValidateForgotPasswordCommand());
        this.getRulesBeforeMainFlow().add(new GenerateAndMergeNewForgotPasswordTokenCommand());
    }

    @Override
    public void loadBusinessRulesAfterMainFlow() {
        this.getRulesAfterMainFlow().add(new EmailfySendRedefintionPasswordCommand());
    }
    
    
    
    
    
    
    
}
