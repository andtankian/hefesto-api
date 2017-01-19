package br.com.hefesto.resources.impl.user.rules;

import br.com.hefesto.domain.impl.Department;
import br.com.hefesto.domain.impl.Permission;
import br.com.hefesto.domain.impl.User;
import br.com.wsbasestructure.dto.FlowContainer;
import br.com.wsbasestructure.dto.Message;
import br.com.wsbasestructure.dto.Result;
import br.com.wsbasestructure.dto.interfaces.IHolder;
import br.com.wsbasestructure.rules.interfaces.ICommand;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.Session;

/**
 *
 * @author Andrew Ribeiro
 */
public class ValidateUserDataCommand implements ICommand {

    @Override
    public IHolder exe(IHolder holder, FlowContainer flowContainer) {
        User u = (User) holder.getEntities().get(0);
        Message m = new Message();
        /*Empty and Invalid Validation*/
        boolean valid = true;
        if (u.getDepartment().getId() == null
                || u.getDepartment().getId() == 0) {
            m.setError("invalid department id");
            flowContainer.getFc().setMustContinue(false);
            valid = false;
        } else if (u.getEmail() == null || u.getEmail().isEmpty()) {
            m.setError("empty email");
            flowContainer.getFc().setMustContinue(false);
            valid = false;
        } else if (u.getFullName() == null || u.getFullName().isEmpty()) {
            m.setError("empty full name");
            flowContainer.getFc().setMustContinue(false);
            valid = false;
        } else if (u.getLogin() == null || u.getLogin().isEmpty()) {
            m.setError("empty login");
            flowContainer.getFc().setMustContinue(false);
            valid = false;
        } else if (u.getPassword() == null || u.getPassword().isEmpty()) {
            m.setError("empty password");
            flowContainer.getFc().setMustContinue(false);
            valid = false;
        } else if (u.getPermissions() == null) {
            m.setError("empty permissions");
            flowContainer.getFc().setMustContinue(false);
            valid = false;
        }

        if (!valid) {
            flowContainer.getResult().setStatus(Result.ERROR);
            flowContainer.getResult().setMessage(m);
            flowContainer.getResult().setHolder(holder);
            return holder;
        }
        /*Existence of Entities Validation*/
        Session s = flowContainer.getSession();
        Department depLoaded = (Department) s.get(Department.class, u.getDepartment().getId());
        if (depLoaded == null) {
            m.setError("department doesn't exist");
            flowContainer.getFc().setMustContinue(false);
            flowContainer.getResult().setStatus(Result.ERROR);
            flowContainer.getResult().setMessage(m);
            return holder;
        } else {
            u.setDepartment(depLoaded);
        }

        /*Verifying if permissions exists and all the list has a valid ID do get in the database*/
        Set permissions = u.getPermissions();
        u.setPermissions(new HashSet<>());
        if (permissions == null || permissions.isEmpty()) {
            m.setError("permissions empty");
            flowContainer.getFc().setMustContinue(false);
            flowContainer.getResult().setStatus(Result.ERROR);
            flowContainer.getResult().setMessage(m);
            return holder;
        } else {
            Long idPerm;
            for (Object permission : permissions) {
                try {
                    idPerm = Long.parseLong((String) permission);
                    try {
                        Permission p = (Permission) s.get(Permission.class, (long) idPerm);
                        if (p == null) {
                            m.setError(new StringBuilder("permission ").append(idPerm).append(" doesn't exist").toString());
                            flowContainer.getFc().setMustContinue(false);
                            flowContainer.getResult().setStatus(Result.ERROR);
                            flowContainer.getResult().setMessage(m);
                            return holder;
                        } else {
                            u.getPermissions().add(p);
                        }
                    } catch (Exception e) {
                        m.setError(new StringBuilder("unknown fatal error ").append(e.getMessage()).toString());
                        flowContainer.getFc().setMustContinue(false);
                        flowContainer.getResult().setStatus(Result.FATAL_ERROR);
                        flowContainer.getResult().setMessage(m);
                        return holder;
                    }
                } catch (NumberFormatException n) {
                    m.setError("permission id invalid");
                    flowContainer.getFc().setMustContinue(false);
                    flowContainer.getResult().setStatus(Result.ERROR);
                    flowContainer.getResult().setMessage(m);
                    return holder;
                }
            }
        }
        flowContainer.getResult().setHolder(holder);

        return holder;
    }

}
