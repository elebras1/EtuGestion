package com.core.services;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class AuthService {

    private final WebClient webClient;
    private final String baseUrl = "http://localhost:8080"; // URL de l'API d'authentification

    public AuthService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(baseUrl).build();
    }

    // Réinitialiser le mot de passe
    public Mono<String> resetPassword(String username, String newPassword) {
        return webClient.put()
                .uri(baseUrl+"/reset-password/{username}", username)
                .bodyValue(newPassword)
                .retrieve()
                .bodyToMono(String.class);
    }

    // Enregistrer un nouvel utilisateur
    public Mono<String> register(String registerRequestJson) {
        return webClient.post()
                .uri(baseUrl+"/register")
                .bodyValue(registerRequestJson)
                .retrieve()
                .bodyToMono(String.class);
    }

    // Connexion de l'utilisateur
    public Mono<String> login(String authRequestJson) {
        return webClient.post()
                .uri(baseUrl+"/login")
                .bodyValue(authRequestJson)
                .retrieve()
                .bodyToMono(String.class);
    }

    // Valider le token
    public Mono<String> validateToken(String token) {
        return webClient.get()
                .uri(baseUrl+"/me")
                .header("Authorization", token)
                .retrieve()
                .bodyToMono(String.class);
    }

    // Se déconnecter
    public Mono<String> logout(String token) {
        return webClient.delete()
                .uri(baseUrl+"/logout")
                .header("Authorization", token)
                .retrieve()
                .bodyToMono(String.class);
    }

    // Désinscription (suppression de compte)
    public Mono<String> unregister(String token) {
        return webClient.delete()
                .uri(baseUrl+"/unregister")
                .header("Authorization", token)
                .retrieve()
                .bodyToMono(String.class);
    }
}
