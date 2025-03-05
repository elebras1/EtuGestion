package com.services;

import com.dtos.AcademicYearDto;
import com.dtos.GroupDto;
import com.dtos.RequestDto;
import com.dtos.TeachingUnitDto;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface AcademicYearService {
    AcademicYearDto saveAcademicYear(AcademicYearDto academicYearDto);

    AcademicYearDto updateAcademicYear(AcademicYearDto academicYearDto);

    AcademicYearDto getAcademicYearById(Long academicYearId);

    boolean deleteAcademicYear(Long academicYearId);

    List<AcademicYearDto> getAllAcademicYears();

    List<Long> getStudentsIdByAcademicYear(Long academicYearId);

    List<GroupDto> getGroupsByAcademicYear(Long academicYearId);

    List<TeachingUnitDto> getTeachingUnitsByAcademicYear(Long academicYearId);

    boolean registerStudentToAcademicYear(Long academicYearId, Long studentId);

    boolean acceptStudentToAcademicYear(Long academicYearId, Long studentId);
    boolean rejectStudentToAcademicYear(Long academicYearId, Long studentId);
    void saveAcademicYearFromScraper(String url);
}
