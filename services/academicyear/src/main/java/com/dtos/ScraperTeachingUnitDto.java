package com.dtos;

import lombok.Data;

@Data
public class ScraperTeachingUnitDto {
    private String name;
    private Short ects;
    private Short hours;
    private Short capacity;
    private Boolean isMandatory;
}
