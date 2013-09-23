package com.conferenceengineer.iosched.server.datamodel.utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;

/**
 * Wrapper for the EntityManagerFactory which re-uses any existing EntityManager and allows the
 * EntityManager to be cleanly closed
 */
public class EntityManagerFactoryWrapper {

    /**
     * The EntityManagerFactory for this application.
     */

    private final EntityManagerFactory mEntityManagerFactory;

    /**
     * The holder to the EntityManager
     */

    private static ThreadLocal<EntityManager> sEntityManagerHolder = new ThreadLocal<EntityManager>();

    /**
     * Private constructor to enforce singleton pattern
     */

    private EntityManagerFactoryWrapper() {
        Map<String,String> entityProperties = new HashMap<String, String>();
        entityProperties.put("javax.persistence.jdbc.user", "cewebapp");
        entityProperties.put("javax.persistence.jdbc.password", "c00lb34ns");
        mEntityManagerFactory = Persistence.createEntityManagerFactory("conferenceengineer", entityProperties);
    }


    /**
     * Get an EntityManager for use in an application.
     *
     * @return An usable EntityManager
     */

    public EntityManager getEntityManager() {
        return mEntityManagerFactory.createEntityManager();
    }

    /**
     * Close the factory
     */

    public void close() {
        mEntityManagerFactory.close();
    }


    /**
     * Singleton getInstance method
     */

    public static EntityManagerFactoryWrapper getInstance() {
        return InstanceHolder.INSTANCE;
    }

    /**
     * Singleton instance holder
     */

    private static final class InstanceHolder {
        private static final EntityManagerFactoryWrapper INSTANCE = new EntityManagerFactoryWrapper();
    }
}
