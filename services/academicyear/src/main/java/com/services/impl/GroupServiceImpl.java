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
        Group group = this.groupMapper.toEntity(groupDto);
        group.setAcademicYear(academicYear);
        group = this.groupRepository.save(group);
        academicYear.getGroups().add(group);
        return this.groupMapper.toDto(group);
    }

    @Override
    public GroupDto updateGroup(GroupDto groupDto) {
        Group group = this.groupRepository.findById(groupDto.getId()).orElseThrow(() ->
                new EntityNotFoundException("Group not found"));

        if (groupDto.getName() != null) {
            group.setName(groupDto.getName());
        }
        if (groupDto.getAcademicYearId() != null) {
            AcademicYear academicYear = this.academicYearRepository.findById(groupDto.getAcademicYearId())
                    .orElseThrow(() -> new EntityNotFoundException("Academic Year not found"));
            group.setAcademicYear(academicYear);
        }
        if(groupDto.getStudentsIds() != null) {
            group.getStudentsIds().clear();
            for(Long studentId : groupDto.getStudentsIds()) {
                group.getStudentsIds().add(studentId);
            }
        }

        group = this.groupRepository.save(group);
        return this.groupMapper.toDto(group);
    }

    @Override
    public GroupDto getGroupById(Long groupId) {
        Group group = this.groupRepository.findById(groupId).orElseThrow(() ->
                new EntityNotFoundException("Group not found"));
        return this.groupMapper.toDto(group);
    }

    @Override
    public boolean deleteGroup(Long groupId) {
        this.groupRepository.deleteById(groupId);
        return this.groupRepository.findById(groupId).isEmpty();
    }

    @Override
    public List<GroupDto> getAllGroups() {
        return this.groupRepository.findAll().stream().map(this.groupMapper::toDto).toList();
    }
}
