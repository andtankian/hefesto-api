package br.com.hefesto.tests;

import br.com.hefesto.domain.impl.SLA;
import br.com.hefesto.domain.impl.Service;
import br.com.wsbasestructure.dao.abstracts.Connection;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Andrew Ribeiro
 */
public class TestServicesSLA {

    public static void main(String[] args) {
        Connection c = new Connection();
        Session s = c.openSession();

        Transaction t = s.beginTransaction();

        Service sv = new Service();
        sv.setName("Organização de cabos individuais");
        SLA sla = new SLA();
        sla.setMinutes(30);
        sv.setSla(sla);
        
        s.persist(sv);

        t.commit();

        c.closeEverything();
    }

}
