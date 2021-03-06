package br.com.hefesto.resources.impl.deparment;

import br.com.hefesto.resources.impl.deparment.view.DeleteDepartmentViewHelper;
import br.com.hefesto.resources.impl.deparment.view.DumpDepartmentViewHelper;
import br.com.hefesto.resources.impl.deparment.view.NewDepartmentViewHelper;
import br.com.hefesto.resources.impl.deparment.view.ReadAllDepartmentsViewHelper;
import br.com.hefesto.resources.impl.deparment.view.ReadDepartmentViewHelper;
import br.com.hefesto.resources.impl.deparment.view.ReadOneDepartmentViewHelper;
import br.com.hefesto.resources.impl.deparment.view.UpdateDepartmentViewHelper;
import br.com.wsbasestructure.control.Facade;
import br.com.wsbasestructure.domain.abstracts.AbstractResource;
import br.com.wsbasestructure.dto.FlowContainer;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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
@Path("departments")
public class ResourceDepartment extends AbstractResource {

    /**
     * Retrieves representation of an instance of
     * br.com.hefesto.resources.impl.ResourceDepartment
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getDepartments() {
        return new Facade(new FlowContainer(new ReadDepartmentViewHelper(),
                (Session) this.httpRequest.getAttribute("session"),
                cr, httpRequest)).process();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/all")
    public String allDepartments() {
        return new Facade(new FlowContainer(new ReadAllDepartmentsViewHelper(),
                (Session) this.httpRequest.getAttribute("session"),
                cr, httpRequest)).process();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/dump")
    public String dumpDepartments() {
        return new Facade(new FlowContainer(new DumpDepartmentViewHelper(),
                (Session) this.httpRequest.getAttribute("session"),
                cr, httpRequest)).process();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public String getDepartment() {
        return new Facade(new FlowContainer(new ReadOneDepartmentViewHelper(),
                (Session) this.httpRequest.getAttribute("session"),
                cr, httpRequest)).process();
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public String newDepartment() {
        return new Facade(new FlowContainer(new NewDepartmentViewHelper(),
                (Session) httpRequest.getAttribute("session"),
                cr, httpRequest)).process();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public String updateDepartment() {
        return new Facade(new FlowContainer(new UpdateDepartmentViewHelper(),
                (Session) httpRequest.getAttribute("session"),
                cr, httpRequest)).process();
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public String deactiveDepartment() {
        return new Facade(new FlowContainer(new DeleteDepartmentViewHelper(),
                (Session) httpRequest.getAttribute("session"),
                cr, httpRequest)).process();
    }
}
