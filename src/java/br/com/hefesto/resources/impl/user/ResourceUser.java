package br.com.hefesto.resources.impl.user;

import br.com.hefesto.resources.impl.user.view.UpdateProfilePictureViewHelper;
import br.com.hefesto.resources.impl.user.view.DeleteUsersViewHelper;
import br.com.hefesto.resources.impl.user.view.ForgotPasswordUserViewHelper;
import br.com.hefesto.resources.impl.user.view.LoginUserViewHelper;
import br.com.hefesto.resources.impl.user.view.NewUserViewHelper;
import br.com.hefesto.resources.impl.user.view.ReadAllUsersViewHelper;
import br.com.hefesto.resources.impl.user.view.ReadUserByTokenViewHelper;
import br.com.hefesto.resources.impl.user.view.ReadUserViewHelper;
import br.com.hefesto.resources.impl.user.view.ReadUsersViewHelper;
import br.com.hefesto.resources.impl.user.view.RedefinePasswordViewHelper;
import br.com.hefesto.resources.impl.user.view.UpdateUserViewHelper;
import br.com.hefesto.resources.impl.user.view.widgets.ReadUserRecentInteractionsWidgetViewHelper;
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
@Path("users")
@Produces(MediaType.APPLICATION_JSON)
public class ResourceUser extends AbstractResource {

    @GET
    public String getUsers() {
        return new Facade(new FlowContainer(new ReadUsersViewHelper(),
                (Session) httpRequest.getAttribute("session"),
                cr,
                httpRequest))
                .process();
    }

    @GET
    @Path("/all")
    public String getAllUsers() {
        return new Facade(new FlowContainer(new ReadAllUsersViewHelper(),
                (Session) httpRequest.getAttribute("session"),
                cr,
                httpRequest))
                .process();
    }

    @GET
    @Path("{id}")
    public String getUser() {
        return new Facade(new FlowContainer(new ReadUserViewHelper(),
                (Session) httpRequest.getAttribute("session"),
                cr,
                httpRequest))
                .process();
    }

    @POST
    @Path("/login")
    public String loginUser() {
        return new Facade(new FlowContainer(new LoginUserViewHelper(),
                (Session) httpRequest.getAttribute("session"),
                cr,
                httpRequest))
                .process();
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public String newUser() {
        return new Facade(new FlowContainer(new NewUserViewHelper(),
                (Session) httpRequest.getAttribute("session"),
                cr, httpRequest))
                .process();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Path("{id}")
    public String updateUser() {
        return new Facade(new FlowContainer(new UpdateUserViewHelper(),
                (Session) httpRequest.getAttribute("session"),
                cr, httpRequest))
                .process();
    }

    @DELETE
    @Path("{id}")
    public String deleteUser() {
        return new Facade(new FlowContainer(new DeleteUsersViewHelper(),
                (Session) httpRequest.getAttribute("session"),
                cr, httpRequest))
                .process();
    }
    
    @PUT
    @Path("/profile/pic/{id}")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public String updateProfilePic(){
        return new Facade(new FlowContainer(new UpdateProfilePictureViewHelper(),
                (Session)httpRequest.getAttribute("session"), cr, httpRequest))
                .process();
    }
    
    @PUT
    @Path("forgotpass")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
     public String forgotPassword(){
        return new Facade(new FlowContainer(new ForgotPasswordUserViewHelper(),
                (Session)httpRequest.getAttribute("session"), cr, httpRequest))
                .process();
    }
     
     @GET
     @Path("getfromtoken/{token}")
     public String getFromToken(){
        return new Facade(new FlowContainer(new ReadUserByTokenViewHelper(),
                (Session)httpRequest.getAttribute("session"), cr, httpRequest))
                .process();
    }
     
     @PUT
     @Path("redefinepass/{id}")
     public String redefinePass(){
         return new Facade(new FlowContainer(new RedefinePasswordViewHelper(),
                (Session)httpRequest.getAttribute("session"), cr, httpRequest))
                .process();         
     }
     
     @GET
     @Path("{id}/widgets/recentinteractions/")
     public String getWidgetsRecentInteractions(){
         return new Facade(new FlowContainer(new ReadUserRecentInteractionsWidgetViewHelper(),
                (Session)httpRequest.getAttribute("session"), cr, httpRequest))
                .process();
     }

}
