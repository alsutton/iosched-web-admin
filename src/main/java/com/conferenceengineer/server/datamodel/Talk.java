package com.conferenceengineer.iosched.server.datamodel;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Representation of a user in the system
 */
@Entity
@Table( name = "talk" )
public class Talk {

    /**
     * The types of talks.
     */

    public static final int TYPE_ACCEPTED = 0x00,
                            TYPE_PROPOSED = 0x01,
                            TYPE_KEYNOTE = 0x02;

    @Id
    @GeneratedValue
    @Column(name="id")
    private int id;

    @ManyToOne
    @JoinColumn(name="conference_id")
    private Conference conference;

    @ManyToOne
    @JoinColumn(name="track_id")
    private Track track;

    @ManyToOne
    @JoinColumn(name="talk_slot_id")
    private TalkSlot slot;

    @ManyToOne
    @JoinColumn(name="talk_location_id")
    private TalkLocation location;

    @Column(name="type")
    private Integer type;

    @Column(name="title")
    private String name;

    @Column(name="abstract")
    private String shortDescription;

    @Column(name="long_description")
    private String longDescription;

    @Column(name="information_url")
    private String informationURL;

    @ManyToMany
    @JoinTable(name="talk_presenter")
    @OrderBy("name")
    private Set<Presenter> presenters;

    public Talk() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Track getTrack() {
        return track;
    }

    public void setTrack(Track track) {
        this.track = track;
    }

    public Conference getConference() {
        return conference;
    }

    public void setConference(Conference conference) {
        this.conference = conference;
    }

    public TalkSlot getSlot() {
        return slot;
    }

    public void setSlot(TalkSlot slot) {
        this.slot = slot;
    }

    public TalkLocation getLocation() {
        return location;
    }

    public void setLocation(TalkLocation location) {
        this.location = location;
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

    public String getInformationURL() {
        return informationURL;
    }

    public void setInformationURL(String informationURL) {
        this.informationURL = informationURL;
    }

    public Set<Presenter> getPresenters() {
        return presenters;
    }

    public void setPresenters(Set<Presenter> presenters) {
        this.presenters = presenters;
    }
}
