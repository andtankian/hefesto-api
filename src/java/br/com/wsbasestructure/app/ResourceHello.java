package br.com.wsbasestructure.app;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author Andrew Ribeiro
 */
@Path("hello")
public class ResourceHello {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of ResourceHello
     */
    public ResourceHello() {
    }

    /**
     * Retrieves representation of an instance of br.com.wsbasestructure.app.ResourceHello
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
        return "Hello, it's working!";
    }
}
