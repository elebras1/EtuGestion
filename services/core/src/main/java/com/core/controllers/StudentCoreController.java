package com.core.controllers;

import com.core.services.*;
import com.fasterxml.jackson.databind.*;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.*;

@RestController

public class StudentCoreController {

    private final StudentService StudentService;
    private final MessageService messageService;
    private final AuthCoreController authService;

    public StudentCoreController(StudentService StudentService, MessageService messageService, AuthCoreController authService) {
        this.StudentService = StudentService;
        this.messageService = messageService;
        this.authService = authService;
    }

    // Récupérer un étudiant par ID
    @GetMapping("/students/{id}")
    public Mono<String> getStudentById(@PathVariable int id) {
        return StudentService.getStudentById(id);

    }

    // Ajouter un nouvel étudiant
    @PostMapping("/students")
    public Mono<String> createStudent(@RequestBody String studentJson) {
        return StudentService.createStudent(studentJson).flatMap(response -> {
            try {
                // Extraire l'ID de l'étudiant à partir de la réponse JSON
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(response);
                Long studentId = jsonNode.get("id").asLong();

                String randomPassword = UUID.randomUUID().toString();
                String username = jsonNode.get("firstname").asText() + jsonNode.get("surname").asText();

                String welcomeMessage = "{"
                        + "\"text\": \"Votre inscription a été acceptée. Bienvenue ! \\n"
                        + "Votre identifiant est : " + username + " \\n"
                        + "Mot de passe temporaire : " + randomPassword + " \\n"
                        + "Veuillez changer votre mot de passe dès que possible.\","
                        + "\"student\": " + studentId + ", "
                        + "\"readed\": false"
                        + "}";
                // Envoi du message
                messageService.createMessage(welcomeMessage);
                // Créer la demande d'inscription pour le compte utilisateur
                String authRequestJson = "{"
                        + "\"username\": \"" + username + "\","
                        + "\"password\": \"" + randomPassword + "\""
                        + "\"student\": " + studentId
                        + "}";

                // creation de compte
                return authService.register(authRequestJson);
            } catch (Exception e) {

                return Mono.error(new RuntimeException("Erreur lors du traitement de l'inscription ou du message.", e));
            }
        });

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
