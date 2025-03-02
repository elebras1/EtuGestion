package com.services.impl;

import com.dtos.TeachingUnitDto;
import com.entities.AcademicYear;
import com.entities.Group;
import com.entities.TeachingUnit;
import com.mappers.TeachingUnitMapper;
import com.repositories.AcademicYearRepository;
import com.repositories.TeachingUnitRepository;
import com.services.TeachingUnitService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("teachingunit")
@Transactional
public class TeachingUnitServiceImpl implements TeachingUnitService {
    private final TeachingUnitRepository teachingUnitRepository;
    private final TeachingUnitMapper teachingUnitMapper;
    private final AcademicYearRepository academicYearRepository;

    public TeachingUnitServiceImpl(TeachingUnitRepository teachingUnitRepository, TeachingUnitMapper teachingUnitMapper, AcademicYearRepository academicYearRepository) {
        this.teachingUnitRepository = teachingUnitRepository;
        this.teachingUnitMapper = teachingUnitMapper;
        this.academicYearRepository = academicYearRepository;
    }

    @Override
    public TeachingUnitDto saveTeachingUnit(TeachingUnitDto teachingUnitDto) {
        AcademicYear academicYear = this.academicYearRepository.findById(teachingUnitDto.getAcademicYearId())
                .orElseThrow(() -> new EntityNotFoundException("Academic Year not found"));
        TeachingUnit teachingUnit = this.teachingUnitMapper.toEntity(teachingUnitDto);
        teachingUnit.setId(null);
        teachingUnit.setAcademicYear(academicYear);
        teachingUnit = this.teachingUnitRepository.save(teachingUnit);
        academicYear.getTeachingUnits().add(teachingUnit);
        return this.teachingUnitMapper.toDto(teachingUnit);
    }

    @Override
    public TeachingUnitDto getTeachingUnitById(Long id) {
        TeachingUnit teachingUnit = this.teachingUnitRepository.findById(id).orElse(null);
        return this.teachingUnitMapper.toDto(teachingUnit);
    }

    @Override
    public boolean deleteTeachingUnit(Long id) {
        this.teachingUnitRepository.deleteById(id);
        return this.teachingUnitRepository.findById(id).isEmpty();
    }

    @Override
    public List<TeachingUnitDto> getAllTeachingUnits() {
        return this.teachingUnitRepository.findAll().stream().map(this.teachingUnitMapper::toDto).toList();
    }
}
