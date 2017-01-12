/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.hefesto.resources.impl.permission;

import br.com.hefesto.resources.impl.permission.view.NewPermissionViewHelper;
import br.com.hefesto.resources.impl.permission.view.ReadPermissionViewHelper;
import br.com.wsbasestructure.control.Facade;
import br.com.wsbasestructure.domain.abstracts.AbstractResource;
import br.com.wsbasestructure.dto.FlowContainer;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
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
public class ResourcePermission extends AbstractResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getPermissions() {
        return new Facade(
                new FlowContainer(new ReadPermissionViewHelper(), (Session) httpRequest.getAttribute("session"), cr, httpRequest)
        ).process();
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public String newPermission() {
        return new Facade(new FlowContainer(new NewPermissionViewHelper(), (Session) httpRequest.getAttribute("session"), cr, httpRequest))
                .process();
    }

}
