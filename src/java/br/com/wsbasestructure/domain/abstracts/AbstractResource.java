package br.com.wsbasestructure.domain.abstracts;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import org.glassfish.jersey.server.ContainerRequest;

/**
 *
 * @author Andrew Ribeiro
 */
public class AbstractResource {
    
    @Context
    protected ContainerRequest cr;
    
    @Context
    protected HttpServletRequest httpRequest;
    
    
    
}
