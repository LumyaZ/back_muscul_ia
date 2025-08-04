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
 * Training session controller for managing training sessions.
 * Contrôleur de sessions d'entraînement pour gérer les sessions d'entraînement.
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
        User user = userService.getCurrentUser(authentication);
        TrainingSessionDto createdSession = trainingSessionService.createTrainingSession(user, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSession);
    }
    
    /**
     * Get a training session by ID.
     * Récupérer une session d'entraînement par ID.
     */
    @GetMapping("/{sessionId}")
    @Operation(summary = "Get training session by ID", description = "Get a specific training session by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Training session retrieved successfully",
            content = @Content(schema = @Schema(implementation = TrainingSessionDto.class))),
        @ApiResponse(responseCode = "404", description = "Training session not found")
    })
    public ResponseEntity<TrainingSessionDto> getTrainingSessionById(@PathVariable Long sessionId) {
        Optional<TrainingSessionDto> session = trainingSessionService.getTrainingSessionById(sessionId);
        
        if (session.isPresent()) {
            return ResponseEntity.ok(session.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Get all training sessions for the current user.
     * Récupérer toutes les sessions d'entraînement pour l'utilisateur actuel.
     */
    @GetMapping
    @Operation(summary = "Get user's training sessions", description = "Get all training sessions for the current user")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Training sessions retrieved successfully",
            content = @Content(schema = @Schema(implementation = TrainingSessionDto.class))),
        @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<List<TrainingSessionDto>> getUserTrainingSessions(Authentication authentication) {
        User user = userService.getCurrentUser(authentication);
        List<TrainingSessionDto> sessions = trainingSessionService.getTrainingSessionsByUser(user);
        return ResponseEntity.ok(sessions);
    }
    
    /**
     * Get all training sessions for a specific user with pagination.
     * Récupérer toutes les sessions d'entraînement pour un utilisateur spécifique avec pagination.
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
        // Security check: user can only access their own sessions
        User currentUser = userService.getCurrentUser(authentication);
        if (!currentUser.getId().equals(userId)) {
            return ResponseEntity.status(403).build();
        }
        
        Pageable pageable = PageRequest.of(page, size);
        Page<TrainingSessionDto> sessions = trainingSessionService.getTrainingSessionsByUserId(userId, pageable);
        return ResponseEntity.ok(sessions);
    }
    
    /**
     * Get training sessions by date range for the current user.
     * Récupérer les sessions d'entraînement par plage de dates pour l'utilisateur actuel.
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
        User user = userService.getCurrentUser(authentication);
        LocalDateTime start = LocalDateTime.parse(startDate);
        LocalDateTime end = LocalDateTime.parse(endDate);
        
        List<TrainingSessionDto> sessions = trainingSessionService.getTrainingSessionsByUserAndDateRange(user, start, end);
        return ResponseEntity.ok(sessions);
    }
    
    /**
     * Get training sessions by type for the current user.
     * Récupérer les sessions d'entraînement par type pour l'utilisateur actuel.
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
        User user = userService.getCurrentUser(authentication);
        List<TrainingSessionDto> sessions = trainingSessionService.getTrainingSessionsByUserAndType(user, sessionType);
        return ResponseEntity.ok(sessions);
    }
    
    /**
     * Get training sessions by training program for the current user.
     * Récupérer les sessions d'entraînement par programme d'entraînement pour l'utilisateur actuel.
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
        User user = userService.getCurrentUser(authentication);
        List<TrainingSessionDto> sessions = trainingSessionService.getTrainingSessionsByUserAndTrainingProgram(user, trainingProgramId);
        return ResponseEntity.ok(sessions);
    }
    
    /**
     * Search training sessions by name for the current user.
     * Rechercher les sessions d'entraînement par nom pour l'utilisateur actuel.
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
        User user = userService.getCurrentUser(authentication);
        List<TrainingSessionDto> sessions = trainingSessionService.searchTrainingSessionsByUserAndName(user, name);
        return ResponseEntity.ok(sessions);
    }
    
    /**
     * Get the most recent training session for the current user.
     * Récupérer la session d'entraînement la plus récente pour l'utilisateur actuel.
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
        User user = userService.getCurrentUser(authentication);
        Optional<TrainingSessionDto> session = trainingSessionService.getMostRecentTrainingSessionByUser(user);
        
        if (session.isPresent()) {
            return ResponseEntity.ok(session.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Get the count of training sessions for the current user.
     * Récupérer le nombre de sessions d'entraînement pour l'utilisateur actuel.
     */
    @GetMapping("/count")
    @Operation(summary = "Get training sessions count", description = "Get the total number of training sessions for the current user")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Count retrieved successfully"),
        @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<Long> getTrainingSessionsCount(Authentication authentication) {
        User user = userService.getCurrentUser(authentication);
        long count = trainingSessionService.countTrainingSessionsByUser(user);
        return ResponseEntity.ok(count);
    }
    
    /**
     * Update a training session.
     * Mettre à jour une session d'entraînement.
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
        // Security check: verify the session belongs to the current user
        User currentUser = userService.getCurrentUser(authentication);
        Optional<TrainingSessionDto> existingSession = trainingSessionService.getTrainingSessionById(sessionId);
        
        if (existingSession.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        if (!existingSession.get().getUserId().equals(currentUser.getId())) {
            return ResponseEntity.status(403).build();
        }
        
        TrainingSessionDto updatedSession = trainingSessionService.updateTrainingSession(sessionId, request);
        return ResponseEntity.ok(updatedSession);
    }
    
    /**
     * Delete a training session.
     * Supprimer une session d'entraînement.
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
        // Security check: verify the session belongs to the current user
        User currentUser = userService.getCurrentUser(authentication);
        Optional<TrainingSessionDto> existingSession = trainingSessionService.getTrainingSessionById(sessionId);
        
        if (existingSession.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        if (!existingSession.get().getUserId().equals(currentUser.getId())) {
            return ResponseEntity.status(403).build();
        }
        
        trainingSessionService.deleteTrainingSession(sessionId);
        return ResponseEntity.noContent().build();
    }
} 