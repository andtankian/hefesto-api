package br.com.wsbasestructure.endpoints.abstracts;


import br.com.wsbasestructure.endpoints.interfaces.IEndPointWebSocket;
import br.com.wsbasestructure.endpoints.sessions.interfaces.WebSocketSessionHandler;
import javax.servlet.ServletContext;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnOpen;
import javax.websocket.Session;

/**
 *
 * @author Andrew Ribeiro
 */
public abstract class AbstractServerPointWebSocket implements IEndPointWebSocket{

    private EndpointConfig config;
    private ServletContext app;
    private WebSocketSessionHandler handler;

    @OnOpen
    public void onConnect(Session session, EndpointConfig config) {
        this.config = config;
        app = (ServletContext) config.getUserProperties().get("servletContext");
        handler = (WebSocketSessionHandler) app.getAttribute(getWebSocketAttribute());
        handler.add(session);
    }

    @OnClose
    public void onClose(Session session) {
        handler.remove(session);
    }
    
    @OnError
    public void onError(Session session, Throwable t) {
        /* Remove this connection from the queue */
        handler.remove(session);
        System.out.println(t.getCause().getMessage());
    }

}
