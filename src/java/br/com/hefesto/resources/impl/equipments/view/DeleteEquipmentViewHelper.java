package br.com.hefesto.resources.impl.equipments.view;

import br.com.hefesto.domain.impl.Equipment;
import br.com.hefesto.resources.impl.rules.NotifyContentCommand;
import br.com.wsbasestructure.dto.FlowContainer;
import br.com.wsbasestructure.dto.interfaces.IHolder;
import br.com.wsbasestructure.rules.impl.ValidateAndMergeEntityCommand;
import br.com.wsbasestructure.view.abstracts.AbstractViewHelper;
import br.com.wsbasestructure.view.impl.GeneratorGenericHolderToDeleteUtil;

/**
 *
 * @author Andrew Ribeiro
 */
public class DeleteEquipmentViewHelper extends AbstractViewHelper{

    @Override
    public IHolder getView(FlowContainer fc) {
        super.getView(fc);
        
        Equipment e = new Equipment();
        
        loadBusinessRulesBeforeMainFlow();
        loadBusinessRulesAfterMainFlow();
        
        return GeneratorGenericHolderToDeleteUtil.generateGenericHolderToDelete(e, fc);
    }

    @Override
    public void loadBusinessRulesBeforeMainFlow() {
        getRulesBeforeMainFlow().add(new ValidateAndMergeEntityCommand());
    }

    @Override
    public void loadBusinessRulesAfterMainFlow() {
        getRulesAfterMainFlow().add(new NotifyContentCommand(new String[]{"none"}));
    }
    
    
}
