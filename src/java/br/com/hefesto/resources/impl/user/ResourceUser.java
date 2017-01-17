/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.hefesto.resources.impl.user;

import br.com.hefesto.resources.impl.user.view.ReadAllUsersViewHelper;
import br.com.wsbasestructure.control.Facade;
import br.com.wsbasestructure.domain.abstracts.AbstractResource;
import br.com.wsbasestructure.dto.FlowContainer;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
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
    @Path("/all")
    public String getAllUsers() {
        return new Facade(new FlowContainer(new ReadAllUsersViewHelper(),
                (Session) httpRequest.getAttribute("session"),
                cr,
                httpRequest))
                .process();
    }
}
