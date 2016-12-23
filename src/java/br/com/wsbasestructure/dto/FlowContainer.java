package br.com.wsbasestructure.dto;

import br.com.wsbasestructure.view.interfaces.IViewHelper;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;
import org.hibernate.Session;

/**
 *
 * @author Andrew Ribeiro
 */
public class FlowContainer {

    public FlowContainer(IViewHelper viewHelper, Session session, UriInfo uriInfo, Request request, HttpServletRequest httprequest) {
        this.viewHelper = viewHelper;
        this.session = session;
        this.uriInfo = uriInfo;
        this.request = request;
        this.httprequest = httprequest;
    }

   
    
    
    
    private IViewHelper viewHelper;
    private Session session;
    private UriInfo uriInfo;
    private Request request;
    private HttpServletRequest httprequest;

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public HttpServletRequest getHttprequest() {
        return httprequest;
    }

    public void setHttprequest(HttpServletRequest httprequest) {
        this.httprequest = httprequest;
    }

    public IViewHelper getViewHelper() {
        return viewHelper;
    }

    public void setViewHelper(IViewHelper viewHelper) {
        this.viewHelper = viewHelper;
    }

    public UriInfo getUriInfo() {
        return uriInfo;
    }

    public void setUriInfo(UriInfo uriInfo) {
        this.uriInfo = uriInfo;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }
    
    
    
    
}
