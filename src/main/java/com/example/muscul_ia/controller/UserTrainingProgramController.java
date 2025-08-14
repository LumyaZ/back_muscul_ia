package com.example.muscul_ia.controller;

import com.example.muscul_ia.dto.UserTrainingProgramDto;
import com.example.muscul_ia.service.UserTrainingProgramService;
import com.example.muscul_ia.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * User training program controller for managing user-training program relationships.
 * Contrôleur de programmes d'entraînement utilisateur pour gérer les relations utilisateur-programme.
 */
@RestController
@RequestMapping("/api/user-training-programs")
@Tag(name = "User Training Programs", description = "User training program relationship management endpoints")
@CrossOrigin(origins = "*")
public class UserTrainingProgramController {
    
    private static final Logger logger = LoggerFactory.getLogger(UserTrainingProgramController.class);
    
    @Autowired
    private UserTrainingProgramService userTrainingProgramService;
    
    @Autowired
    private UserService userService;
    
    /**
     * Subscribe a user to a training program.
     * Abonner un utilisateur à un programme d'entraînement.
     */
    @PostMapping("/subscribe")
    @Operation(summary = "Subscribe user to training program")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "User subscribed successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid request data"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "403", description = "Forbidden - User can only subscribe themselves")
    })
    public ResponseEntity<UserTrainingProgramDto> subscribeUserToProgram(
            @RequestParam Long userId,
            @RequestParam Long trainingProgramId,
            Authentication authentication) {
        try {
            Long authenticatedUserId = userService.getCurrentUser(authentication).getId();
            if (!authenticatedUserId.equals(userId)) {
                logger.warn("User {} attempted to subscribe user {} to program {}", 
                    authenticatedUserId, userId, trainingProgramId);
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            
            logger.info("Subscribing user {} to training program {}", userId, trainingProgramId);
            UserTrainingProgramDto result = userTrainingProgramService.subscribeUserToProgram(userId, trainingProgramId);
            logger.info("User {} successfully subscribed to training program {}", userId, trainingProgramId);
            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            logger.error("Failed to subscribe user {} to program {}: {}", userId, trainingProgramId, e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            logger.error("Failed to subscribe user to program: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Unsubscribe a user from a training program.
     * Désabonner un utilisateur d'un programme d'entraînement.
     */
    @DeleteMapping("/unsubscribe")
    @Operation(summary = "Unsubscribe user from training program")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "User unsubscribed successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid request data"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "403", description = "Forbidden - User can only unsubscribe themselves")
    })
    public ResponseEntity<Void> unsubscribeUserFromProgram(
            @RequestParam Long userId,
            @RequestParam Long trainingProgramId,
            Authentication authentication) {
        try {
            Long authenticatedUserId = userService.getCurrentUser(authentication).getId();
            if (!authenticatedUserId.equals(userId)) {
                logger.warn("User {} attempted to unsubscribe user {} from program {}", 
                    authenticatedUserId, userId, trainingProgramId);
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            
            logger.info("Unsubscribing user {} from training program {}", userId, trainingProgramId);
            userTrainingProgramService.unsubscribeUserFromProgram(userId, trainingProgramId);
            logger.info("User {} successfully unsubscribed from training program {}", userId, trainingProgramId);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            logger.error("Failed to unsubscribe user {} from program {}: {}", userId, trainingProgramId, e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            logger.error("Failed to unsubscribe user from program: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Get all training programs that a user is subscribed to.
     * Récupérer tous les programmes d'entraînement auxquels un utilisateur est abonné.
     */
    @GetMapping("/user/{userId}")
    @Operation(summary = "Get user's training programs")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "User programs retrieved successfully"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "403", description = "Forbidden - User can only access their own programs")
    })
    public ResponseEntity<List<UserTrainingProgramDto>> getUserPrograms(
            @PathVariable Long userId,
            Authentication authentication) {
        try {
            Long authenticatedUserId = userService.getCurrentUser(authentication).getId();
            if (!authenticatedUserId.equals(userId)) {
                logger.warn("User {} attempted to access programs of user {}", authenticatedUserId, userId);
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            
            logger.info("Retrieving training programs for user: {}", userId);
            List<UserTrainingProgramDto> programs = userTrainingProgramService.getUserPrograms(userId);
            return ResponseEntity.ok(programs);
        } catch (Exception e) {
            logger.error("Failed to retrieve programs for user {}: {}", userId, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Get all users subscribed to a specific training program.
     * Récupérer tous les utilisateurs abonnés à un programme d'entraînement spécifique.
     */
    @GetMapping("/program/{trainingProgramId}")
    @Operation(summary = "Get users subscribed to training program")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Program users retrieved successfully"),
        @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<List<UserTrainingProgramDto>> getProgramUsers(@PathVariable Long trainingProgramId) {
        try {
            logger.info("Retrieving users for training program: {}", trainingProgramId);
            List<UserTrainingProgramDto> users = userTrainingProgramService.getProgramUsers(trainingProgramId);
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            logger.error("Failed to retrieve users for program {}: {}", trainingProgramId, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Check if a user is subscribed to a specific training program.
     * Vérifier si un utilisateur est abonné à un programme d'entraînement spécifique.
     */
    @GetMapping("/check")
    @Operation(summary = "Check if user is subscribed to training program")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Check completed successfully"),
        @ApiResponse(responseCode = "404", description = "User not subscribed to program"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "403", description = "Forbidden - User can only check their own subscriptions")
    })
    public ResponseEntity<UserTrainingProgramDto> checkUserProgram(
            @RequestParam Long userId,
            @RequestParam Long trainingProgramId,
            Authentication authentication) {
        try {
            Long authenticatedUserId = userService.getCurrentUser(authentication).getId();
            if (!authenticatedUserId.equals(userId)) {
                logger.warn("User {} attempted to check subscription of user {} to program {}", 
                    authenticatedUserId, userId, trainingProgramId);
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            
            logger.info("Checking if user {} is subscribed to training program {}", userId, trainingProgramId);
            UserTrainingProgramDto result = userTrainingProgramService.getUserProgram(userId, trainingProgramId);
            if (result != null) {
                return ResponseEntity.ok(result);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            logger.error("Failed to check user program subscription: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
} 