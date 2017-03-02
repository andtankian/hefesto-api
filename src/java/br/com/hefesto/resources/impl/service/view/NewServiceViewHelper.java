package br.com.hefesto.resources.impl.service.view;

import br.com.hefesto.domain.impl.SLA;
import br.com.hefesto.domain.impl.Service;
import br.com.hefesto.resources.impl.rules.NotifyContentCommand;
import br.com.hefesto.resources.impl.service.rules.ValidateServiceDataCommand;
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
public class NewServiceViewHelper extends AbstractViewHelper{

    @Override
    public IHolder getView(FlowContainer fc) {
        super.getView(fc);
        
        Service s = new Service();
        SLA sla = new SLA();
        GenericHolder gh = new GenericHolder();
        
        Form f = fc.getCr().readEntity(Form.class);
        
        MultivaluedHashMap mvhm = (MultivaluedHashMap) f.asMap();
        
        String type;
        String name;
        String description;
        String days;
        String hours;
        String minutes;
        Integer idays;
        Integer iminutes;
        Integer ihours;
        
        try {
            type = (String)mvhm.get("type").get(0);
        }catch(NullPointerException e){
            type = null;
        }
        
        try {
            name = (String)mvhm.get("name").get(0);
        }catch(NullPointerException e){
            name = null;
        }
        
        try {
            description = (String)mvhm.get("description").get(0);
        }catch(NullPointerException e){
            description = null;
        }
        
        try {
            days = (String)mvhm.get("days").get(0);
        }catch(NullPointerException e){
            days = null;
        }
        
        try {
            hours = (String)mvhm.get("hours").get(0);
        }catch(NullPointerException e){
            hours = null;
        }
        
        try {
            minutes = (String)mvhm.get("minutes").get(0);
        }catch(NullPointerException e){
            minutes = null;
        }
        
        try {
           idays = Integer.parseInt(days);
        }catch(NumberFormatException e){
            idays = 0;
        }
        
        try {
            ihours = Integer.parseInt(hours);
        }catch(NumberFormatException e){
            ihours = 0;
        }
        
        try {
           iminutes = Integer.parseInt(minutes);
        }catch(NumberFormatException e){
            iminutes = 0;
        }
        
        sla.setDays(idays);
        sla.setHours(ihours);
        sla.setMinutes(iminutes);
        
        s.setSla(sla);
        s.setDateReg(new Timestamp(Calendar.getInstance().getTimeInMillis()));
        s.setDescription(description);
        s.setName(name);
        s.setType(type);
        
        gh.getEntities().add(s);
        
        loadBusinessRulesBeforeMainFlow();
        loadBusinessRulesAfterMainFlow();
        return gh;
    }

    @Override
    public void loadBusinessRulesBeforeMainFlow() {
        getRulesBeforeMainFlow().add(new ValidateServiceDataCommand());        
    }

    @Override
    public void loadBusinessRulesAfterMainFlow() {
        getRulesAfterMainFlow().add(new NotifyContentCommand(new String[]{"none"}));
    }
    
    
}
