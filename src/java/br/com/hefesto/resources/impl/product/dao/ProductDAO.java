package br.com.hefesto.resources.impl.product.dao;

import br.com.hefesto.domain.impl.Product;
import br.com.wsbasestructure.dao.impl.GenericCRUDDAO;
import br.com.wsbasestructure.dto.Result;
import br.com.wsbasestructure.dto.SearchModel;
import br.com.wsbasestructure.dto.interfaces.IHolder;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Andrew Ribeiro
 */
public class ProductDAO extends GenericCRUDDAO {

    public ProductDAO(Session session, IHolder holder) {
        super(session, holder);
    }

    @Override
    public Result read() {
        Product p = (Product) holder.getEntities().get(0);
        SearchModel sm = holder.getSm();
        Criteria c = session.createCriteria(p.getClass());

        if (sm != null && sm.getEntity() != null && sm.getEntity().getId() != null) {
            readOne();
        } else if(sm != null && sm.getSearch() != null){
            c.addOrder(Order.desc("dateReg"));
            c.add(Restrictions.disjunction(
                    Restrictions.ilike("name", sm.getSearch(), MatchMode.ANYWHERE),
                    Restrictions.ilike("description", sm.getSearch(), MatchMode.ANYWHERE),
                    Restrictions.ilike("link", sm.getSearch()),
                    Restrictions.ilike("type", sm.getSearch()),
                    Restrictions.sqlRestriction("lower(id) like '%" + String.valueOf(p.getId()) + "%'"),
                    Restrictions.sqlRestriction("lower(dateReg) like '%" + String.valueOf(sm.getSearch()) + "%'")
            )).add(Restrictions.isNull("status"));
            
            c.setMaxResults(sm.getLimit().intValue());
            c.setFirstResult(sm.getOffset().intValue());
            holder.setEntities(c.list());
            
            c.setFirstResult(0);
            c.setMaxResults(0);
            c.setProjection(Projections.rowCount());
            
            holder.setTotalEntities((Long) c.uniqueResult());
            message.setText("read products");
            result.setStatus(Result.SUCCESS);
        }
        else {

            c.addOrder(Order.desc("dateReg"));
            c.add(Restrictions.isNull("status"));
            c.setFirstResult(sm.getOffset().intValue());
            c.setMaxResults(sm.getLimit().intValue());
            if (sm != null && sm.getEntity() != null
                    && sm.getEntity().getStatus() != null) {
                c.add(Restrictions.eq("status", sm.getEntity().getStatus()));
            } else {
                c.add(Restrictions.isNull("status"));
            }

            holder.setEntities(c.list());
            c.setProjection(Projections.rowCount());
            holder.setTotalEntities((Long) c.uniqueResult());
            message.setText("read all");
            result.setStatus(Result.SUCCESS);
        }

        result.setHolder(holder);

        return result;
    }

}
