package br.com.hefesto.resources.impl.equipments.dao;

import br.com.hefesto.domain.impl.Equipment;
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
public class EquipmentDAO extends GenericCRUDDAO{

    public EquipmentDAO(Session session, IHolder holder) {
        super(session, holder);
    }

    @Override
    public Result read() {
        SearchModel sm = holder.getSm();
        Equipment e = (Equipment) holder.getEntities().get(0);
        if(sm != null && sm.getEntity() != null && sm.getEntity().getId() != null){
            readOne();
        } else if(sm != null && sm.getEntity() != null && sm.getSearch() != null && !sm.getSearch().isEmpty()){
            Equipment esearch = (Equipment)sm.getEntity();
            try {
                esearch.setId(Long.parseLong(sm.getSearch()));
            } catch (NumberFormatException n) {
                esearch.setId((long) -1);
            }
            Criteria crSearch = session.createCriteria(Equipment.class);
            crSearch.setMaxResults(sm.getLimit().intValue());
            crSearch.setFirstResult(sm.getOffset().intValue());
            crSearch.add(Restrictions.disjunction(
                    Restrictions.ilike("name", esearch.getName(), MatchMode.ANYWHERE),
                    Restrictions.ilike("description", esearch.getDescription(), MatchMode.ANYWHERE),
                    Restrictions.ilike("patrimonial", sm.getSearch(), MatchMode.ANYWHERE),
                    Restrictions.sqlRestriction("lower(id) like '%" + String.valueOf(esearch.getId()) + "%'"),
                    Restrictions.sqlRestriction("lower(dateReg) like '%" + String.valueOf(sm.getSearch()) + "%'")
            )).add(Restrictions.isNull("status"));
            
            crSearch.addOrder(Order.desc("dateReg"));

            holder.setEntities(crSearch.list());

            crSearch.setMaxResults(0);
            crSearch.setFirstResult(0);
            crSearch.setProjection(Projections.rowCount());
            holder.setTotalEntities((Long) crSearch.uniqueResult());

            message.setText("read");
            result.setStatus(Result.SUCCESS);
        } else {
            Criteria c = session.createCriteria(Equipment.class);
            if (sm != null && sm.getEntity() != null
                    && sm.getEntity().getStatus() != null) {
                c.add(Restrictions.eq("status", sm.getEntity().getStatus()));
            } else {
                c.add(Restrictions.isNull("status"));
            }
            c.setMaxResults(sm.getLimit().intValue());
            c.setFirstResult(sm.getOffset().intValue());
            c.addOrder(Order.desc("dateReg"));
            holder.setEntities(c.list());

            c.setMaxResults(0);
            c.setFirstResult(0);
            c.setProjection(Projections.rowCount());
            holder.setTotalEntities((Long) c.uniqueResult());
            message.setText("read");
            result.setStatus(Result.SUCCESS);
        }
        result.setMessage(message);
        result.setHolder(holder);

        return result;
    }
    
}
