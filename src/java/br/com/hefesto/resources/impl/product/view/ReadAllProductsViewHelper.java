package br.com.hefesto.resources.impl.product.view;

import br.com.hefesto.domain.impl.Product;
import br.com.wsbasestructure.dto.FlowContainer;
import br.com.wsbasestructure.dto.SearchModel;
import br.com.wsbasestructure.dto.impl.GenericHolder;
import br.com.wsbasestructure.dto.interfaces.IHolder;
import br.com.wsbasestructure.view.abstracts.AbstractViewHelper;

/**
 *
 * @author Andrew Ribeiro
 */
public class ReadAllProductsViewHelper extends AbstractViewHelper{

    @Override
    public IHolder getView(FlowContainer fc) {
        super.getView(fc);
        
        Product p = new Product();
        GenericHolder gh = new GenericHolder();
        SearchModel sm = gh.getSm();
        sm.setLimit((long)0);
        sm.setOffset((long)0);
        sm.setEntity(p);
        gh.getEntities().add(p);
        
        return gh;
    }
    
    
    
}
