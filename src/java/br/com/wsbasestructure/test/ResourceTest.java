package br.com.wsbasestructure.test;

import br.com.wsbasestructure.control.Facade;
import br.com.wsbasestructure.domain.abstracts.AbstractResource;
import br.com.wsbasestructure.dto.FlowContainer;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author Andrew Ribeiro
 */
@Path("test")
public class ResourceTest extends AbstractResource {

    /**
     * Retrieves representation of an instance of
     * br.com.wsbasestructure.test.ResourceTest
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {

        return new Facade().process(
                new FlowContainer(
                        new TestViewHelper(), cr, httpRequest)
        );
    }
}
