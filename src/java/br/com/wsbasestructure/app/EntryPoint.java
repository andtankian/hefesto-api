package br.com.wsbasestructure.app;

import br.com.hefesto.resources.impl.deparment.ResourceDepartment;
import br.com.hefesto.resources.impl.permission.ResourcePermission;
import br.com.wsbasestructure.filter.CORSFilter;
import javax.ws.rs.ApplicationPath;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;


/**
 *
 * @author Andrew Ribeiro
 */
@ApplicationPath("ws")
public class EntryPoint extends ResourceConfig {

    public EntryPoint() {
        super(ResourceHello.class, ResourceDepartment.class, ResourcePermission.class, MultiPartFeature.class, CORSFilter.class);
    }
    
    

}
