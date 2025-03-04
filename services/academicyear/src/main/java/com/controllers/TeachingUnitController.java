package com.controllers;

import com.dtos.TeachingUnitDto;
import com.services.TeachingUnitService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teachingunits")
public class TeachingUnitController {
    private final TeachingUnitService teachingUnitService;

    public TeachingUnitController(TeachingUnitService teachingUnitService) {
        this.teachingUnitService = teachingUnitService;
    }

    @GetMapping
    public List<TeachingUnitDto> getTeachingUnits() {
        return this.teachingUnitService.getAllTeachingUnits();
    }

    @PostMapping
    public TeachingUnitDto saveTeachingUnit(@RequestBody TeachingUnitDto teachingUnitDto){
        teachingUnitDto.setId(null);
        return this.teachingUnitService.saveTeachingUnit(teachingUnitDto);
    }

    @PutMapping("/{id}")
    public TeachingUnitDto updateTeachingUnit(@PathVariable Long id, @RequestBody TeachingUnitDto teachingUnitDto){
        teachingUnitDto.setId(id);
        return this.teachingUnitService.updateTeachingUnit(teachingUnitDto);
    }

    @GetMapping("/{id}")
    public TeachingUnitDto getTeachingUnit(@PathVariable Long id){
        return this.teachingUnitService.getTeachingUnitById(id);
    }

    @DeleteMapping("/{id}")
    public boolean deleteTeachingUnit(@PathVariable Long id){
        return this.teachingUnitService.deleteTeachingUnit(id);
    }

    @PostMapping("{id}/register/{studentId}")
    public boolean registerStudent(@PathVariable Long id, @PathVariable Long studentId){
        return this.teachingUnitService.registerStudent(id, studentId);
    }

    @PostMapping("{id}/unregister/{studentId}")
    public boolean unregisterStudent(@PathVariable Long id, @PathVariable Long studentId){
        return this.teachingUnitService.unregisterStudent(id, studentId);
    }
}
