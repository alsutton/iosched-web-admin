package com.conferenceengineer.iosched.server.datamodel;

import javax.persistence.*;
import java.util.Set;

/**
 * Representation of a user in the system
 */
@Entity
@Table( name = "presenter" )
public class Presenter {

    @Id
    @GeneratedValue
    @Column(name="id")
    private int id;

    @ManyToOne
    @JoinColumn(name="user_id")
    private SystemUser user;

    @Column(name="short_biography")
    private String shortBiography;

    @Column(name="long_biography")
    private String longBiography;

    @ManyToMany
    @JoinTable(name="talk_presenter")
    private Set<Talk> talks;

    public Presenter() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public SystemUser getUser() {
        return user;
    }

    public void setUser(SystemUser user) {
        this.user = user;
    }

    public String getShortBiography() {
        return shortBiography;
    }

    public void setShortBiography(String shortBiography) {
        this.shortBiography = shortBiography;
    }

    public String getLongBiography() {
        return longBiography;
    }

    public void setLongBiography(String longBiography) {
        this.longBiography = longBiography;
    }

    public Set<Talk> getTalks() {
        return talks;
    }

    public void setTalks(Set<Talk> talks) {
        this.talks = talks;
    }
}
