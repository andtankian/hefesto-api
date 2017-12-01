package br.com.wsbasestructure.test;

import br.com.hefesto.domain.impl.Interaction;
import br.com.wsbasestructure.dao.abstracts.Connection;
import java.util.List;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.type.IntegerType;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;

/**
 *
 * @author andrew
 */
public class CriteriaSubQueries {

    public static void main(String[] args) {
        Connection c = new Connection();
        Session s = c.openSession();

        DetachedCriteria dc = DetachedCriteria.forClass(Interaction.class);
        dc.setProjection(Projections.count("id"))
                .add(Restrictions.sqlRestriction("datediff(now(),dateReg) <= 7"));

        String query = "select DAY(intsys.dateReg) all_days ,\n"
                + " count(intsys.id) all_interactions, \n"
                + " (select count(us.id) from Interaction us where us.user_id = 2 and MONTH(us.dateReg) = 7 and DAY(us.dateReg) = DAY(intsys.dateReg)) user_interactions \n"
                + "from Interaction intsys\n"
                + "where MONTH(intsys.dateReg) = 7 group by DAY(intsys.dateReg);";
        SQLQuery q = s.createSQLQuery(query);
        q.addScalar("all_days", IntegerType.INSTANCE);
        q.addScalar("all_interactions", LongType.INSTANCE);
        q.addScalar("user_interactions", LongType.INSTANCE);
        List l = q.list();
        s.close();
        c.closeEverything();
    }

}
