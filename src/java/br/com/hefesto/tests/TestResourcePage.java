package br.com.hefesto.tests;

import br.com.hefesto.domain.impl.Groups;
import br.com.hefesto.domain.impl.ResourcePage;
import br.com.wsbasestructure.dao.abstracts.Connection;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author Andrew Ribeiro
 */
public class TestResourcePage {

    public static void main(String[] args) {
        Connection c = new Connection();
        Session s = c.openSession();
        Transaction t = s.beginTransaction();

        Set read = new HashSet<>();
        read.add("1");

        ResourcePage r = new ResourcePage();
        r.setId((long) 2);
        r.setRead(new HashSet<>());
        r.setWrite(new HashSet<>());
        for (Object object : read) {
            Groups p = new Groups();
            p.setId(Long.parseLong((String) object));
            p = (Groups) s.get(Groups.class, p.getId());
            r.getRead().add(p);
            r.getWrite().add(p);
        }
        ResourcePage r2 = (ResourcePage) s.get(ResourcePage.class, r.getId());
        r2.merge(r);
        s.update(r2);
        try {
            t.commit();
        } finally {
            c.closeEverything();
        }
    }

}
