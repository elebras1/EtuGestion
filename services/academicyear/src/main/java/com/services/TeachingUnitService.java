package com.services;

import com.dtos.TeachingUnitDto;

import java.util.List;

public interface TeachingUnitService {
    TeachingUnitDto saveTeachingUnit(TeachingUnitDto teachingUnitDto);

    TeachingUnitDto getTeachingUnitById(Long TeachingUnitId);

    boolean deleteTeachingUnit(Long TeachingUnitId);

    List<TeachingUnitDto> getAllTeachingUnits();
}
