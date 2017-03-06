package br.com.hefesto.resources.impl.equipments.view;

import br.com.hefesto.domain.impl.Equipment;
import br.com.hefesto.resources.impl.rules.NotifyContentCommand;
import br.com.wsbasestructure.dto.FlowContainer;
import br.com.wsbasestructure.dto.impl.GenericHolder;
import br.com.wsbasestructure.dto.interfaces.IHolder;
import br.com.wsbasestructure.rules.impl.ValidateAndMergeEntityCommand;
import br.com.wsbasestructure.rules.impl.ValidateIDEntityCommand;
import br.com.wsbasestructure.view.abstracts.AbstractViewHelper;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author Andrew Ribeiro
 */
public class UpdateEquipmentViewHelper extends AbstractViewHelper{

    @Override
    public IHolder getView(FlowContainer fc) {
        super.getView(fc);
        
        Equipment e = new Equipment();
        GenericHolder gh = new GenericHolder();
        UriInfo ui = fc.getCr().getUriInfo();
        MultivaluedMap<String, String> mvm = ui.getPathParameters();
        Form f = fc.getCr().readEntity(Form.class);
        
        MultivaluedHashMap mvhm = (MultivaluedHashMap) f.asMap();
        
        String id;
        Long longId;
        
        try {
            id = mvm.get("id").get(0);
        }catch(NullPointerException n){
            id = null;
        }
        
        try {
            longId = Long.parseLong(id);
        }catch(NumberFormatException n){
            longId = null;
        }
        
        e.setId(longId);
        
        String name;
        String description;
        String patrimonial;
        String status;
        
        try {
            name = (String) mvhm.get("name").get(0);
        }catch(NullPointerException n){
            name = null;
        }
        
        try {
            description = (String) mvhm.get("description").get(0);
        }catch(NullPointerException n){
            description = null;
        }
        
        try {
            patrimonial = (String) mvhm.get("patrimonial").get(0);
        }catch(NullPointerException n){
            patrimonial = null;
        }
        
        try {
            status = (String) mvhm.get("status").get(0);
        }catch(NullPointerException n){
            status = null;
        }
        
        e.setDescription(description);
        e.setName(name);
        e.setPatrimonial(patrimonial);
        e.setStatus(status);
        
        gh.getEntities().add(e);
        loadBusinessRulesBeforeMainFlow();
        loadBusinessRulesAfterMainFlow();
        
        return gh;
    }

    @Override
    public void loadBusinessRulesBeforeMainFlow() {
        getRulesBeforeMainFlow().add(new ValidateIDEntityCommand());
        getRulesBeforeMainFlow().add(new ValidateAndMergeEntityCommand());
    }

    @Override
    public void loadBusinessRulesAfterMainFlow() {
        getRulesAfterMainFlow().add(new NotifyContentCommand(new String[]{"none"}));
    }
    
    
    
    
    
}
