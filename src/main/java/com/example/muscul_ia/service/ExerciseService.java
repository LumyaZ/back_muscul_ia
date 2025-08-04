package com.example.muscul_ia.service;

import com.example.muscul_ia.dto.CreateExerciseRequest;
import com.example.muscul_ia.dto.ExerciseDto;
import com.example.muscul_ia.entity.Exercise;

import java.util.List;
import java.util.Optional;

/**
 * Exercise service for managing exercise business logic.
 * Service d'exercices pour gérer la logique métier d'exercices.
 */
public interface ExerciseService {
    
    /**
     * Create a new exercise.
     * Créer un nouvel exercice.
     */
    ExerciseDto createExercise(CreateExerciseRequest request);
    
    /**
     * Get all active exercises.
     * Récupérer tous les exercices actifs.
     */
    List<ExerciseDto> getAllActiveExercises();
    
    /**
     * Get an exercise by its ID.
     * Récupérer un exercice par son ID.
     */
    Optional<ExerciseDto> getExerciseById(Long id);
    
    /**
     * Update an existing exercise.
     * Mettre à jour un exercice existant.
     */
    ExerciseDto updateExercise(Long id, CreateExerciseRequest request);
    
    /**
     * Delete (deactivate) an exercise.
     * Supprimer (désactiver) un exercice.
     */
    void deleteExercise(Long id);
    
    /**
     * Search exercises by name.
     * Rechercher des exercices par nom.
     */
    List<ExerciseDto> searchExercisesByName(String name);
    
    /**
     * Get exercises by category.
     * Récupérer des exercices par catégorie.
     */
    List<ExerciseDto> getExercisesByCategory(String category);
    
    /**
     * Get exercises by muscle group.
     * Récupérer des exercices par groupe musculaire.
     */
    List<ExerciseDto> getExercisesByMuscleGroup(String muscleGroup);
    
    /**
     * Get exercises by difficulty level.
     * Récupérer des exercices par niveau de difficulté.
     */
    List<ExerciseDto> getExercisesByDifficultyLevel(String difficultyLevel);
    
    /**
     * Get exercises by required equipment.
     * Récupérer des exercices par équipement nécessaire.
     */
    List<ExerciseDto> getExercisesByEquipment(String equipment);
    
    /**
     * Search exercises by description.
     * Rechercher des exercices par description.
     */
    List<ExerciseDto> searchExercisesByDescription(String description);
    
    /**
     * Get exercises by category and difficulty level.
     * Récupérer des exercices par catégorie et niveau de difficulté.
     */
    List<ExerciseDto> getExercisesByCategoryAndDifficulty(String category, String difficultyLevel);
    
    /**
     * Get exercises by muscle group and equipment.
     * Récupérer des exercices par groupe musculaire et équipement.
     */
    List<ExerciseDto> getExercisesByMuscleGroupAndEquipment(String muscleGroup, String equipment);
    
    /**
     * Convert an Exercise entity to DTO.
     * Convertir une entité Exercise en DTO.
     */
    ExerciseDto convertToDto(Exercise exercise);
    
    /**
     * Convert a list of Exercise entities to DTOs.
     * Convertir une liste d'entités Exercise en DTOs.
     */
    List<ExerciseDto> convertToDtoList(List<Exercise> exercises);
} 