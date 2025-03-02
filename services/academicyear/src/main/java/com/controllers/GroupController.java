package com.controllers;

import com.dtos.GroupDto;
import com.services.GroupService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/groups")
public class GroupController {
    private final GroupService groupService;

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping
    public List<GroupDto> getGroups() {
        return this.groupService.getAllGroups();
    }

    @PostMapping
    public GroupDto saveGroup(@RequestBody GroupDto groupDto){
        return this.groupService.saveGroup(groupDto);
    }

    @GetMapping("/{id}")
    public GroupDto getGroup(@PathVariable Long id){
        return this.groupService.getGroupById(id);
    }

    @DeleteMapping("/{id}")
    public boolean deleteGroup(@PathVariable Long id){
        return this.groupService.deleteGroup(id);
    }

}
