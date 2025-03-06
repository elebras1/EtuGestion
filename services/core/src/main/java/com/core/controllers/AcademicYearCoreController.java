package com.core.controllers;

import com.core.services.AcademicYearApiService;
import org.springframework.web.bind.annotation.*;

import reactor.core.publisher.Mono;

@CrossOrigin(origins = "http://localhost:5173")
@RestController

public class AcademicYearCoreController {

    private final AcademicYearApiService academicYearApiService;

    public AcademicYearCoreController(AcademicYearApiService academicYearApiService) {
        this.academicYearApiService = academicYearApiService;
    }

    // **Routes pour gérer les formations**

    @GetMapping("/academicyears")
    public Mono<String> getAllFormations() {
        return academicYearApiService.getAllFormations();
    }

    @GetMapping("/academicyears/{id}")
    public Mono<String> getFormationById(@PathVariable int id) {
        return academicYearApiService.getFormationById(id);
    }

    @PostMapping("/academicyears")
    public Mono<String> createFormation(@RequestBody String formation) {
        return academicYearApiService.createFormation(formation);
    }

    @PutMapping("/academicyears/{id}")
    public Mono<String> updateFormation(@PathVariable int id, @RequestBody String formation) {
        return academicYearApiService.updateFormation(id, formation);
    }

    @DeleteMapping("/academicyears/{id}")
    public Mono<String> deleteFormation(@PathVariable int id) {
        return academicYearApiService.deleteFormation(id);
    }

    // **Routes pour gérer les étudiants dans les formations**

    @GetMapping("/academicyears/{id}/students")
    public Mono<String> getStudentsByAcademicYearId(@PathVariable int id) {
        return academicYearApiService.getStudentsByAcademicYearId(id);
    }

    @PostMapping("/academicyears/{id}/register/{numEtudiant}")
    public Mono<String> registerStudentInAcademicYear(@PathVariable int id, @PathVariable String numEtudiant) {
        return academicYearApiService.registerStudentInAcademicYear(id, numEtudiant);
    }

    @PostMapping("/academicyears/{id}/accept/{numEtudiant}")
    public Mono<String> acceptStudentInAcademicYear(@PathVariable int id, @PathVariable String numEtudiant) {
        return academicYearApiService.acceptStudentInAcademicYear(id, numEtudiant);
    }

    @PostMapping("/academicyears/{id}/reject/{numEtudiant}")
    public Mono<String> rejectStudentInAcademicYear(@PathVariable int id, @PathVariable String numEtudiant) {
        return academicYearApiService.rejectStudentInAcademicYear(id, numEtudiant);
    }

    // **Routes pour gérer les groupes d'une formation**

    @GetMapping("/academicyears/{id}/groups")
    public Mono<String> getGroupsByAcademicYearId(@PathVariable int id) {
        return academicYearApiService.getGroupsByAcademicYearId(id);
    }

    // **Routes pour gérer les unités d'enseignement d'une formation**

    @GetMapping("/academicyears/{id}/teachingunits")
    public Mono<String> getTeachingUnitsByAcademicYearId(@PathVariable int id) {
        return academicYearApiService.getTeachingUnitsByAcademicYearId(id);
    }

    // **Routes pour gérer les groupes**

    @GetMapping("/groups")
    public Mono<String> getAllGroups() {
        return academicYearApiService.getAllGroups();
    }

    @PostMapping("/groups")
    public Mono<String> createGroup(@RequestBody String group) {
        return academicYearApiService.createGroup(group);
    }

    @GetMapping("/groups/{id}")
    public Mono<String> getGroupById(@PathVariable int id) {
        return academicYearApiService.getGroupById(id);
    }

    @PutMapping("/groups/{id}")
    public Mono<String> updateGroup(@PathVariable int id, @RequestBody String group) {
        return academicYearApiService.updateGroup(id, group);
    }

    @DeleteMapping("/groups/{id}")
    public Mono<String> deleteGroup(@PathVariable int id) {
        return academicYearApiService.deleteGroup(id);
    }

    // **Routes pour gérer les unités d'enseignement**

    @GetMapping("/teachingunits")
    public Mono<String> getAllTeachingUnits() {
        return academicYearApiService.getAllTeachingUnits();
    }

    @PostMapping("/teachingunits")
    public Mono<String> createTeachingUnit(@RequestBody String teachingUnit) {
        return academicYearApiService.createTeachingUnit(teachingUnit);
    }

    @GetMapping("/teachingunits/{id}")
    public Mono<String> getTeachingUnitById(@PathVariable int id) {
        return academicYearApiService.getTeachingUnitById(id);
    }

    @PutMapping("/teachingunits/{id}")
    public Mono<String> updateTeachingUnit(@PathVariable int id, @RequestBody String teachingUnit) {
        return academicYearApiService.updateTeachingUnit(id, teachingUnit);
    }

    @DeleteMapping("/teachingunits/{id}")
    public Mono<String> deleteTeachingUnit(@PathVariable int id) {
        return academicYearApiService.deleteTeachingUnit(id);
    }

    // **Routes pour gérer l'inscription et la désinscription dans les unités d'enseignement**

    @PostMapping("/teachingunits/{id}/register/{numEtudiant}")
    public Mono<String> registerStudentInTeachingUnit(@PathVariable int id, @PathVariable String numEtudiant) {
        return academicYearApiService.registerStudentInTeachingUnit(id, numEtudiant);
    }

    @PostMapping("/teachingunits/{id}/unregister/{numEtudiant}")
    public Mono<String> unregisterStudentFromTeachingUnit(@PathVariable int id, @PathVariable String numEtudiant) {
        return academicYearApiService.unregisterStudentFromTeachingUnit(id, numEtudiant);
    }
}
