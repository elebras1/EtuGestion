package com.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class AcademicYearDto {

    @NotNull
    private Long id;
    private String name;
    private Short praticalWorkSize;
    private Short directedWorkSize;
    private Short numberOptionalTeachingUnit;
    private Long responsibleId;
}
