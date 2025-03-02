package com.services;

import com.dtos.GroupDto;

import java.util.List;

public interface GroupService {
    GroupDto saveGroup(GroupDto groupDto);

    GroupDto getGroupById(Long groupId);

    boolean deleteGroup(Long groupId);

    List<GroupDto> getAllGroups();
}
