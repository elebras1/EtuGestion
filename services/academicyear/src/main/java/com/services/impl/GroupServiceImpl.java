package com.services.impl;

import com.dtos.GroupDto;
import com.entities.Group;
import com.mappers.GroupMapper;
import com.repositories.GroupRepository;
import com.services.GroupService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("group")
@Transactional
public class GroupServiceImpl implements GroupService {
    private final GroupRepository groupRepository;
    private final GroupMapper groupMapper;

    public GroupServiceImpl(GroupRepository groupRepository, GroupMapper groupMapper) {
        this.groupRepository = groupRepository;
        this.groupMapper = groupMapper;
    }

    @Override
    public GroupDto saveGroup(GroupDto GroupDto) {
        Group group = this.groupRepository.save(this.groupMapper.toEntity(GroupDto));
        return this.groupMapper.toDto(group);
    }

    @Override
    public GroupDto getGroupById(Long GroupId) {
        Group group = this.groupRepository.findById(GroupId).orElse(null);
        return this.groupMapper.toDto(group);
    }

    @Override
    public boolean deleteGroup(Long GroupId) {
        this.groupRepository.deleteById(GroupId);
        return this.groupRepository.findById(GroupId).isEmpty();
    }

    @Override
    public List<GroupDto> getAllGroups() {
        return this.groupRepository.findAll().stream().map(this.groupMapper::toDto).toList();
    }
}
