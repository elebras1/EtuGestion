package com.services.impl;

import com.dtos.TeachingUnitDto;
import com.entities.TeachingUnit;
import com.mappers.TeachingUnitMapper;
import com.repositories.TeachingUnitRepository;
import com.services.TeachingUnitService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("teachingunit")
@Transactional
public class TeachingUnitServiceImpl implements TeachingUnitService {
    private final TeachingUnitRepository teachingUnitRepository;
    private final TeachingUnitMapper teachingUnitMapper;

    public TeachingUnitServiceImpl(TeachingUnitRepository teachingUnitRepository, TeachingUnitMapper teachingUnitMapper) {
        this.teachingUnitRepository = teachingUnitRepository;
        this.teachingUnitMapper = teachingUnitMapper;
    }

    @Override
    public TeachingUnitDto saveTeachingUnit(TeachingUnitDto teachingUnitDto) {
        TeachingUnit teachingUnit = this.teachingUnitMapper.toEntity(teachingUnitDto);
        TeachingUnit savedTeachingUnit = this.teachingUnitRepository.save(teachingUnit);
        return this.teachingUnitMapper.toDto(savedTeachingUnit);
    }

    @Override
    public TeachingUnitDto getTeachingUnitById(Long TeachingUnitId) {
        TeachingUnit teachingUnit = this.teachingUnitRepository.findById(TeachingUnitId).orElse(null);
        return this.teachingUnitMapper.toDto(teachingUnit);
    }

    @Override
    public boolean deleteTeachingUnit(Long TeachingUnitId) {
        this.teachingUnitRepository.deleteById(TeachingUnitId);
        return this.teachingUnitRepository.findById(TeachingUnitId).isEmpty();
    }

    @Override
    public List<TeachingUnitDto> getAllTeachingUnits() {
        return this.teachingUnitRepository.findAll().stream().map(this.teachingUnitMapper::toDto).toList();
    }
}
