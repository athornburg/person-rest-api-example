package com.alex.thornburg.web.rest.repo;

import com.alex.thornburg.web.rest.model.Family;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by alexthornburg on 9/3/15.
 */
public interface FamilyRepository extends JpaRepository<Family, Long> {
    Family findById(@Param("id")long id);
    Family findBySurname(@Param("surname")String surname);
}
