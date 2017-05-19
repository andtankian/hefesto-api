package br.com.hefesto.resources.impl.rules;

import br.com.wsbasestructure.dto.FlowContainer;
import br.com.wsbasestructure.dto.interfaces.IHolder;
import br.com.wsbasestructure.endpoints.sessions.interfaces.WebSocketSessionHandler;
import br.com.wsbasestructure.rules.interfaces.AbstractRejectAttributesCommand;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Andrew Ribeiro
 */
public class NotifyContentCommand extends AbstractRejectAttributesCommand{

    public NotifyContentCommand(String[] rejects) {
        super(rejects);
    }
    @Override
    public IHolder exe(IHolder holder, FlowContainer flowContainer) {
        HttpServletRequest r = flowContainer.getHttprequest();
        WebSocketSessionHandler ws = (WebSocketSessionHandler)r.getServletContext().getAttribute("contentep");
        if(ws != null){
            if(flowContainer.getResult().getStatus() == 1){
                ws.notify(flowContainer, this.rejects);
            }
        }
        
        return holder;
    }
    
}
