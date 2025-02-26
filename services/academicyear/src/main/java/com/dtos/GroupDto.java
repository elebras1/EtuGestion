package com.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class GroupDto {

    @NotNull
    private Long Id;
    private String name;
    private AcademicYearDto academicYear;
    private List<Long> studentsIds;
}
