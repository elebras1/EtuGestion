package com.repositories;

import com.entities.TeachingUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeachingUnitRepository extends JpaRepository<TeachingUnit, Long> {
}
