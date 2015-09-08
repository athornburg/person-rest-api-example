package com.alex.thornburg.web.rest.repo;

import com.alex.thornburg.web.rest.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;


/**
 * Created by alexthornburg on 8/31/15.
 */
public interface PersonRepository extends JpaRepository<Person, Long> {
    Person findById(@Param("id")long id);
    List<Person> findByFirstName(@Param("firstName")String firstName);
}
