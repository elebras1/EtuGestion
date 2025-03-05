package com.core.services;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class StudentService {

    private final WebClient webClient;
    private final String baseUrl = "http://localhost:8080"; // Remarque : Assure-toi que l'URL est correcte

    public StudentService(WebClient webClient) {
        this.webClient = webClient;
    }

    // Récupérer un étudiant par ID
    public Mono<String> getStudentById(int id) {
        return webClient.get()
                .uri(baseUrl + "/students/{id}", id)  // Utilisation de {id} comme paramètre d'URI
                .retrieve()
                .bodyToMono(String.class)  // Ici, tu retournes une chaîne de caractères, mais tu pourrais aussi utiliser un objet Student
                .switchIfEmpty(Mono.just("Aucun étudiant trouvé"));
    }

    // Ajouter un nouvel étudiant
    public Mono<String> createStudent(String studentJson) {
        return webClient.post()
                .uri(baseUrl + "/students")
                .bodyValue(studentJson)  // Corps de la requête, ici c'est un String mais tu peux envoyer un objet Student
                .retrieve()
                .bodyToMono(String.class)
                .switchIfEmpty(Mono.just("Erreur lors de la création de l'étudiant"));
    }

    // Mettre à jour un étudiant
    public Mono<String> updateStudent(int id, String studentJson) {
        return webClient.put()
                .uri(baseUrl + "/students/{id}", id)
                .bodyValue(studentJson)
                .retrieve()
                .bodyToMono(String.class)
                .switchIfEmpty(Mono.just("Erreur lors de la mise à jour de l'étudiant"));
    }

    // Supprimer un étudiant
    public Mono<String> deleteStudent(int id) {
        return webClient.delete()
                .uri(baseUrl + "/students/{id}", id)
                .retrieve()
                .bodyToMono(String.class)
                .switchIfEmpty(Mono.just("Erreur lors de la suppression de l'étudiant"));
    }

    // Récupérer la liste des étudiants
    public Mono<String> getAllStudents() {
        return webClient.get()
                .uri(baseUrl + "/students")
                .retrieve()
                .bodyToMono(String.class)
                .switchIfEmpty(Mono.just("Aucun étudiant trouvé"));
    }
}
