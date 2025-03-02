package com.mappers;

import com.dtos.AcademicYearDto;
import com.entities.AcademicYear;
import com.entities.Group;
import com.entities.TeachingUnit;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AcademicYearMapper {

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

        List<Long> groupsIds = new ArrayList<>();
        if (academicYear.getGroups() != null) {
            for (Group group : academicYear.getGroups()) {
                groupsIds.add(group.getId());
            }
        }
        academicYearDto.setGroupsIds(groupsIds);

        List<Long> teachingUnitsIds = new ArrayList<>();
        if (academicYear.getTeachingUnits() != null) {
            for (TeachingUnit teachingUnit : academicYear.getTeachingUnits()) {
                teachingUnitsIds.add(teachingUnit.getId());
            }
        }
        academicYearDto.setTeachingUnitsIds(teachingUnitsIds);

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
