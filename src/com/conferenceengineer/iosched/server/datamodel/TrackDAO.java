package com.conferenceengineer.iosched.server.datamodel;

import javax.persistence.EntityManager;

/**
 * Data Accessor Object for Track Objects
 */
public class TrackDAO {

    /**
     * Store the information about a track.
     *
     * @param entityManager The currently active EntityManager
     * @param name The name of the track.
     * @param conference The conference the track belongs to
     */
    public void store(final EntityManager entityManager, final String name, final Conference conference ) {
        entityManager.persist(new Track(name, conference));
    }

    /**
     * Fetch and object by it's ID
     *
     * @param entityManager The currently active EntityManager
     * @param id The ID of the object to get
     */

    public Track getById(final EntityManager entityManager, final int id) {
        return entityManager.find(Track.class, id);
    }

    //------------------------ Singleton pattern to fetch this DAO ----------------------------------

    private static final class InstanceHolder {
        private static final TrackDAO INSTANCE = new TrackDAO();
    }

    public static TrackDAO getInstance() {
        return InstanceHolder.INSTANCE;
    }
}
