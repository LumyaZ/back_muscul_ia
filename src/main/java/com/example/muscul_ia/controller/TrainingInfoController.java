package com.example.muscul_ia.controller;

import com.example.muscul_ia.dto.CreateTrainingInfoRequest;
import com.example.muscul_ia.dto.TrainingInfoDto;
import com.example.muscul_ia.dto.UpdateTrainingInfoRequest;
import com.example.muscul_ia.entity.User;
import com.example.muscul_ia.service.TrainingInfoService;
import com.example.muscul_ia.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

/**
 * Training info controller for managing user training information.
 * Contrôleur d'informations d'entraînement pour gérer les informations d'entraînement utilisateur.
 */
@RestController
@RequestMapping("/api/training-info")
@CrossOrigin(origins = "*")
public class TrainingInfoController {
    
    @Autowired
    private TrainingInfoService trainingInfoService;
    
    @Autowired
    private UserService userService;
    
    /**
     * Create training info for current user.
     * Créer les informations d'entraînement pour l'utilisateur actuel.
     */
    @PostMapping
    public ResponseEntity<TrainingInfoDto> createTrainingInfo(
            @RequestBody CreateTrainingInfoRequest request,
            Authentication authentication) {
        User user = userService.getCurrentUser(authentication);
        TrainingInfoDto createdInfo = trainingInfoService.createTrainingInfo(user, request);
        return ResponseEntity.ok(createdInfo);
    }
    
    /**
     * Get training info for current user.
     * Récupérer les informations d'entraînement pour l'utilisateur actuel.
     */
    @GetMapping
    public ResponseEntity<TrainingInfoDto> getMyTrainingInfo(Authentication authentication) {
        User user = userService.getCurrentUser(authentication);
        TrainingInfoDto info = trainingInfoService.getTrainingInfoByUser(user);
        return ResponseEntity.ok(info);
    }
    
    /**
     * Get training info by user ID.
     * Récupérer les informations d'entraînement par ID utilisateur.
     */
    @GetMapping("/{userId}")
    public ResponseEntity<TrainingInfoDto> getTrainingInfoByUserId(@PathVariable Long userId) {
        TrainingInfoDto info = trainingInfoService.getTrainingInfoByUserId(userId);
        return ResponseEntity.ok(info);
    }
    
    /**
     * Update training info for current user.
     * Mettre à jour les informations d'entraînement pour l'utilisateur actuel.
     */
    @PutMapping
    public ResponseEntity<TrainingInfoDto> updateMyTrainingInfo(
            @RequestBody UpdateTrainingInfoRequest request,
            Authentication authentication) {
        User user = userService.getCurrentUser(authentication);
        TrainingInfoDto updatedInfo = trainingInfoService.updateTrainingInfo(user, request);
        return ResponseEntity.ok(updatedInfo);
    }
    
    /**
     * Delete training info for current user.
     * Supprimer les informations d'entraînement pour l'utilisateur actuel.
     */
    @DeleteMapping
    public ResponseEntity<Void> deleteMyTrainingInfo(Authentication authentication) {
        User user = userService.getCurrentUser(authentication);
        trainingInfoService.deleteTrainingInfo(user);
        return ResponseEntity.noContent().build();
    }
    
    /**
     * Check if training info exists for current user.
     * Vérifier si les informations d'entraînement existent pour l'utilisateur actuel.
     */
    @GetMapping("/exists")
    public ResponseEntity<Boolean> checkTrainingInfoExists(Authentication authentication) {
        User user = userService.getCurrentUser(authentication);
        boolean exists = trainingInfoService.existsByUser(user);
        return ResponseEntity.ok(exists);
    }
} 