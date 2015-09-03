package com.alex.thornburg.web.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * Created by alexthornburg on 8/31/15.
 */
public class Family {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    private String surname;
    private
    @OneToMany
    List<Person> people;

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public List<Person> getPeople() {
        return people;
    }

    public void setPeople(List<Person> people) {
        this.people = people;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
