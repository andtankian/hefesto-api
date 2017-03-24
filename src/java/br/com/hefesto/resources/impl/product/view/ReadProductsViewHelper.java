package br.com.hefesto.resources.impl.product.view;

import br.com.hefesto.domain.impl.Product;
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
public class ReadProductsViewHelper extends AbstractViewHelper{

    @Override
    public IHolder getView(FlowContainer fc) {
        super.getView(fc);
        
        Product p = new Product();
        GenericHolder gh = new GenericHolder();
        SearchModel sm = gh.getSm();
        UriInfo u = fc.getCr().getUriInfo();
        sm.getBasics(u);
        MultivaluedMap<String, String> mvm = u.getQueryParameters();
        
        String search;
        
        try {
            search = mvm.get("search").get(0);
        }catch(NullPointerException npe) {
            search = null;
        }
        
        sm.setSearch(search);
        sm.setEntity(p);
        
        gh.getEntities().add(p);
        
        loadBusinessRulesBeforeMainFlow();
        
        return gh;
    }


    @Override
    public void loadBusinessRulesBeforeMainFlow() {
        getRulesBeforeMainFlow().add(new ValidateSearchEntitiesCommand());
    }
    
   
}
