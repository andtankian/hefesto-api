package br.com.hefesto.resources.impl.user.view;

import br.com.hefesto.domain.impl.User;
import br.com.hefesto.resources.impl.rules.GenericAcceptAttributes;
import br.com.hefesto.resources.impl.rules.ValidateSearchEntitiesCommand;
import br.com.wsbasestructure.dto.FlowContainer;
import br.com.wsbasestructure.dto.SearchModel;
import br.com.wsbasestructure.dto.impl.GenericHolder;
import br.com.wsbasestructure.dto.interfaces.IHolder;
import br.com.wsbasestructure.view.abstracts.AbstractViewHelper;
import java.util.Arrays;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author Andrew Ribeiro
 */
public class ReadUsersViewHelper extends AbstractViewHelper{

    private String[] accepts;
    @Override
    public IHolder getView(FlowContainer fc) {
        super.getView(fc);
        
        User u = new User();
        GenericHolder gh = new GenericHolder();
        SearchModel sm = gh.getSm();
        UriInfo ui = fc.getCr().getUriInfo();
        MultivaluedMap<String, String> mvm = ui.getQueryParameters();
        
        Object[] oa;
        try {
            oa = mvm.get("accepts").toArray();
            this.accepts = Arrays.copyOf(oa, oa.length, String[].class);
        }catch(Exception e){
            accepts = new String[]{"none"};
        }
        
        String search;
        
        try {
            search = mvm.get("search").get(0);
        }catch(NullPointerException e){
            search = null;
        }
        
        u.setEmail(search);
        u.setFullName(search);
        
        sm.getBasics(ui);
        sm.setSearch(search);
        sm.setEntity(u);
        gh.getEntities().add(u);
        
        loadBusinessRulesBeforeMainFlow();
        loadBusinessRulesAfterMainFlow();
        return gh;
        
    }

    @Override
    public void loadBusinessRulesBeforeMainFlow() {
        getRulesBeforeMainFlow().add(new ValidateSearchEntitiesCommand());
    }

    @Override
    public void loadBusinessRulesAfterMainFlow() {
        getRulesAfterMainFlow().add(new GenericAcceptAttributes(accepts, rejects, new String[]{"department", "groups", "users"}));
    }
    
    
    
    
    
}
