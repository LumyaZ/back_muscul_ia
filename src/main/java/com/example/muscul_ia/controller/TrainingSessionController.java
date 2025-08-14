package com.example.muscul_ia.controller;

import com.example.muscul_ia.dto.CreateTrainingSessionRequest;
import com.example.muscul_ia.dto.TrainingSessionDto;
import com.example.muscul_ia.entity.User;
import com.example.muscul_ia.service.TrainingSessionService;
import com.example.muscul_ia.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 */
@RestController
@RequestMapping("/api/training-sessions")
@Tag(name = "Training Sessions", description = "Training session management endpoints")
@CrossOrigin(origins = "*")
public class TrainingSessionController {
    
    private static final Logger logger = LoggerFactory.getLogger(TrainingSessionController.class);
    
    @Autowired
    private TrainingSessionService trainingSessionService;
    
    @Autowired
    private UserService userService;
    
    /**
     * Create a new training session for the current user.
     */
    @PostMapping
    @Operation(summary = "Create training session")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Training session created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid request data"),
        @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<TrainingSessionDto> createTrainingSession(
            @Valid @RequestBody CreateTrainingSessionRequest request,
            Authentication authentication) {
        try {
            User user = userService.getCurrentUser(authentication);
            logger.info("Creating training session for user: {}", user.getEmail());
            TrainingSessionDto createdSession = trainingSessionService.createTrainingSession(user, request);
            logger.info("Training session created successfully: {}", createdSession.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(createdSession);
        } catch (Exception e) {
            logger.error("Failed to create training session: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    /**
     * Get a training session by ID.
     */
    @GetMapping("/{sessionId}")
    @Operation(summary = "Get training session by ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Training session found"),
        @ApiResponse(responseCode = "404", description = "Training session not found")
    })
    public ResponseEntity<TrainingSessionDto> getTrainingSessionById(@PathVariable Long sessionId) {
        logger.info("Retrieving training session by ID: {}", sessionId);
        try {
            Optional<TrainingSessionDto> session = trainingSessionService.getTrainingSessionById(sessionId);
            return session.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            logger.error("Failed to retrieve training session {}: {}", sessionId, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Get all training sessions for the current user.
     */
    @GetMapping
    @Operation(summary = "Get user's training sessions")
    @ApiResponse(responseCode = "200", description = "Training sessions retrieved successfully")
    public ResponseEntity<List<TrainingSessionDto>> getUserTrainingSessions(Authentication authentication) {
        try {
            User user = userService.getCurrentUser(authentication);
            logger.info("Retrieving training sessions for user: {}", user.getEmail());
            List<TrainingSessionDto> sessions = trainingSessionService.getTrainingSessionsByUser(user);
            return ResponseEntity.ok(sessions);
        } catch (Exception e) {
            logger.error("Failed to retrieve training sessions: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Get all training sessions for a specific user with pagination.
     */
    @GetMapping("/user/{userId}")
    @Operation(summary = "Get user's training sessions with pagination")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Training sessions retrieved successfully"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "403", description = "Forbidden - User can only access their own sessions")
    })
    public ResponseEntity<Page<TrainingSessionDto>> getUserTrainingSessionsWithPagination(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Authentication authentication) {
        try {
            User currentUser = userService.getCurrentUser(authentication);
            if (!currentUser.getId().equals(userId)) {
                logger.warn("User {} attempted to access sessions of user {}", currentUser.getId(), userId);
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            
            logger.info("Retrieving training sessions for user: {} with pagination (page: {}, size: {})", userId, page, size);
            Pageable pageable = PageRequest.of(page, size);
            Page<TrainingSessionDto> sessions = trainingSessionService.getTrainingSessionsByUserId(userId, pageable);
            return ResponseEntity.ok(sessions);
        } catch (Exception e) {
            logger.error("Failed to retrieve training sessions with pagination: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Get training sessions by date range for the current user.
     */
    @GetMapping("/date-range")
    @Operation(summary = "Get training sessions by date range")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Training sessions retrieved successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid date format"),
        @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<List<TrainingSessionDto>> getTrainingSessionsByDateRange(
            @RequestParam String startDate,
            @RequestParam String endDate,
            Authentication authentication) {
        try {
            User user = userService.getCurrentUser(authentication);
            logger.info("Retrieving training sessions for user: {} by date range: {} to {}", user.getEmail(), startDate, endDate);
            LocalDateTime start = LocalDateTime.parse(startDate);
            LocalDateTime end = LocalDateTime.parse(endDate);
            
            List<TrainingSessionDto> sessions = trainingSessionService.getTrainingSessionsByUserAndDateRange(user, start, end);
            return ResponseEntity.ok(sessions);
        } catch (Exception e) {
            logger.error("Failed to retrieve training sessions by date range: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    /**
     * Get training sessions by type for the current user.
     */
    @GetMapping("/type/{sessionType}")
    @Operation(summary = "Get training sessions by type")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Training sessions retrieved successfully"),
        @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<List<TrainingSessionDto>> getTrainingSessionsByType(
            @PathVariable String sessionType,
            Authentication authentication) {
        try {
            User user = userService.getCurrentUser(authentication);
            logger.info("Retrieving training sessions for user: {} by type: {}", user.getEmail(), sessionType);
            List<TrainingSessionDto> sessions = trainingSessionService.getTrainingSessionsByUserAndType(user, sessionType);
            return ResponseEntity.ok(sessions);
        } catch (Exception e) {
            logger.error("Failed to retrieve training sessions by type: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Get training sessions by training program for the current user.
     */
    @GetMapping("/program/{trainingProgramId}")
    @Operation(summary = "Get training sessions by program")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Training sessions retrieved successfully"),
        @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<List<TrainingSessionDto>> getTrainingSessionsByProgram(
            @PathVariable Long trainingProgramId,
            Authentication authentication) {
        try {
            User user = userService.getCurrentUser(authentication);
            logger.info("Retrieving training sessions for user: {} by program: {}", user.getEmail(), trainingProgramId);
            List<TrainingSessionDto> sessions = trainingSessionService.getTrainingSessionsByUserAndTrainingProgram(user, trainingProgramId);
            return ResponseEntity.ok(sessions);
        } catch (Exception e) {
            logger.error("Failed to retrieve training sessions by program: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Search training sessions by name for the current user.
     */
    @GetMapping("/search")
    @Operation(summary = "Search training sessions by name")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Training sessions retrieved successfully"),
        @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<List<TrainingSessionDto>> searchTrainingSessionsByName(
            @RequestParam String name,
            Authentication authentication) {
        try {
            User user = userService.getCurrentUser(authentication);
            logger.info("Searching training sessions for user: {} by name: {}", user.getEmail(), name);
            List<TrainingSessionDto> sessions = trainingSessionService.searchTrainingSessionsByUserAndName(user, name);
            return ResponseEntity.ok(sessions);
        } catch (Exception e) {
            logger.error("Failed to search training sessions: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Get the most recent training session for the current user.
     */
    @GetMapping("/recent")
    @Operation(summary = "Get most recent training session")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Most recent training session retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "No training sessions found"),
        @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<TrainingSessionDto> getMostRecentTrainingSession(Authentication authentication) {
        try {
            User user = userService.getCurrentUser(authentication);
            logger.info("Retrieving most recent training session for user: {}", user.getEmail());
            Optional<TrainingSessionDto> session = trainingSessionService.getMostRecentTrainingSessionByUser(user);
            
            return session.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            logger.error("Failed to retrieve most recent training session: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Get the count of training sessions for the current user.
     */
    @GetMapping("/count")
    @Operation(summary = "Get training sessions count")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Count retrieved successfully"),
        @ApiResponse(responseCode = "401", description = "Unauthorized")
    })
    public ResponseEntity<Long> getTrainingSessionsCount(Authentication authentication) {
        try {
            User user = userService.getCurrentUser(authentication);
            logger.info("Retrieving training sessions count for user: {}", user.getEmail());
            long count = trainingSessionService.countTrainingSessionsByUser(user);
            return ResponseEntity.ok(count);
        } catch (Exception e) {
            logger.error("Failed to retrieve training sessions count: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    /**
     * Update a training session.
     */
    @PutMapping("/{sessionId}")
    @Operation(summary = "Update training session")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Training session updated successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid request data"),
        @ApiResponse(responseCode = "404", description = "Training session not found"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "403", description = "Forbidden - User can only update their own sessions")
    })
    public ResponseEntity<TrainingSessionDto> updateTrainingSession(
            @PathVariable Long sessionId,
            @Valid @RequestBody CreateTrainingSessionRequest request,
            Authentication authentication) {
        try {
            User currentUser = userService.getCurrentUser(authentication);
            logger.info("Updating training session: {} for user: {}", sessionId, currentUser.getEmail());
            
            Optional<TrainingSessionDto> existingSession = trainingSessionService.getTrainingSessionById(sessionId);
            if (existingSession.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            
            if (!existingSession.get().getUserId().equals(currentUser.getId())) {
                logger.warn("User {} attempted to update session {} owned by user {}", 
                    currentUser.getId(), sessionId, existingSession.get().getUserId());
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            
            TrainingSessionDto updatedSession = trainingSessionService.updateTrainingSession(sessionId, request);
            logger.info("Training session updated successfully: {}", sessionId);
            return ResponseEntity.ok(updatedSession);
        } catch (RuntimeException e) {
            logger.error("Failed to update training session {}: {}", sessionId, e.getMessage());
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            logger.error("Failed to update training session: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    /**
     * Delete a training session.
     */
    @DeleteMapping("/{sessionId}")
    @Operation(summary = "Delete training session")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Training session deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Training session not found"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "403", description = "Forbidden - User can only delete their own sessions")
    })
    public ResponseEntity<Void> deleteTrainingSession(
            @PathVariable Long sessionId,
            Authentication authentication) {
        try {
            User currentUser = userService.getCurrentUser(authentication);
            logger.info("Deleting training session: {} for user: {}", sessionId, currentUser.getEmail());
            
            Optional<TrainingSessionDto> existingSession = trainingSessionService.getTrainingSessionById(sessionId);
            if (existingSession.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            
            if (!existingSession.get().getUserId().equals(currentUser.getId())) {
                logger.warn("User {} attempted to delete session {} owned by user {}", 
                    currentUser.getId(), sessionId, existingSession.get().getUserId());
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            
            trainingSessionService.deleteTrainingSession(sessionId);
            logger.info("Training session deleted successfully: {}", sessionId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            logger.error("Failed to delete training session {}: {}", sessionId, e.getMessage());
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            logger.error("Failed to delete training session: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
} 