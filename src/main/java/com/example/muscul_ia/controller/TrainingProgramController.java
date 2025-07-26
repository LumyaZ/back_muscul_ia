package com.example.muscul_ia.controller;

import com.example.muscul_ia.dto.CreateTrainingProgramRequest;
import com.example.muscul_ia.dto.TrainingProgramDto;
import com.example.muscul_ia.service.TrainingProgramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/training-programs")
@CrossOrigin(origins = "*")
public class TrainingProgramController {
    
    @Autowired
    private TrainingProgramService trainingProgramService;
    
    // Créer un nouveau programme d'entraînement
    @PostMapping
    public ResponseEntity<TrainingProgramDto> createTrainingProgram(
            @RequestBody CreateTrainingProgramRequest request) {
        try {
            TrainingProgramDto createdProgram = trainingProgramService.createTrainingProgram(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdProgram);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    
    // Récupérer tous les programmes actifs
    @GetMapping
    public ResponseEntity<List<TrainingProgramDto>> getAllPrograms() {
        List<TrainingProgramDto> programs = trainingProgramService.getAllActivePrograms();
        return ResponseEntity.ok(programs);
    }
    
    // Récupérer tous les programmes publics et actifs
    @GetMapping("/public")
    public ResponseEntity<List<TrainingProgramDto>> getPublicPrograms() {
        List<TrainingProgramDto> programs = trainingProgramService.getAllPublicActivePrograms();
        return ResponseEntity.ok(programs);
    }
    
    // Récupérer un programme par ID
    @GetMapping("/{id}")
    public ResponseEntity<TrainingProgramDto> getProgramById(@PathVariable Long id) {
        return trainingProgramService.getProgramById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    // Récupérer les programmes créés par un utilisateur
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TrainingProgramDto>> getProgramsByUser(@PathVariable Long userId) {
        List<TrainingProgramDto> programs = trainingProgramService.getProgramsByUser(userId);
        return ResponseEntity.ok(programs);
    }
    
    // Mettre à jour un programme
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
    
    // Supprimer un programme
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProgram(@PathVariable Long id, @RequestParam Long userId) {
        try {
            trainingProgramService.deleteProgram(id, userId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    // Rechercher des programmes par nom
    @GetMapping("/search")
    public ResponseEntity<List<TrainingProgramDto>> searchProgramsByName(@RequestParam String name) {
        List<TrainingProgramDto> programs = trainingProgramService.searchProgramsByName(name);
        return ResponseEntity.ok(programs);
    }
    
    // Rechercher des programmes publics par nom
    @GetMapping("/public/search")
    public ResponseEntity<List<TrainingProgramDto>> searchPublicProgramsByName(@RequestParam String name) {
        List<TrainingProgramDto> programs = trainingProgramService.searchPublicProgramsByName(name);
        return ResponseEntity.ok(programs);
    }
    
    // Récupérer les programmes par niveau de difficulté
    @GetMapping("/difficulty/{difficultyLevel}")
    public ResponseEntity<List<TrainingProgramDto>> getProgramsByDifficultyLevel(@PathVariable String difficultyLevel) {
        List<TrainingProgramDto> programs = trainingProgramService.getProgramsByDifficultyLevel(difficultyLevel);
        return ResponseEntity.ok(programs);
    }
    
    // Récupérer les programmes publics par niveau de difficulté
    @GetMapping("/public/difficulty/{difficultyLevel}")
    public ResponseEntity<List<TrainingProgramDto>> getPublicProgramsByDifficultyLevel(@PathVariable String difficultyLevel) {
        List<TrainingProgramDto> programs = trainingProgramService.getPublicProgramsByDifficultyLevel(difficultyLevel);
        return ResponseEntity.ok(programs);
    }
    
    // Récupérer les programmes par catégorie
    @GetMapping("/category/{category}")
    public ResponseEntity<List<TrainingProgramDto>> getProgramsByCategory(@PathVariable String category) {
        List<TrainingProgramDto> programs = trainingProgramService.getProgramsByCategory(category);
        return ResponseEntity.ok(programs);
    }
    
    // Récupérer les programmes publics par catégorie
    @GetMapping("/public/category/{category}")
    public ResponseEntity<List<TrainingProgramDto>> getPublicProgramsByCategory(@PathVariable String category) {
        List<TrainingProgramDto> programs = trainingProgramService.getPublicProgramsByCategory(category);
        return ResponseEntity.ok(programs);
    }
    
    // Récupérer les programmes par audience cible
    @GetMapping("/audience/{targetAudience}")
    public ResponseEntity<List<TrainingProgramDto>> getProgramsByTargetAudience(@PathVariable String targetAudience) {
        List<TrainingProgramDto> programs = trainingProgramService.getProgramsByTargetAudience(targetAudience);
        return ResponseEntity.ok(programs);
    }
    
    // Récupérer les programmes publics par audience cible
    @GetMapping("/public/audience/{targetAudience}")
    public ResponseEntity<List<TrainingProgramDto>> getPublicProgramsByTargetAudience(@PathVariable String targetAudience) {
        List<TrainingProgramDto> programs = trainingProgramService.getPublicProgramsByTargetAudience(targetAudience);
        return ResponseEntity.ok(programs);
    }
    
    // Récupérer les programmes par durée (en semaines)
    @GetMapping("/duration/{durationWeeks}")
    public ResponseEntity<List<TrainingProgramDto>> getProgramsByDuration(@PathVariable Integer durationWeeks) {
        List<TrainingProgramDto> programs = trainingProgramService.getProgramsByDuration(durationWeeks);
        return ResponseEntity.ok(programs);
    }
    
    // Récupérer les programmes publics par durée (en semaines)
    @GetMapping("/public/duration/{durationWeeks}")
    public ResponseEntity<List<TrainingProgramDto>> getPublicProgramsByDuration(@PathVariable Integer durationWeeks) {
        List<TrainingProgramDto> programs = trainingProgramService.getPublicProgramsByDuration(durationWeeks);
        return ResponseEntity.ok(programs);
    }
    
    // Récupérer les programmes par nombre de sessions par semaine
    @GetMapping("/sessions/{sessionsPerWeek}")
    public ResponseEntity<List<TrainingProgramDto>> getProgramsBySessionsPerWeek(@PathVariable Integer sessionsPerWeek) {
        List<TrainingProgramDto> programs = trainingProgramService.getProgramsBySessionsPerWeek(sessionsPerWeek);
        return ResponseEntity.ok(programs);
    }
    
    // Récupérer les programmes publics par nombre de sessions par semaine
    @GetMapping("/public/sessions/{sessionsPerWeek}")
    public ResponseEntity<List<TrainingProgramDto>> getPublicProgramsBySessionsPerWeek(@PathVariable Integer sessionsPerWeek) {
        List<TrainingProgramDto> programs = trainingProgramService.getPublicProgramsBySessionsPerWeek(sessionsPerWeek);
        return ResponseEntity.ok(programs);
    }
    
    // Rechercher des programmes par description
    @GetMapping("/search/description")
    public ResponseEntity<List<TrainingProgramDto>> searchProgramsByDescription(@RequestParam String description) {
        List<TrainingProgramDto> programs = trainingProgramService.searchProgramsByDescription(description);
        return ResponseEntity.ok(programs);
    }
    
    // Rechercher des programmes publics par description
    @GetMapping("/public/search/description")
    public ResponseEntity<List<TrainingProgramDto>> searchPublicProgramsByDescription(@RequestParam String description) {
        List<TrainingProgramDto> programs = trainingProgramService.searchPublicProgramsByDescription(description);
        return ResponseEntity.ok(programs);
    }
    
    // Récupérer les programmes par catégorie et niveau de difficulté
    @GetMapping("/category/{category}/difficulty/{difficultyLevel}")
    public ResponseEntity<List<TrainingProgramDto>> getProgramsByCategoryAndDifficulty(
            @PathVariable String category, 
            @PathVariable String difficultyLevel) {
        List<TrainingProgramDto> programs = trainingProgramService.getProgramsByCategoryAndDifficulty(category, difficultyLevel);
        return ResponseEntity.ok(programs);
    }
    
    // Récupérer les programmes publics par catégorie et niveau de difficulté
    @GetMapping("/public/category/{category}/difficulty/{difficultyLevel}")
    public ResponseEntity<List<TrainingProgramDto>> getPublicProgramsByCategoryAndDifficulty(
            @PathVariable String category, 
            @PathVariable String difficultyLevel) {
        List<TrainingProgramDto> programs = trainingProgramService.getPublicProgramsByCategoryAndDifficulty(category, difficultyLevel);
        return ResponseEntity.ok(programs);
    }
} 