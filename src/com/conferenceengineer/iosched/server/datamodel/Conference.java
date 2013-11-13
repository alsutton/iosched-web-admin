package com.conferenceengineer.iosched.server.datamodel;

import com.conferenceengineer.iosched.server.datamodel.ConferenceDay;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Representation of a user in the system
 */
@Entity
@Table( name = "conference" )
public class Conference {

    @Id
    @GeneratedValue
    @Column(name="id")
    private int id;

    @ManyToOne
    @JoinColumn(name="series_id")
    private Series series;

    @ManyToOne
    @JoinColumn(name="location_id")
    private Location location;

    @Column(name="conference_name")
    private String name;

    @Column(name="hashtag")
    private String hashtag;

    @OneToOne(mappedBy = "conference")
    private ConferenceMetadata metadata;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "conference")
    @OrderBy("date")
    private List<ConferenceDay> dateList;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "conference")
    @OrderBy("name")
    private List<Presenter> presenterList;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "conference")
    @OrderBy("name")
    private List<Track> trackList;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "conference")
    @OrderBy("name")
    private List<TalkLocation> talkLocationList;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "conference")
    private List<ConferencePermission> collaborators;

    public Conference() {
        super();
    }

    public Conference(final String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Series getSeries() {
        return series;
    }

    public void setSeries(Series series) {
        this.series = series;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHashtag() {
        return hashtag;
    }

    public void setHashtag(String hashtag) {
        this.hashtag = hashtag;
    }

    public ConferenceMetadata getMetadata() {
        return metadata;
    }

    public void setMetadata(ConferenceMetadata metadata) {
        this.metadata = metadata;
    }

    public List<ConferenceDay> getDateList() {
        return dateList;
    }

    public void setDateList(List<ConferenceDay> dateList) {
        this.dateList = dateList;
    }

    public List<Presenter> getPresenterList() {
        return presenterList;
    }

    public void setPresenterList(List<Presenter> presenterList) {
        this.presenterList = presenterList;
    }

    public List<Track> getTrackList() {
        return trackList;
    }

    public void setTrackList(List<Track> trackList) {
        this.trackList = trackList;
    }

    public List<TalkLocation> getTalkLocationList() {
        return talkLocationList;
    }

    public void setTalkLocationList(List<TalkLocation> talkLocationList) {
        this.talkLocationList = talkLocationList;
    }

    public List<ConferencePermission> getCollaborators() {
        return collaborators;
    }

    public void setCollaborators(List<ConferencePermission> collaborators) {
        this.collaborators = collaborators;
    }
}
