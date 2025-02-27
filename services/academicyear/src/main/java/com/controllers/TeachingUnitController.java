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
    public TeachingUnitDto saveTeachingUnit(final @RequestBody TeachingUnitDto teachingUnitDto){
        return this.teachingUnitService.saveTeachingUnit(teachingUnitDto);
    }

    @GetMapping("/{id}")
    public TeachingUnitDto getTeachingUnit(final @PathVariable Long id){
        return this.teachingUnitService.getTeachingUnitById(id);
    }

    @DeleteMapping("/{id}")
    public boolean deleteTeachingUnit(final @PathVariable Long id){
        return this.teachingUnitService.deleteTeachingUnit(id);
    }
}
