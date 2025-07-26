package com.example.muscul_ia.service;

import com.example.muscul_ia.dto.CreateExerciseRequest;
import com.example.muscul_ia.dto.ExerciseDto;
import com.example.muscul_ia.entity.Exercise;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for Exercise operations.
 * Interface de service pour les opérations Exercise.
 * 
 * This service provides methods to manage exercises including creation,
 * retrieval, updating, and deletion operations. It also supports filtering
 * and searching capabilities for exercises by various criteria such as
 * category, muscle group, difficulty level, and equipment.
 * 
 * Ce service fournit des méthodes pour gérer les exercices incluant la création,
 * récupération, mise à jour et suppression. Il supporte également les capacités
 * de filtrage et de recherche pour les exercices selon divers critères comme
 * la catégorie, le groupe musculaire, le niveau de difficulté et l'équipement.
 * 
 * @author Muscul IA Team
 * @version 1.0
 * @since 2024-01-01
 */
public interface ExerciseService {
    
    /**
     * Create a new exercise.
     * Créer un nouvel exercice.
     * 
     * @param request - Request containing exercise data
     * @return ExerciseDto - Created exercise with all details
     * @throws RuntimeException if invalid data provided
     */
    ExerciseDto createExercise(CreateExerciseRequest request);
    
    /**
     * Get all active exercises.
     * Récupérer tous les exercices actifs.
     * 
     * @return List<ExerciseDto> - List of all active exercises
     */
    List<ExerciseDto> getAllActiveExercises();
    
    /**
     * Get an exercise by its ID.
     * Récupérer un exercice par son ID.
     * 
     * @param id - Exercise ID
     * @return Optional<ExerciseDto> - Exercise if found, empty otherwise
     */
    Optional<ExerciseDto> getExerciseById(Long id);
    
    /**
     * Get an exercise entity by its ID.
     * Récupérer une entité exercice par son ID.
     * 
     * @param id - Exercise ID
     * @return Optional<Exercise> - Exercise entity if found, empty otherwise
     */
    Optional<Exercise> getExerciseEntityById(Long id);
    
    /**
     * Update an existing exercise.
     * Mettre à jour un exercice existant.
     * 
     * @param id - Exercise ID to update
     * @param request - Updated exercise data
     * @return ExerciseDto - Updated exercise
     * @throws RuntimeException if exercise not found
     */
    ExerciseDto updateExercise(Long id, CreateExerciseRequest request);
    
    /**
     * Delete (deactivate) an exercise.
     * Supprimer (désactiver) un exercice.
     * 
     * @param id - Exercise ID to delete
     * @throws RuntimeException if exercise not found
     */
    void deleteExercise(Long id);
    
    /**
     * Search exercises by name.
     * Rechercher des exercices par nom.
     * 
     * @param name - Name to search for (case-insensitive)
     * @return List<ExerciseDto> - Matching exercises
     */
    List<ExerciseDto> searchExercisesByName(String name);
    
    /**
     * Get exercises by category.
     * Récupérer des exercices par catégorie.
     * 
     * @param category - Exercise category (e.g., "Musculation", "Cardio", "Flexibilité")
     * @return List<ExerciseDto> - Exercises in specified category
     */
    List<ExerciseDto> getExercisesByCategory(String category);
    
    /**
     * Get exercises by muscle group.
     * Récupérer des exercices par groupe musculaire.
     * 
     * @param muscleGroup - Muscle group (e.g., "Pectoraux", "Dos", "Jambes")
     * @return List<ExerciseDto> - Exercises targeting specified muscle group
     */
    List<ExerciseDto> getExercisesByMuscleGroup(String muscleGroup);
    
    /**
     * Get exercises by difficulty level.
     * Récupérer des exercices par niveau de difficulté.
     * 
     * @param difficultyLevel - Difficulty level (e.g., "Débutant", "Intermédiaire", "Avancé")
     * @return List<ExerciseDto> - Exercises with specified difficulty
     */
    List<ExerciseDto> getExercisesByDifficultyLevel(String difficultyLevel);
    
    /**
     * Get exercises by required equipment.
     * Récupérer des exercices par équipement nécessaire.
     * 
     * @param equipment - Required equipment (e.g., "Haltères", "Barre", "Poids du corps")
     * @return List<ExerciseDto> - Exercises requiring specified equipment
     */
    List<ExerciseDto> getExercisesByEquipment(String equipment);
    
    /**
     * Search exercises by description.
     * Rechercher des exercices par description.
     * 
     * @param description - Description text to search for (case-insensitive)
     * @return List<ExerciseDto> - Exercises with matching description
     */
    List<ExerciseDto> searchExercisesByDescription(String description);
    
    /**
     * Get exercises by category and difficulty level.
     * Récupérer des exercices par catégorie et niveau de difficulté.
     * 
     * @param category - Exercise category
     * @param difficultyLevel - Difficulty level
     * @return List<ExerciseDto> - Exercises matching both criteria
     */
    List<ExerciseDto> getExercisesByCategoryAndDifficulty(String category, String difficultyLevel);
    
    /**
     * Get exercises by muscle group and equipment.
     * Récupérer des exercices par groupe musculaire et équipement.
     * 
     * @param muscleGroup - Muscle group
     * @param equipment - Required equipment
     * @return List<ExerciseDto> - Exercises matching both criteria
     */
    List<ExerciseDto> getExercisesByMuscleGroupAndEquipment(String muscleGroup, String equipment);
    
    /**
     * Convert an Exercise entity to DTO.
     * Convertir une entité Exercise en DTO.
     * 
     * @param exercise - Exercise entity to convert
     * @return ExerciseDto - Converted DTO
     */
    ExerciseDto convertToDto(Exercise exercise);
    
    /**
     * Convert a list of Exercise entities to DTOs.
     * Convertir une liste d'entités Exercise en DTOs.
     * 
     * @param exercises - List of Exercise entities to convert
     * @return List<ExerciseDto> - List of converted DTOs
     */
    List<ExerciseDto> convertToDtoList(List<Exercise> exercises);
} 