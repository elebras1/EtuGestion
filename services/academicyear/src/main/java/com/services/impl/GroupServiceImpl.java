package com.services.impl;

import com.dtos.GroupDto;
import com.entities.AcademicYear;
import com.entities.Group;
import com.mappers.GroupMapper;
import com.repositories.AcademicYearRepository;
import com.repositories.GroupRepository;
import com.services.GroupService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("group")
@Transactional
public class GroupServiceImpl implements GroupService {
    private final GroupRepository groupRepository;
    private final GroupMapper groupMapper;
    private final AcademicYearRepository academicYearRepository;

    public GroupServiceImpl(GroupRepository groupRepository, GroupMapper groupMapper, AcademicYearRepository academicYearRepository) {
        this.groupRepository = groupRepository;
        this.groupMapper = groupMapper;
        this.academicYearRepository = academicYearRepository;
    }

    @Override
    public GroupDto saveGroup(GroupDto groupDto) {
        AcademicYear academicYear = this.academicYearRepository.findById(groupDto.getAcademicYearId())
                .orElseThrow(() -> new EntityNotFoundException("Academic Year not found"));
        Group group = this.groupRepository.save(this.groupMapper.toEntity(groupDto));
        group.setAcademicYear(academicYear);
        academicYear.getGroups().add(group);
        return this.groupMapper.toDto(group);
    }

    @Override
    public GroupDto getGroupById(Long groupDto) {
        Group group = this.groupRepository.findById(groupDto).orElse(null);
        return this.groupMapper.toDto(group);
    }

    @Override
    public boolean deleteGroup(Long groupDto) {
        this.groupRepository.deleteById(groupDto);
        return this.groupRepository.findById(groupDto).isEmpty();
    }

    @Override
    public List<GroupDto> getAllGroups() {
        return this.groupRepository.findAll().stream().map(this.groupMapper::toDto).toList();
    }
}
