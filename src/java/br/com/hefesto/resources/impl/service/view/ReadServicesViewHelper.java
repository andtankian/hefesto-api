package br.com.hefesto.resources.impl.service.view;

import br.com.hefesto.domain.impl.SLA;
import br.com.hefesto.domain.impl.Service;
import br.com.hefesto.resources.impl.rules.ValidateSearchEntitiesCommand;
import br.com.wsbasestructure.dto.FlowContainer;
import br.com.wsbasestructure.dto.SearchModel;
import br.com.wsbasestructure.dto.impl.GenericHolder;
import br.com.wsbasestructure.dto.interfaces.IHolder;
import br.com.wsbasestructure.view.abstracts.AbstractViewHelper;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author Andrew Ribeiro
 */
public class ReadServicesViewHelper extends AbstractViewHelper{

    @Override
    public IHolder getView(FlowContainer fc) {
        super.getView(fc);
        
        Service s = new Service();
        SLA sla = new SLA();
        GenericHolder gh = new GenericHolder();
        SearchModel sm = gh.getSm();
        UriInfo u = fc.getCr().getUriInfo();
        MultivaluedMap<String, String> mvm = u.getQueryParameters();
        
        String search;
        
        try {
            search = mvm.get("search").get(0);
        } catch(NullPointerException e){
            search = null;
        }
        
        sm.getBasics(u);
        sm.setSearch(search);
        s.setDescription(search);
        s.setName(search);
        sm.setEntity(s);
        gh.getEntities().add(s);
        
        loadBusinessRulesBeforeMainFlow();
        return gh;
    }

    @Override
    public void loadBusinessRulesBeforeMainFlow() {
        getRulesBeforeMainFlow().add(new ValidateSearchEntitiesCommand());
    }
    
    
    
    
    
}
