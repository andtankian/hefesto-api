package br.com.hefesto.resources.impl.permission.view;

import br.com.hefesto.domain.impl.Permission;
import br.com.hefesto.resources.impl.permission.rules.AcceptPermissionAttributes;
import br.com.wsbasestructure.dto.FlowContainer;
import br.com.wsbasestructure.dto.interfaces.IHolder;
import br.com.wsbasestructure.rules.impl.ValidateAndMergeEntityCommand;
import br.com.wsbasestructure.view.abstracts.AbstractViewHelper;
import br.com.wsbasestructure.view.impl.GeneratorGenericHolderToDeleteUtil;

/**
 *
 * @author Andrew Ribeiro
 */
public class DeletePermissionViewHelper extends AbstractViewHelper{

    @Override
    public IHolder getView(FlowContainer fc) {
        super.getView(fc);
        
        Permission p = new Permission();
        
        loadBusinessRulesBeforeMainFlow();
        loadBusinessRulesAfterMainFlow();
        
        return GeneratorGenericHolderToDeleteUtil.generateGenericHolderToDelete(p, fc);
    }

    @Override
    public void loadBusinessRulesBeforeMainFlow() {
        this.getRulesBeforeMainFlow().add(new ValidateAndMergeEntityCommand());
    }

    @Override
    public void loadBusinessRulesAfterMainFlow() {
        this.getRulesAfterMainFlow().add(new AcceptPermissionAttributes(new String[]{"none"}, rejects));
    }
    
    
    
    
    
}
