package br.com.hefesto.resources.impl.permission;

import br.com.hefesto.resources.impl.permission.view.NewPermissionViewHelper;
import br.com.hefesto.resources.impl.permission.view.ReadAllPermissionsViewHelper;
import br.com.hefesto.resources.impl.permission.view.ReadOnePermissionViewHelper;
import br.com.hefesto.resources.impl.permission.view.ReadPermissionViewHelper;
import br.com.hefesto.resources.impl.permission.view.UpdatePermissionViewHelper;
import br.com.wsbasestructure.control.Facade;
import br.com.wsbasestructure.domain.abstracts.AbstractResource;
import br.com.wsbasestructure.dto.FlowContainer;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
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
@Path("permissions")
@Produces(MediaType.APPLICATION_JSON)
public class ResourcePermission extends AbstractResource {

    @GET
    public String getPermissions() {
        return new Facade(
                new FlowContainer(new ReadPermissionViewHelper(), (Session) httpRequest.getAttribute("session"), cr, httpRequest)
        ).process();
    }
    
    @GET
    @Path("/{id}")
    public String getPermission(){
        return new Facade(new FlowContainer(new ReadOnePermissionViewHelper(), (Session)httpRequest.getAttribute("session"), cr, httpRequest))
                .process();
    }
    
    @GET
    @Path("/all")
    public String getAllPermissions(){
        return new Facade(new FlowContainer(new ReadAllPermissionsViewHelper(), (Session)httpRequest.getAttribute("session"), cr, httpRequest))
                .process();
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public String newPermission() {
        return new Facade(new FlowContainer(new NewPermissionViewHelper(), (Session) httpRequest.getAttribute("session"), cr, httpRequest))
                .process();
    }
    
    @PUT
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Path("/{id}")
    public String updatePermission(){
        return new Facade(new FlowContainer(new UpdatePermissionViewHelper(), (Session)httpRequest.getAttribute("session"), cr, httpRequest))
                .process();
    }

}
