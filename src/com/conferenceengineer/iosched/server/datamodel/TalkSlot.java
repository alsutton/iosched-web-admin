package com.conferenceengineer.iosched.server.datamodel;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

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

    @Column(name="slot_date")
    @Temporal(value=TemporalType.DATE)
    private Date date;

    @Column(name="slot_start")
    @Temporal(value=TemporalType.TIMESTAMP)
    private Calendar start;

    @Column(name="slot_end")
    @Temporal(value=TemporalType.TIMESTAMP)
    private Calendar end;

    @ManyToOne
    @JoinColumn(name="location_id")
    private Location location;

    @ManyToOne
    @JoinColumn(name="track_id")
    private Track track;

    public TalkSlot() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

    public Track getTrack() {
        return track;
    }

    public void setTrack(Track track) {
        this.track = track;
    }
}
