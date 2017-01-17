package br.com.hefesto.resources.impl.user.view;

import br.com.hefesto.domain.impl.User;
import br.com.hefesto.resources.impl.user.AcceptUserAttributesCommand;
import br.com.wsbasestructure.dto.FlowContainer;
import br.com.wsbasestructure.dto.SearchModel;
import br.com.wsbasestructure.dto.impl.GenericHolder;
import br.com.wsbasestructure.dto.interfaces.IHolder;
import br.com.wsbasestructure.view.abstracts.AbstractViewHelper;

/**
 *
 * @author Andrew Ribeiro
 */
public class ReadAllUsersViewHelper extends AbstractViewHelper{

    @Override
    public IHolder getView(FlowContainer fc) {
        super.getView(fc);
        
        User u = new User();
        GenericHolder gh = new GenericHolder();
        SearchModel sm = gh.getSm();
        sm.setLimit((long)0);
        sm.setOffset((long)0);
        
        gh.getEntities().add(u);
        
        loadBusinessRulesAfterMainFlow();
        return gh;
        
        
        
    }

    @Override
    public void loadBusinessRulesAfterMainFlow() {
        this.getRulesAfterMainFlow().add(new AcceptUserAttributesCommand(new String[]{"none"}, rejects));
    }
    
}
