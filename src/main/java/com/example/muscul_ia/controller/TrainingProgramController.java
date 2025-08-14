package com.example.muscul_ia.controller;

import com.example.muscul_ia.dto.CreateTrainingProgramRequest;
import com.example.muscul_ia.dto.TrainingProgramDto;
import com.example.muscul_ia.service.TrainingProgramService;
import com.example.muscul_ia.service.UserTrainingProgramService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Training program controller for managing training programs.
 */
@RestController
@RequestMapping("/api/training-programs")
@Tag(name = "Training Programs", description = "Training program management endpoints")
@CrossOrigin(origins = "*")
public class TrainingProgramController {
    
    private static final Logger logger = LoggerFactory.getLogger(TrainingProgramController.class);
    
    @Autowired
    private TrainingProgramService trainingProgramService;
    
    @Autowired
    private UserTrainingProgramService userTrainingProgramService;
    
    /**
     * Create a new training program and link user automatically.
     */
    @PostMapping
    @Operation(summary = "Create training program")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Training program created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid data")
    })
    public ResponseEntity<TrainingProgramDto> createTrainingProgram(
            @RequestBody CreateTrainingProgramRequest request,
            @RequestParam Long userId) {
        logger.info("Creating training program for user: {}", userId);
        try {
            TrainingProgramDto createdProgram = trainingProgramService.createTrainingProgram(request, userId);
            userTrainingProgramService.subscribeUserToProgram(userId, createdProgram.getId());
            logger.info("Training program created successfully: {}", createdProgram.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(createdProgram);
        } catch (Exception e) {
            logger.error("Failed to create training program: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    /**
     * Get all active programs.
     */
    @GetMapping
    @Operation(summary = "Get all training programs")
    @ApiResponse(responseCode = "200", description = "Training programs retrieved successfully")
    public ResponseEntity<List<TrainingProgramDto>> getAllPrograms() {
        logger.info("Retrieving all active training programs");
        List<TrainingProgramDto> programs = trainingProgramService.getAllActivePrograms();
        return ResponseEntity.ok(programs);
    }
    
    /**
     * Get all public and active programs.
     */
    @GetMapping("/public")
    @Operation(summary = "Get public training programs")
    @ApiResponse(responseCode = "200", description = "Public training programs retrieved successfully")
    public ResponseEntity<List<TrainingProgramDto>> getPublicPrograms() {
        logger.info("Retrieving all public training programs");
        List<TrainingProgramDto> programs = trainingProgramService.getAllPublicActivePrograms();
        return ResponseEntity.ok(programs);
    }
    
    /**
     * Get a program by ID.
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get training program by ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Training program found"),
        @ApiResponse(responseCode = "404", description = "Training program not found")
    })
    public ResponseEntity<TrainingProgramDto> getProgramById(@PathVariable Long id) {
        logger.info("Retrieving training program by ID: {}", id);
        return trainingProgramService.getProgramById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * Get programs created by a user.
     */
    @GetMapping("/user/{userId}")
    @Operation(summary = "Get training programs by user")
    @ApiResponse(responseCode = "200", description = "User training programs retrieved successfully")
    public ResponseEntity<List<TrainingProgramDto>> getProgramsByUser(@PathVariable Long userId) {
        logger.info("Retrieving training programs for user: {}", userId);
        List<TrainingProgramDto> programs = trainingProgramService.getProgramsByUser(userId);
        return ResponseEntity.ok(programs);
    }
    
    /**
     * Update a program.
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update training program")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Training program updated successfully"),
        @ApiResponse(responseCode = "404", description = "Training program not found")
    })
    public ResponseEntity<TrainingProgramDto> updateProgram(
            @PathVariable Long id, 
            @RequestBody CreateTrainingProgramRequest request,
            @RequestParam Long userId) {
        logger.info("Updating training program with ID: {}", id);
        try {
            TrainingProgramDto updatedProgram = trainingProgramService.updateProgram(id, request, userId);
            logger.info("Training program updated successfully: {}", id);
            return ResponseEntity.ok(updatedProgram);
        } catch (RuntimeException e) {
            logger.error("Failed to update training program {}: {}", id, e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Delete a program.
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete training program")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Training program deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Training program not found")
    })
    public ResponseEntity<Void> deleteProgram(@PathVariable Long id, @RequestParam Long userId) {
        logger.info("Deleting training program with ID: {}", id);
        try {
            trainingProgramService.deleteProgram(id, userId);
            logger.info("Training program deleted successfully: {}", id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            logger.error("Failed to delete training program {}: {}", id, e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Search programs by name.
     */
    @GetMapping("/search")
    @Operation(summary = "Search training programs by name")
    @ApiResponse(responseCode = "200", description = "Search results")
    public ResponseEntity<List<TrainingProgramDto>> searchProgramsByName(@RequestParam String name) {
        logger.info("Searching training programs by name: {}", name);
        List<TrainingProgramDto> programs = trainingProgramService.searchProgramsByName(name);
        return ResponseEntity.ok(programs);
    }
    
    /**
     * Search public programs by name.
     */
    @GetMapping("/public/search")
    @Operation(summary = "Search public training programs by name")
    @ApiResponse(responseCode = "200", description = "Search results")
    public ResponseEntity<List<TrainingProgramDto>> searchPublicProgramsByName(@RequestParam String name) {
        logger.info("Searching public training programs by name: {}", name);
        List<TrainingProgramDto> programs = trainingProgramService.searchPublicProgramsByName(name);
        return ResponseEntity.ok(programs);
    }
    
    /**
     * Get programs by difficulty level.
     */
    @GetMapping("/difficulty/{difficultyLevel}")
    @Operation(summary = "Get training programs by difficulty level")
    @ApiResponse(responseCode = "200", description = "Training programs by difficulty level")
    public ResponseEntity<List<TrainingProgramDto>> getProgramsByDifficultyLevel(@PathVariable String difficultyLevel) {
        logger.info("Getting training programs by difficulty level: {}", difficultyLevel);
        List<TrainingProgramDto> programs = trainingProgramService.getProgramsByDifficultyLevel(difficultyLevel);
        return ResponseEntity.ok(programs);
    }
    
    /**
     * Get public programs by difficulty level.
     */
    @GetMapping("/public/difficulty/{difficultyLevel}")
    @Operation(summary = "Get public training programs by difficulty level")
    @ApiResponse(responseCode = "200", description = "Public training programs by difficulty level")
    public ResponseEntity<List<TrainingProgramDto>> getPublicProgramsByDifficultyLevel(@PathVariable String difficultyLevel) {
        logger.info("Getting public training programs by difficulty level: {}", difficultyLevel);
        List<TrainingProgramDto> programs = trainingProgramService.getPublicProgramsByDifficultyLevel(difficultyLevel);
        return ResponseEntity.ok(programs);
    }
    
    /**
     * Get programs by category.
     */
    @GetMapping("/category/{category}")
    @Operation(summary = "Get training programs by category")
    @ApiResponse(responseCode = "200", description = "Training programs by category")
    public ResponseEntity<List<TrainingProgramDto>> getProgramsByCategory(@PathVariable String category) {
        logger.info("Getting training programs by category: {}", category);
        List<TrainingProgramDto> programs = trainingProgramService.getProgramsByCategory(category);
        return ResponseEntity.ok(programs);
    }
    
    /**
     * Get public programs by category.
     */
    @GetMapping("/public/category/{category}")
    @Operation(summary = "Get public training programs by category")
    @ApiResponse(responseCode = "200", description = "Public training programs by category")
    public ResponseEntity<List<TrainingProgramDto>> getPublicProgramsByCategory(@PathVariable String category) {
        logger.info("Getting public training programs by category: {}", category);
        List<TrainingProgramDto> programs = trainingProgramService.getPublicProgramsByCategory(category);
        return ResponseEntity.ok(programs);
    }
    
    /**
     * Get programs by target audience.
     */
    @GetMapping("/audience/{targetAudience}")
    @Operation(summary = "Get training programs by target audience")
    @ApiResponse(responseCode = "200", description = "Training programs by target audience")
    public ResponseEntity<List<TrainingProgramDto>> getProgramsByTargetAudience(@PathVariable String targetAudience) {
        logger.info("Getting training programs by target audience: {}", targetAudience);
        List<TrainingProgramDto> programs = trainingProgramService.getProgramsByTargetAudience(targetAudience);
        return ResponseEntity.ok(programs);
    }
    
    /**
     * Get public programs by target audience.
     */
    @GetMapping("/public/audience/{targetAudience}")
    @Operation(summary = "Get public training programs by target audience")
    @ApiResponse(responseCode = "200", description = "Public training programs by target audience")
    public ResponseEntity<List<TrainingProgramDto>> getPublicProgramsByTargetAudience(@PathVariable String targetAudience) {
        logger.info("Getting public training programs by target audience: {}", targetAudience);
        List<TrainingProgramDto> programs = trainingProgramService.getPublicProgramsByTargetAudience(targetAudience);
        return ResponseEntity.ok(programs);
    }
    
    /**
     * Search programs by description.
     */
    @GetMapping("/search/description")
    @Operation(summary = "Search training programs by description")
    @ApiResponse(responseCode = "200", description = "Search results")
    public ResponseEntity<List<TrainingProgramDto>> searchProgramsByDescription(@RequestParam String description) {
        logger.info("Searching training programs by description: {}", description);
        List<TrainingProgramDto> programs = trainingProgramService.searchProgramsByDescription(description);
        return ResponseEntity.ok(programs);
    }
    
    /**
     * Search public programs by description.
     */
    @GetMapping("/public/search/description")
    @Operation(summary = "Search public training programs by description")
    @ApiResponse(responseCode = "200", description = "Search results")
    public ResponseEntity<List<TrainingProgramDto>> searchPublicProgramsByDescription(@RequestParam String description) {
        logger.info("Searching public training programs by description: {}", description);
        List<TrainingProgramDto> programs = trainingProgramService.searchPublicProgramsByDescription(description);
        return ResponseEntity.ok(programs);
    }
    
    /**
     * Get programs by category and difficulty.
     */
    @GetMapping("/category/{category}/difficulty/{difficultyLevel}")
    @Operation(summary = "Get training programs by category and difficulty")
    @ApiResponse(responseCode = "200", description = "Training programs by category and difficulty")
    public ResponseEntity<List<TrainingProgramDto>> getProgramsByCategoryAndDifficulty(
            @PathVariable String category, 
            @PathVariable String difficultyLevel) {
        logger.info("Getting training programs by category: {} and difficulty: {}", category, difficultyLevel);
        List<TrainingProgramDto> programs = trainingProgramService.getProgramsByCategoryAndDifficulty(category, difficultyLevel);
        return ResponseEntity.ok(programs);
    }
    
    /**
     * Get public programs by category and difficulty.
     */
    @GetMapping("/public/category/{category}/difficulty/{difficultyLevel}")
    @Operation(summary = "Get public training programs by category and difficulty")
    @ApiResponse(responseCode = "200", description = "Public training programs by category and difficulty")
    public ResponseEntity<List<TrainingProgramDto>> getPublicProgramsByCategoryAndDifficulty(
            @PathVariable String category, 
            @PathVariable String difficultyLevel) {
        logger.info("Getting public training programs by category: {} and difficulty: {}", category, difficultyLevel);
        List<TrainingProgramDto> programs = trainingProgramService.getPublicProgramsByCategoryAndDifficulty(category, difficultyLevel);
        return ResponseEntity.ok(programs);
    }
} 