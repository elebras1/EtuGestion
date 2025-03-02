package com.services.impl;

import com.dtos.AcademicYearDto;
import com.dtos.GroupDto;
import com.entities.AcademicYear;
import com.entities.Group;
import com.mappers.AcademicYearMapper;
import com.mappers.GroupMapper;
import com.repositories.AcademicYearRepository;
import com.repositories.GroupRepository;
import com.services.AcademicYearService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("academicYear")
@Transactional
public class AcademicYearServiceImpl implements AcademicYearService {
    private final AcademicYearRepository academicYearRepository;
    private final AcademicYearMapper academicYearMapper;
    private final GroupRepository groupRepository;
    private final GroupMapper groupMapper;

    public AcademicYearServiceImpl(AcademicYearRepository academicYearRepository, AcademicYearMapper academicYearMapper, GroupRepository groupRepository, GroupMapper groupMapper) {
        this.academicYearRepository = academicYearRepository;
        this.academicYearMapper = academicYearMapper;
        this.groupRepository = groupRepository;
        this.groupMapper = groupMapper;
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
}
