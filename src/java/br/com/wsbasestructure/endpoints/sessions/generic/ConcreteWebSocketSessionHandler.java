package br.com.wsbasestructure.endpoints.sessions.generic;

import br.com.wsbasestructure.dto.FlowContainer;
import br.com.wsbasestructure.endpoints.sessions.interfaces.WebSocketSessionHandler;
import br.com.wsbasestructure.view.impl.GenericExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.websocket.Session;

/**
 *
 * @author Andrew Ribeiro
 */
public class ConcreteWebSocketSessionHandler implements WebSocketSessionHandler{
    
    private final Set sessions;

    public ConcreteWebSocketSessionHandler() {
        this.sessions = new HashSet<>();
    }

    @Override
    public synchronized void add(Session session) {
        session.setMaxIdleTimeout(-1);
        this.sessions.add(session);
    }

    @Override
    public synchronized void remove(Session session) {
        this.sessions.remove(session);
    }

    @Override
    public Set getSessions() {
        return sessions;
    }
    
    @Override
    public synchronized void notify(FlowContainer fc, List rejects) {
        Gson gson = new GsonBuilder().addSerializationExclusionStrategy(new GenericExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes fa) {
                return rejects.contains(fa.getName());
            }
        }).create();
        JsonObject g = (JsonObject) gson.toJsonTree(fc.getResult());
        g.addProperty("header", fc.getResult().getHolder().getEntities().get(0).getClass().getName());
        g.addProperty("action", fc.getCr().getMethod());
        String message = g.toString();
        for (Object session : sessions) {
            Session s = (Session)session;
            s.getAsyncRemote().sendText(message);
        }
    }
    
}
