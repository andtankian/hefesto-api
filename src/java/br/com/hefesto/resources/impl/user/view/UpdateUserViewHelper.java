package br.com.hefesto.resources.impl.user.view;

import br.com.hefesto.domain.impl.Department;
import br.com.hefesto.domain.impl.User;
import br.com.hefesto.resources.impl.rules.NotifyContentCommand;
import br.com.hefesto.resources.impl.user.rules.AcceptUserAttributesCommand;
import br.com.hefesto.resources.impl.user.rules.ValidateAndMergeUserCommand;
import br.com.wsbasestructure.dto.FlowContainer;
import br.com.wsbasestructure.dto.Result;
import br.com.wsbasestructure.dto.impl.GenericHolder;
import br.com.wsbasestructure.dto.interfaces.IHolder;
import br.com.wsbasestructure.rules.impl.ValidateIDEntityCommand;
import br.com.wsbasestructure.view.abstracts.AbstractViewHelper;
import br.com.wsbasestructure.view.impl.GenericExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.GsonBuilder;
import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author Andrew Ribeiro
 */
public class UpdateUserViewHelper extends AbstractViewHelper {

    @Override
    public IHolder getView(FlowContainer fc) {
        super.getView(fc);
        User u = new User();
        GenericHolder gh = new GenericHolder();
        UriInfo ui = fc.getCr().getUriInfo();
        MultivaluedMap<String, String> mvm = ui.getPathParameters();
        Form f = fc.getCr().readEntity(Form.class);
        MultivaluedHashMap mvhm = (MultivaluedHashMap) f.asMap();
        String id;
        String fullName;
        String login;
        String email;
        String department;
        String password;
        Set groups;
        String type;
        
        try {
            id = mvm.get("id").get(0);
        } catch(NullPointerException n){
            id = null;
        }
        
        try {
            u.setId(Long.parseLong(id));
        }catch(NumberFormatException n){
            u.setId((long)0);
        }
        
        try {
            fullName = (String) mvhm.get("fullName").get(0);
        } catch(NullPointerException n){
            fullName = null;
        }
        
        try {
            login = (String) mvhm.get("login").get(0);
        } catch(NullPointerException n){
            login = null;
        }
        
        try {
            email = (String) mvhm.get("email").get(0);
        } catch(NullPointerException n){
            email = null;
        }
        
        try {
            department = (String) mvhm.get("department").get(0);
        } catch(NullPointerException n){
            department = null;
        }
        
        try {
            password = (String) mvhm.get("password1").get(0);
        } catch(NullPointerException n){
            password = null;
        }
        
        try {
            type = (String) mvhm.get("type").get(0);
        } catch(NullPointerException n){
            type = null;
        }
        
        try {
            groups = new HashSet<>(mvhm.get("groups"));
        } catch(NullPointerException n){
            groups = null;
        }
        Department d = new Department();
        
        try {
            d.setId(Long.parseLong(department));
        }catch(NumberFormatException n){
            d = null;
        }
        
        u.setDepartment(d);
        u.setEmail(email);
        u.setFullName(fullName);
        u.setGroups(groups);
        u.setLogin(login);
        u.setPassword(password);
        u.setType(type);
        
        gh.getEntities().add(u);
        
        this.loadBusinessRulesBeforeMainFlow();
        this.loadBusinessRulesAfterMainFlow();
        
        return gh;
    }

    @Override
    public void loadBusinessRulesBeforeMainFlow() {
        
        getRulesBeforeMainFlow().add(new ValidateIDEntityCommand());
        getRulesBeforeMainFlow().add(new ValidateAndMergeUserCommand());
    }

    @Override
    public void loadBusinessRulesAfterMainFlow() {
        getRulesAfterMainFlow().add(new AcceptUserAttributesCommand(new String[]{"none"}, rejects));
        getRulesAfterMainFlow().add(new NotifyContentCommand(new String[]{"users"}));
    }

}
