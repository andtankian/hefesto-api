package br.com.hefesto.resources.impl.equipments.view;

import br.com.hefesto.domain.impl.Equipment;
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
public class ReadOneEquipmentViewHelper extends AbstractViewHelper{

    @Override
    public IHolder getView(FlowContainer fc) {
        super.getView(fc);
        
        Equipment e = new Equipment();
        GenericHolder gh = new GenericHolder();
        SearchModel sm = gh.getSm();
        UriInfo ui = fc.getCr().getUriInfo();
        MultivaluedMap<String, String> mvm = ui.getPathParameters();
        String id;
        Long longId;
        
        try {
            id = mvm.get("id").get(0);
        }catch(NullPointerException n){
            id = null;
        }
        
        try {
            longId = Long.parseLong(id);
        }catch(NumberFormatException b){
            longId = null;
        }
        
        e.setId(longId);
        gh.getEntities().add(e);
        sm.setEntity(e);
        
        loadBusinessRulesBeforeMainFlow();
        
        return gh;        
    }

    @Override
    public void loadBusinessRulesBeforeMainFlow() {
        getRulesBeforeMainFlow().add(new ValidateIDEntityCommand());
    }
    
    
}
