package br.com.hefesto.resources.impl.equipments.view;

import br.com.hefesto.domain.impl.Equipment;
import br.com.hefesto.resources.impl.equipments.rules.ValidateEquipmentDataCommand;
import br.com.hefesto.resources.impl.rules.NotifyContentCommand;
import br.com.wsbasestructure.dto.FlowContainer;
import br.com.wsbasestructure.dto.impl.GenericHolder;
import br.com.wsbasestructure.dto.interfaces.IHolder;
import br.com.wsbasestructure.view.abstracts.AbstractViewHelper;
import java.sql.Timestamp;
import java.util.Calendar;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MultivaluedHashMap;

/**
 *
 * @author Andrew Ribeiro
 */
public class NewEquipmentViewHelper extends AbstractViewHelper{

    @Override
    public IHolder getView(FlowContainer fc) {
        super.getView(fc);
        
        Equipment e = new Equipment();
        GenericHolder gh = new GenericHolder();
        Form f = fc.getCr().readEntity(Form.class);
        MultivaluedHashMap mvhm = (MultivaluedHashMap) f.asMap();
        
        String description;
        String name;
        String patrimonial;
        
        try {
            description = (String) mvhm.get("description").get(0);
        } catch(NullPointerException n){
            description = null;
        }
        
        try {
            name = (String) mvhm.get("name").get(0);
        } catch(NullPointerException n){
            name = null;
        }
        
        try {
            patrimonial = (String) mvhm.get("patrimonial").get(0);
        } catch(NullPointerException n){
            patrimonial = null;
        }
        
        e.setDateReg(new Timestamp(Calendar.getInstance().getTimeInMillis()));
        e.setDescription(description);
        e.setName(name);
        e.setPatrimonial(patrimonial);
        
        gh.getEntities().add(e);
        
        loadBusinessRulesBeforeMainFlow();
        loadBusinessRulesAfterMainFlow();
        
        return gh;
    }

    @Override
    public void loadBusinessRulesBeforeMainFlow() {
        getRulesBeforeMainFlow().add(new ValidateEquipmentDataCommand());
    }
    
    @Override
    public void loadBusinessRulesAfterMainFlow() {
        getRulesAfterMainFlow().add(new NotifyContentCommand(new String[]{"none"}));
    }
    
    
    
}
