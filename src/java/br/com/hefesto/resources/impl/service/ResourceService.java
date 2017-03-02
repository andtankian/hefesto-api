package br.com.hefesto.resources.impl.service;

import br.com.hefesto.resources.impl.service.view.DeleteServiceViewHelper;
import br.com.hefesto.resources.impl.service.view.NewServiceViewHelper;
import br.com.hefesto.resources.impl.service.view.ReadAllServicesViewHelper;
import br.com.hefesto.resources.impl.service.view.ReadOneServiceViewHelper;
import br.com.hefesto.resources.impl.service.view.ReadServicesViewHelper;
import br.com.hefesto.resources.impl.service.view.UpdateServiceViewHelper;
import br.com.wsbasestructure.control.Facade;
import br.com.wsbasestructure.domain.abstracts.AbstractResource;
import br.com.wsbasestructure.dto.FlowContainer;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.hibernate.Session;

/**
 * REST Web Service
 *
 * @author Andrew Ribeiro
 */
@Path("services")
@Produces(MediaType.APPLICATION_JSON)
public class ResourceService extends AbstractResource{
    
    @GET
    public String getServices(){
        return new Facade(new FlowContainer(new ReadServicesViewHelper(),
                (Session)httpRequest.getAttribute("session"),
                cr, httpRequest))
                .process();
    }
    
    @GET
    @Path("/all")
    public String getAllServices(){
        return new Facade(new FlowContainer(new ReadAllServicesViewHelper(),
                (Session)httpRequest.getAttribute("session"),
                cr, httpRequest))
                .process();
    }
    
    @GET
    @Path("{id}")
    public String getService(){
        return new Facade(new FlowContainer(new ReadOneServiceViewHelper(),
                (Session)httpRequest.getAttribute("session"),
                cr, httpRequest))
                .process();
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public String newService(){
        return new Facade(new FlowContainer(new NewServiceViewHelper(), 
                (Session)httpRequest.getAttribute("session"), cr, httpRequest))
                .process();
    }
    
    @PUT
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Path("{id}")
    public String updateService(){
        return new Facade(new FlowContainer(new UpdateServiceViewHelper(), 
                (Session)httpRequest.getAttribute("session"), cr, httpRequest))
                .process();
    }
    
    @DELETE
    @Path("{id}")
    public String deleteService(){
        return new Facade(new FlowContainer(new DeleteServiceViewHelper(), 
                (Session)httpRequest.getAttribute("session"), cr, httpRequest))
                .process();
    }
    
}
