package com.repositories;

import com.entities.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {
    Request findByAcademicYearIdAndStudentId(Long academicYearId, Long studentId);
}
