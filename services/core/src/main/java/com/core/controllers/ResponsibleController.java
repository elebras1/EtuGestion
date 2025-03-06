package com.core.controllers;

import com.core.services.ResponsibleService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/managers")

public class ResponsibleController {

    private final ResponsibleService ResponsibleService;

    public ResponsibleController(ResponsibleService ResponsibleService) {
        this.ResponsibleService = ResponsibleService;
    }

    // 1. Lister tous les responsables
    @GetMapping
    public Mono<String> getAllManagers() {
        // Appel au service pour récupérer tous les responsables
        return ResponsibleService.getAllManagers();
    }

    // 2. Ajouter un nouveau responsable
    @PostMapping
    public Mono<String> addManager(@RequestBody String manager) {
        // Appel au service pour ajouter un responsable
        return ResponsibleService.addManager(manager);
    }

    // 3. Trouver un responsable par ID
    @GetMapping("/{id}")
    public Mono<String> getManagerById(@PathVariable long id) {
        // Appel au service pour récupérer un responsable par ID
        return ResponsibleService.getManagerById(id);
    }

    // 4. Mettre à jour un responsable
    @PutMapping("/{id}")
    public Mono<String> updateManager(@PathVariable long id, @RequestBody String manager) {
        // Appel au service pour mettre à jour le responsable avec l'ID donné
        return ResponsibleService.updateManager(id, manager);
    }

    // 5. Supprimer un responsable
    @DeleteMapping("/{id}")
    public Mono<String> deleteManager(@PathVariable long id) {
        // Appel au service pour supprimer un responsable par ID
        return ResponsibleService.deleteManager(id);
    }
}
