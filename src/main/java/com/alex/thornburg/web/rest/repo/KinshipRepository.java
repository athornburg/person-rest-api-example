package com.alex.thornburg.web.rest.repo;

import com.alex.thornburg.web.rest.model.Kinship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by alexthornburg on 9/3/15.
 */
public interface KinshipRepository extends JpaRepository<Kinship, Long> {
    Kinship findById(@Param("id")long id);
}
