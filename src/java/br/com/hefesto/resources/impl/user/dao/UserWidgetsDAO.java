package br.com.hefesto.resources.impl.user.dao;

import br.com.hefesto.domain.impl.User;
import br.com.hefesto.resources.impl.user.holder.RecentInteractionsHomeWidget;
import br.com.hefesto.resources.impl.user.holder.UserHomeWidgetsHolder;
import br.com.wsbasestructure.dao.abstracts.AbstractDAO;
import br.com.wsbasestructure.dao.interfaces.ICRUD;
import br.com.wsbasestructure.dto.FlowControl;
import br.com.wsbasestructure.dto.Result;
import br.com.wsbasestructure.dto.SearchModel;
import br.com.wsbasestructure.dto.interfaces.IHolder;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;

/**
 *
 * @author andrew
 */
public class UserWidgetsDAO extends AbstractDAO implements ICRUD{

    public UserWidgetsDAO(Session session, IHolder holder) {
        super(session, holder);
    }

    @Override
    public Result perform(FlowControl fc, Result result, String method) {
        super.perform(fc, result, method);
        
        if(method.equalsIgnoreCase("get")){
            return read();
        } else {
            return null;
        }
    }
    
    

    @Override
    public Result create() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Result read() {
        User u = (User) holder.getEntities().get(0);
        SearchModel sm = holder.getSm();
        
        String t = sm.getSearch();
        UserHomeWidgetsHolder uhwh = (UserHomeWidgetsHolder)holder;
        if(t.equals(RecentInteractionsHomeWidget.class.getName())){
            SQLQuery sql = session.createSQLQuery(new StringBuilder("select DATE_FORMAT(intsys.dateReg, '%d/%m/%Y') all_days_formated, DAY(intsys.dateReg) all_days, count(intsys.id) all_interactions, (select count(us.id) from Interaction us where us.user_id = :userid and DAY(intsys.dateReg) = DAY(us.dateReg) and datediff(now(), us.dateReg) <= 30) user_interactions from Interaction intsys where datediff(now(), intsys.dateReg) <= 30 group by DAY(intsys.dateReg)").toString());            
            sql.setLong("userid", u.getId());
            sql.addScalar("all_days_formated", StringType.INSTANCE).addScalar("all_days", LongType.INSTANCE).addScalar("all_interactions", LongType.INSTANCE)
                    .addScalar("user_interactions", LongType.INSTANCE);
            
            List l = sql.list();
            l = l == null ? new ArrayList() : l;
            uhwh.getRecentInteractionsHomeWidget().setAll_days(new Long[l.size()]);
            uhwh.getRecentInteractionsHomeWidget().setAll_days_formated(new String[l.size()]);
            uhwh.getRecentInteractionsHomeWidget().setAll_interactions(new Long[l.size()]);
            uhwh.getRecentInteractionsHomeWidget().setUser_interactions(new Long[l.size()]);
            for (int i = 0; i < l.size(); i++) {
                Object[]o = (Object[]) l.get(i);
                uhwh.getRecentInteractionsHomeWidget().getAll_days_formated()[i] = (String) o[0];
                uhwh.getRecentInteractionsHomeWidget().getAll_days()[i] = (Long) o[1];
                uhwh.getRecentInteractionsHomeWidget().getAll_interactions()[i] = (Long)o[2];
                uhwh.getRecentInteractionsHomeWidget().getUser_interactions()[i] = (Long)o[3];
                
            }
            
            
        }
        message.setText("read");
        result.setHolder(holder);
        result.setStatus(Result.SUCCESS);
        return result;
    }

    @Override
    public Result update() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Result delete() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
