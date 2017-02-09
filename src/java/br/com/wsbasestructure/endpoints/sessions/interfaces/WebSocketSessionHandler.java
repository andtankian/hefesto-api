package br.com.wsbasestructure.endpoints.sessions.interfaces;

import br.com.wsbasestructure.dto.FlowContainer;
import java.util.List;
import java.util.Set;
import javax.websocket.Session;

/**
 *
 * @author Andrew Ribeiro
 */
public interface WebSocketSessionHandler {
    
    public void add(Session session);
    public void notify(FlowContainer fc, List rejects);
    public void remove(Session session);
    public Set getSessions();
}
