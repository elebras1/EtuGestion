package com.core.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Service
public class AcademicYearApiService {

    //private final WebClient webClient;

    // Injecter l'URL de l'API externe
    //private static final String API_URL = "http://17.18.2.156:8080";

    public AcademicYearApiService(WebClient.Builder webClientBuilder) {
        //this.webClient = webClientBuilder.baseUrl(API_URL).build();
    }
/*
    public Mono<String> fetchAcademicYearList() {
        return webClient.get()
                .uri("/academicyears/")
                .retrieve()
                .bodyToMono(String.class)  // Transformation de la réponse en String
                .onErrorResume(WebClientResponseException.class, e -> Mono.error(new RuntimeException("Erreur de communication avec l'API externe")));
    }
*/

}
