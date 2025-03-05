package com.core.controllers;

import com.core.services.AuthService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController

public class AuthCoreController {

    private final AuthService authService;

    public AuthCoreController(AuthService authService) {
        this.authService = authService;
    }

    // Réinitialiser le mot de passe
    @PutMapping("/reset-password/{username}")
    public Mono<String> resetPassword(@PathVariable String username, @RequestBody String newPassword) {
        return authService.resetPassword(username, newPassword);
    }

    // Enregistrer un nouvel utilisateur
    @PostMapping("/register")
    public Mono<String> register(@RequestBody String registerRequestJson) {
        return authService.register(registerRequestJson);
    }

    // Connexion d'un utilisateur
    @PostMapping("/login")
    public Mono<String> login(@RequestBody String authRequestJson) {
        return authService.login(authRequestJson);
    }

    // Vérifier le token d'authentification
    @GetMapping("/me")
    public Mono<String> validateToken(@RequestHeader("Authorization") String token) {
        return authService.validateToken(token);
    }

    // Déconnexion
    @DeleteMapping("/logout")
    public Mono<String> logout(@RequestHeader("Authorization") String token) {
        return authService.logout(token);
    }

    // Désinscription d'un utilisateur
    @DeleteMapping("/unregister")
    public Mono<String> unregister(@RequestHeader("Authorization") String token) {
        return authService.unregister(token);
    }
}
