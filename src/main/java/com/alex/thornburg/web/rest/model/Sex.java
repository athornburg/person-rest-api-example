package com.alex.thornburg.web.rest.model;

import javax.persistence.*;

/**
 * Created by alexthornburg on 9/2/15.
 */
@Entity
public class Sex {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    private boolean male,female;
    @OneToOne
    Person person;

    public Sex(){}
    public Sex(boolean male,boolean female) {
        this.male = male;
        this.female = female;
    }

    public boolean isMale() {
        return male;
    }

    public void setMale(boolean male) {
        this.male = male;
    }

    public boolean isFemale() {
        return female;
    }

    public void setFemale(boolean female) {
        this.female = female;
    }
}
