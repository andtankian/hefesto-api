package br.com.wsbasestructure.app.listener;

import br.com.wsbasestructure.dao.abstracts.Connection;
import br.com.wsbasestructure.endpoints.sessions.generic.ConcreteWebSocketSessionHandler;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 *
 * @author Andrew Ribeiro
 */
@WebListener
public class AppContextListener implements ServletContextListener {

    /**
     *
     * @param sce
     *
     * This method is executed when the application is up by the first time. It
     * initiates a new connection to be used by all users in the application.
     * This connection is set on a appcontext attribute. Then this attribute
     * could be retrieved by any requester.
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        sce.getServletContext().setAttribute("hefesto", new Connection()); //set the new connection into a appcontext attribute.
        sce.getServletContext().setAttribute("contentep", new ConcreteWebSocketSessionHandler());
    }

    /**
     *
     * @param sce
     *
     * This method will be executed when the application is shutdown. It will
     * completely finalize all the hibernate connections resources, including
     * the C3p0 connection pool.
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if (sce.getServletContext().getAttribute("hefesto") != null) {
            //if the appcontext attribute containing the connection is different of null, then lets finalize eveything.
            Connection c = (Connection) sce.getServletContext().getAttribute("hefesto");
            c.closeEverything();
        }
    }

}
