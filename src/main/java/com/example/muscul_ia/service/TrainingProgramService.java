package com.example.muscul_ia.service;

import com.example.muscul_ia.dto.CreateTrainingProgramRequest;
import com.example.muscul_ia.dto.TrainingProgramDto;
import com.example.muscul_ia.entity.TrainingProgram;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for TrainingProgram operations.
 * Interface de service pour les opérations TrainingProgram.
 * 
 * This service provides methods to manage training programs including creation,
 * retrieval, updating, and deletion operations. It also supports filtering
 * and searching capabilities for both public and private programs.
 * 
 * Ce service fournit des méthodes pour gérer les programmes d'entraînement
 * incluant la création, récupération, mise à jour et suppression. Il supporte
 * également les capacités de filtrage et de recherche pour les programmes
 * publics et privés.
 * 
 * @author Muscul IA Team
 * @version 1.0
 * @since 2024-01-01
 */
public interface TrainingProgramService {
    
    /**
     * Create a new training program.
     * Créer un nouveau programme d'entraînement.
     * 
     * @param request - Request containing program data and exercises
     * @param userId - ID of the user creating the program
     * @return TrainingProgramDto - Created program with all details
     * @throws RuntimeException if user not found or invalid data
     */
    TrainingProgramDto createTrainingProgram(CreateTrainingProgramRequest request, Long userId);

    /**
     * Create a new training program without exercises.
     * Créer un nouveau programme d'entraînement sans exercices.
     * 
     * This method creates a basic training program structure that can
     * be populated with exercises later.
     * 
     * Cette méthode crée une structure de programme d'entraînement de base
     * qui peut être remplie d'exercices plus tard.
     * 
     * @param request - Request containing basic program data
     * @return TrainingProgramDto - Created program
     * @throws RuntimeException if invalid data
     */
    TrainingProgramDto createTrainingProgram(CreateTrainingProgramRequest request);
    
    /**
     * Get all active training programs.
     * Récupérer tous les programmes d'entraînement actifs.
     * 
     * @return List<TrainingProgramDto> - List of all active programs
     */
    List<TrainingProgramDto> getAllActivePrograms();
    
    /**
     * Get all public and active training programs.
     * Récupérer tous les programmes d'entraînement publics et actifs.
     * 
     * @return List<TrainingProgramDto> - List of public active programs
     */
    List<TrainingProgramDto> getAllPublicActivePrograms();
    
    /**
     * Get a training program by its ID.
     * Récupérer un programme d'entraînement par son ID.
     * 
     * @param id - Program ID
     * @return Optional<TrainingProgramDto> - Program if found, empty otherwise
     */
    Optional<TrainingProgramDto> getProgramById(Long id);
    
    /**
     * Get a training program entity by its ID.
     * Récupérer une entité programme d'entraînement par son ID.
     * 
     * @param id - Program ID
     * @return Optional<TrainingProgram> - Program entity if found, empty otherwise
     */
    Optional<TrainingProgram> getProgramEntityById(Long id);
    
    /**
     * Get all training programs created by a specific user.
     * Récupérer tous les programmes d'entraînement créés par un utilisateur spécifique.
     * 
     * @param userId - User ID
     * @return List<TrainingProgramDto> - List of user's programs
     */
    List<TrainingProgramDto> getProgramsByUser(Long userId);
    
    /**
     * Update an existing training program.
     * Mettre à jour un programme d'entraînement existant.
     * 
     * @param id - Program ID to update
     * @param request - Updated program data
     * @param userId - ID of the user performing the update
     * @return TrainingProgramDto - Updated program
     * @throws RuntimeException if program not found or user not authorized
     */
    TrainingProgramDto updateProgram(Long id, CreateTrainingProgramRequest request, Long userId);
    
    /**
     * Delete (deactivate) a training program.
     * Supprimer (désactiver) un programme d'entraînement.
     * 
     * @param id - Program ID to delete
     * @param userId - ID of the user performing the deletion
     * @throws RuntimeException if program not found or user not authorized
     */
    void deleteProgram(Long id, Long userId);
    
    /**
     * Search training programs by name.
     * Rechercher des programmes d'entraînement par nom.
     * 
     * @param name - Name to search for (case-insensitive)
     * @return List<TrainingProgramDto> - Matching programs
     */
    List<TrainingProgramDto> searchProgramsByName(String name);
    
    /**
     * Search public training programs by name.
     * Rechercher des programmes d'entraînement publics par nom.
     * 
     * @param name - Name to search for (case-insensitive)
     * @return List<TrainingProgramDto> - Matching public programs
     */
    List<TrainingProgramDto> searchPublicProgramsByName(String name);
    
    /**
     * Get training programs by difficulty level.
     * Récupérer des programmes d'entraînement par niveau de difficulté.
     * 
     * @param difficultyLevel - Difficulty level (e.g., "Débutant", "Intermédiaire", "Avancé")
     * @return List<TrainingProgramDto> - Programs with specified difficulty
     */
    List<TrainingProgramDto> getProgramsByDifficultyLevel(String difficultyLevel);
    
    /**
     * Get public training programs by difficulty level.
     * Récupérer des programmes d'entraînement publics par niveau de difficulté.
     * 
     * @param difficultyLevel - Difficulty level (e.g., "Débutant", "Intermédiaire", "Avancé")
     * @return List<TrainingProgramDto> - Public programs with specified difficulty
     */
    List<TrainingProgramDto> getPublicProgramsByDifficultyLevel(String difficultyLevel);
    
    /**
     * Get training programs by category.
     * Récupérer des programmes d'entraînement par catégorie.
     * 
     * @param category - Program category (e.g., "Musculation", "Cardio", "Flexibilité")
     * @return List<TrainingProgramDto> - Programs in specified category
     */
    List<TrainingProgramDto> getProgramsByCategory(String category);
    
    /**
     * Get public training programs by category.
     * Récupérer des programmes d'entraînement publics par catégorie.
     * 
     * @param category - Program category (e.g., "Musculation", "Cardio", "Flexibilité")
     * @return List<TrainingProgramDto> - Public programs in specified category
     */
    List<TrainingProgramDto> getPublicProgramsByCategory(String category);
    
    /**
     * Get training programs by target audience.
     * Récupérer des programmes d'entraînement par audience cible.
     * 
     * @param targetAudience - Target audience (e.g., "Débutants", "Intermédiaires", "Avancés")
     * @return List<TrainingProgramDto> - Programs for specified audience
     */
    List<TrainingProgramDto> getProgramsByTargetAudience(String targetAudience);
    
    /**
     * Get public training programs by target audience.
     * Récupérer des programmes d'entraînement publics par audience cible.
     * 
     * @param targetAudience - Target audience (e.g., "Débutants", "Intermédiaires", "Avancés")
     * @return List<TrainingProgramDto> - Public programs for specified audience
     */
    List<TrainingProgramDto> getPublicProgramsByTargetAudience(String targetAudience);
    
    /**
     * Get training programs by duration in weeks.
     * Récupérer des programmes d'entraînement par durée en semaines.
     * 
     * @param durationWeeks - Program duration in weeks
     * @return List<TrainingProgramDto> - Programs with specified duration
     */
    List<TrainingProgramDto> getProgramsByDuration(Integer durationWeeks);
    
    /**
     * Get public training programs by duration in weeks.
     * Récupérer des programmes d'entraînement publics par durée en semaines.
     * 
     * @param durationWeeks - Program duration in weeks
     * @return List<TrainingProgramDto> - Public programs with specified duration
     */
    List<TrainingProgramDto> getPublicProgramsByDuration(Integer durationWeeks);
    
    /**
     * Get training programs by sessions per week.
     * Récupérer des programmes d'entraînement par nombre de sessions par semaine.
     * 
     * @param sessionsPerWeek - Number of sessions per week
     * @return List<TrainingProgramDto> - Programs with specified session frequency
     */
    List<TrainingProgramDto> getProgramsBySessionsPerWeek(Integer sessionsPerWeek);
    
    /**
     * Get public training programs by sessions per week.
     * Récupérer des programmes d'entraînement publics par nombre de sessions par semaine.
     * 
     * @param sessionsPerWeek - Number of sessions per week
     * @return List<TrainingProgramDto> - Public programs with specified session frequency
     */
    List<TrainingProgramDto> getPublicProgramsBySessionsPerWeek(Integer sessionsPerWeek);
    
    /**
     * Search training programs by description.
     * Rechercher des programmes d'entraînement par description.
     * 
     * @param description - Description text to search for (case-insensitive)
     * @return List<TrainingProgramDto> - Programs with matching description
     */
    List<TrainingProgramDto> searchProgramsByDescription(String description);
    
    /**
     * Search public training programs by description.
     * Rechercher des programmes d'entraînement publics par description.
     * 
     * @param description - Description text to search for (case-insensitive)
     * @return List<TrainingProgramDto> - Public programs with matching description
     */
    List<TrainingProgramDto> searchPublicProgramsByDescription(String description);
    
    /**
     * Get training programs by category and difficulty level.
     * Récupérer des programmes d'entraînement par catégorie et niveau de difficulté.
     * 
     * @param category - Program category
     * @param difficultyLevel - Difficulty level
     * @return List<TrainingProgramDto> - Programs matching both criteria
     */
    List<TrainingProgramDto> getProgramsByCategoryAndDifficulty(String category, String difficultyLevel);
    
    /**
     * Get public training programs by category and difficulty level.
     * Récupérer des programmes d'entraînement publics par catégorie et niveau de difficulté.
     * 
     * @param category - Program category
     * @param difficultyLevel - Difficulty level
     * @return List<TrainingProgramDto> - Public programs matching both criteria
     */
    List<TrainingProgramDto> getPublicProgramsByCategoryAndDifficulty(String category, String difficultyLevel);
    
    /**
     * Convert a TrainingProgram entity to DTO.
     * Convertir une entité TrainingProgram en DTO.
     * 
     * @param program - TrainingProgram entity to convert
     * @return TrainingProgramDto - Converted DTO
     */
    TrainingProgramDto convertToDto(TrainingProgram program);
    
    /**
     * Convert a list of TrainingProgram entities to DTOs.
     * Convertir une liste d'entités TrainingProgram en DTOs.
     * 
     * @param programs - List of TrainingProgram entities to convert
     * @return List<TrainingProgramDto> - List of converted DTOs
     */
    List<TrainingProgramDto> convertToDtoList(List<TrainingProgram> programs);
} 