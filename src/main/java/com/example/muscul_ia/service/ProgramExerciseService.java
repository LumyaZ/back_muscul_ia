package com.example.muscul_ia.service;

import com.example.muscul_ia.dto.ProgramExerciseDto;
import com.example.muscul_ia.dto.CreateProgramExerciseRequest;

import java.util.List;
import java.util.Optional;

public interface ProgramExerciseService {
    
    /**
     * Récupérer tous les exercices d'un programme par son ID
     * @param programId L'ID du programme
     * @return Liste des exercices du programme
     */
    List<ProgramExerciseDto> getExercisesByProgramId(Long programId);
    
    /**
     * Récupérer un exercice de programme par son ID
     * @param id L'ID de l'exercice de programme
     * @return L'exercice de programme s'il existe
     */
    Optional<ProgramExerciseDto> getProgramExerciseById(Long id);
    
    /**
     * Add an exercise to a training program.
     * Ajouter un exercice à un programme d'entraînement.
     * 
     * This method creates a new program exercise by associating an existing
     * exercise with a training program and setting specific parameters
     * like sets, reps, duration, and rest periods.
     * 
     * Cette méthode crée un nouvel exercice de programme en associant
     * un exercice existant à un programme d'entraînement et en définissant
     * des paramètres spécifiques comme les séries, répétitions, durée
     * et périodes de repos.
     * 
     * @param programId - ID of the training program
     * @param request - Exercise data to add to the program
     * @return Created program exercise DTO
     * @throws IllegalArgumentException if program or exercise doesn't exist
     * @throws RuntimeException if database operation fails
     */
    ProgramExerciseDto addExerciseToProgram(Long programId, CreateProgramExerciseRequest request);
} 