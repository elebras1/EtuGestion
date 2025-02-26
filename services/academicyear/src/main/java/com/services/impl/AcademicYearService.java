package com.services.impl;


import com.dtos.AcademicYearDto;

import java.util.List;

public interface AcademicYearService {
    AcademicYearDto saveAcademicYear(AcademicYearDto academicYearDto);

    AcademicYearDto getAcademicYearById(Long academicYearId);

    boolean deleteAcademicYear(Long academicYearId);

    List<AcademicYearDto> getAllAcademicYears();
}
