package com.conferenceengineer.iosched.server.datamodel;

import com.conferenceengineer.iosched.server.datamodel.Conference;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Representation of a user in the system
 */
@Entity
@Table( name = "conference_day" )
public class ConferenceDay implements Comparable<ConferenceDay>{

    @Id
    @GeneratedValue
    @Column(name="id")
    private int id;

    @ManyToOne
    @JoinColumn(name="conference_id")
    private Conference conference;

    @Column(name="day")
    @Temporal(value=TemporalType.DATE)
    private Date date;

    @OneToMany(mappedBy = "conferenceDay", cascade = CascadeType.ALL)
    @OrderBy("start")
    private List<TalkSlot> talkSlotList;


    public ConferenceDay() {
        super();
    }

    public ConferenceDay(final Conference conference, final Date date) {
        setDate(date);
        setConference(conference);
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<TalkSlot> getTalkSlotList() {
        return talkSlotList;
    }

    public void setTalkSlotList(List<TalkSlot> talkSlotList) {
        this.talkSlotList = talkSlotList;
    }

    @Override
    public int compareTo(ConferenceDay other) {
        if(date == null && other.date == null) {
            return 0;
        }
        if(date == null) {
            return Integer.MIN_VALUE;
        }
        if(other.date == null) {
            return Integer.MAX_VALUE;
        }
        return date.compareTo(other.date);
    }
}
