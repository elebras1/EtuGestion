package com.core.services;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ResponsibleService {

    private final WebClient webClient;
    private String baseUrl = "http://jesaispas:8080";  // URL de l'API des responsables

    // Injection du WebClient via le constructeur
    public ResponsibleService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(baseUrl).build();
    }

    // 1. Récupérer tous les responsables
    public Mono<String> getAllManagers() {
        return webClient.get()
                .uri(baseUrl+"/managers")  // L'endpoint pour récupérer tous les responsables
                .retrieve()
                .bodyToMono(String.class)  // On convertit la réponse en chaîne de caractères (JSON ou autre format)
                .switchIfEmpty(Mono.just("Liste des responsables est vide."));  // Valeur par défaut si la réponse est vide
    }

    // 2. Ajouter un nouveau responsable
    public Mono<String> addManager(String manager) {
        return webClient.post()
                .uri(baseUrl+"/managers")  // L'endpoint pour ajouter un responsable
                .bodyValue(manager)  // Le corps de la requête (le responsable à ajouter)
                .retrieve()
                .bodyToMono(String.class)
                .switchIfEmpty(Mono.just("Erreur lors de l'ajout du responsable."));
    }

    // 3. Récupérer un responsable par ID
    public Mono<String> getManagerById(long id) {
        return webClient.get()
                .uri(baseUrl+"/managers/{id}", id)  // L'endpoint avec l'ID du responsable
                .retrieve()
                .bodyToMono(String.class)
                .switchIfEmpty(Mono.just("Responsable non trouvé."));
    }

    // 4. Mettre à jour un responsable
    public Mono<String> updateManager(long id, String manager) {
        return webClient.put()
                .uri(baseUrl+"/managers/{id}", id)  // L'endpoint avec l'ID du responsable
                .bodyValue(manager)  // Le corps de la requête (les nouvelles informations du responsable)
                .retrieve()
                .bodyToMono(String.class)
                .switchIfEmpty(Mono.just("Erreur lors de la mise à jour du responsable."));
    }

    // 5. Supprimer un responsable
    public Mono<String> deleteManager(long id) {
        return webClient.delete()
                .uri(baseUrl+"/managers/{id}", id)  // L'endpoint avec l'ID du responsable à supprimer
                .retrieve()
                .bodyToMono(String.class)
                .switchIfEmpty(Mono.just("Erreur lors de la suppression du responsable."));
    }
}
