package br.com.wsbasestructure.endpoints.impl;

import br.com.wsbasestructure.endpoints.abstracts.AbstractServerPointWebSocket;
import br.com.wsbasestructure.endpoints.configurator.CustomWebSocketConfigurator;
import javax.websocket.server.ServerEndpoint;

/**
 *
 * @author Andrew Ribeiro
 */
@ServerEndpoint(value="/dash/content", configurator = CustomWebSocketConfigurator.class)
public class ContentEndpoint extends AbstractServerPointWebSocket{

    @Override
    public String getWebSocketAttribute() {
        return "contentep";
    }
    
}
