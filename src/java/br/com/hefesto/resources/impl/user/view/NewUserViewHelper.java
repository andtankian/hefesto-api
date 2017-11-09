package br.com.hefesto.resources.impl.user.view;

import br.com.hefesto.domain.impl.Department;
import br.com.hefesto.domain.impl.User;
import br.com.hefesto.domain.impl.UserConfig;
import br.com.hefesto.domain.impl.UserVisual;
import br.com.hefesto.resources.impl.rules.NotifyContentCommand;
import br.com.hefesto.resources.impl.user.rules.AcceptUserAttributesCommand;
import br.com.hefesto.resources.impl.user.rules.EncryptUserPasswordCommand;
import br.com.hefesto.resources.impl.user.rules.PermissionAssociationsPersistenceHelperCommand;
import br.com.hefesto.resources.impl.user.rules.ValidateUserDataCommand;
import br.com.wsbasestructure.dto.FlowContainer;
import br.com.wsbasestructure.dto.impl.GenericHolder;
import br.com.wsbasestructure.dto.interfaces.IHolder;
import br.com.wsbasestructure.view.abstracts.AbstractViewHelper;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MultivaluedHashMap;

/**
 *
 * @author Andrew Ribeiro
 */
public class NewUserViewHelper extends AbstractViewHelper {

    @Override
    public IHolder getView(FlowContainer fc) {
        super.getView(fc);

        GenericHolder gh = new GenericHolder();
        User u = new User();
        UserVisual uv = new UserVisual();
        UserConfig uc = new UserConfig();
        Form f = (Form) fc.getCr().readEntity(Form.class);
        MultivaluedHashMap mvhm = (MultivaluedHashMap) f.asMap();
        String fullName;
        String login;
        String password;
        String email;
        String type;
        String department;
        Set permissions;
        try {
            fullName = (String) mvhm.get("fullName").get(0);
        } catch (NullPointerException n) {
            fullName = null;
        }
        try {
            login = (String) mvhm.get("login").get(0);
        } catch (NullPointerException n) {
            login = null;
        }
        try {
            password = (String) mvhm.get("password1").get(0);
        } catch (NullPointerException n) {
            password = null;
        }
        try {
            email = (String)mvhm.get("email").get(0);
        } catch (NullPointerException n) {
            email = null;
        }
        try {
            department = (String)mvhm.get("department").get(0);
        } catch (NullPointerException n) {
            department = null;
        }
        
        try {
            type = (String)mvhm.get("type").get(0);
        }catch(NullPointerException n){
            type = null;
        }
        Long idDep;
        try {
            idDep = Long.parseLong(department);
        } catch(NumberFormatException n){
            idDep = (long)0;
        }
        
        try {
            permissions = new HashSet<>(mvhm.get("groups"));
        } catch(NullPointerException n){
            permissions = null;
        }
        
        Department d = new Department();
        d.setId(idDep);
        
        u.setDateReg(new Timestamp(Calendar.getInstance().getTimeInMillis()));
        u.setDepartment(d);
        u.setEmail(email);
        u.setFullName(fullName);
        u.setLogin(login);
        u.setPassword(password);
        u.setGroups(permissions);
        u.setType(type);
        
        uv.setDateReg(u.getDateReg());
        uv.setLandscape("/wsbasestructure/images/images/no_ls.jpg");
        uv.setProfile("/wsbasestructure/images/images/no_user.jpg");
        u.setUserVisual(uv);
        u.setUserConfig(uc);
        
        gh.getEntities().add(u);
        
        this.loadBusinessRulesBeforeMainFlow();
        this.loadBusinessRulesAfterMainFlow();
        
        return gh;
    }

    @Override
    public void loadBusinessRulesBeforeMainFlow() {
        
        this.getRulesBeforeMainFlow().add(new ValidateUserDataCommand());
        this.getRulesBeforeMainFlow().add(new EncryptUserPasswordCommand());
    }

    @Override
    public void loadBusinessRulesAfterMainFlow() {
        this.getRulesAfterMainFlow().add(new PermissionAssociationsPersistenceHelperCommand());
        this.getRulesAfterMainFlow().add(new AcceptUserAttributesCommand(new String[]{"groups", ""}, rejects));
        this.getRulesAfterMainFlow().add(new NotifyContentCommand(new String[]{"users"}));
    }
    
    
    
    

}
