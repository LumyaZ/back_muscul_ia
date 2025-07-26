package com.example.muscul_ia.controller;

import com.example.muscul_ia.dto.ProgramExerciseDto;
import com.example.muscul_ia.dto.CreateProgramExerciseRequest;
import com.example.muscul_ia.service.ProgramExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/program-exercises")
@CrossOrigin(origins = "*")
public class ProgramExerciseController {
    
    @Autowired
    private ProgramExerciseService programExerciseService;
    
    // Récupérer tous les exercices d'un programme par son ID
    @GetMapping("/program/{programId}")
    public ResponseEntity<List<ProgramExerciseDto>> getExercisesByProgramId(@PathVariable Long programId) {
        List<ProgramExerciseDto> exercises = programExerciseService.getExercisesByProgramId(programId);
        return ResponseEntity.ok(exercises);
    }
    
    // Récupérer un exercice de programme par son ID
    @GetMapping("/{id}")
    public ResponseEntity<ProgramExerciseDto> getProgramExerciseById(@PathVariable Long id) {
        return programExerciseService.getProgramExerciseById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * Add an exercise to a training program.
     * Ajouter un exercice à un programme d'entraînement.
     * 
     * This endpoint allows users to add a new exercise to an existing
     * training program with specific parameters like sets, reps,
     * duration, and rest periods.
     * 
     * Cet endpoint permet aux utilisateurs d'ajouter un nouvel exercice
     * à un programme d'entraînement existant avec des paramètres
     * spécifiques comme les séries, répétitions, durée et périodes de repos.
     * 
     * @param programId - ID of the training program
     * @param request - Exercise data to add to the program
     * @return Created program exercise with HTTP 201 status
     */
    @PostMapping("/program/{programId}")
    public ResponseEntity<ProgramExerciseDto> addExerciseToProgram(
            @PathVariable Long programId,
            @RequestBody CreateProgramExerciseRequest request) {
        
        try {
            ProgramExerciseDto createdExercise = programExerciseService.addExerciseToProgram(programId, request);
            return ResponseEntity.status(201).body(createdExercise);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
} 