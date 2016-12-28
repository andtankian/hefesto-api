package br.com.wsbasestructure.filter.sivm.impl;

import br.com.wsbasestructure.dao.abstracts.Connection;
import java.io.IOException;
import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import org.hibernate.Session;

/**
 *
 * @author Andrew Ribeiro
 */
@WebFilter(filterName = "SessionInViewManager", urlPatterns = {"/*"}, dispatcherTypes = {DispatcherType.REQUEST})
public class SessionInViewManager implements Filter {
    private Connection conn = null;

    public SessionInViewManager() {
    }

    /**
     * 
     * @param request that will be intercepted
     * @param response that will be intercepted
     * @throws IOException
     * @throws ServletException 
     * 
     * Method that will be the interceptor of all the requests made from an user.
     * It's responsible to retrive a connection from the app context and init a new hibernate session setting it
     * in a request attribute.
     * It also begin a transaction.
     */
    private void doBeforeProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        conn = (Connection) request.getServletContext().getAttribute("hefesto"); //get the connection from appcontext attribute
        request.setAttribute("session", conn.openSession()); //open a new session and set it in a request attribute
        ((Session) request.getAttribute("session")).beginTransaction(); //begin a new transation from the newest opened session
    }

    /**
     * 
     * @param request that will be intercepted after servlet ends its processessing
     * @param response that will be intercepted after servlet ends its processing
     * @throws IOException
     * @throws ServletException 
     * 
     * This method will intercept a request and response after servlet end its processing.
     * It will close each new session opened earlier.
     */
    private void doAfterProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        ((Session) request.getAttribute("session")).close(); //Close the opened session
    }

    /**
     *
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     * @param chain The filter chain we are processing
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     * 
     * This method set requests and responses free to be executed by servlets.
     * It serves as a doBefore and doAfter container.
     */
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        doBeforeProcessing(request, response);

        chain.doFilter(request, response);

        doAfterProcessing(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        
    }

    @Override
    public void destroy() {
        
    }
}