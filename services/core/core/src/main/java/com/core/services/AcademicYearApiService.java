package com.core.services;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class AcademicYearApiService {

    private final WebClient webClient;
    private String baseUrl = "http://172.18.1.91:8080";

    public AcademicYearApiService(WebClient webClient) {
        this.webClient = webClient;
    }

    // **Méthodes pour gérer les formations**
    public Mono<String> getAllFormations() {
        return webClient.get()
                .uri(baseUrl + "/academicyears")
                .retrieve()
                .bodyToMono(String.class)
                .switchIfEmpty(Mono.just("Liste des formations"));
    }

    public Mono<String> createFormation(String formation) {
        return webClient.post()
                .uri(baseUrl + "/academicyears")
                .bodyValue(formation)
                .retrieve()
                .bodyToMono(String.class)
                .switchIfEmpty(Mono.just("Formation créée: " + formation));
    }

    public Mono<String> getFormationById(int id) {
        return webClient.get()
                .uri(baseUrl + "/academicyears/{id}", id)
                .retrieve()
                .bodyToMono(String.class)
                .switchIfEmpty(Mono.just("Formation ID: " + id));
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

    // **Méthodes pour gérer les étudiants d'une formation**
    public Mono<String> getStudentsByAcademicYearId(int id) {
        return webClient.get()
                .uri(baseUrl + "/academicyears/{id}/students", id)
                .retrieve()
                .bodyToMono(String.class)
                .switchIfEmpty(Mono.just("Aucun étudiant trouvé pour la formation ID: " + id));
    }

    public Mono<String> registerStudentInAcademicYear(int id, String numEtudiant) {
        return webClient.post()
                .uri(baseUrl + "/academicyears/{id}/register/{numEtudiant}", id, numEtudiant)
                .retrieve()
                .bodyToMono(String.class)
                .switchIfEmpty(Mono.just("Étudiant inscrit dans la formation ID " + id));
    }

    public Mono<String> acceptStudentInAcademicYear(int id, String numEtudiant) {
        return webClient.post()
                .uri(baseUrl + "/academicyears/{id}/accept/{numEtudiant}", id, numEtudiant)
                .retrieve()
                .bodyToMono(String.class)
                .switchIfEmpty(Mono.just("Étudiant accepté dans la formation ID " + id));
    }

    public Mono<String> rejectStudentInAcademicYear(int id, String numEtudiant) {
        return webClient.post()
                .uri(baseUrl + "/academicyears/{id}/reject/{numEtudiant}", id, numEtudiant)
                .retrieve()
                .bodyToMono(String.class)
                .switchIfEmpty(Mono.just("Étudiant rejeté dans la formation ID " + id));
    }

    // **Méthodes pour gérer les groupes d'une formation**
    public Mono<String> getGroupsByAcademicYearId(int id) {
        return webClient.get()
                .uri(baseUrl + "/academicyears/{id}/groups", id)
                .retrieve()
                .bodyToMono(String.class)
                .switchIfEmpty(Mono.just("Aucun groupe trouvé pour la formation ID: " + id));
    }

    // **Méthodes pour gérer les unités d'enseignement d'une formation**
    public Mono<String> getTeachingUnitsByAcademicYearId(int id) {
        return webClient.get()
                .uri(baseUrl + "/academicyears/{id}/teachingunits", id)
                .retrieve()
                .bodyToMono(String.class)
                .switchIfEmpty(Mono.just("Aucune unité d'enseignement trouvée pour la formation ID: " + id));
    }

    // **Méthodes pour gérer les groupes**
    public Mono<String> getAllGroups() {
        return webClient.get()
                .uri(baseUrl + "/groups")
                .retrieve()
                .bodyToMono(String.class)
                .switchIfEmpty(Mono.just("Liste des groupes"));
    }

    public Mono<String> createGroup(String group) {
        return webClient.post()
                .uri(baseUrl + "/groups")
                .bodyValue(group)
                .retrieve()
                .bodyToMono(String.class)
                .switchIfEmpty(Mono.just("Groupe créé: " + group));
    }

    public Mono<String> getGroupById(int id) {
        return webClient.get()
                .uri(baseUrl + "/groups/{id}", id)
                .retrieve()
                .bodyToMono(String.class)
                .switchIfEmpty(Mono.just("Groupe ID: " + id));
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

    // **Méthodes pour gérer les unités d'enseignement**
    public Mono<String> getAllTeachingUnits() {
        return webClient.get()
                .uri(baseUrl + "/teachingunits")
                .retrieve()
                .bodyToMono(String.class)
                .switchIfEmpty(Mono.just("Liste des unités d'enseignement"));
    }

    public Mono<String> createTeachingUnit(String teachingUnit) {
        return webClient.post()
                .uri(baseUrl + "/teachingunits")
                .bodyValue(teachingUnit)
                .retrieve()
                .bodyToMono(String.class)
                .switchIfEmpty(Mono.just("Unité d'enseignement créée: " + teachingUnit));
    }

    public Mono<String> getTeachingUnitById(int id) {
        return webClient.get()
                .uri(baseUrl + "/teachingunits/{id}", id)
                .retrieve()
                .bodyToMono(String.class)
                .switchIfEmpty(Mono.just("Unité d'enseignement ID: " + id));
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

    // **Méthodes pour gérer l'inscription et désinscription dans les unités d'enseignement**
    public Mono<String> registerStudentInTeachingUnit(int id, String numEtudiant) {
        return webClient.post()
                .uri(baseUrl + "/teachingunits/{id}/register/{numEtudiant}", id, numEtudiant)
                .retrieve()
                .bodyToMono(String.class)
                .switchIfEmpty(Mono.just("Étudiant inscrit dans l'unité d'enseignement ID " + id));
    }

    public Mono<String> unregisterStudentFromTeachingUnit(int id, String numEtudiant) {
        return webClient.post()
                .uri(baseUrl + "/teachingunits/{id}/unregister/{numEtudiant}", id, numEtudiant)
                .retrieve()
                .bodyToMono(String.class)
                .switchIfEmpty(Mono.just("Étudiant désinscrit de l'unité d'enseignement ID " + id));
    }
}
