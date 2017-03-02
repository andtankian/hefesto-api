package br.com.hefesto.resources.impl.service.view;

import br.com.hefesto.domain.impl.SLA;
import br.com.hefesto.domain.impl.Service;
import br.com.wsbasestructure.dto.FlowContainer;
import br.com.wsbasestructure.dto.SearchModel;
import br.com.wsbasestructure.dto.impl.GenericHolder;
import br.com.wsbasestructure.dto.interfaces.IHolder;
import br.com.wsbasestructure.rules.impl.ValidateIDEntityCommand;
import br.com.wsbasestructure.view.abstracts.AbstractViewHelper;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author Andrew Ribeiro
 */
public class ReadOneServiceViewHelper extends AbstractViewHelper{

    @Override
    public IHolder getView(FlowContainer fc) {
        super.getView(fc);
        
        Service s = new Service();
        GenericHolder gh = new GenericHolder();
        SearchModel sm = gh.getSm();
        UriInfo u = fc.getCr().getUriInfo();
        MultivaluedMap<String, String> mvm = u.getPathParameters();
        String id;
        Long longId;
        
        try {
            id = mvm.get("id").get(0);
            try {
                longId = Long.parseLong(id);
            }catch(NumberFormatException e){
                longId = (long)-1;
            }
        } catch(NullPointerException e){
            longId = (long)-1;
        }
        
       s.setId(longId);
       sm.setEntity(s);
       gh.getEntities().add(s);
       
       loadBusinessRulesBeforeMainFlow();
       
       return gh;
        
    }

    @Override
    public void loadBusinessRulesBeforeMainFlow() {
        getRulesBeforeMainFlow().add(new ValidateIDEntityCommand());
    }
    
    
    
    
    
}
