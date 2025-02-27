package com.controllers;

import com.dtos.AcademicYearDto;
import com.services.AcademicYearService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/academicyears")
public class AcademicYearController {
    private final AcademicYearService academicYearService;

    public AcademicYearController(AcademicYearService academicYearService) {
        this.academicYearService = academicYearService;
    }

    @GetMapping
    public List<AcademicYearDto> getAcademicYears() {
        return this.academicYearService.getAllAcademicYears();
    }

    @PostMapping
    public AcademicYearDto saveAcademicYear(final @RequestBody AcademicYearDto academicYearDto){
        return this.academicYearService.saveAcademicYear(academicYearDto);
    }

    @GetMapping("/{id}")
    public AcademicYearDto getAcademicYear(final @PathVariable Long id){
        return this.academicYearService.getAcademicYearById(id);
    }

    @DeleteMapping("/{id}")
    public boolean deleteAcademicYear(final @PathVariable Long id){
        return this.academicYearService.deleteAcademicYear(id);
    }

}
