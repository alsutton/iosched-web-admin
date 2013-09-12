package com.conferenceengineer.iosched.server.datamodel;

import javax.persistence.EntityManager;

/**
 * Data Accessor Object for Location Objects
 */
public class LocationDAO {

    /**
     * Store the information about a location.
     *
     * @param entityManager The currently active EntityManager
     * @param name The name of the location.
     * @param address The address of the location.
     */
    public void store(final EntityManager entityManager, final String name, final String address ) {
        entityManager.persist(new Location(name, address));
    }

    /**
     * Fetch and object by it's ID
     *
     * @param entityManager The currently active EntityManager
     * @param id The ID of the object to get
     */

    public Location getById(final EntityManager entityManager, final int id) {
        return entityManager.find(Location.class, id);
    }
}
