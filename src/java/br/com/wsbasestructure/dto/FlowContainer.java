package br.com.wsbasestructure.dto;

import br.com.wsbasestructure.view.interfaces.IViewHelper;
import javax.servlet.http.HttpServletRequest;
import org.glassfish.jersey.server.ContainerRequest;
import org.hibernate.Session;

/**
 *
 * @author Andrew Ribeiro
 */
public class FlowContainer {

    public FlowContainer(IViewHelper viewHelper, Session session, ContainerRequest cr, HttpServletRequest httprequest) {
        this.viewHelper = viewHelper;
        this.session = session;
        this.cr = cr;
        this.httprequest = httprequest;
    }

    public FlowContainer(IViewHelper viewHelper, ContainerRequest cr, HttpServletRequest httprequest) {
        this.viewHelper = viewHelper;
        this.cr = cr;
        this.httprequest = httprequest;
        this.session = null;
    }

      
    
    private IViewHelper viewHelper;
    private Session session;
    private ContainerRequest cr;
    private HttpServletRequest httprequest;
    private Result result;
    private FlowControl fc;

    public ContainerRequest getCr() {
        return cr;
    }

    public void setCr(ContainerRequest cr) {
        this.cr = cr;
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

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public FlowControl getFc() {
        return fc;
    }

    public void setFc(FlowControl fc) {
        this.fc = fc;
    }
    
    
    
    
}
