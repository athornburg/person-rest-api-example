package com.alex.thornburg.web.rest.model;


import javax.persistence.*;
import java.util.List;

/**
 * Created by alexthornburg on 8/31/15.
 */
@Entity
public class Family {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    private String surname;
    @OneToMany
    private List<Kinship> people;

    public Family(){}

    public Family(String surname,List<Kinship> people){
        this.surname = surname;
        this.people = people;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public List<Kinship> getPeople() {
        return people;
    }

    public void setPeople(List<Kinship> people) {
        this.people = people;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
