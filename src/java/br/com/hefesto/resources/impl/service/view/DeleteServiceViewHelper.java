package br.com.hefesto.resources.impl.service.view;

import br.com.hefesto.domain.impl.SLA;
import br.com.hefesto.domain.impl.Service;
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
public class DeleteServiceViewHelper extends AbstractViewHelper{

    @Override
    public IHolder getView(FlowContainer fc) {
        super.getView(fc);
        
        loadBusinessRulesAfterMainFlow();
        loadBusinessRulesBeforeMainFlow();
        Service s = new Service();
        SLA sla = new SLA();
        s.setSla(sla);
        
        return GeneratorGenericHolderToDeleteUtil.generateGenericHolderToDelete(s, fc);
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
