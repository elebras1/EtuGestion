package com.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class AcademicYearDto {

    @NotNull
    private Long Id;
    private String name;
    private Short praticalWorkSize;
    private Short directedWorkSize;
    private Short numberOptionalTeachingUnit;
    private Long responsibleId;
    private List<GroupDto> groups;
    private List<TeachingUnitDto> teachingUnits;
}
