package com.example.muscul_ia.service;

import com.example.muscul_ia.dto.ProgramExerciseDto;

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
} 