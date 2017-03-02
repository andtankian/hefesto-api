package br.com.hefesto.resources.impl.service.view;

import br.com.hefesto.domain.impl.SLA;
import br.com.hefesto.domain.impl.Service;
import br.com.hefesto.resources.impl.rules.NotifyContentCommand;
import br.com.wsbasestructure.dto.FlowContainer;
import br.com.wsbasestructure.dto.impl.GenericHolder;
import br.com.wsbasestructure.dto.interfaces.IHolder;
import br.com.wsbasestructure.rules.impl.ValidateAndMergeEntityCommand;
import br.com.wsbasestructure.view.abstracts.AbstractViewHelper;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author Andrew Ribeiro
 */
public class UpdateServiceViewHelper extends AbstractViewHelper {

    @Override
    public IHolder getView(FlowContainer fc) {
        super.getView(fc);

        Service s = new Service();
        SLA sla = new SLA();
        GenericHolder gh = new GenericHolder();
        Form f = fc.getCr().readEntity(Form.class);
        MultivaluedHashMap mvhm = (MultivaluedHashMap) f.asMap();
        UriInfo uri = fc.getCr().getUriInfo();
        MultivaluedMap<String, String> mvm = uri.getPathParameters();

        String id;
        Long longId;
        String name;
        String description;
        String type;
        String days;
        String minutes;
        String hours;
        Integer idays;
        Integer iminutes;
        Integer ihours;
        String status;
        
        try {
            id = (String) mvm.get("id").get(0);
        } catch (NullPointerException e) {
            id = null;
        }
        
        try {
            longId = Long.parseLong(id);
        }catch(NumberFormatException e){
            longId = null;
        }
        
        try {
            status = (String) mvhm.get("status").get(0);
        } catch (NullPointerException e) {
            status = null;
        }

        try {
            name = (String) mvhm.get("name").get(0);
        } catch (NullPointerException e) {
            name = null;
        }
        try {
            description = (String) mvhm.get("description").get(0);
        } catch (NullPointerException e) {
            description = null;
        }
        try {
            type = (String) mvhm.get("type").get(0);
        } catch (NullPointerException e) {
            type = null;
        }
        try {
            days = (String) mvhm.get("days").get(0);
        } catch (NullPointerException e) {
            days = null;
        }
        try {
            minutes = (String) mvhm.get("minutes").get(0);
        } catch (NullPointerException e) {
            minutes = null;
        }
        try {
            hours = (String) mvhm.get("hours").get(0);
        } catch (NullPointerException e) {
            hours = null;
        }
        try {
            idays = Integer.parseInt(days);
        }catch(NumberFormatException e){
            idays = null;
        }
        try {
            iminutes = Integer.parseInt(minutes);
        } catch (NumberFormatException e) {
            iminutes = null;
        }
        try {
            ihours = Integer.parseInt(hours);
        }catch(NumberFormatException e){
            ihours = null;
        }
        
        sla.setDays(idays);
        sla.setHours(ihours);
        sla.setMinutes(iminutes);
        
        s.setId(longId);
        s.setDescription(description);
        s.setName(name);
        s.setSla(sla);
        s.setType(type);
        s.setStatus(status);
        
        gh.getEntities().add(s);
        
        loadBusinessRulesBeforeMainFlow();
        loadBusinessRulesAfterMainFlow();
        
        return gh;        

    }

    @Override
    public void loadBusinessRulesBeforeMainFlow() {
        getRulesBeforeMainFlow().add(new ValidateAndMergeEntityCommand());
    }

    @Override
    public void loadBusinessRulesAfterMainFlow() {
        getRulesAfterMainFlow().add(new NotifyContentCommand(new String[]{"none"}));
    }
    
    

    
}
