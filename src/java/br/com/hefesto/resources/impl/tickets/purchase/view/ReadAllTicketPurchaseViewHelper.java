package br.com.hefesto.resources.impl.tickets.purchase.view;

import br.com.hefesto.domain.impl.Ticket;
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
public class ReadAllTicketPurchaseViewHelper extends AbstractViewHelper{

    @Override
    public void loadBusinessRulesAfterMainFlow() {
        getRulesAfterMainFlow().add(new GenericAcceptAttributes(new String[]{"none"}, rejects, new String[]{"interactions", "groups", "requestedProducts", "ticket"}));
    }

    @Override
    public IHolder getView(FlowContainer fc) {
        super.getView(fc);
        
        Ticket t = new Ticket();
        t.setType(Ticket.PURCHASE);
        GenericHolder gh = new GenericHolder();
        SearchModel sm = gh.getSm();
        
        sm.setLimit((long)0);
        sm.setOffset((long)0);
        
        gh.getEntities().add(t);
        sm.setEntity(t);
        
        loadBusinessRulesAfterMainFlow();
        
        
        return gh;
    }
    
    
    
}
