package br.com.hefesto.resources.impl.resourcepage;

import br.com.hefesto.resources.impl.resourcepage.view.NewResourcePageViewHelper;
import br.com.hefesto.resources.impl.resourcepage.view.ReadOneByNameResourcePageViewHelper;
import br.com.hefesto.resources.impl.resourcepage.view.UpdateResourcePageViewHelper;
import br.com.wsbasestructure.control.Facade;
import br.com.wsbasestructure.domain.abstracts.AbstractResource;
import br.com.wsbasestructure.dto.FlowContainer;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import org.hibernate.Session;

/**
 * REST Web Service
 *
 * @author Andrew Ribeiro
 */
@Path("pages")
@Produces(MediaType.APPLICATION_JSON)
public class ResourceResourcePage extends AbstractResource {

    @GET
    @Path("{name}")
    public String getResourcePage() {
        return new Facade(new FlowContainer(new ReadOneByNameResourcePageViewHelper(), 
                (Session)httpRequest.getAttribute("session"), 
                cr, httpRequest))
                .process();
    }
    
    @POST
    public String newResourcePage(){
        return new Facade(new FlowContainer(new NewResourcePageViewHelper(), 
                (Session)httpRequest.getAttribute("session"), 
                cr, httpRequest))
                .process();
    }
    
    @PUT
    @Path("{id}")
    public String updateResourcePage(){
        return new Facade(new FlowContainer(new UpdateResourcePageViewHelper(),
                (Session)httpRequest.getAttribute("session"),
                cr, httpRequest))
                .process();
    }

}
