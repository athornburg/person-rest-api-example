package com.alex.thornburg.web.rest.repo;

import com.alex.thornburg.web.rest.model.Sex;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by alexthornburg on 9/3/15.
 */
public interface SexRepository extends JpaRepository<Sex, Long> {
    Sex findById(@Param("id")long id);
}
