package com.core.controllers;

import com.core.services.AcademicYearApiService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class CoreController {

    private final com.core.services.AcademicYearApiService AcademicYearApiService;

    public CoreController(AcademicYearApiService AcademicYearApiService) {
        this.AcademicYearApiService = AcademicYearApiService;
    }

    // Exposer un endpoint qui appelle l'API externe
    @GetMapping("/fetch-academic-years")
    public Mono<String> getAcademicYears() {
        return AcademicYearApiService.fetchAcademicYearList();
    }

    // D'autres endpoints peuvent être ajoutés ici pour des fonctionnalités spécifiques
}
