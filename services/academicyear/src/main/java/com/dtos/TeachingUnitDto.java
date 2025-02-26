package com.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class TeachingUnitDto {

    @NotNull
    private Long Id;
    private String name;
    private Boolean isRequired;
    private Short capacity;
    private Long academicYearId;
    private Long responsibleId;
    private List<Long> studentsIds;
}
