package com.dtos;

import lombok.Data;


@Data
public class AcademicYearDto {
    private Long id;
    private String name;
    private Short praticalWorkSize;
    private Short directedWorkSize;
    private Short numberOptionalTeachingUnit;
    private Long responsibleId;
}
