package br.com.wsbasestructure.dao.abstracts;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.c3p0.internal.C3P0ConnectionProvider;
import org.hibernate.cfg.Configuration;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.service.ServiceRegistry;

/**
 *
 * @author Andrew Ribeiro
 */
public class Connection {

    private SessionFactory factory;

    public Connection() {
        Configuration configuration = new Configuration();
        configuration.configure();
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties()).build();
        factory = configuration.buildSessionFactory(serviceRegistry);
    }

    public Session openSession() {
        return factory.openSession();
    }

    public StatelessSession openStatelessSession() {
        return factory.openStatelessSession();
    }

    public SessionFactory getFactory() {
        return factory;
    }

    public void setFactory(SessionFactory factory) {
        this.factory = factory;
    }

    public void closeEverything() {
        SessionFactory s = this.factory;
        if (s instanceof SessionFactoryImpl) {
            ConnectionProvider cp = ((SessionFactoryImpl) s).getConnectionProvider();
            if (cp instanceof C3P0ConnectionProvider) {
                ((C3P0ConnectionProvider) cp).stop();
            }
        }
        if (s != null && !s.isClosed()) {
            s.close();
        }
    }
}
