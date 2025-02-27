package com.services;

import com.dtos.GroupDto;

import java.util.List;

public interface GroupService {
    GroupDto saveGroup(GroupDto GroupDto);

    GroupDto getGroupById(Long GroupId);

    boolean deleteGroup(Long GroupId);

    List<GroupDto> getAllGroups();
}
