package com.conferenceengineer.iosched.server.datamodel;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Representation of a user in the system
 */
@Entity
@Table( name = "talk_slot" )
public class TalkSlot {

    @Id
    @GeneratedValue
    @Column(name="id")
    private int id;

    @ManyToOne
    @JoinColumn(name="conference_day")
    private ConferenceDay conferenceDay;

    @Column(name="slot_start")
    @Temporal(value=TemporalType.TIMESTAMP)
    private Calendar start;

    @Column(name="slot_end")
    @Temporal(value=TemporalType.TIMESTAMP)
    private Calendar end;

    @ManyToOne
    @JoinColumn(name="location_id")
    private Location location;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "slot")
    @OrderBy("name")
    private List<Talk> talkList;

    public TalkSlot() {
        super();
    }

    public TalkSlot(final ConferenceDay conferenceDay, final Calendar start, final Calendar end) {
        this.conferenceDay = conferenceDay;
        this.start = start;
        this.end = end;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ConferenceDay getConferenceDay() {
        return conferenceDay;
    }

    public void setConferenceDay(ConferenceDay conferenceDay) {
        this.conferenceDay = conferenceDay;
    }

    public Calendar getStart() {
        return start;
    }

    public void setStart(Calendar start) {
        this.start = start;
    }

    public Calendar getEnd() {
        return end;
    }

    public void setEnd(Calendar end) {
        this.end = end;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<Talk> getTalkList() {
        return talkList;
    }

    public void setTalkList(List<Talk> talkList) {
        this.talkList = talkList;
    }
}
