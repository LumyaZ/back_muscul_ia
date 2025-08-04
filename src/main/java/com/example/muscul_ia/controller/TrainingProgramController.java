package com.example.muscul_ia.controller;

import com.example.muscul_ia.dto.CreateTrainingProgramRequest;
import com.example.muscul_ia.dto.TrainingProgramDto;
import com.example.muscul_ia.service.TrainingProgramService;
import com.example.muscul_ia.service.UserTrainingProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Training program controller for managing training programs.
 * Contrôleur de programmes d'entraînement pour gérer les programmes d'entraînement.
 */
@RestController
@RequestMapping("/api/training-programs")
@CrossOrigin(origins = "*")
public class TrainingProgramController {
    
    @Autowired
    private TrainingProgramService trainingProgramService;
    
    @Autowired
    private UserTrainingProgramService userTrainingProgramService;
    
    /**
     * Create a new training program and link user automatically.
     * Créer un nouveau programme d'entraînement et lier l'utilisateur automatiquement.
     */
    @PostMapping
    public ResponseEntity<TrainingProgramDto> createTrainingProgram(
            @RequestBody CreateTrainingProgramRequest request,
            @RequestParam Long userId) {
        try {
            TrainingProgramDto createdProgram = trainingProgramService.createTrainingProgram(request, userId);
            userTrainingProgramService.subscribeUserToProgram(userId, createdProgram.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(createdProgram);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    /**
     * Get all active programs.
     * Récupérer tous les programmes actifs.
     */
    @GetMapping
    public ResponseEntity<List<TrainingProgramDto>> getAllPrograms() {
        List<TrainingProgramDto> programs = trainingProgramService.getAllActivePrograms();
        return ResponseEntity.ok(programs);
    }
    
    /**
     * Get all public and active programs.
     * Récupérer tous les programmes publics et actifs.
     */
    @GetMapping("/public")
    public ResponseEntity<List<TrainingProgramDto>> getPublicPrograms() {
        List<TrainingProgramDto> programs = trainingProgramService.getAllPublicActivePrograms();
        return ResponseEntity.ok(programs);
    }
    
    /**
     * Get a program by ID.
     * Récupérer un programme par ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TrainingProgramDto> getProgramById(@PathVariable Long id) {
        return trainingProgramService.getProgramById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * Get programs created by a user.
     * Récupérer les programmes créés par un utilisateur.
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TrainingProgramDto>> getProgramsByUser(@PathVariable Long userId) {
        List<TrainingProgramDto> programs = trainingProgramService.getProgramsByUser(userId);
        return ResponseEntity.ok(programs);
    }
    
    /**
     * Update a program.
     * Mettre à jour un programme.
     */
    @PutMapping("/{id}")
    public ResponseEntity<TrainingProgramDto> updateProgram(
            @PathVariable Long id, 
            @RequestBody CreateTrainingProgramRequest request,
            @RequestParam Long userId) {
        try {
            TrainingProgramDto updatedProgram = trainingProgramService.updateProgram(id, request, userId);
            return ResponseEntity.ok(updatedProgram);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Delete a program.
     * Supprimer un programme.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProgram(@PathVariable Long id, @RequestParam Long userId) {
        try {
            trainingProgramService.deleteProgram(id, userId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * Search programs by name.
     * Rechercher des programmes par nom.
     */
    @GetMapping("/search")
    public ResponseEntity<List<TrainingProgramDto>> searchProgramsByName(@RequestParam String name) {
        List<TrainingProgramDto> programs = trainingProgramService.searchProgramsByName(name);
        return ResponseEntity.ok(programs);
    }
    
    /**
     * Search public programs by name.
     * Rechercher des programmes publics par nom.
     */
    @GetMapping("/public/search")
    public ResponseEntity<List<TrainingProgramDto>> searchPublicProgramsByName(@RequestParam String name) {
        List<TrainingProgramDto> programs = trainingProgramService.searchPublicProgramsByName(name);
        return ResponseEntity.ok(programs);
    }
    
    /**
     * Get programs by difficulty level.
     * Récupérer les programmes par niveau de difficulté.
     */
    @GetMapping("/difficulty/{difficultyLevel}")
    public ResponseEntity<List<TrainingProgramDto>> getProgramsByDifficultyLevel(@PathVariable String difficultyLevel) {
        List<TrainingProgramDto> programs = trainingProgramService.getProgramsByDifficultyLevel(difficultyLevel);
        return ResponseEntity.ok(programs);
    }
    
    /**
     * Get public programs by difficulty level.
     * Récupérer les programmes publics par niveau de difficulté.
     */
    @GetMapping("/public/difficulty/{difficultyLevel}")
    public ResponseEntity<List<TrainingProgramDto>> getPublicProgramsByDifficultyLevel(@PathVariable String difficultyLevel) {
        List<TrainingProgramDto> programs = trainingProgramService.getPublicProgramsByDifficultyLevel(difficultyLevel);
        return ResponseEntity.ok(programs);
    }
    
    /**
     * Get programs by category.
     * Récupérer les programmes par catégorie.
     */
    @GetMapping("/category/{category}")
    public ResponseEntity<List<TrainingProgramDto>> getProgramsByCategory(@PathVariable String category) {
        List<TrainingProgramDto> programs = trainingProgramService.getProgramsByCategory(category);
        return ResponseEntity.ok(programs);
    }
    
    /**
     * Get public programs by category.
     * Récupérer les programmes publics par catégorie.
     */
    @GetMapping("/public/category/{category}")
    public ResponseEntity<List<TrainingProgramDto>> getPublicProgramsByCategory(@PathVariable String category) {
        List<TrainingProgramDto> programs = trainingProgramService.getPublicProgramsByCategory(category);
        return ResponseEntity.ok(programs);
    }
    
    /**
     * Get programs by target audience.
     * Récupérer les programmes par public cible.
     */
    @GetMapping("/audience/{targetAudience}")
    public ResponseEntity<List<TrainingProgramDto>> getProgramsByTargetAudience(@PathVariable String targetAudience) {
        List<TrainingProgramDto> programs = trainingProgramService.getProgramsByTargetAudience(targetAudience);
        return ResponseEntity.ok(programs);
    }
    
    /**
     * Get public programs by target audience.
     * Récupérer les programmes publics par public cible.
     */
    @GetMapping("/public/audience/{targetAudience}")
    public ResponseEntity<List<TrainingProgramDto>> getPublicProgramsByTargetAudience(@PathVariable String targetAudience) {
        List<TrainingProgramDto> programs = trainingProgramService.getPublicProgramsByTargetAudience(targetAudience);
        return ResponseEntity.ok(programs);
    }
    
    /**
     * Search programs by description.
     * Rechercher des programmes par description.
     */
    @GetMapping("/search/description")
    public ResponseEntity<List<TrainingProgramDto>> searchProgramsByDescription(@RequestParam String description) {
        List<TrainingProgramDto> programs = trainingProgramService.searchProgramsByDescription(description);
        return ResponseEntity.ok(programs);
    }
    
    /**
     * Search public programs by description.
     * Rechercher des programmes publics par description.
     */
    @GetMapping("/public/search/description")
    public ResponseEntity<List<TrainingProgramDto>> searchPublicProgramsByDescription(@RequestParam String description) {
        List<TrainingProgramDto> programs = trainingProgramService.searchPublicProgramsByDescription(description);
        return ResponseEntity.ok(programs);
    }
    
    /**
     * Get programs by category and difficulty.
     * Récupérer les programmes par catégorie et difficulté.
     */
    @GetMapping("/category/{category}/difficulty/{difficultyLevel}")
    public ResponseEntity<List<TrainingProgramDto>> getProgramsByCategoryAndDifficulty(
            @PathVariable String category, 
            @PathVariable String difficultyLevel) {
        List<TrainingProgramDto> programs = trainingProgramService.getProgramsByCategoryAndDifficulty(category, difficultyLevel);
        return ResponseEntity.ok(programs);
    }
    
    /**
     * Get public programs by category and difficulty.
     * Récupérer les programmes publics par catégorie et difficulté.
     */
    @GetMapping("/public/category/{category}/difficulty/{difficultyLevel}")
    public ResponseEntity<List<TrainingProgramDto>> getPublicProgramsByCategoryAndDifficulty(
            @PathVariable String category, 
            @PathVariable String difficultyLevel) {
        List<TrainingProgramDto> programs = trainingProgramService.getPublicProgramsByCategoryAndDifficulty(category, difficultyLevel);
        return ResponseEntity.ok(programs);
    }
} 