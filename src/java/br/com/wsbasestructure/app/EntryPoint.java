package br.com.wsbasestructure.app;

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
        super(ResourceHello.class, MultiPartFeature.class);
    }
    
    

}
