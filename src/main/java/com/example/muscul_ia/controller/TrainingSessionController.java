package com.example.muscul_ia.controller;

import com.example.muscul_ia.dto.CreateTrainingSessionRequest;
import com.example.muscul_ia.dto.TrainingSessionDto;
import com.example.muscul_ia.entity.User;
import com.example.muscul_ia.service.TrainingSessionService;
import com.example.muscul_ia.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * REST Controller for managing training sessions.
 * Contrôleur REST pour gérer les sessions d'entraînement.
 * 
 * This controller provides endpoints for creating, reading, updating, and deleting
 * training sessions, as well as searching and filtering functionality.
 * 
 * Ce contrôleur fournit des endpoints pour créer, lire, mettre à jour et supprimer
 * des sessions d'entraînement, ainsi que des fonctionnalités de recherche et de filtrage.
 * 
 * @author Muscul IA Team
 * @version 1.0
 * @since 2024-01-01
 */
@RestController
@RequestMapping("/api/training-sessions")
@Tag(name = "Training Sessions", description = "Endpoints for managing training sessions")
@CrossOrigin(origins = "*")
public class TrainingSessionController {
    
    @Autowired
    private TrainingSessionService trainingSessionService;
    
    @Autowired
    private UserService userService;
    
    /**
     * Create a new training session for the current user.
     * Créer une nouvelle session d'entraînement pour l'utilisateur actuel.
     * 
     * @param request - Request containing session details
     * @param authentication - Spring Security authentication object
     * @return ResponseEntity with the created training session
     */
    @PostMapping
    @Operation(summary = "Create training session", description = "Create a new training session for the current user")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Training session created successfully",
            content = @Content(schema = @Schema(implementation = TrainingSessionDto.class))),
        @ApiResponse(responseCode = "400", description = "Invalid request data"),
        @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<TrainingSessionDto> createTrainingSession(
            @Valid @RequestBody CreateTrainingSessionRequest request,
            Authentication authentication) {
        System.out.println("=== TRAINING SESSION CONTROLLER: CREATE ===");
        System.out.println("Request: " + request);
        System.out.println("Authentication: " + authentication);
        System.out.println("Authentication principal: " + (authentication != null ? authentication.getPrincipal() : "null"));
        System.out.println("Authentication authorities: " + (authentication != null ? authentication.getAuthorities() : "null"));
        
        User user = userService.getCurrentUser(authentication);
        System.out.println("Current user: " + user);
        
        TrainingSessionDto createdSession = trainingSessionService.createTrainingSession(user, request);
        
        System.out.println("Training session created: " + createdSession.getId() + " for user " + createdSession.getUserId());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSession);
    }
    
    /**
     * Get a training session by ID.
     * Récupérer une session d'entraînement par ID.
     * 
     * @param sessionId - ID of the training session
     * @return ResponseEntity with the training session if found
     */
    @GetMapping("/{sessionId}")
    @Operation(summary = "Get training session by ID", description = "Get a specific training session by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Training session retrieved successfully",
            content = @Content(schema = @Schema(implementation = TrainingSessionDto.class))),
        @ApiResponse(responseCode = "404", description = "Training session not found")
    })
    public ResponseEntity<TrainingSessionDto> getTrainingSessionById(@PathVariable Long sessionId) {
        System.out.println("=== TRAINING SESSION CONTROLLER: GET BY ID ===");
        System.out.println("Session ID: " + sessionId);
        
        Optional<TrainingSessionDto> session = trainingSessionService.getTrainingSessionById(sessionId);
        
        if (session.isPresent()) {
            System.out.println("Training session found: " + session.get().getId());
            return ResponseEntity.ok(session.get());
        } else {
            System.out.println("Training session not found: " + sessionId);
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Get all training sessions for the current user.
     * Récupérer toutes les sessions d'entraînement pour l'utilisateur actuel.
     * 
     * @param authentication - Spring Security authentication object
     * @return ResponseEntity with the list of training sessions
     */
    @GetMapping
    @Operation(summary = "Get user's training sessions", description = "Get all training sessions for the current user")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Training sessions retrieved successfully",
            content = @Content(schema = @Schema(implementation = TrainingSessionDto.class))),
        @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<List<TrainingSessionDto>> getUserTrainingSessions(Authentication authentication) {
        System.out.println("=== TRAINING SESSION CONTROLLER: GET USER SESSIONS ===");
        
        User user = userService.getCurrentUser(authentication);
        List<TrainingSessionDto> sessions = trainingSessionService.getTrainingSessionsByUser(user);
        
        System.out.println("Found " + sessions.size() + " training sessions for user " + user.getId());
        return ResponseEntity.ok(sessions);
    }
    
    /**
     * Get all training sessions for a specific user with pagination.
     * Récupérer toutes les sessions d'entraînement pour un utilisateur spécifique avec pagination.
     * 
     * @param userId - ID of the user
     * @param page - Page number (0-based)
     * @param size - Page size
     * @param authentication - Spring Security authentication object
     * @return ResponseEntity with the page of training sessions
     */
    @GetMapping("/user/{userId}")
    @Operation(summary = "Get user's training sessions with pagination", description = "Get all training sessions for a specific user with pagination")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Training sessions retrieved successfully",
            content = @Content(schema = @Schema(implementation = TrainingSessionDto.class))),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "403", description = "Forbidden - User can only access their own sessions")
    })
    public ResponseEntity<Page<TrainingSessionDto>> getUserTrainingSessionsWithPagination(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Authentication authentication) {
        System.out.println("=== TRAINING SESSION CONTROLLER: GET USER SESSIONS WITH PAGINATION ===");
        System.out.println("User ID: " + userId + ", Page: " + page + ", Size: " + size);
        
        // Security check: user can only access their own sessions
        User currentUser = userService.getCurrentUser(authentication);
        if (!currentUser.getId().equals(userId)) {
            System.out.println("ACCESS DENIED: User " + currentUser.getId() + " tried to access sessions for user " + userId);
            return ResponseEntity.status(403).build();
        }
        
        Pageable pageable = PageRequest.of(page, size);
        Page<TrainingSessionDto> sessions = trainingSessionService.getTrainingSessionsByUserId(userId, pageable);
        
        System.out.println("Found " + sessions.getTotalElements() + " training sessions for user " + userId);
        return ResponseEntity.ok(sessions);
    }
    
    /**
     * Get training sessions by date range for the current user.
     * Récupérer les sessions d'entraînement par plage de dates pour l'utilisateur actuel.
     * 
     * @param startDate - Start date of the range
     * @param endDate - End date of the range
     * @param authentication - Spring Security authentication object
     * @return ResponseEntity with the list of training sessions
     */
    @GetMapping("/date-range")
    @Operation(summary = "Get training sessions by date range", description = "Get training sessions for the current user within a date range")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Training sessions retrieved successfully",
            content = @Content(schema = @Schema(implementation = TrainingSessionDto.class))),
        @ApiResponse(responseCode = "400", description = "Invalid date format"),
        @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<List<TrainingSessionDto>> getTrainingSessionsByDateRange(
            @RequestParam String startDate,
            @RequestParam String endDate,
            Authentication authentication) {
        System.out.println("=== TRAINING SESSION CONTROLLER: GET BY DATE RANGE ===");
        System.out.println("Start Date: " + startDate + ", End Date: " + endDate);
        
        User user = userService.getCurrentUser(authentication);
        LocalDateTime start = LocalDateTime.parse(startDate);
        LocalDateTime end = LocalDateTime.parse(endDate);
        
        List<TrainingSessionDto> sessions = trainingSessionService.getTrainingSessionsByUserAndDateRange(user, start, end);
        
        System.out.println("Found " + sessions.size() + " training sessions in date range for user " + user.getId());
        return ResponseEntity.ok(sessions);
    }
    
    /**
     * Get training sessions by type for the current user.
     * Récupérer les sessions d'entraînement par type pour l'utilisateur actuel.
     * 
     * @param sessionType - Type of training session
     * @param authentication - Spring Security authentication object
     * @return ResponseEntity with the list of training sessions
     */
    @GetMapping("/type/{sessionType}")
    @Operation(summary = "Get training sessions by type", description = "Get training sessions for the current user by session type")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Training sessions retrieved successfully",
            content = @Content(schema = @Schema(implementation = TrainingSessionDto.class))),
        @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<List<TrainingSessionDto>> getTrainingSessionsByType(
            @PathVariable String sessionType,
            Authentication authentication) {
        System.out.println("=== TRAINING SESSION CONTROLLER: GET BY TYPE ===");
        System.out.println("Session Type: " + sessionType);
        
        User user = userService.getCurrentUser(authentication);
        List<TrainingSessionDto> sessions = trainingSessionService.getTrainingSessionsByUserAndType(user, sessionType);
        
        System.out.println("Found " + sessions.size() + " training sessions of type " + sessionType + " for user " + user.getId());
        return ResponseEntity.ok(sessions);
    }
    
    /**
     * Get training sessions by training program for the current user.
     * Récupérer les sessions d'entraînement par programme d'entraînement pour l'utilisateur actuel.
     * 
     * @param trainingProgramId - ID of the training program
     * @param authentication - Spring Security authentication object
     * @return ResponseEntity with the list of training sessions
     */
    @GetMapping("/program/{trainingProgramId}")
    @Operation(summary = "Get training sessions by program", description = "Get training sessions for the current user by training program")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Training sessions retrieved successfully",
            content = @Content(schema = @Schema(implementation = TrainingSessionDto.class))),
        @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<List<TrainingSessionDto>> getTrainingSessionsByProgram(
            @PathVariable Long trainingProgramId,
            Authentication authentication) {
        System.out.println("=== TRAINING SESSION CONTROLLER: GET BY PROGRAM ===");
        System.out.println("Training Program ID: " + trainingProgramId);
        
        User user = userService.getCurrentUser(authentication);
        List<TrainingSessionDto> sessions = trainingSessionService.getTrainingSessionsByUserAndTrainingProgram(user, trainingProgramId);
        
        System.out.println("Found " + sessions.size() + " training sessions for program " + trainingProgramId + " and user " + user.getId());
        return ResponseEntity.ok(sessions);
    }
    
    /**
     * Search training sessions by name for the current user.
     * Rechercher les sessions d'entraînement par nom pour l'utilisateur actuel.
     * 
     * @param name - Name to search for
     * @param authentication - Spring Security authentication object
     * @return ResponseEntity with the list of training sessions
     */
    @GetMapping("/search")
    @Operation(summary = "Search training sessions by name", description = "Search training sessions for the current user by name")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Training sessions retrieved successfully",
            content = @Content(schema = @Schema(implementation = TrainingSessionDto.class))),
        @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<List<TrainingSessionDto>> searchTrainingSessionsByName(
            @RequestParam String name,
            Authentication authentication) {
        System.out.println("=== TRAINING SESSION CONTROLLER: SEARCH BY NAME ===");
        System.out.println("Search Name: " + name);
        
        User user = userService.getCurrentUser(authentication);
        List<TrainingSessionDto> sessions = trainingSessionService.searchTrainingSessionsByUserAndName(user, name);
        
        System.out.println("Found " + sessions.size() + " training sessions matching '" + name + "' for user " + user.getId());
        return ResponseEntity.ok(sessions);
    }
    
    /**
     * Get the most recent training session for the current user.
     * Récupérer la session d'entraînement la plus récente pour l'utilisateur actuel.
     * 
     * @param authentication - Spring Security authentication object
     * @return ResponseEntity with the most recent training session
     */
    @GetMapping("/recent")
    @Operation(summary = "Get most recent training session", description = "Get the most recent training session for the current user")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Most recent training session retrieved successfully",
            content = @Content(schema = @Schema(implementation = TrainingSessionDto.class))),
        @ApiResponse(responseCode = "404", description = "No training sessions found"),
        @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<TrainingSessionDto> getMostRecentTrainingSession(Authentication authentication) {
        System.out.println("=== TRAINING SESSION CONTROLLER: GET MOST RECENT ===");
        
        User user = userService.getCurrentUser(authentication);
        Optional<TrainingSessionDto> session = trainingSessionService.getMostRecentTrainingSessionByUser(user);
        
        if (session.isPresent()) {
            System.out.println("Most recent training session found: " + session.get().getId());
            return ResponseEntity.ok(session.get());
        } else {
            System.out.println("No training sessions found for user " + user.getId());
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Get the count of training sessions for the current user.
     * Récupérer le nombre de sessions d'entraînement pour l'utilisateur actuel.
     * 
     * @param authentication - Spring Security authentication object
     * @return ResponseEntity with the count of training sessions
     */
    @GetMapping("/count")
    @Operation(summary = "Get training sessions count", description = "Get the total number of training sessions for the current user")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Count retrieved successfully"),
        @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<Long> getTrainingSessionsCount(Authentication authentication) {
        System.out.println("=== TRAINING SESSION CONTROLLER: GET COUNT ===");
        
        User user = userService.getCurrentUser(authentication);
        long count = trainingSessionService.countTrainingSessionsByUser(user);
        
        System.out.println("Training sessions count for user " + user.getId() + ": " + count);
        return ResponseEntity.ok(count);
    }
    
    /**
     * Update a training session.
     * Mettre à jour une session d'entraînement.
     * 
     * @param sessionId - ID of the session to update
     * @param request - Request containing updated session details
     * @param authentication - Spring Security authentication object
     * @return ResponseEntity with the updated training session
     */
    @PutMapping("/{sessionId}")
    @Operation(summary = "Update training session", description = "Update an existing training session")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Training session updated successfully",
            content = @Content(schema = @Schema(implementation = TrainingSessionDto.class))),
        @ApiResponse(responseCode = "400", description = "Invalid request data"),
        @ApiResponse(responseCode = "404", description = "Training session not found"),
        @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<TrainingSessionDto> updateTrainingSession(
            @PathVariable Long sessionId,
            @Valid @RequestBody CreateTrainingSessionRequest request,
            Authentication authentication) {
        System.out.println("=== TRAINING SESSION CONTROLLER: UPDATE ===");
        System.out.println("Session ID: " + sessionId);
        System.out.println("Request: " + request);
        
        // Security check: verify the session belongs to the current user
        User currentUser = userService.getCurrentUser(authentication);
        Optional<TrainingSessionDto> existingSession = trainingSessionService.getTrainingSessionById(sessionId);
        
        if (existingSession.isEmpty()) {
            System.out.println("Training session not found: " + sessionId);
            return ResponseEntity.notFound().build();
        }
        
        if (!existingSession.get().getUserId().equals(currentUser.getId())) {
            System.out.println("ACCESS DENIED: User " + currentUser.getId() + " tried to update session " + sessionId + " belonging to user " + existingSession.get().getUserId());
            return ResponseEntity.status(403).build();
        }
        
        TrainingSessionDto updatedSession = trainingSessionService.updateTrainingSession(sessionId, request);
        
        System.out.println("Training session updated: " + updatedSession.getId());
        return ResponseEntity.ok(updatedSession);
    }
    
    /**
     * Delete a training session.
     * Supprimer une session d'entraînement.
     * 
     * @param sessionId - ID of the session to delete
     * @param authentication - Spring Security authentication object
     * @return ResponseEntity indicating success
     */
    @DeleteMapping("/{sessionId}")
    @Operation(summary = "Delete training session", description = "Delete an existing training session")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Training session deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Training session not found"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "403", description = "Forbidden - User can only delete their own sessions")
    })
    public ResponseEntity<Void> deleteTrainingSession(
            @PathVariable Long sessionId,
            Authentication authentication) {
        System.out.println("=== TRAINING SESSION CONTROLLER: DELETE ===");
        System.out.println("Session ID: " + sessionId);
        
        // Security check: verify the session belongs to the current user
        User currentUser = userService.getCurrentUser(authentication);
        Optional<TrainingSessionDto> existingSession = trainingSessionService.getTrainingSessionById(sessionId);
        
        if (existingSession.isEmpty()) {
            System.out.println("Training session not found: " + sessionId);
            return ResponseEntity.notFound().build();
        }
        
        if (!existingSession.get().getUserId().equals(currentUser.getId())) {
            System.out.println("ACCESS DENIED: User " + currentUser.getId() + " tried to delete session " + sessionId + " belonging to user " + existingSession.get().getUserId());
            return ResponseEntity.status(403).build();
        }
        
        trainingSessionService.deleteTrainingSession(sessionId);
        
        System.out.println("Training session deleted: " + sessionId);
        return ResponseEntity.noContent().build();
    }
} 