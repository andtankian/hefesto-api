package br.com.hefesto.resources.impl.user.view;

import br.com.hefesto.domain.impl.User;
import br.com.hefesto.resources.impl.user.rules.EncryptUserPasswordCommand;
import br.com.hefesto.resources.impl.user.rules.ValidateAndMergeRedefinePassCommand;
import br.com.wsbasestructure.dto.FlowContainer;
import br.com.wsbasestructure.dto.Result;
import br.com.wsbasestructure.dto.impl.GenericHolder;
import br.com.wsbasestructure.dto.interfaces.IHolder;
import br.com.wsbasestructure.rules.impl.ValidateIDEntityCommand;
import br.com.wsbasestructure.view.abstracts.AbstractViewHelper;
import br.com.wsbasestructure.view.impl.GenericExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author andrew
 */
public class RedefinePasswordViewHelper extends AbstractViewHelper {

    @Override
    public IHolder getView(FlowContainer fc) {
        super.getView(fc);

        GenericHolder gh = new GenericHolder();
        UriInfo uri = fc.getCr().getUriInfo();
        Form f = fc.getCr().readEntity(Form.class);
        MultivaluedHashMap mvhm = (MultivaluedHashMap) f.asMap();
        MultivaluedMap<String, String> mvm = uri.getPathParameters();

        User user = new User();

        try {
            user.setId(Long.parseLong((String) mvm.get("id").get(0)));
        } catch (NullPointerException | NumberFormatException npe) {
            user.setId(null);
        }

        try {
            user.setPassword((String) mvhm.get("password1").get(0));
        } catch (NullPointerException npe) {
            user.setPassword(null);
        }

        gh.getEntities().add(user);

        loadBusinessRulesBeforeMainFlow();

        return gh;
    }

    @Override
    public void loadBusinessRulesBeforeMainFlow() {
        this.getRulesBeforeMainFlow().add(new ValidateIDEntityCommand());
        this.getRulesBeforeMainFlow().add(new EncryptUserPasswordCommand());
        this.getRulesBeforeMainFlow().add(new ValidateAndMergeRedefinePassCommand());
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
