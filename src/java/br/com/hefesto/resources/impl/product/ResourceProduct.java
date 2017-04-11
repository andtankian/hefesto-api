package br.com.hefesto.resources.impl.product;

import br.com.hefesto.resources.impl.product.view.DeleteProductViewHelper;
import br.com.hefesto.resources.impl.product.view.NewProductViewHelper;
import br.com.hefesto.resources.impl.product.view.ReadAllProductsViewHelper;
import br.com.hefesto.resources.impl.product.view.ReadOneProductViewHelper;
import br.com.hefesto.resources.impl.product.view.ReadProductsViewHelper;
import br.com.hefesto.resources.impl.product.view.UpdateProductViewHelper;
import br.com.wsbasestructure.control.Facade;
import br.com.wsbasestructure.domain.abstracts.AbstractResource;
import br.com.wsbasestructure.dto.FlowContainer;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.Produces;
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
@Path("products")
@Produces(MediaType.APPLICATION_JSON)
public class ResourceProduct extends AbstractResource{

    @GET
    public String getProducts() {
        return new Facade(new FlowContainer(new ReadProductsViewHelper(),
                (Session) httpRequest.getAttribute("session"),
                cr, httpRequest))
                .process();
    }
    
    @GET
    @Path("/all")
    public String getAllProducts(){        
        return new Facade(new FlowContainer(new ReadAllProductsViewHelper(),
                (Session) httpRequest.getAttribute("session"),
                cr, httpRequest))
                .process();
    }
    
    @GET
    @Path("{id}")
    public String getProduct(){
        return new Facade(new FlowContainer(new ReadOneProductViewHelper(),
                (Session) httpRequest.getAttribute("session"),
                cr, httpRequest))
                .process();
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public String newProduct(){
        return new Facade(new FlowContainer(new NewProductViewHelper(),
                (Session) httpRequest.getAttribute("session"),
                cr, httpRequest))
                .process();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Path("{id}")
    public String udateProduct() {
        return new Facade(new FlowContainer(new UpdateProductViewHelper(),
                (Session) httpRequest.getAttribute("session"),
                cr, httpRequest))
                .process();
    }
    
    @DELETE
    @Path("{id}")
    public String deleteProduct(){
        return new Facade(new FlowContainer(new DeleteProductViewHelper(),
                (Session) httpRequest.getAttribute("session"),
                cr, httpRequest))
                .process();
    }
}
