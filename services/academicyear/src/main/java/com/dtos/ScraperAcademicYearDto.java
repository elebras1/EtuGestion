package com.dtos;

import lombok.Data;

import java.util.List;

@Data
public class ScraperAcademicYearDto {
    private String name;
    private Short tdSize;
    private Short tpSize;
    private Short OptionsNumber;
    private List<ScraperTeachingUnitDto> teachingUnits;
}
