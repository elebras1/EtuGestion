package com.core.controllers;

import com.core.services.StudentService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController

public class StudentCoreController {

    private final StudentService StudentService;

    public StudentCoreController(StudentService StudentService) {
        this.StudentService = StudentService;
    }

    // Récupérer un étudiant par ID
    @GetMapping("/students/{id}")
    public Mono<String> getStudentById(@PathVariable int id) {
        return StudentService.getStudentById(id);

    }

    // Ajouter un nouvel étudiant
    @PostMapping("/students")
    public Mono<String> createStudent(@RequestBody String studentJson) {
        return StudentService.createStudent(studentJson);

    }

    // Mettre à jour un étudiant
    @PutMapping("/students/{id}")
    public Mono<String> updateStudent(@PathVariable int id, @RequestBody String studentJson) {
        return StudentService.updateStudent(id, studentJson);
    }

    // Supprimer un étudiant
    @DeleteMapping("/students/{id}")
    public Mono<String> deleteStudent(@PathVariable int id) {
        return StudentService.deleteStudent(id);
    }

    // Récupérer la liste des étudiants
    @GetMapping("/students")
    public Mono<String> getAllStudents() {
        return StudentService.getAllStudents();
    }
}
