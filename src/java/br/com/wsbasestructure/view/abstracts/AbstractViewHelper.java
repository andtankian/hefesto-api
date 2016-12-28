package br.com.wsbasestructure.view.abstracts;

import br.com.wsbasestructure.dto.Result;
import br.com.wsbasestructure.dto.interfaces.IHolder;
import br.com.wsbasestructure.rules.interfaces.ICommand;
import br.com.wsbasestructure.view.interfaces.IViewHelper;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author Andrew Ribeiro
 */
public abstract class AbstractViewHelper implements IViewHelper{

    public AbstractViewHelper() {
        rulesBeforeMainFlow = new ArrayList<>();
        rulesAfterMainFlow = new ArrayList<>();
    }
     private List<ICommand> rulesBeforeMainFlow;
     private List<ICommand> rulesAfterMainFlow;
     protected String typeRequest;

    @Override
    public IHolder getView(UriInfo uriInfo, HttpServletRequest httpReq) {
        List l = uriInfo.getQueryParameters().get("treq");
        typeRequest = l != null ? (String)l.get(0) : "crud";
        return null;
    }
     
     

    @Override
    public String getTypeRequest() {
        return typeRequest;
    }

    @Override
    public List<ICommand> getRulesBeforeMainFlow() {
        return rulesBeforeMainFlow;
    }

    @Override
    public List<ICommand> getRulesAfterMainFlow() {
        return rulesAfterMainFlow;
    }         

    @Override
    public void loadBusinessRulesBeforeMainFlow() {
    }

    @Override
    public void loadBusinessRulesAfterMainFlow() {
    }

    
    @Override
    public String setView(Result result) {
        Gson g = new Gson();
        return g.toJson(result);
    }

  
    
     
}
