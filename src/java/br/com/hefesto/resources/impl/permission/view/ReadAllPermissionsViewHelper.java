package br.com.hefesto.resources.impl.permission.view;

import br.com.hefesto.domain.impl.Permission;
import br.com.hefesto.resources.impl.permission.rules.AcceptPermissionAttributes;
import br.com.wsbasestructure.dto.FlowContainer;
import br.com.wsbasestructure.dto.SearchModel;
import br.com.wsbasestructure.dto.impl.GenericHolder;
import br.com.wsbasestructure.dto.interfaces.IHolder;
import br.com.wsbasestructure.view.abstracts.AbstractViewHelper;

/**
 *
 * @author Andrew Ribeiro
 */
public class ReadAllPermissionsViewHelper extends AbstractViewHelper{

    @Override
    public IHolder getView(FlowContainer fc) {
        super.getView(fc);
        
        Permission p = new Permission();
        GenericHolder gh = new GenericHolder();
        SearchModel sm = gh.getSm();
        sm.setLimit((long)0);
        sm.setOffset((long)0);
        gh.getEntities().add(p);
        
        this.loadBusinessRulesAfterMainFlow();
        return gh;
    }

    @Override
    public void loadBusinessRulesAfterMainFlow() {
        this.getRulesAfterMainFlow().add(new AcceptPermissionAttributes(new String[]{"none"}, rejects));
    }

    
    
    
    
    
    
}
