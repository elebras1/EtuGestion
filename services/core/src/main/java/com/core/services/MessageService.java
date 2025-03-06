package com.core.services;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class MessageService {

    private final WebClient webClient;
    private final String baseUrl = "http://localhost:8085";  // URL de ton serveur d'API

    public MessageService(WebClient webClient) {
        this.webClient = webClient;
    }

    // Récupérer tous les messages d'un étudiant
    public Mono<String> getMessagesForStudent(int student) {
        return webClient.get()
                .uri(baseUrl + "/messages/student/{student}", student)
                .retrieve()
                .bodyToMono(String.class)  // La réponse est une chaîne JSON
                .switchIfEmpty(Mono.just("Aucun message trouvé pour cet étudiant"));  // Retourne un message par défaut si aucun résultat
    }

    // Créer un nouveau message
    public Mono<String> createMessage(Object message) {
        return webClient.post()
                .uri(baseUrl + "/messages")
                .bodyValue(message)  // Envoie l'objet JSON reçu dans le corps de la requête
                .retrieve()
                .bodyToMono(String.class)  // La réponse est une chaîne JSON
                .switchIfEmpty(Mono.just("Erreur de création du message"));  // Message par défaut en cas d'erreur
    }

    // Récupérer un message par ID
    public Mono<String> getMessageById(String id) {
        return webClient.get()
                .uri(baseUrl + "/messages/{id}", id)
                .retrieve()
                .bodyToMono(String.class)  // La réponse est une chaîne JSON
                .switchIfEmpty(Mono.just("Message non trouvé"));  // Message par défaut si le message n'est pas trouvé
    }

    // Mettre à jour un message par ID
    public Mono<String> updateMessage(String id, Object message) {
        return webClient.put()
                .uri(baseUrl + "/messages/{id}", id)
                .bodyValue(message)  // Envoie les données de mise à jour sous forme de JSON
                .retrieve()
                .bodyToMono(String.class)  // La réponse est une chaîne JSON
                .switchIfEmpty(Mono.just("Erreur de mise à jour du message"));
    }

    // Supprimer un message par ID
    public Mono<String> deleteMessage(String id) {
        return webClient.delete()
                .uri(baseUrl + "/messages/{id}", id)
                .retrieve()
                .bodyToMono(String.class)  // La réponse est une chaîne JSON
                .switchIfEmpty(Mono.just("Message non trouvé"));
    }
}
