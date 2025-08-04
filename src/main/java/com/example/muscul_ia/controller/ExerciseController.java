package com.example.muscul_ia.controller;

import com.example.muscul_ia.dto.CreateExerciseRequest;
import com.example.muscul_ia.dto.ExerciseDto;
import com.example.muscul_ia.service.ExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Exercise controller for managing exercise operations.
 * Contrôleur d'exercices pour gérer les opérations d'exercices.
 */
@RestController
@RequestMapping("/api/exercises")
@CrossOrigin(origins = "*")
public class ExerciseController {
    
    @Autowired
    private ExerciseService exerciseService;
    
    /**
     * Create a new exercise.
     * Créer un nouvel exercice.
     */
    @PostMapping
    public ResponseEntity<ExerciseDto> createExercise(@RequestBody CreateExerciseRequest request) {
        try {
            ExerciseDto createdExercise = exerciseService.createExercise(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdExercise);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    /**
     * Get all active exercises.
     * Récupérer tous les exercices actifs.
     */
    @GetMapping
    public ResponseEntity<List<ExerciseDto>> getAllExercises() {
        List<ExerciseDto> exercises = exerciseService.getAllActiveExercises();
        return ResponseEntity.ok(exercises);
    }
    
    /**
     * Get an exercise by ID.
     * Récupérer un exercice par ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ExerciseDto> getExerciseById(@PathVariable Long id) {
        return exerciseService.getExerciseById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * Update an exercise.
     * Mettre à jour un exercice.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ExerciseDto> updateExercise(@PathVariable Long id, @RequestBody CreateExerciseRequest request) {
        try {
            ExerciseDto updatedExercise = exerciseService.updateExercise(id, request);
            return ResponseEntity.ok(updatedExercise);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Delete an exercise.
     * Supprimer un exercice.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExercise(@PathVariable Long id) {
        try {
            exerciseService.deleteExercise(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Search exercises by name.
     * Rechercher des exercices par nom.
     */
    @GetMapping("/search")
    public ResponseEntity<List<ExerciseDto>> searchExercisesByName(@RequestParam String name) {
        List<ExerciseDto> exercises = exerciseService.searchExercisesByName(name);
        return ResponseEntity.ok(exercises);
    }
    
    /**
     * Get exercises by category.
     * Récupérer les exercices par catégorie.
     */
    @GetMapping("/category/{category}")
    public ResponseEntity<List<ExerciseDto>> getExercisesByCategory(@PathVariable String category) {
        List<ExerciseDto> exercises = exerciseService.getExercisesByCategory(category);
        return ResponseEntity.ok(exercises);
    }
    
    /**
     * Get exercises by muscle group.
     * Récupérer les exercices par groupe musculaire.
     */
    @GetMapping("/muscle-group/{muscleGroup}")
    public ResponseEntity<List<ExerciseDto>> getExercisesByMuscleGroup(@PathVariable String muscleGroup) {
        List<ExerciseDto> exercises = exerciseService.getExercisesByMuscleGroup(muscleGroup);
        return ResponseEntity.ok(exercises);
    }
    
    /**
     * Get exercises by difficulty level.
     * Récupérer les exercices par niveau de difficulté.
     */
    @GetMapping("/difficulty/{difficultyLevel}")
    public ResponseEntity<List<ExerciseDto>> getExercisesByDifficultyLevel(@PathVariable String difficultyLevel) {
        List<ExerciseDto> exercises = exerciseService.getExercisesByDifficultyLevel(difficultyLevel);
        return ResponseEntity.ok(exercises);
    }
    
    /**
     * Get exercises by equipment.
     * Récupérer les exercices par équipement.
     */
    @GetMapping("/equipment/{equipment}")
    public ResponseEntity<List<ExerciseDto>> getExercisesByEquipment(@PathVariable String equipment) {
        List<ExerciseDto> exercises = exerciseService.getExercisesByEquipment(equipment);
        return ResponseEntity.ok(exercises);
    }
    
    /**
     * Search exercises by description.
     * Rechercher des exercices par description.
     */
    @GetMapping("/search/description")
    public ResponseEntity<List<ExerciseDto>> searchExercisesByDescription(@RequestParam String description) {
        List<ExerciseDto> exercises = exerciseService.searchExercisesByDescription(description);
        return ResponseEntity.ok(exercises);
    }
    
    /**
     * Get exercises by category and difficulty.
     * Récupérer les exercices par catégorie et difficulté.
     */
    @GetMapping("/category/{category}/difficulty/{difficultyLevel}")
    public ResponseEntity<List<ExerciseDto>> getExercisesByCategoryAndDifficulty(
            @PathVariable String category, 
            @PathVariable String difficultyLevel) {
        List<ExerciseDto> exercises = exerciseService.getExercisesByCategoryAndDifficulty(category, difficultyLevel);
        return ResponseEntity.ok(exercises);
    }
    
    /**
     * Get exercises by muscle group and equipment.
     * Récupérer les exercices par groupe musculaire et équipement.
     */
    @GetMapping("/muscle-group/{muscleGroup}/equipment/{equipment}")
    public ResponseEntity<List<ExerciseDto>> getExercisesByMuscleGroupAndEquipment(
            @PathVariable String muscleGroup, 
            @PathVariable String equipment) {
        List<ExerciseDto> exercises = exerciseService.getExercisesByMuscleGroupAndEquipment(muscleGroup, equipment);
        return ResponseEntity.ok(exercises);
    }
} 