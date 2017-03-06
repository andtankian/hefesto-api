package br.com.hefesto.resources.impl.equipments.view;

import br.com.hefesto.domain.impl.Equipment;
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
public class ReadEquipmentsViewHelper extends AbstractViewHelper {
    
    @Override
    public IHolder getView(FlowContainer fc) {
        super.getView(fc);
        
        Equipment e = new Equipment();
        GenericHolder gh = new GenericHolder();
        SearchModel sm = gh.getSm();
        UriInfo ui = fc.getCr().getUriInfo();
        MultivaluedMap<String, String> mvm = ui.getQueryParameters();
        sm.getBasics(ui);
        
        String search;
        
        try {
            search = mvm.get("search").get(0);
        } catch (NullPointerException n) {
            search = null;
        }
        
        e.setDescription(search);
        e.setName(search);
        e.setPatrimonial(search);
        
        sm.setEntity(e);
        sm.setSearch(search);
        gh.getEntities().add(e);
        
        loadBusinessRulesBeforeMainFlow();
        
        return gh;
    }

    @Override
    public void loadBusinessRulesBeforeMainFlow() {
        getRulesBeforeMainFlow().add(new ValidateSearchEntitiesCommand());
    }
    
    
}
