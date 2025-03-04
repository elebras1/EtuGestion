package com.services;

import com.dtos.TeachingUnitDto;

import java.util.List;

public interface TeachingUnitService {
    TeachingUnitDto saveTeachingUnit(TeachingUnitDto teachingUnitDto);

    TeachingUnitDto updateTeachingUnit(TeachingUnitDto teachingUnitDto);

    TeachingUnitDto getTeachingUnitById(Long teachingUnitId);

    boolean deleteTeachingUnit(Long teachingUnitId);

    List<TeachingUnitDto> getAllTeachingUnits();

    boolean registerStudent(Long teachingUnitId, Long studentId);

    boolean unregisterStudent(Long teachingUnitId, Long studentId);
}
