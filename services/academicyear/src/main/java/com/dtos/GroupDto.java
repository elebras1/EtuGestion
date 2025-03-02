package com.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class GroupDto {

    @NotNull
    private Long id;
    private String name;
    private Long academicYearId;
    private List<Long> studentsIds;
}
