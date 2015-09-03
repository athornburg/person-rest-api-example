package com.alex.thornburg.web.rest.model;

import javax.persistence.*;
import java.util.List;

/**
 * Created by alexthornburg on 8/31/15.
 */
@Entity
public class Address {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    @OneToMany(mappedBy="address")
    public List<Person> people;
    private String streetAddress;
    private String country;
    private String city;
    private int zip;

    public Address(){}

    public Address(String streetAddress,String country,String city,int zip){
        this.streetAddress = streetAddress;
        this.country = country;
        this.city = city;
        this.zip = zip;
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Person> getPeople() {
        return people;
    }

    public void setPeople(List<Person> people) {
        this.people = people;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getZip() {
        return zip;
    }

    public void setZip(int zip) {
        this.zip = zip;
    }

}
