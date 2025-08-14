package com.example.muscul_ia.controller;

import com.example.muscul_ia.dto.CreateExerciseRequest;
import com.example.muscul_ia.dto.ExerciseDto;
import com.example.muscul_ia.service.ExerciseService;
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
 * Exercise controller for managing exercise operations.
 */
@RestController
@RequestMapping("/api/exercises")
@Tag(name = "Exercises", description = "Exercise management endpoints")
@CrossOrigin(origins = "*")
public class ExerciseController {
    
    private static final Logger logger = LoggerFactory.getLogger(ExerciseController.class);
    
    @Autowired
    private ExerciseService exerciseService;
    
    /**
     * Create a new exercise.
     */
    @PostMapping
    @Operation(summary = "Create new exercise")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Exercise created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid data")
    })
    public ResponseEntity<ExerciseDto> createExercise(@RequestBody CreateExerciseRequest request) {
        logger.info("Creating new exercise: {}", request.getName());
        try {
            ExerciseDto createdExercise = exerciseService.createExercise(request);
            logger.info("Exercise created successfully: {}", createdExercise.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(createdExercise);
        } catch (Exception e) {
            logger.error("Failed to create exercise: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    /**
     * Get all active exercises.
     */
    @GetMapping
    @Operation(summary = "Get all exercises")
    @ApiResponse(responseCode = "200", description = "Exercises retrieved successfully")
    public ResponseEntity<List<ExerciseDto>> getAllExercises() {
        logger.info("Retrieving all active exercises");
        List<ExerciseDto> exercises = exerciseService.getAllActiveExercises();
        return ResponseEntity.ok(exercises);
    }
    
    /**
     * Get an exercise by ID.
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get exercise by ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Exercise found"),
        @ApiResponse(responseCode = "404", description = "Exercise not found")
    })
    public ResponseEntity<ExerciseDto> getExerciseById(@PathVariable Long id) {
        logger.info("Retrieving exercise by ID: {}", id);
        return exerciseService.getExerciseById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * Update an exercise.
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update exercise")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Exercise updated successfully"),
        @ApiResponse(responseCode = "404", description = "Exercise not found")
    })
    public ResponseEntity<ExerciseDto> updateExercise(@PathVariable Long id, @RequestBody CreateExerciseRequest request) {
        logger.info("Updating exercise with ID: {}", id);
        try {
            ExerciseDto updatedExercise = exerciseService.updateExercise(id, request);
            logger.info("Exercise updated successfully: {}", id);
            return ResponseEntity.ok(updatedExercise);
        } catch (RuntimeException e) {
            logger.error("Failed to update exercise {}: {}", id, e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Delete an exercise.
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete exercise")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Exercise deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Exercise not found")
    })
    public ResponseEntity<Void> deleteExercise(@PathVariable Long id) {
        logger.info("Deleting exercise with ID: {}", id);
        try {
            exerciseService.deleteExercise(id);
            logger.info("Exercise deleted successfully: {}", id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            logger.error("Failed to delete exercise {}: {}", id, e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Search exercises by name.
     */
    @GetMapping("/search")
    @Operation(summary = "Search exercises by name")
    @ApiResponse(responseCode = "200", description = "Search results")
    public ResponseEntity<List<ExerciseDto>> searchExercisesByName(@RequestParam String name) {
        logger.info("Searching exercises by name: {}", name);
        List<ExerciseDto> exercises = exerciseService.searchExercisesByName(name);
        return ResponseEntity.ok(exercises);
    }
    
    /**
     * Get exercises by category.
     */
    @GetMapping("/category/{category}")
    @Operation(summary = "Get exercises by category")
    @ApiResponse(responseCode = "200", description = "Exercises by category")
    public ResponseEntity<List<ExerciseDto>> getExercisesByCategory(@PathVariable String category) {
        logger.info("Getting exercises by category: {}", category);
        List<ExerciseDto> exercises = exerciseService.getExercisesByCategory(category);
        return ResponseEntity.ok(exercises);
    }
    
    /**
     * Get exercises by muscle group.
     */
    @GetMapping("/muscle-group/{muscleGroup}")
    @Operation(summary = "Get exercises by muscle group")
    @ApiResponse(responseCode = "200", description = "Exercises by muscle group")
    public ResponseEntity<List<ExerciseDto>> getExercisesByMuscleGroup(@PathVariable String muscleGroup) {
        logger.info("Getting exercises by muscle group: {}", muscleGroup);
        List<ExerciseDto> exercises = exerciseService.getExercisesByMuscleGroup(muscleGroup);
        return ResponseEntity.ok(exercises);
    }
    
    /**
     * Get exercises by difficulty level.
     */
    @GetMapping("/difficulty/{difficultyLevel}")
    @Operation(summary = "Get exercises by difficulty level")
    @ApiResponse(responseCode = "200", description = "Exercises by difficulty level")
    public ResponseEntity<List<ExerciseDto>> getExercisesByDifficultyLevel(@PathVariable String difficultyLevel) {
        logger.info("Getting exercises by difficulty level: {}", difficultyLevel);
        List<ExerciseDto> exercises = exerciseService.getExercisesByDifficultyLevel(difficultyLevel);
        return ResponseEntity.ok(exercises);
    }
    
    /**
     * Get exercises by equipment.
     */
    @GetMapping("/equipment/{equipment}")
    @Operation(summary = "Get exercises by equipment")
    @ApiResponse(responseCode = "200", description = "Exercises by equipment")
    public ResponseEntity<List<ExerciseDto>> getExercisesByEquipment(@PathVariable String equipment) {
        logger.info("Getting exercises by equipment: {}", equipment);
        List<ExerciseDto> exercises = exerciseService.getExercisesByEquipment(equipment);
        return ResponseEntity.ok(exercises);
    }
    
    /**
     * Search exercises by description.
     */
    @GetMapping("/search/description")
    @Operation(summary = "Search exercises by description")
    @ApiResponse(responseCode = "200", description = "Search results")
    public ResponseEntity<List<ExerciseDto>> searchExercisesByDescription(@RequestParam String description) {
        logger.info("Searching exercises by description: {}", description);
        List<ExerciseDto> exercises = exerciseService.searchExercisesByDescription(description);
        return ResponseEntity.ok(exercises);
    }
    
    /**
     * Get exercises by category and difficulty.
     */
    @GetMapping("/category/{category}/difficulty/{difficultyLevel}")
    @Operation(summary = "Get exercises by category and difficulty")
    @ApiResponse(responseCode = "200", description = "Exercises by category and difficulty")
    public ResponseEntity<List<ExerciseDto>> getExercisesByCategoryAndDifficulty(
            @PathVariable String category, 
            @PathVariable String difficultyLevel) {
        logger.info("Getting exercises by category: {} and difficulty: {}", category, difficultyLevel);
        List<ExerciseDto> exercises = exerciseService.getExercisesByCategoryAndDifficulty(category, difficultyLevel);
        return ResponseEntity.ok(exercises);
    }
    
    /**
     * Get exercises by muscle group and equipment.
     */
    @GetMapping("/muscle-group/{muscleGroup}/equipment/{equipment}")
    @Operation(summary = "Get exercises by muscle group and equipment")
    @ApiResponse(responseCode = "200", description = "Exercises by muscle group and equipment")
    public ResponseEntity<List<ExerciseDto>> getExercisesByMuscleGroupAndEquipment(
            @PathVariable String muscleGroup, 
            @PathVariable String equipment) {
        logger.info("Getting exercises by muscle group: {} and equipment: {}", muscleGroup, equipment);
        List<ExerciseDto> exercises = exerciseService.getExercisesByMuscleGroupAndEquipment(muscleGroup, equipment);
        return ResponseEntity.ok(exercises);
    }
} 