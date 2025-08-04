package com.example.muscul_ia.service;

import com.example.muscul_ia.dto.ProgramExerciseDto;
import com.example.muscul_ia.dto.CreateProgramExerciseRequest;

import java.util.List;
import java.util.Optional;

/**
 * Program exercise service for managing program-exercise relationship business logic.
 * Service d'exercices de programme pour gérer la logique métier de relation programme-exercice.
 */
public interface ProgramExerciseService {
    
    /**
     * Get exercises by program ID.
     * Récupérer les exercices par ID de programme.
     */
    List<ProgramExerciseDto> getExercisesByProgramId(Long programId);
    
    /**
     * Get program exercise by ID.
     * Récupérer un exercice de programme par ID.
     */
    Optional<ProgramExerciseDto> getProgramExerciseById(Long id);
    
    /**
     * Add an exercise to a training program.
     * Ajouter un exercice à un programme d'entraînement.
     */
    ProgramExerciseDto addExerciseToProgram(Long programId, CreateProgramExerciseRequest request);
} 