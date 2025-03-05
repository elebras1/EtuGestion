package com.core.controllers;

import com.core.services.ApiService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController

public class ApiController {

    private final ApiService apiService;

    public ApiController(ApiService apiService) {
        this.apiService = apiService;
    }
/*
    @GetMapping("...")
    public Mono<String> ...() {
        return apiService....();
    }

    @GetMapping("...")
    public Mono<String> ...(@PathVariable int id) {
        return apiService....(id);


    }
*/
}
