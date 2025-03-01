package com.core.services;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ApiService {

    private final WebClient webClient;

    public ApiService(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<String> getPostById(int postId) {
        return webClient.get()
                .uri("/posts/{id}", postId)
                .retrieve()
                .bodyToMono(String.class);
    }
}
