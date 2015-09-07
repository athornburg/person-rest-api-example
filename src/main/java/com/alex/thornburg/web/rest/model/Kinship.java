package com.alex.thornburg.web.rest.model;

import javax.persistence.*;

/**
 * Created by alexthornburg on 9/3/15.
 */
@Entity
public class Kinship {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    @OneToOne
    private Person origin;
    @OneToOne
    private Person relative;
    private String relName;
    private double dnaDifference;

    public Kinship(String relName,Person origin,Person relative,double dnaDifference){
        this.origin = origin;
        this.relative = relative;
        this.dnaDifference = dnaDifference;
        this.relName = relName;
    }

    public String getRelName() {
        return relName;
    }

    public void setRelName(String relName) {
        this.relName = relName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Person getOrigin() {
        return origin;
    }

    public void setOrigin(Person origin) {
        this.origin = origin;
    }

    public Person getRelative() {
        return relative;
    }

    public void setRelative(Person relative) {
        this.relative = relative;
    }

    public double getDnaDifference() {
        return dnaDifference;
    }

    public void setDnaDifference(double dnaDifference) {
        this.dnaDifference = dnaDifference;
    }
}
