package br.com.hefesto.resources.impl.service.view;

import br.com.hefesto.domain.impl.SLA;
import br.com.hefesto.domain.impl.Service;
import br.com.hefesto.resources.impl.rules.GenericAcceptAttributes;
import br.com.wsbasestructure.dto.FlowContainer;
import br.com.wsbasestructure.dto.SearchModel;
import br.com.wsbasestructure.dto.impl.GenericHolder;
import br.com.wsbasestructure.dto.interfaces.IHolder;
import br.com.wsbasestructure.view.abstracts.AbstractViewHelper;

/**
 *
 * @author Andrew Ribeiro
 */
public class ReadAllServicesViewHelper extends AbstractViewHelper{

    @Override
    public IHolder getView(FlowContainer fc) {
        super.getView(fc);
        
        Service s = new Service();
        SLA sla = new SLA();
        GenericHolder gh = new GenericHolder();
        SearchModel sm = gh.getSm();
        sm.setLimit((long)0);
        sm.setOffset((long)0);
        
        gh.getEntities().add(s);
        
        return gh;
    }
    
    
    
}
