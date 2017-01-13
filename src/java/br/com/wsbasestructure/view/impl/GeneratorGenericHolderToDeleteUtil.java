package br.com.wsbasestructure.view.impl;

import br.com.wsbasestructure.domain.abstracts.Entity;
import br.com.wsbasestructure.dto.FlowContainer;
import br.com.wsbasestructure.dto.impl.GenericHolder;
import br.com.wsbasestructure.dto.interfaces.IHolder;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author Andrew Ribeiro
 */
public class GeneratorGenericHolderToDeleteUtil {
    
    public final static IHolder generateGenericHolderToDelete(Entity e, FlowContainer fc){
        
        GenericHolder gh = new GenericHolder();
        e.setStatus("deactivated");
        UriInfo u = fc.getCr().getUriInfo();
        String id = u.getPathParameters().get("id") != null ? u.getPathParameters().get("id").get(0) : null;
        try {
            e.setId(Long.parseLong(id));
        } catch(NumberFormatException n){
            e.setId((long)0);
        }
        gh.getEntities().add(e);
        
        return gh;
    }
}
