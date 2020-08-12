package com.github.zhalabkevich.dao.util;

import org.hibernate.Session;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class HibernateUtil {
    private static EntityManagerFactory factory = null;

    public static EntityManager getEntityManager() {
        if (factory == null) {
            factory = Persistence.createEntityManagerFactory("PU");
        }
        return factory.createEntityManager();
    }

    public static Session getSession() {
        return getEntityManager().unwrap(Session.class);
    }

    public static void close() {
        factory.close();
    }

}
