package com.example.muscul_ia.controller;

import com.example.muscul_ia.dto.CreateTrainingInfoRequest;
import com.example.muscul_ia.dto.TrainingInfoDto;
import com.example.muscul_ia.dto.UpdateTrainingInfoRequest;
import com.example.muscul_ia.entity.User;
import com.example.muscul_ia.service.TrainingInfoService;
import com.example.muscul_ia.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

/**
 * Training info controller for managing user training information.
 * Contrôleur d'informations d'entraînement pour gérer les informations d'entraînement utilisateur.
 */
@RestController
@RequestMapping("/api/training-info")
@Tag(name = "Training Info", description = "Training information management endpoints")
@CrossOrigin(origins = "*")
public class TrainingInfoController {
    
    private static final Logger logger = LoggerFactory.getLogger(TrainingInfoController.class);
    
    @Autowired
    private TrainingInfoService trainingInfoService;
    
    @Autowired
    private UserService userService;
    
    /**
     * Create training info for current user.
     * Créer les informations d'entraînement pour l'utilisateur actuel.
     */
    @PostMapping
    @Operation(summary = "Create training info")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Training info created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid request data"),
        @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<TrainingInfoDto> createTrainingInfo(
            @Valid @RequestBody CreateTrainingInfoRequest request,
            Authentication authentication) {
        try {
            User user = userService.getCurrentUser(authentication);
            logger.info("Creating training info for user: {}", user.getEmail());
            TrainingInfoDto createdInfo = trainingInfoService.createTrainingInfo(user, request);
            logger.info("Training info created successfully for user: {}", user.getEmail());
            return ResponseEntity.status(HttpStatus.CREATED).body(createdInfo);
        } catch (Exception e) {
            logger.error("Failed to create training info: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    /**
     * Get training info for current user.
     * Récupérer les informations d'entraînement pour l'utilisateur actuel.
     */
    @GetMapping
    @Operation(summary = "Get current user's training info")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Training info retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "Training info not found"),
        @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<TrainingInfoDto> getMyTrainingInfo(Authentication authentication) {
        try {
            User user = userService.getCurrentUser(authentication);
            logger.info("Retrieving training info for user: {}", user.getEmail());
            TrainingInfoDto info = trainingInfoService.getTrainingInfoByUser(user);
            return ResponseEntity.ok(info);
        } catch (Exception e) {
            logger.error("Failed to retrieve training info: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Get training info by user ID.
     * Récupérer les informations d'entraînement par ID utilisateur.
     */
    @GetMapping("/{userId}")
    @Operation(summary = "Get training info by user ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Training info retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "Training info not found"),
        @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<TrainingInfoDto> getTrainingInfoByUserId(@PathVariable Long userId) {
        try {
            logger.info("Retrieving training info for user ID: {}", userId);
            TrainingInfoDto info = trainingInfoService.getTrainingInfoByUserId(userId);
            return ResponseEntity.ok(info);
        } catch (Exception e) {
            logger.error("Failed to retrieve training info for user {}: {}", userId, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Update training info for current user.
     * Mettre à jour les informations d'entraînement pour l'utilisateur actuel.
     */
    @PutMapping
    @Operation(summary = "Update current user's training info")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Training info updated successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid request data"),
        @ApiResponse(responseCode = "404", description = "Training info not found"),
        @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<TrainingInfoDto> updateMyTrainingInfo(
            @Valid @RequestBody UpdateTrainingInfoRequest request,
            Authentication authentication) {
        try {
            User user = userService.getCurrentUser(authentication);
            logger.info("Updating training info for user: {}", user.getEmail());
            TrainingInfoDto updatedInfo = trainingInfoService.updateTrainingInfo(user, request);
            logger.info("Training info updated successfully for user: {}", user.getEmail());
            return ResponseEntity.ok(updatedInfo);
        } catch (RuntimeException e) {
            logger.error("Failed to update training info: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            logger.error("Failed to update training info: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    /**
     * Delete training info for current user.
     * Supprimer les informations d'entraînement pour l'utilisateur actuel.
     */
    @DeleteMapping
    @Operation(summary = "Delete current user's training info")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Training info deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Training info not found"),
        @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<Void> deleteMyTrainingInfo(Authentication authentication) {
        try {
            User user = userService.getCurrentUser(authentication);
            logger.info("Deleting training info for user: {}", user.getEmail());
            trainingInfoService.deleteTrainingInfo(user);
            logger.info("Training info deleted successfully for user: {}", user.getEmail());
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            logger.error("Failed to delete training info: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            logger.error("Failed to delete training info: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Check if training info exists for current user.
     * Vérifier si les informations d'entraînement existent pour l'utilisateur actuel.
     */
    @GetMapping("/exists")
    @Operation(summary = "Check if training info exists for current user")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Check completed successfully"),
        @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<Boolean> checkTrainingInfoExists(Authentication authentication) {
        try {
            User user = userService.getCurrentUser(authentication);
            logger.info("Checking if training info exists for user: {}", user.getEmail());
            boolean exists = trainingInfoService.existsByUser(user);
            return ResponseEntity.ok(exists);
        } catch (Exception e) {
            logger.error("Failed to check training info existence: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
} 