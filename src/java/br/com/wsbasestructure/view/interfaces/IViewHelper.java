package br.com.wsbasestructure.view.interfaces;

import br.com.wsbasestructure.dto.Result;
import br.com.wsbasestructure.dto.interfaces.IHolder;
import br.com.wsbasestructure.rules.interfaces.ICommand;
import java.util.List;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author Andrew Ribeiro
 */
public interface IViewHelper {
    public String setView(Result result);
    public IHolder getView(UriInfo uriInfo);
    public String getTypeRequest();
    public void loadBusinessRulesBeforeMainFlow();
    public void loadBusinessRulesAfterMainFlow();
    public List<ICommand> getRulesBeforeMainFlow();
    public List<ICommand> getRulesAfterMainFlow();
}
