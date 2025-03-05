package com.controllers;

import com.dtos.AcademicYearDto;
import com.dtos.GroupDto;
import com.dtos.TeachingUnitDto;
import com.services.AcademicYearService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/academicyears")
public class AcademicYearController {
    final String scraperUrl = "http://localhost:9090/scraper";
    private final AcademicYearService academicYearService;
    public AcademicYearController(AcademicYearService academicYearService) {
        this.academicYearService = academicYearService;
    }

    @GetMapping
    public List<AcademicYearDto> getAcademicYears() {
        return this.academicYearService.getAllAcademicYears();
    }

    @PostMapping
    public AcademicYearDto saveAcademicYear(@RequestBody AcademicYearDto academicYearDto){
        academicYearDto.setId(null);
        return this.academicYearService.saveAcademicYear(academicYearDto);
    }

    @PutMapping("/{id}")
    public AcademicYearDto updateAcademicYear(@PathVariable Long id, @RequestBody AcademicYearDto academicYearDto){
        academicYearDto.setId(id);
        System.out.println(academicYearDto);
        return this.academicYearService.updateAcademicYear(academicYearDto);
    }

    @GetMapping("/{id}")
    public AcademicYearDto getAcademicYear(@PathVariable Long id){
        return this.academicYearService.getAcademicYearById(id);
    }

    @DeleteMapping("/{id}")
    public boolean deleteAcademicYear(@PathVariable Long id){
        return this.academicYearService.deleteAcademicYear(id);
    }

    @GetMapping("/{id}/students")
    public List<Long> getStudentsByAcademicYear(@PathVariable Long id){
        return this.academicYearService.getStudentsIdByAcademicYear(id);
    }

    @GetMapping("/{id}/groups")
    public List<GroupDto> getGroupsByAcademicYear(@PathVariable Long id){
        return this.academicYearService.getGroupsByAcademicYear(id);
    }

    @GetMapping("/{id}/teachingunits")
    public List<TeachingUnitDto> getTeachingUnitsByAcademicYear(@PathVariable Long id){
        return this.academicYearService.getTeachingUnitsByAcademicYear(id);
    }

    @PostMapping("/{id}/register/{studentId}")
    public boolean registerStudentToAcademicYear(@PathVariable Long id, @PathVariable Long studentId){
        return this.academicYearService.registerStudentToAcademicYear(id, studentId);
    }

    @PostMapping("/{id}/accept/{studentId}")
    public boolean acceptStudentToAcademicYear(@PathVariable Long id, @PathVariable Long studentId){
        return this.academicYearService.acceptStudentToAcademicYear(id, studentId);
    }

    @PostMapping("/{id}/reject/{studentId}")
    public boolean rejectStudentToAcademicYear(@PathVariable Long id, @PathVariable Long studentId){
            return this.academicYearService.rejectStudentToAcademicYear(id, studentId);
    }

    @PostMapping("/scraper")
    public void saveAcademicYearFromScraper() {
        System.out.println("Scraper appel");
        this.academicYearService.saveAcademicYearFromScraper(this.scraperUrl);
    }
}
