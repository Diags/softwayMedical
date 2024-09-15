package com.softwaymedical.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.softwaymedical.exception.InvalidIndexException;
import com.softwaymedical.service.PathologyRoutingService;

import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;

/**
 * Contrôleur REST pour gérer les requêtes de diagnostic et de routage vers les
 * départements médicaux.
 */
@RestController
@RequiredArgsConstructor
@Validated
public class HealthIndexController {

    private final PathologyRoutingService routingService;

    /**
     * Obtient les départements médicaux appropriés en fonction de l'index de santé fourni.
     * @param index l'index de santé.
     * @return les départements médicaux associés.
     * @throws InvalidIndexException si l'index est invalide.
     */
    @GetMapping("/departement/{index}")
    public  ResponseEntity<String> getMedicalDepartments(@PathVariable @Min(1) Integer index) {
        return  ResponseEntity
                .status(HttpStatus.OK)
                .body( routingService.getMedicalDepartments(index));
    }
}