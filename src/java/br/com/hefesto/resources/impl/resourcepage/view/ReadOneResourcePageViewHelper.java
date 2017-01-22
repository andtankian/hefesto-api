package br.com.hefesto.resources.impl.resourcepage.view;

import br.com.hefesto.domain.impl.ResourcePage;
import br.com.hefesto.resources.impl.resourcepage.rules.ValidateNameWhenReadingOneResourcePageCommand;
import br.com.wsbasestructure.dto.FlowContainer;
import br.com.wsbasestructure.dto.SearchModel;
import br.com.wsbasestructure.dto.impl.GenericHolder;
import br.com.wsbasestructure.dto.interfaces.IHolder;
import br.com.wsbasestructure.view.abstracts.AbstractViewHelper;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author Andrew Ribeiro
 */
public class ReadOneResourcePageViewHelper extends AbstractViewHelper {

    @Override
    public IHolder getView(FlowContainer fc) {
        super.getView(fc);

        ResourcePage r = new ResourcePage();
        GenericHolder gh = new GenericHolder();
        SearchModel sm = gh.getSm();
        UriInfo u = fc.getCr().getUriInfo();
        String name;
        try {
            name = u.getPathParameters().get("name").get(0);
        } catch (NullPointerException e) {
            name = null;
        }
        
        Long id = (long)-1;
        r.setId(id);

        r.setName(name);

        sm.setEntity(r);
        gh.getEntities().add(r);
        this.loadBusinessRulesBeforeMainFlow();
        return gh;
    }

    @Override
    public void loadBusinessRulesBeforeMainFlow() {
        this.getRulesBeforeMainFlow().add(new ValidateNameWhenReadingOneResourcePageCommand());
    }

}
