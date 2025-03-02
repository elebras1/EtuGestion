package com.services;

import com.dtos.AcademicYearDto;
import com.dtos.GroupDto;

import java.util.List;

public interface AcademicYearService {
    AcademicYearDto saveAcademicYear(AcademicYearDto academicYearDto);

    AcademicYearDto updateAcademicYear(AcademicYearDto academicYearDto);

    AcademicYearDto getAcademicYearById(Long academicYearId);

    boolean deleteAcademicYear(Long academicYearId);

    List<AcademicYearDto> getAllAcademicYears();

    List<Long> getStudentsIdByAcademicYear(Long academicYearId);

    List<GroupDto> getGroupsByAcademicYear(Long academicYearId);
}
