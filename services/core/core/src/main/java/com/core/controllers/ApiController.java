package com.core.controllers;

import com.core.services.ApiService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class ApiController {

    private final ApiService apiService;

    public ApiController(ApiService apiService) {
        this.apiService = apiService;
    }
/*
    @GetMapping("/academicyears/")
    public Mono<String> get() {
        return apiService.get();
    }

    @GetMapping("/posts/{id}")
    public Mono<String> getPost(@PathVariable int id) {
        return apiService.getPostById(id);


    }

 */
}
