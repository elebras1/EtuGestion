package com.dtos;

import lombok.Data;

import java.util.List;

@Data
public class GroupDto {

    private Long id;
    private String name;
    private Long academicYearId;
    private List<Long> studentsIds;
}
