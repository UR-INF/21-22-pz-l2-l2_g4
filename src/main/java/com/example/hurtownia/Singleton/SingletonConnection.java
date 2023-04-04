package com.example.hurtownia.Singleton;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Gwarantuje istnienie tylko jednego obiektu SessionFactory dostarczającego obiektów Session.
 */
public class SingletonConnection {

    private static SessionFactory sessionFactory;

    /**
     * Tworzy instancję obiektu SessionFactory jeśli taki obiekt nie istnieje.
     *
     * @return obiekt SessionFactory
     */
    private SingletonConnection() {
    }

    public static synchronized SessionFactory getSessionFactory() {
        if (sessionFactory == null)
            sessionFactory = new Configuration().configure(SingletonConnection.class.getResource("/DatabaseCfg/hibernate.cfg.xml")).buildSessionFactory();

        return sessionFactory;
    }
}
