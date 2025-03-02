package com.dtos;

import lombok.Data;

import java.util.List;

@Data
public class TeachingUnitDto {

    private Long id;
    private String name;
    private Boolean isRequired;
    private Short capacity;
    private Long academicYearId;
    private Long responsibleId;
    private List<Long> studentsIds;
}
