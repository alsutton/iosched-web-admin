package com.conferenceengineer.iosched.server.datamodel;

import javax.persistence.*;

/**
 * Representation of a user in the system
 */
@Entity
@Table( name = "track" )
public class Track implements Comparable<Track>{

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

    public Conference getConference() {
        return conference;
    }

    public void setConference(Conference conference) {
        this.conference = conference;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int compareTo(Track otherTrack) {
        if(name == null && otherTrack.name == null) {
            return 0;
        }
        if(name == null) {
            return Integer.MIN_VALUE;
        }
        if(otherTrack.name == null) {
            return Integer.MAX_VALUE;
        }
        return name.compareTo(otherTrack.name);
    }
}
