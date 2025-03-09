package com.mappers;

import com.dtos.GroupDto;
import com.entities.Group;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class GroupMapper {

    public GroupDto toDto(Group group) {
        if(group == null) {
            return null;
        }

        GroupDto groupDto = new GroupDto();
        groupDto.setId(group.getId());
        groupDto.setName(group.getName());
        groupDto.setType(group.getType());
        groupDto.setStudentsIds(group.getStudentsIds());
        groupDto.setAcademicYearId(group.getAcademicYear() != null ? group.getAcademicYear().getId() : null);

        return groupDto;
    }

    public List<GroupDto> toDto(List<Group> groups) {
        if(groups == null) {
            return null;
        }

        List<GroupDto> groupDtos = new ArrayList<>();
        for(Group group : groups) {
            groupDtos.add(this.toDto(group));
        }

        return groupDtos;
    }

    public Group toEntity(GroupDto groupDto) {
        if(groupDto == null) {
            return null;
        }

        Group group = new Group();
        group.setId(groupDto.getId());
        group.setName(groupDto.getName());
        group.setType(groupDto.getType());
        group.setStudentsIds(groupDto.getStudentsIds());

        return group;
    }
}
