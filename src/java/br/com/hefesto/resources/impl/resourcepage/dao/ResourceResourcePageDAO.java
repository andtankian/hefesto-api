package br.com.hefesto.resources.impl.resourcepage.dao;

import br.com.hefesto.domain.impl.ResourcePage;
import br.com.wsbasestructure.dao.impl.GenericCRUDDAO;
import br.com.wsbasestructure.dto.Result;
import br.com.wsbasestructure.dto.SearchModel;
import br.com.wsbasestructure.dto.interfaces.IHolder;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Andrew Ribeiro
 */
public class ResourceResourcePageDAO extends GenericCRUDDAO{

    public ResourceResourcePageDAO(Session session, IHolder holder) {
        super(session, holder);
    }

    @Override
    public Result read() {
        SearchModel sm = holder.getSm();
        if(sm.getEntity() != null && sm.getEntity().getId() != null 
                && sm.getEntity().getId() == -1 &&
                ((ResourcePage)sm.getEntity()).getName() != null){
            Criteria cr = session.createCriteria(ResourcePage.class)
                    .add(Restrictions.eq("name", ((ResourcePage)sm.getEntity()).getName()));
            holder.getEntities().set(0, cr.uniqueResult());
            if(holder.getEntities().get(0) == null){
                message.setError("resource page not found");
                result.setStatus(Result.ERROR);
                fc.setMustContinue(false);
            } else {
                message.setText("read");
                result.setStatus(Result.SUCCESS);
            }
        }
        result.setMessage(message);
        result.setHolder(holder);

        return result;
        
    }
    
}
