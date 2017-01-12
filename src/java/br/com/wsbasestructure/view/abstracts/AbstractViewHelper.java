package br.com.wsbasestructure.view.abstracts;

import br.com.wsbasestructure.dto.FlowContainer;
import br.com.wsbasestructure.dto.Result;
import br.com.wsbasestructure.dto.interfaces.IHolder;
import br.com.wsbasestructure.rules.interfaces.ICommand;
import br.com.wsbasestructure.view.impl.GenericExclusionStrategy;
import br.com.wsbasestructure.view.interfaces.IViewHelper;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Andrew Ribeiro
 */
public abstract class AbstractViewHelper implements IViewHelper {

    public AbstractViewHelper() {
        rulesBeforeMainFlow = new ArrayList<>();
        rulesAfterMainFlow = new ArrayList<>();
        rejects = new ArrayList<>();
    }
    private List<ICommand> rulesBeforeMainFlow;
    private List<ICommand> rulesAfterMainFlow;
    protected List rejects;
    protected String typeRequest;

    @Override
    public IHolder getView(FlowContainer fc) {
        List l = fc.getCr().getUriInfo().getQueryParameters().get("treq");
        typeRequest = l != null ? (String) l.get(0) : "crud";
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
        GsonBuilder gb = new GsonBuilder();

        if (rejects != null && !rejects.isEmpty()) {
            gb.addSerializationExclusionStrategy(new GenericExclusionStrategy() {
                @Override
                public boolean shouldSkipField(FieldAttributes fa) {
                    return rejects.contains(fa.getName());
                }
            });
        }
        Gson g = gb.create();

        return g.toJson(result);
    }

}
