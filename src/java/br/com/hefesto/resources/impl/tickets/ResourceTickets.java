package br.com.hefesto.resources.impl.tickets;

import br.com.hefesto.resources.impl.tickets.purchase.view.CreatePurchaseTicketViewHelper;
import br.com.hefesto.resources.impl.tickets.purchase.view.ReadAllTicketPurchaseViewHelper;
import br.com.hefesto.resources.impl.tickets.purchase.view.ReadTicketPurchaseViewHelper;
import br.com.hefesto.resources.impl.tickets.purchase.view.ReadTicketsPurchaseViewHelper;
import br.com.wsbasestructure.control.Facade;
import br.com.wsbasestructure.domain.abstracts.AbstractResource;
import br.com.wsbasestructure.dto.FlowContainer;
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
@Path("tickets")
@Produces(MediaType.APPLICATION_JSON)
public class ResourceTickets extends AbstractResource{

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Path("purchases")
    public String createPurchaseTicket(){
        return new Facade(new FlowContainer(new CreatePurchaseTicketViewHelper(),
                (Session)httpRequest.getAttribute("session"), cr, httpRequest)).process();
    }
    
    @GET
    @Path("purchases/{id}")
    public String readPurchaseTicket(){
        return new Facade(new FlowContainer(new ReadTicketPurchaseViewHelper(),
                (Session)httpRequest.getAttribute("session"), cr, httpRequest)).process();
    }
    
    @GET
    @Path("purchases")
    public String readPurchaseTickets(){
        return new Facade(new FlowContainer(new ReadTicketsPurchaseViewHelper(),
                (Session)httpRequest.getAttribute("session"), cr, httpRequest))
                .process();
    }
    
    @GET
    @Path("purchases/all")
    public String readAllPurchaseTickets(){
        return new Facade(new FlowContainer(new ReadAllTicketPurchaseViewHelper(),
                (Session)httpRequest.getAttribute("session"), cr, httpRequest))
                .process();
    }
}
