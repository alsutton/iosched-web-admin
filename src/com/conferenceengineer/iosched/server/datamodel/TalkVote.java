package com.conferenceengineer.iosched.server.datamodel;

import javax.persistence.*;

/**
 * Representation of a user in the system
 */
@Entity
@Table( name = "talk_vote" )
public class TalkVote {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @ManyToOne
    @JoinColumn(name="talk_id")
    private Talk talk;

    @ManyToOne
    @JoinColumn(name="users_id")
    private SystemUser systemUser;

    @Column(name="vote")
    private int vote;

    public TalkVote() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Talk getTalk() {
        return talk;
    }

    public void setTalk(Talk talk) {
        this.talk = talk;
    }

    public SystemUser getSystemUser() {
        return systemUser;
    }

    public void setSystemUser(SystemUser systemUser) {
        this.systemUser = systemUser;
    }

    public int getVote() {
        return vote;
    }

    public void setVote(int vote) {
        this.vote = vote;
    }
}
