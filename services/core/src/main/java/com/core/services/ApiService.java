package com.core.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ApiService {

    private final WebClient webClient;


    private String baseUrl ="loclahoste:8080";

    public ApiService(WebClient webClient) {
        this.webClient = webClient;
    }

    // Méthodes pour gérer les formations
    public Mono<String> getAllFormations() {
        return webClient.get()
                .uri(baseUrl + "/academicyears")
                .retrieve()
                .bodyToMono(String.class)
                .switchIfEmpty(Mono.just("Liste des formations"));
    }

    public Mono<String> getFormationById(int id) {
        return webClient.get()
                .uri(baseUrl + "/academicyears/{id}", id)
                .retrieve()
                .bodyToMono(String.class)
                .switchIfEmpty(Mono.just("Formation ID: " + id));
    }

    public Mono<String> createFormation(String formation) {
        return webClient.post()
                .uri(baseUrl + "/academicyears")
                .bodyValue(formation)
                .retrieve()
                .bodyToMono(String.class)
                .switchIfEmpty(Mono.just("Formation créée: " + formation));
    }

    public Mono<String> updateFormation(int id, String formation) {
        return webClient.put()
                .uri(baseUrl + "/academicyears/{id}", id)
                .bodyValue(formation)
                .retrieve()
                .bodyToMono(String.class)
                .switchIfEmpty(Mono.just("Formation ID " + id + " mise à jour"));
    }

    public Mono<String> deleteFormation(int id) {
        return webClient.delete()
                .uri(baseUrl + "/academicyears/{id}", id)
                .retrieve()
                .bodyToMono(String.class)
                .switchIfEmpty(Mono.just("Formation ID " + id + " supprimée"));
    }

    // Méthodes pour gérer les groupes
    public Mono<String> getAllGroups() {
        return webClient.get()
                .uri(baseUrl + "/groups")
                .retrieve()
                .bodyToMono(String.class)
                .switchIfEmpty(Mono.just("Liste des groupes"));
    }

    public Mono<String> getGroupById(int id) {
        return webClient.get()
                .uri(baseUrl + "/groups/{id}", id)
                .retrieve()
                .bodyToMono(String.class)
                .switchIfEmpty(Mono.just("Groupe ID: " + id));
    }

    public Mono<String> createGroup(String group) {
        return webClient.post()
                .uri(baseUrl + "/groups")
                .bodyValue(group)
                .retrieve()
                .bodyToMono(String.class)
                .switchIfEmpty(Mono.just("Groupe créé: " + group));
    }

    public Mono<String> updateGroup(int id, String group) {
        return webClient.put()
                .uri(baseUrl + "/groups/{id}", id)
                .bodyValue(group)
                .retrieve()
                .bodyToMono(String.class)
                .switchIfEmpty(Mono.just("Groupe ID " + id + " mis à jour"));
    }

    public Mono<String> deleteGroup(int id) {
        return webClient.delete()
                .uri(baseUrl + "/groups/{id}", id)
                .retrieve()
                .bodyToMono(String.class)
                .switchIfEmpty(Mono.just("Groupe ID " + id + " supprimé"));
    }

    // Méthodes pour gérer les unités d'enseignement
    public Mono<String> getAllTeachingUnits() {
        return webClient.get()
                .uri(baseUrl + "/teachingunits")
                .retrieve()
                .bodyToMono(String.class)
                .switchIfEmpty(Mono.just("Liste des unités d'enseignement"));
    }

    public Mono<String> getTeachingUnitById(int id) {
        return webClient.get()
                .uri(baseUrl + "/teachingunits/{id}", id)
                .retrieve()
                .bodyToMono(String.class)
                .switchIfEmpty(Mono.just("Unité d'enseignement ID: " + id));
    }

    public Mono<String> createTeachingUnit(String teachingUnit) {
        return webClient.post()
                .uri(baseUrl + "/teachingunits")
                .bodyValue(teachingUnit)
                .retrieve()
                .bodyToMono(String.class)
                .switchIfEmpty(Mono.just("Unité d'enseignement créée: " + teachingUnit));
    }

    public Mono<String> updateTeachingUnit(int id, String teachingUnit) {
        return webClient.put()
                .uri(baseUrl + "/teachingunits/{id}", id)
                .bodyValue(teachingUnit)
                .retrieve()
                .bodyToMono(String.class)
                .switchIfEmpty(Mono.just("Unité d'enseignement ID " + id + " mise à jour"));
    }

    public Mono<String> deleteTeachingUnit(int id) {
        return webClient.delete()
                .uri(baseUrl + "/teachingunits/{id}", id)
                .retrieve()
                .bodyToMono(String.class)
                .switchIfEmpty(Mono.just("Unité d'enseignement ID " + id + " supprimée"));
    }

    // Méthodes pour gérer l'inscription et la désinscription des étudiants
    public Mono<String> enrollStudentInTeachingUnit(int id, String student) {
        return webClient.post()
                .uri(baseUrl + "/teachingunits/{id}/enroll", id)
                .bodyValue(student)
                .retrieve()
                .bodyToMono(String.class)
                .switchIfEmpty(Mono.just("Étudiant inscrit dans l'unité d'enseignement ID " + id));
    }

    public Mono<String> unenrollStudentFromTeachingUnit(int id, String student) {
        return webClient.post()
                .uri(baseUrl + "/teachingunits/{id}/unenroll", id)
                .bodyValue(student)
                .retrieve()
                .bodyToMono(String.class)
                .switchIfEmpty(Mono.just("Étudiant désinscrit de l'unité d'enseignement ID " + id));
    }
}
