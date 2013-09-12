package com.conferenceengineer.iosched.server.datamodel;

import javax.persistence.*;
import java.util.Set;

/**
 * Representation of a user in the system
 */
@Entity
@Table( name = "talk" )
public class Talk {

    @Id
    @GeneratedValue
    @Column(name="id")
    private int id;

    @ManyToOne
    @JoinColumn(name="track_id")
    private Track track;

    @ManyToOne
    @JoinColumn(name="talk_slot_id")
    private TalkSlot slot;

    @Column(name="title")
    private String name;

    @Column(name="abstract")
    private String shortDescription;

    @Column(name="long_description")
    private String longDescription;

    @ManyToMany(mappedBy = "talks")
    private Set<Presenter> presenters;

    public Talk() {
        super();
    }

    public Talk(final String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Track getTrack() {
        return track;
    }

    public void setTrack(Track track) {
        this.track = track;
    }

    public TalkSlot getSlot() {
        return slot;
    }

    public void setSlot(TalkSlot slot) {
        this.slot = slot;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }
}
