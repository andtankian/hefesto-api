package br.com.hefesto.resources.impl.equipments;

import br.com.hefesto.resources.impl.equipments.view.DeleteEquipmentViewHelper;
import br.com.hefesto.resources.impl.equipments.view.NewEquipmentViewHelper;
import br.com.hefesto.resources.impl.equipments.view.ReadAllEquipmentsViewHelper;
import br.com.hefesto.resources.impl.equipments.view.ReadEquipmentsViewHelper;
import br.com.hefesto.resources.impl.equipments.view.ReadOneEquipmentViewHelper;
import br.com.hefesto.resources.impl.equipments.view.UpdateEquipmentViewHelper;
import br.com.wsbasestructure.control.Facade;
import br.com.wsbasestructure.domain.abstracts.AbstractResource;
import br.com.wsbasestructure.dto.FlowContainer;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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
@Path("equipments")
@Produces(MediaType.APPLICATION_JSON)
public class ResourceEquipment extends AbstractResource {

    @GET
    public String getEquipments() {
        return new Facade(new FlowContainer(new ReadEquipmentsViewHelper(),
                (Session) httpRequest.getAttribute("session"), cr, httpRequest))
                .process();
    }

    @GET
    @Path("/all")
    public String getAllEquipments() {
        return new Facade(new FlowContainer(new ReadAllEquipmentsViewHelper(),
                (Session) httpRequest.getAttribute("session"), cr, httpRequest))
                .process();
    }
    
    @GET
    @Path("{id}")
    public String getEquipment(){
        return new Facade(new FlowContainer(new ReadOneEquipmentViewHelper(),
                (Session) httpRequest.getAttribute("session"), cr, httpRequest))
                .process();
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public String newEquipment() {
        return new Facade(new FlowContainer(new NewEquipmentViewHelper(),
                (Session) httpRequest.getAttribute("session"), cr, httpRequest))
                .process();
    }
    
    @PUT
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Path("{id}")
    public String updateEquipment(){
        return new Facade(new FlowContainer(new UpdateEquipmentViewHelper(),
                (Session) httpRequest.getAttribute("session"), cr, httpRequest))
                .process();
    }
    
    @DELETE
    @Path("{id}")
    public String deleteEquipment(){
        return new Facade(new FlowContainer(new DeleteEquipmentViewHelper(),
                (Session)httpRequest.getAttribute("session"),
                cr, httpRequest))
                .process();
    }
    
}
