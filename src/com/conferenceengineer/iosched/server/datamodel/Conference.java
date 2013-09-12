package com.conferenceengineer.iosched.server.datamodel;

import javax.persistence.*;
import java.util.Date;

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

    @Column(name="start_date")
    @Temporal(value=TemporalType.DATE)
    private Date startDate;

    @Column(name="end_date")
    @Temporal(value=TemporalType.DATE)
    private Date endDate;


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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
