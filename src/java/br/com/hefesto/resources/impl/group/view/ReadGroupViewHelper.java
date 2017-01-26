package br.com.hefesto.resources.impl.group.view;

import br.com.hefesto.domain.impl.Groups;
import br.com.hefesto.resources.impl.group.rules.AcceptGroupAttributes;
import br.com.hefesto.resources.impl.rules.ValidateSearchEntitiesCommand;
import br.com.wsbasestructure.dto.FlowContainer;
import br.com.wsbasestructure.dto.Result;
import br.com.wsbasestructure.dto.SearchModel;
import br.com.wsbasestructure.dto.impl.GenericHolder;
import br.com.wsbasestructure.dto.interfaces.IHolder;
import br.com.wsbasestructure.view.abstracts.AbstractViewHelper;
import br.com.wsbasestructure.view.impl.GenericExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author Andrew Ribeiro
 */
public class ReadGroupViewHelper extends AbstractViewHelper{
    
    private String[] accepts;
    private List rejects;

    @Override
    public IHolder getView(FlowContainer fc) {
        super.getView(fc);
        
        Groups p = new Groups();
        GenericHolder gh = new GenericHolder();
        SearchModel sm = gh.getSm();
        UriInfo u = fc.getCr().getUriInfo();
        MultivaluedMap<String, String> mvhm =  u.getQueryParameters();
        String search;
        try {
            search = (String)mvhm.get("search").get(0);
        } catch (Exception e){
            search = null;
        }
        
        try {
            Object[] oarray = mvhm.get("accepts").toArray();
            accepts = Arrays.copyOf(oarray, oarray.length, String[].class);
        } catch (Exception e){
            accepts = new String[]{"none"};
        }
        sm.getBasics(u);
        sm.setSearch(search);
        p.setName(sm.getSearch());
        
        sm.setEntity(p);
        
        gh.getEntities().add(p);
        
        loadBusinessRulesBeforeMainFlow();
        loadBusinessRulesAfterMainFlow();
        
        return gh;
    }

    @Override
    public void loadBusinessRulesBeforeMainFlow() {        
        this.getRulesBeforeMainFlow().add(new ValidateSearchEntitiesCommand());
        
    }

    @Override
    public void loadBusinessRulesAfterMainFlow() {
        rejects = new ArrayList<>();
        this.getRulesAfterMainFlow().add(new AcceptGroupAttributes(accepts, rejects));
    }
    
    

    @Override
    public String setView(Result result) {
        Gson g = new GsonBuilder().addSerializationExclusionStrategy(new GenericExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes fa) {
                return rejects.contains(fa.getName()) || fa.getName().equals("permissions");
            }
        }).create();
        
        return g.toJson(result);
    }
    
    
    
    
    
    
    
    
}
