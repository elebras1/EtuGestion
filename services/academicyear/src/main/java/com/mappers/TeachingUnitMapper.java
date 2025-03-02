package com.mappers;

import com.dtos.TeachingUnitDto;
import com.entities.TeachingUnit;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TeachingUnitMapper {

    public TeachingUnitDto toDto(TeachingUnit teachingUnit) {
        if(teachingUnit == null) {
            return null;
        }

        TeachingUnitDto teachingUnitDto = new TeachingUnitDto();
        teachingUnitDto.setId(teachingUnit.getId());
        teachingUnitDto.setName(teachingUnit.getName());
        teachingUnitDto.setIsRequired(teachingUnit.getIsRequired());
        teachingUnitDto.setCapacity(teachingUnit.getCapacity());
        teachingUnitDto.setResponsibleId(teachingUnit.getResponsibleId());
        teachingUnitDto.setStudentsIds(teachingUnit.getStudentsIds());
        teachingUnitDto.setAcademicYearId(teachingUnit.getAcademicYear() != null ? teachingUnit.getAcademicYear().getId() : null);

        return teachingUnitDto;
    }

    public List<TeachingUnitDto> toDto(List<TeachingUnit> teachingUnits) {
        if(teachingUnits == null) {
            return null;
        }

        List<TeachingUnitDto> teachingUnitDtos = new ArrayList<>();
        for(TeachingUnit teachingUnit : teachingUnits) {
            teachingUnitDtos.add(this.toDto(teachingUnit));
        }

        return teachingUnitDtos;
    }

    public TeachingUnit toEntity(TeachingUnitDto teachingUnitDto) {
        if(teachingUnitDto == null) {
            return null;
        }

        TeachingUnit teachingUnit = new TeachingUnit();
        teachingUnit.setId(teachingUnitDto.getId());
        teachingUnit.setName(teachingUnitDto.getName());
        teachingUnit.setIsRequired(teachingUnitDto.getIsRequired());
        teachingUnit.setCapacity(teachingUnitDto.getCapacity());
        teachingUnit.setResponsibleId(teachingUnitDto.getResponsibleId());
        teachingUnit.setStudentsIds(teachingUnitDto.getStudentsIds());

        return teachingUnit;
    }
}
