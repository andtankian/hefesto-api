package br.com.hefesto.resources.impl.user.view;

import br.com.hefesto.domain.impl.User;
import br.com.hefesto.domain.impl.UserConfig;
import br.com.hefesto.resources.impl.user.rules.ValidateTokenRedefinePasswordCommand;
import br.com.wsbasestructure.dto.FlowContainer;
import br.com.wsbasestructure.dto.Result;
import br.com.wsbasestructure.dto.impl.GenericHolder;
import br.com.wsbasestructure.dto.interfaces.IHolder;
import br.com.wsbasestructure.view.abstracts.AbstractViewHelper;
import br.com.wsbasestructure.view.impl.GenericExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author andrew
 */
public class ReadUserByTokenViewHelper extends AbstractViewHelper {

    @Override
    public IHolder getView(FlowContainer fc) {
        super.getView(fc);

        User u = new User();
        UserConfig uc = new UserConfig();
        u.setUserConfig(uc);
        GenericHolder gh = new GenericHolder();
        UriInfo uri = fc.getCr().getUriInfo();
        MultivaluedMap<String, String> mvm = uri.getPathParameters();

        try {
            uc.setForgotPasswordCurrentToken(mvm.get("token").get(0));
        } catch (NullPointerException npe) {
            uc.setForgotPasswordCurrentToken(null);
        }

        gh.getEntities().add(u);
        gh.getSm().setEntity(u);

        loadBusinessRulesBeforeMainFlow();
        return gh;
    }

    @Override
    public void loadBusinessRulesBeforeMainFlow() {
        this.getRulesBeforeMainFlow().add(new ValidateTokenRedefinePasswordCommand());
    }

     @Override
    public String setView(Result result) {
        GsonBuilder gb = new GsonBuilder();

        gb.addSerializationExclusionStrategy(new GenericExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes fa) {
                return fa.getName().equals("user") || fa.getName().equals("ticket")
                        || fa.getName().equals("groups");
            }
        });
        Gson g = gb.create();

        return g.toJson(result);
    }
    
    

}
