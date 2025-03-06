package com.core.controllers;

import com.core.services.AcademicYearApiService;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class CoreController {

    //private final AcademicYearApiService AcademicYearApiService;

    public CoreController(AcademicYearApiService AcademicYearApiService) {
        //this.AcademicYearApiService = AcademicYearApiService;
    }
/*
    // Exposer un endpoint qui appelle l'API externe
    @GetMapping("/fetch-academic-years")
    public Mono<String> getAcademicYears() {
        System.out.println("Salut");
        return AcademicYearApiService.fetchAcademicYearList();
    }
*/
    // D'autres endpoints peuvent être ajoutés ici pour des fonctionnalités spécifiques
}
