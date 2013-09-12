package com.conferenceengineer.iosched.server.datamodel;

import javax.persistence.*;

/**
 * Representation of a user in the system
 */
@Entity
@Table( name = "track" )
public class Track {

    @Id
    @GeneratedValue
    @Column(name="id")
    private int id;

    @ManyToOne
    @JoinColumn(name="conference_id")
    private Conference conference;

    @Column(name="track_name")
    private String name;

    public Track() {
        super();
    }

    public Track(final String name, final Conference conference) {
        this.name = name;
        this.conference = conference;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
