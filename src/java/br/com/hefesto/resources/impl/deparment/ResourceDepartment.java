package br.com.hefesto.resources.impl.deparment;

import br.com.hefesto.resources.impl.deparment.view.NewDepartmentViewHelper;
import br.com.hefesto.resources.impl.deparment.view.ReadDepartmentViewHelper;
import br.com.wsbasestructure.control.Facade;
import br.com.wsbasestructure.domain.abstracts.AbstractResource;
import br.com.wsbasestructure.dto.FlowContainer;
import com.sun.prism.impl.BaseMesh;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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
        return new Facade().process(new FlowContainer(new ReadDepartmentViewHelper(),
                (Session) this.httpRequest.getAttribute("session"),
                uriInfo,
                request,
                httpRequest));
    }

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String newDepartment() {
        return new Facade().process(new FlowContainer(new NewDepartmentViewHelper(), null, uriInfo, request, httpRequest));
    }
}
