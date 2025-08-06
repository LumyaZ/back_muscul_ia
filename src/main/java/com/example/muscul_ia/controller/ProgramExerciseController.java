package com.example.muscul_ia.controller;

import com.example.muscul_ia.dto.CreateProgramExerciseRequest;
import com.example.muscul_ia.dto.ProgramExerciseDto;
import com.example.muscul_ia.service.ProgramExerciseService;
import com.example.muscul_ia.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Program exercise controller for managing program-exercise relationships.
 * Contrôleur de programme d'exercices pour gérer les relations programme-exercice.
 */
@RestController
@RequestMapping("/api/program-exercises")
@CrossOrigin(origins = "*")
public class ProgramExerciseController {
    
    @Autowired
    private ProgramExerciseService programExerciseService;
    
    @Autowired
    private UserService userService;
    
    /**
     * Get exercises by program ID.
     * Récupérer les exercices par ID de programme.
     */
    @GetMapping("/program/{programId}")
    public ResponseEntity<List<ProgramExerciseDto>> getExercisesByProgramId(
            @PathVariable Long programId,
            Authentication authentication) {
        try {
            userService.getCurrentUser(authentication);
            
            List<ProgramExerciseDto> exercises = programExerciseService.getExercisesByProgramId(programId);
            return ResponseEntity.ok(exercises);
        } catch (Exception e) {
            return ResponseEntity.status(403).build();
        }
    }
    
    /**
     * Get a program exercise by ID.
     * Récupérer un exercice de programme par ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProgramExerciseDto> getProgramExerciseById(
            @PathVariable Long id,
            Authentication authentication) {
        try {
            userService.getCurrentUser(authentication);
            
            return programExerciseService.getProgramExerciseById(id)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(403).build();
        }
    }
    
    /**
     * Add an exercise to a program.
     * Ajouter un exercice à un programme.
     */
    @PostMapping("/program/{programId}")
    public ResponseEntity<ProgramExerciseDto> addExerciseToProgram(
            @PathVariable Long programId,
            @RequestBody CreateProgramExerciseRequest request,
            Authentication authentication) {
        try {
            // Vérifier que l'utilisateur est authentifié
            userService.getCurrentUser(authentication);
            
            ProgramExerciseDto addedExercise = programExerciseService.addExerciseToProgram(programId, request);
            return ResponseEntity.ok(addedExercise);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(403).build();
        }
    }
} 