package com.alex.thornburg.web.repo;

import com.alex.thornburg.web.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * Created by alexthornburg on 8/31/15.
 */
@RepositoryRestResource(collectionResourceRel = "people", path = "people")
public interface PersonRepo extends JpaRepository<Person, Long> {
    List<Person> findByLastName(@Param("lastName")String lastName);
    List<Person> findByFirstName(@Param("firstName")String firstName);
    List<Person> findByFirstAndLastName(@Param("firstName")String firstName,@Param("lastName")String lastName);
    Person findById(@Param("id")long id);
}
