package br.com.hefesto.resources.impl.product.view;

import br.com.hefesto.domain.impl.Product;
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
public class ReadOneProductViewHelper extends AbstractViewHelper{

    @Override
    public IHolder getView(FlowContainer fc) {
        super.getView(fc);
        
        Product p = new Product();
        GenericHolder gh = new GenericHolder();
        SearchModel sm = gh.getSm();
        UriInfo u = fc.getCr().getUriInfo();
        MultivaluedMap<String, String> mvm = u.getPathParameters();
        
        String id;
        Long longId = null;
        
        try {
            id = mvm.get("id").get(0);
            try {
                longId = Long.parseLong(id);
            }catch(NumberFormatException nfe){
                longId = null;
            }
        }catch(NullPointerException npe){
            id = null;
        }
        
        p.setId(longId);
        
        sm.setEntity(p);
        gh.getEntities().add(p);
        
        return gh;
    }

    
}
