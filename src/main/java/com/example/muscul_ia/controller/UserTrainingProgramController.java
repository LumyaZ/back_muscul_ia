package com.example.muscul_ia.controller;

import com.example.muscul_ia.dto.UserTrainingProgramDto;
import com.example.muscul_ia.service.UserTrainingProgramService;
import com.example.muscul_ia.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
@CrossOrigin(origins = "*")
public class UserTrainingProgramController {
    
    @Autowired
    private UserTrainingProgramService userTrainingProgramService;
    
    @Autowired
    private UserService userService;
    
    /**
     * Subscribe a user to a training program.
     * Abonner un utilisateur à un programme d'entraînement.
     */
    @PostMapping("/subscribe")
    public ResponseEntity<UserTrainingProgramDto> subscribeUserToProgram(
            @RequestParam Long userId,
            @RequestParam Long trainingProgramId,
            Authentication authentication) {
        try {
            // Vérifier que l'utilisateur authentifié correspond à l'userId
            Long authenticatedUserId = userService.getCurrentUser(authentication).getId();
            if (!authenticatedUserId.equals(userId)) {
                return ResponseEntity.status(403).build();
            }
            
            UserTrainingProgramDto result = userTrainingProgramService.subscribeUserToProgram(userId, trainingProgramId);
            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * Unsubscribe a user from a training program.
     * Désabonner un utilisateur d'un programme d'entraînement.
     */
    @DeleteMapping("/unsubscribe")
    public ResponseEntity<Void> unsubscribeUserFromProgram(
            @RequestParam Long userId,
            @RequestParam Long trainingProgramId,
            Authentication authentication) {
        try {
            // Vérifier que l'utilisateur authentifié correspond à l'userId
            Long authenticatedUserId = userService.getCurrentUser(authentication).getId();
            if (!authenticatedUserId.equals(userId)) {
                return ResponseEntity.status(403).build();
            }
            
            userTrainingProgramService.unsubscribeUserFromProgram(userId, trainingProgramId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * Get all training programs that a user is subscribed to.
     * Récupérer tous les programmes d'entraînement auxquels un utilisateur est abonné.
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<UserTrainingProgramDto>> getUserPrograms(
            @PathVariable Long userId,
            Authentication authentication) {
        try {
            // Vérifier que l'utilisateur authentifié correspond à l'userId
            Long authenticatedUserId = userService.getCurrentUser(authentication).getId();
            if (!authenticatedUserId.equals(userId)) {
                return ResponseEntity.status(403).build();
            }
            
            List<UserTrainingProgramDto> programs = userTrainingProgramService.getUserPrograms(userId);
            return ResponseEntity.ok(programs);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * Get all users subscribed to a specific training program.
     * Récupérer tous les utilisateurs abonnés à un programme d'entraînement spécifique.
     */
    @GetMapping("/program/{trainingProgramId}")
    public ResponseEntity<List<UserTrainingProgramDto>> getProgramUsers(@PathVariable Long trainingProgramId) {
        try {
            List<UserTrainingProgramDto> users = userTrainingProgramService.getProgramUsers(trainingProgramId);
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    /**
     * Check if a user is subscribed to a specific training program.
     * Vérifier si un utilisateur est abonné à un programme d'entraînement spécifique.
     */
    @GetMapping("/check")
    public ResponseEntity<UserTrainingProgramDto> checkUserProgram(
            @RequestParam Long userId,
            @RequestParam Long trainingProgramId,
            Authentication authentication) {
        try {
            // Vérifier que l'utilisateur authentifié correspond à l'userId
            Long authenticatedUserId = userService.getCurrentUser(authentication).getId();
            if (!authenticatedUserId.equals(userId)) {
                return ResponseEntity.status(403).build();
            }
            
            UserTrainingProgramDto result = userTrainingProgramService.getUserProgram(userId, trainingProgramId);
            if (result != null) {
                return ResponseEntity.ok(result);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
} 