package br.com.hefesto.resources.impl.deparment.view;

import br.com.hefesto.domain.impl.Department;
import br.com.hefesto.resources.impl.department.rules.impl.ValidateSearchDepartmentsCommand;
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
public class ReadDepartmentViewHelper extends AbstractViewHelper{

    @Override
    public IHolder getView(FlowContainer fc) {
        super.getView(fc);
        
        Department d = new Department();
        GenericHolder gh = new GenericHolder();
        SearchModel sm = gh.getSm();
        UriInfo u = fc.getCr().getUriInfo();
        MultivaluedMap<String, String> mvhm =  u.getQueryParameters();
        String search;
        try {
            search = (String)mvhm.get("search").get(0);
        } catch (Exception e){
            search = null;
        }
        sm.getBasics(u);
        sm.setSearch(search);
        d.setDescription(sm.getSearch());
        d.setName(sm.getSearch());
        
        sm.setEntity(d);
        
        gh.getEntities().add(d);
        
        loadBusinessRulesBeforeMainFlow();
        
        return gh;
    }

    @Override
    public void loadBusinessRulesBeforeMainFlow() {
        super.loadBusinessRulesBeforeMainFlow();
        this.getRulesBeforeMainFlow().add(new ValidateSearchDepartmentsCommand());
    }
    
    
    
}
