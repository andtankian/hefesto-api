package br.com.hefesto.resources.impl.product.view;

import br.com.hefesto.domain.impl.Product;
import br.com.hefesto.resources.impl.rules.NotifyContentCommand;
import br.com.wsbasestructure.dto.FlowContainer;
import br.com.wsbasestructure.dto.impl.GenericHolder;
import br.com.wsbasestructure.dto.interfaces.IHolder;
import br.com.wsbasestructure.rules.impl.ValidateAndMergeEntityCommand;
import br.com.wsbasestructure.view.abstracts.AbstractViewHelper;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

/**
 *
 * @author Andrew Ribeiro
 */
public class UpdateProductViewHelper extends AbstractViewHelper {

    @Override
    public IHolder getView(FlowContainer fc) {
        super.getView(fc);

        Product p = new Product();

        GenericHolder gh = new GenericHolder();
        Form f = fc.getCr().readEntity(Form.class);
        MultivaluedHashMap mvhm = (MultivaluedHashMap) f.asMap();
        MultivaluedMap<String, String> mvm = fc.getCr().getUriInfo().getPathParameters();

        String id;
        Long longId = null;

        String name;
        String description;
        String link;
        String type;

        try {
            id = mvm.get("id").get(0);
            try {
                longId = Long.parseLong(id);
            } catch (NumberFormatException nfe) {
                longId = null;
            }
        } catch (NullPointerException npe) {
            id = null;
        }

        try {
            name = (String) mvhm.get("name").get(0);
        } catch (NullPointerException npe) {
            name = null;
        }

        try {
            description = (String) mvhm.get("description").get(0);
        } catch (NullPointerException npe) {
            description = null;
        }

        try {
            link = (String) mvhm.get("link").get(0);
        } catch (NullPointerException npe) {
            link = null;
        }

        try {
            type = (String) mvhm.get("type").get(0);
        } catch (NullPointerException npe) {
            type = null;
        }
        
        p.setId(longId);
        p.setDescription(description);
        p.setLink(link);
        p.setType(type);
        p.setName(name);
        
        gh.getEntities().add(p);
        
        loadBusinessRulesBeforeMainFlow();
        loadBusinessRulesAfterMainFlow();
        return gh;
    }

    @Override
    public void loadBusinessRulesAfterMainFlow() {
        getRulesAfterMainFlow().add(new NotifyContentCommand(new String[]{"none"}));
    }

    @Override
    public void loadBusinessRulesBeforeMainFlow() {
        getRulesBeforeMainFlow().add(new ValidateAndMergeEntityCommand());
    }

}
