package com.services.impl;

import com.dtos.AcademicYearDto;
import com.entities.AcademicYear;
import com.mappers.AcademicYearMapper;
import com.repositories.AcademicYearRepository;
import com.services.AcademicYearService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("academicYear")
@Transactional
public class AcademicYearServiceImpl implements AcademicYearService {
    private final AcademicYearRepository academicYearRepository;
    private final AcademicYearMapper academicYearMapper;

    public AcademicYearServiceImpl(AcademicYearRepository academicYearRepository, AcademicYearMapper academicYearMapper) {
        this.academicYearRepository = academicYearRepository;
        this.academicYearMapper = academicYearMapper;
    }

    @Override
    public AcademicYearDto saveAcademicYear(AcademicYearDto academicYearDto) {
        AcademicYear academicYear = this.academicYearMapper.toEntity(academicYearDto);
        AcademicYear savedAcademicYear = this.academicYearRepository.save(academicYear);
        return this.academicYearMapper.toDto(savedAcademicYear);
    }

    @Override
    public AcademicYearDto getAcademicYearById(Long academicYearId) {
        AcademicYear academicYear = this.academicYearRepository.findById(academicYearId).orElse(null);
        return this.academicYearMapper.toDto(academicYear);
    }

    @Override
    public boolean deleteAcademicYear(Long academicYearId) {
        this.academicYearRepository.deleteById(academicYearId);
        return false;
    }

    @Override
    public List<AcademicYearDto> getAllAcademicYears() {
        return this.academicYearRepository.findAll().stream().map(this.academicYearMapper::toDto).toList();
    }
}
