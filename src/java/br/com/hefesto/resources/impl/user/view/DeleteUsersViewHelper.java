package br.com.hefesto.resources.impl.user.view;

import br.com.hefesto.domain.impl.User;
import br.com.hefesto.resources.impl.rules.GenericAcceptAttributes;
import br.com.hefesto.resources.impl.rules.NotifyContentCommand;
import br.com.wsbasestructure.dto.FlowContainer;
import br.com.wsbasestructure.dto.impl.GenericHolder;
import br.com.wsbasestructure.dto.interfaces.IHolder;
import br.com.wsbasestructure.rules.impl.ValidateAndMergeEntityCommand;
import br.com.wsbasestructure.view.abstracts.AbstractViewHelper;
import br.com.wsbasestructure.view.impl.GeneratorGenericHolderToDeleteUtil;

/**
 *
 * @author Andrew Ribeiro
 */
public class DeleteUsersViewHelper extends AbstractViewHelper {

    @Override
    public IHolder getView(FlowContainer fc) {
        super.getView(fc);

        User u = new User();

        loadBusinessRulesBeforeMainFlow();
        loadBusinessRulesAfterMainFlow();
        return GeneratorGenericHolderToDeleteUtil.generateGenericHolderToDelete(u, fc);
    }

    @Override
    public void loadBusinessRulesBeforeMainFlow() {
        getRulesBeforeMainFlow().add(new ValidateAndMergeEntityCommand());
    }

    @Override
    public void loadBusinessRulesAfterMainFlow() {
        getRulesAfterMainFlow().add(new GenericAcceptAttributes(new String[]{"none"}, rejects, new String[]{"groups", "users", "department"}));
        getRulesAfterMainFlow().add(new NotifyContentCommand(new String[]{"groups", "users", "department"}));
    }

}
