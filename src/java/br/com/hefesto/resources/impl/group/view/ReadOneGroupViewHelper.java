package br.com.hefesto.resources.impl.group.view;

import br.com.hefesto.domain.impl.Groups;
import br.com.wsbasestructure.dto.FlowContainer;
import br.com.wsbasestructure.dto.Result;
import br.com.wsbasestructure.dto.SearchModel;
import br.com.wsbasestructure.dto.impl.GenericHolder;
import br.com.wsbasestructure.dto.interfaces.IHolder;
import br.com.wsbasestructure.rules.impl.ValidateIDEntityCommand;
import br.com.wsbasestructure.view.abstracts.AbstractViewHelper;
import br.com.wsbasestructure.view.impl.GenericExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author Andrew Ribeiro
 */
public class ReadOneGroupViewHelper extends AbstractViewHelper{

    @Override
    public IHolder getView(FlowContainer fc) {
        super.getView(fc);
        
        Groups p = new Groups();
        GenericHolder gh = new GenericHolder();
        SearchModel sm = gh.getSm();
        UriInfo u = fc.getCr().getUriInfo();

        String id = u.getPathParameters().get("id") != null ? u.getPathParameters().get("id").get(0) : null;

        try {
            p.setId(Long.parseLong(id));
        } catch (NumberFormatException n) {
            p.setId((long) 0);
        }
        
        sm.setEntity(p);
        gh.getEntities().add(p);
        
        loadBusinessRulesBeforeMainFlow();
        
        return gh;
    }
    
     @Override
    public void loadBusinessRulesBeforeMainFlow() {
        this.getRulesBeforeMainFlow().add(new ValidateIDEntityCommand());
    }

    @Override
    public String setView(Result result) {
        
        Gson g = new GsonBuilder().addSerializationExclusionStrategy(new GenericExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes fa) {
                return fa.getName().equals("permissions");
            }
        }).create();
        
        return g.toJson(result);
    }
    
    
    
}
