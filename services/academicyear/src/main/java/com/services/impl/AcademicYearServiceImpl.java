package com.services.impl;

import com.dtos.AcademicYearDto;
import com.dtos.GroupDto;
import com.dtos.TeachingUnitDto;
import com.entities.AcademicYear;
import com.entities.Group;
import com.entities.TeachingUnit;
import com.mappers.AcademicYearMapper;
import com.mappers.GroupMapper;
import com.mappers.TeachingUnitMapper;
import com.repositories.AcademicYearRepository;
import com.services.AcademicYearService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("academicYear")
@Transactional
public class AcademicYearServiceImpl implements AcademicYearService {
    private final AcademicYearRepository academicYearRepository;
    private final AcademicYearMapper academicYearMapper;
    private final GroupMapper groupMapper;
    private final TeachingUnitMapper teachingUnitMapper;

    public AcademicYearServiceImpl(AcademicYearRepository academicYearRepository, AcademicYearMapper academicYearMapper, GroupMapper groupMapper, TeachingUnitMapper teachingUnitMapper) {
        this.academicYearRepository = academicYearRepository;
        this.academicYearMapper = academicYearMapper;
        this.groupMapper = groupMapper;
        this.teachingUnitMapper = teachingUnitMapper;
    }

    @Override
    public AcademicYearDto saveAcademicYear(AcademicYearDto academicYearDto) {
        AcademicYear academicYear = this.academicYearMapper.toEntity(academicYearDto);
        AcademicYear savedAcademicYear = this.academicYearRepository.save(academicYear);
        return this.academicYearMapper.toDto(savedAcademicYear);
    }

    @Override
    public AcademicYearDto updateAcademicYear(AcademicYearDto academicYearDto) {
        AcademicYear academicYear = this.academicYearRepository.findById(academicYearDto.getId()).orElseThrow(() ->
                new EntityNotFoundException("Academic year not found"));

        if (academicYearDto.getName() != null) {
            academicYear.setName(academicYearDto.getName());
        }
        if (academicYearDto.getPraticalWorkSize() != null) {
            academicYear.setPraticalWorkSize(academicYearDto.getPraticalWorkSize());
        }
        if (academicYearDto.getDirectedWorkSize() != null) {
            academicYear.setDirectedWorkSize(academicYearDto.getDirectedWorkSize());
        }
        if (academicYearDto.getNumberOptionalTeachingUnit() != null) {
            academicYear.setNumberOptionalTeachingUnit(academicYearDto.getNumberOptionalTeachingUnit());
        }
        if (academicYearDto.getResponsibleId() != null) {
            academicYear.setResponsibleId(academicYearDto.getResponsibleId());
        }

        academicYear = this.academicYearRepository.save(academicYear);
        return this.academicYearMapper.toDto(academicYear);
    }


    @Override
    public AcademicYearDto getAcademicYearById(Long academicYearId) {
        AcademicYear academicYear = this.academicYearRepository.findById(academicYearId).orElseThrow(() ->
                new EntityNotFoundException("Academic year not found"));
        return this.academicYearMapper.toDto(academicYear);
    }

    @Override
    public boolean deleteAcademicYear(Long academicYearId) {
        this.academicYearRepository.deleteById(academicYearId);
        return this.academicYearRepository.findById(academicYearId).isEmpty();
    }

    @Override
    public List<AcademicYearDto> getAllAcademicYears() {
        return this.academicYearRepository.findAll().stream().map(this.academicYearMapper::toDto).toList();
    }

    public List<Long> getStudentsIdByAcademicYear(Long academicYearId) {
        AcademicYear academicYear = this.academicYearRepository.findById(academicYearId).orElse(null);
        if (academicYear != null) {
            return academicYear.getGroups().stream().map(Group::getStudentsIds).flatMap(List::stream).toList();
        }

        return null;
    }

    @Override
    public List<GroupDto> getGroupsByAcademicYear(Long academicYearId) {
        AcademicYear academicYear = this.academicYearRepository.findById(academicYearId).orElse(null);
        if (academicYear == null) {
            return null;
        }

        List<GroupDto> groupDtos = new ArrayList<>();
        for (Group group : academicYear.getGroups()) {
            groupDtos.add(this.groupMapper.toDto(group));
        }

        return groupDtos;
    }

    @Override
    public List<TeachingUnitDto> getTeachingUnitsByAcademicYear(Long academicYearId) {
        AcademicYear academicYear = this.academicYearRepository.findById(academicYearId).orElse(null);
        if (academicYear == null) {
            return null;
        }

        List<TeachingUnitDto> teachingUnitDtos = new ArrayList<>();
        for(TeachingUnit teachingUnit : academicYear.getTeachingUnits()) {
            teachingUnitDtos.add(this.teachingUnitMapper.toDto(teachingUnit));
        }

        return teachingUnitDtos;
    }
}
