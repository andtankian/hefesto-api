package br.com.hefesto.tests;

import br.com.hefesto.domain.impl.Department;
import br.com.hefesto.domain.impl.User;
import br.com.wsbasestructure.dao.abstracts.Connection;
import java.sql.Timestamp;
import java.util.Calendar;
import org.hibernate.Session;

/**
 *
 * @author Andrew Ribeiro
 */
public class SaveBiDirection {
    
    public static void main(String[] args) {
        Connection c = new Connection();
        Session session = c.openSession();
        
        Department d = new Department();
        d.setDateReg(new Timestamp(Calendar.getInstance().getTimeInMillis()));
        d.setName("Administração");
        d.setDescription("Departamento de administração");
        
        User u = new User();
        u.setFullName("Fulano da Silva");
        u.setLogin("fulano.silva");
        u.setPassword("12345");
        u.setDepartment(d);
        
        session.beginTransaction();
        session.persist(u);
        session.getTransaction().commit();
        session.close();
        c.closeEverything();
        
        
    }
}
