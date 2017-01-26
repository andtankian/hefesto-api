package br.com.hefesto.resources.impl.group;

import br.com.hefesto.resources.impl.group.view.DeleteGroupViewHelper;
import br.com.hefesto.resources.impl.group.view.NewGroupViewHelper;
import br.com.hefesto.resources.impl.group.view.ReadAllGroupsViewHelper;
import br.com.hefesto.resources.impl.group.view.ReadOneGroupViewHelper;
import br.com.hefesto.resources.impl.group.view.ReadGroupViewHelper;
import br.com.hefesto.resources.impl.group.view.UpdateGroupViewHelper;
import br.com.wsbasestructure.control.Facade;
import br.com.wsbasestructure.domain.abstracts.AbstractResource;
import br.com.wsbasestructure.dto.FlowContainer;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import org.hibernate.Session;

/**
 * REST Web Service
 *
 * @author Andrew Ribeiro
 */
@Path("groups")
@Produces(MediaType.APPLICATION_JSON)
public class ResourceGroups extends AbstractResource {

    @GET
    public String getGroups() {
        return new Facade(
                new FlowContainer(new ReadGroupViewHelper(), (Session) httpRequest.getAttribute("session"), cr, httpRequest)
        ).process();
    }
    
    @GET
    @Path("/{id}")
    public String getGroup(){
        return new Facade(new FlowContainer(new ReadOneGroupViewHelper(), (Session)httpRequest.getAttribute("session"), cr, httpRequest))
                .process();
    }
    
    @GET
    @Path("/all")
    public String getAllGroups(){
        return new Facade(new FlowContainer(new ReadAllGroupsViewHelper(), (Session)httpRequest.getAttribute("session"), cr, httpRequest))
                .process();
    }
    
    @GET
    @Path("/dump")
    public String getDumpGroups(){
        return null;
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public String newGroup() {
        return new Facade(new FlowContainer(new NewGroupViewHelper(), (Session) httpRequest.getAttribute("session"), cr, httpRequest))
                .process();
    }
    
    @PUT
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Path("/{id}")
    public String updateGroup(){
        return new Facade(new FlowContainer(new UpdateGroupViewHelper(), (Session)httpRequest.getAttribute("session"), cr, httpRequest))
                .process();
    }
    
    @DELETE
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Path("/{id}")
    public String deleteGroup(){
        return new Facade(new FlowContainer(new DeleteGroupViewHelper(), (Session)httpRequest.getAttribute("session"), cr, httpRequest))
                .process();
    }

}
