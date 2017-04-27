package br.com.hefesto.resources.impl.user.rules;

import br.com.hefesto.domain.impl.User;
import br.com.wsbasestructure.dto.FlowContainer;
import br.com.wsbasestructure.dto.interfaces.IHolder;
import br.com.wsbasestructure.rules.interfaces.ICommand;

/**
 *
 * @author Andrew Ribeiro
 */
public class RemoveUserFromPersistenceContextCommand implements ICommand {

    @Override
    public IHolder exe(IHolder holder, FlowContainer flowContainer) {

        User user = (User) holder.getEntities().get(0);
        User outOfContext = new User();
        
        outOfContext.setDateReg(user.getDateReg());
        outOfContext.setDepartment(user.getDepartment());
        outOfContext.setEmail(user.getEmail());
        outOfContext.setFullName(user.getFullName());
        outOfContext.setGroups(user.getGroups());
        outOfContext.setId(user.getId());
        outOfContext.setLogin(user.getLogin());
        outOfContext.setPassword(null);
        outOfContext.setStatus(user.getStatus());
        outOfContext.setType(user.getType());
        outOfContext.setUserVisual(user.getUserVisual());
        
        holder.getEntities().set(0, outOfContext);
        
        return holder;
    }

}
