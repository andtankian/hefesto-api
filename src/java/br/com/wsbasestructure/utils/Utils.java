package br.com.wsbasestructure.utils;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

/**
 *
 * @author Andrew Ribeiro
 */
public class Utils {

    public static <T> T initializeAndUnproxy(T entity) {
        if (entity == null) {
            return null;
        }

        Hibernate.initialize(entity);
        if (entity instanceof HibernateProxy) {
            entity = (T) ((HibernateProxy) entity).getHibernateLazyInitializer()
                    .getImplementation();
        }
        return entity;
    }

    public static String convertStreamToString(java.io.InputStream is) {
        if (is == null) {
            return "";
        } else {
            java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
            return s.hasNext() ? s.next() : "";
        }
    }

}
