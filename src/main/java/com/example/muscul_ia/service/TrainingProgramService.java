package com.example.muscul_ia.service;

import com.example.muscul_ia.dto.CreateTrainingProgramRequest;
import com.example.muscul_ia.dto.TrainingProgramDto;
import com.example.muscul_ia.entity.TrainingProgram;

import java.util.List;
import java.util.Optional;

/**
 * Training program service for managing training program business logic.
 * Service de programmes d'entraînement pour gérer la logique métier de programmes d'entraînement.
 */
public interface TrainingProgramService {
    
    /**
     * Create a new training program.
     * Créer un nouveau programme d'entraînement.
     */
    TrainingProgramDto createTrainingProgram(CreateTrainingProgramRequest request, Long userId);
    
    /**
     * Get all active training programs.
     * Récupérer tous les programmes d'entraînement actifs.
     */
    List<TrainingProgramDto> getAllActivePrograms();
    
    /**
     * Get all public and active training programs.
     * Récupérer tous les programmes d'entraînement publics et actifs.
     */
    List<TrainingProgramDto> getAllPublicActivePrograms();
    
    /**
     * Get a training program by its ID.
     * Récupérer un programme d'entraînement par son ID.
     */
    Optional<TrainingProgramDto> getProgramById(Long id);
    
    /**
     * Get all training programs created by a specific user.
     * Récupérer tous les programmes d'entraînement créés par un utilisateur spécifique.
     */
    List<TrainingProgramDto> getProgramsByUser(Long userId);
    
    /**
     * Update an existing training program.
     * Mettre à jour un programme d'entraînement existant.
     */
    TrainingProgramDto updateProgram(Long id, CreateTrainingProgramRequest request, Long userId);
    
    /**
     * Delete (deactivate) a training program.
     * Supprimer (désactiver) un programme d'entraînement.
     */
    void deleteProgram(Long id, Long userId);
    
    /**
     * Search training programs by name.
     * Rechercher des programmes d'entraînement par nom.
     */
    List<TrainingProgramDto> searchProgramsByName(String name);
    
    /**
     * Search public training programs by name.
     * Rechercher des programmes d'entraînement publics par nom.
     */
    List<TrainingProgramDto> searchPublicProgramsByName(String name);
    
    /**
     * Get training programs by difficulty level.
     * Récupérer des programmes d'entraînement par niveau de difficulté.
     */
    List<TrainingProgramDto> getProgramsByDifficultyLevel(String difficultyLevel);
    
    /**
     * Get public training programs by difficulty level.
     * Récupérer des programmes d'entraînement publics par niveau de difficulté.
     */
    List<TrainingProgramDto> getPublicProgramsByDifficultyLevel(String difficultyLevel);
    
    /**
     * Get training programs by category.
     * Récupérer des programmes d'entraînement par catégorie.
     */
    List<TrainingProgramDto> getProgramsByCategory(String category);
    
    /**
     * Get public training programs by category.
     * Récupérer des programmes d'entraînement publics par catégorie.
     */
    List<TrainingProgramDto> getPublicProgramsByCategory(String category);
    
    /**
     * Get training programs by target audience.
     * Récupérer des programmes d'entraînement par audience cible.
     */
    List<TrainingProgramDto> getProgramsByTargetAudience(String targetAudience);
    
    /**
     * Get public training programs by target audience.
     * Récupérer des programmes d'entraînement publics par audience cible.
     */
    List<TrainingProgramDto> getPublicProgramsByTargetAudience(String targetAudience);
    
    /**
     * Search training programs by description.
     * Rechercher des programmes d'entraînement par description.
     */
    List<TrainingProgramDto> searchProgramsByDescription(String description);
    
    /**
     * Search public training programs by description.
     * Rechercher des programmes d'entraînement publics par description.
     */
    List<TrainingProgramDto> searchPublicProgramsByDescription(String description);
    
    /**
     * Get training programs by category and difficulty level.
     * Récupérer des programmes d'entraînement par catégorie et niveau de difficulté.
     */
    List<TrainingProgramDto> getProgramsByCategoryAndDifficulty(String category, String difficultyLevel);
    
    /**
     * Get public training programs by category and difficulty level.
     * Récupérer des programmes d'entraînement publics par catégorie et niveau de difficulté.
     */
    List<TrainingProgramDto> getPublicProgramsByCategoryAndDifficulty(String category, String difficultyLevel);
    
    /**
     * Convert a TrainingProgram entity to DTO.
     * Convertir une entité TrainingProgram en DTO.
     */
    TrainingProgramDto convertToDto(TrainingProgram program);
    
    /**
     * Convert a list of TrainingProgram entities to DTOs.
     * Convertir une liste d'entités TrainingProgram en DTOs.
     */
    List<TrainingProgramDto> convertToDtoList(List<TrainingProgram> programs);
} 