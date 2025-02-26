package com.mappers;

import com.dtos.AcademicYearDto;
import com.entities.AcademicYear;
import org.springframework.stereotype.Component;

@Component
public class AcademicYearMapper {

    private final GroupMapper groupMapper;
    private final TeachingUnitMapper teachingUnitMapper;

    public AcademicYearMapper(GroupMapper groupMapper, TeachingUnitMapper teachingUnitMapper) {
        this.groupMapper = groupMapper;
        this.teachingUnitMapper = teachingUnitMapper;
    }

    public AcademicYearDto toDto(AcademicYear academicYear) {
        if(academicYear == null) {
            return null;
        }

        AcademicYearDto academicYearDto = new AcademicYearDto();
        academicYearDto.setId(academicYear.getId());
        academicYearDto.setName(academicYear.getName());
        academicYearDto.setPraticalWorkSize(academicYear.getPraticalWorkSize());
        academicYearDto.setDirectedWorkSize(academicYear.getDirectedWorkSize());
        academicYearDto.setNumberOptionalTeachingUnit(academicYear.getNumberOptionalTeachingUnit());
        academicYearDto.setResponsibleId(academicYear.getResponsibleId());
        academicYearDto.setGroups(groupMapper.toDto(academicYear.getGroups()));
        academicYearDto.setTeachingUnits(teachingUnitMapper.toDto(academicYear.getTeachingUnits()));

        return academicYearDto;
    }

    public AcademicYear toEntity(AcademicYearDto academicYearDto) {
        if(academicYearDto == null) {
            return null;
        }

        AcademicYear academicYear = new AcademicYear();
        academicYear.setName(academicYearDto.getName());
        academicYear.setPraticalWorkSize(academicYearDto.getPraticalWorkSize());
        academicYear.setDirectedWorkSize(academicYearDto.getDirectedWorkSize());
        academicYear.setNumberOptionalTeachingUnit(academicYearDto.getNumberOptionalTeachingUnit());
        academicYear.setResponsibleId(academicYearDto.getResponsibleId());

        return academicYear;
    }
}
