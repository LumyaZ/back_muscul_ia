package com.example.muscul_ia.service;

import com.example.muscul_ia.dto.CreateTrainingSessionRequest;
import com.example.muscul_ia.dto.TrainingSessionDto;
import com.example.muscul_ia.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Service interface for managing training sessions.
 * Interface de service pour gérer les sessions d'entraînement.
 * 
 * This service provides business logic for creating, reading, updating, and
 * deleting training sessions, as well as searching and filtering functionality.
 * 
 * Ce service fournit la logique métier pour créer, lire, mettre à jour et
 * supprimer les sessions d'entraînement, ainsi que des fonctionnalités de
 * recherche et de filtrage.
 * 
 * @author Muscul IA Team
 * @version 1.0
 * @since 2024-01-01
 */
public interface TrainingSessionService {
    
    /**
     * Create a new training session.
     * Créer une nouvelle session d'entraînement.
     * 
     * @param user - User who performed the session
     * @param request - Request containing session details
     * @return TrainingSessionDto - Created training session
     */
    TrainingSessionDto createTrainingSession(User user, CreateTrainingSessionRequest request);
    
    /**
     * Get a training session by ID.
     * Récupérer une session d'entraînement par ID.
     * 
     * @param sessionId - ID of the training session
     * @return Optional containing the training session if found
     */
    Optional<TrainingSessionDto> getTrainingSessionById(Long sessionId);
    
    /**
     * Get all training sessions for a specific user.
     * Récupérer toutes les sessions d'entraînement pour un utilisateur spécifique.
     * 
     * @param user - User to get sessions for
     * @return List of training sessions
     */
    List<TrainingSessionDto> getTrainingSessionsByUser(User user);
    
    /**
     * Get all training sessions for a specific user with pagination.
     * Récupérer toutes les sessions d'entraînement pour un utilisateur spécifique avec pagination.
     * 
     * @param user - User to get sessions for
     * @param pageable - Pagination information
     * @return Page of training sessions
     */
    Page<TrainingSessionDto> getTrainingSessionsByUser(User user, Pageable pageable);
    
    /**
     * Get all training sessions for a specific user ID.
     * Récupérer toutes les sessions d'entraînement pour un ID utilisateur spécifique.
     * 
     * @param userId - User ID to get sessions for
     * @return List of training sessions
     */
    List<TrainingSessionDto> getTrainingSessionsByUserId(Long userId);
    
    /**
     * Get all training sessions for a specific user ID with pagination.
     * Récupérer toutes les sessions d'entraînement pour un ID utilisateur spécifique avec pagination.
     * 
     * @param userId - User ID to get sessions for
     * @param pageable - Pagination information
     * @return Page of training sessions
     */
    Page<TrainingSessionDto> getTrainingSessionsByUserId(Long userId, Pageable pageable);
    
    /**
     * Get training sessions by user and date range.
     * Récupérer les sessions d'entraînement par utilisateur et plage de dates.
     * 
     * @param user - User to get sessions for
     * @param startDate - Start date of the range
     * @param endDate - End date of the range
     * @return List of training sessions
     */
    List<TrainingSessionDto> getTrainingSessionsByUserAndDateRange(User user, LocalDateTime startDate, LocalDateTime endDate);
    
    /**
     * Get training sessions by user ID and date range.
     * Récupérer les sessions d'entraînement par ID utilisateur et plage de dates.
     * 
     * @param userId - User ID to get sessions for
     * @param startDate - Start date of the range
     * @param endDate - End date of the range
     * @return List of training sessions
     */
    List<TrainingSessionDto> getTrainingSessionsByUserIdAndDateRange(Long userId, LocalDateTime startDate, LocalDateTime endDate);
    
    /**
     * Get training sessions by user and session type.
     * Récupérer les sessions d'entraînement par utilisateur et type de session.
     * 
     * @param user - User to get sessions for
     * @param sessionType - Type of session to get
     * @return List of training sessions
     */
    List<TrainingSessionDto> getTrainingSessionsByUserAndType(User user, String sessionType);
    
    /**
     * Get training sessions by user ID and session type.
     * Récupérer les sessions d'entraînement par ID utilisateur et type de session.
     * 
     * @param userId - User ID to get sessions for
     * @param sessionType - Type of session to get
     * @return List of training sessions
     */
    List<TrainingSessionDto> getTrainingSessionsByUserIdAndType(Long userId, String sessionType);
    
    /**
     * Get training sessions by user and training program.
     * Récupérer les sessions d'entraînement par utilisateur et programme d'entraînement.
     * 
     * @param user - User to get sessions for
     * @param trainingProgramId - Training program ID
     * @return List of training sessions
     */
    List<TrainingSessionDto> getTrainingSessionsByUserAndTrainingProgram(User user, Long trainingProgramId);
    
    /**
     * Get training sessions by user ID and training program.
     * Récupérer les sessions d'entraînement par ID utilisateur et programme d'entraînement.
     * 
     * @param userId - User ID to get sessions for
     * @param trainingProgramId - Training program ID
     * @return List of training sessions
     */
    List<TrainingSessionDto> getTrainingSessionsByUserIdAndTrainingProgram(Long userId, Long trainingProgramId);
    
    /**
     * Search training sessions by name for a specific user.
     * Rechercher les sessions d'entraînement par nom pour un utilisateur spécifique.
     * 
     * @param user - User to search sessions for
     * @param name - Name to search for
     * @return List of training sessions
     */
    List<TrainingSessionDto> searchTrainingSessionsByUserAndName(User user, String name);
    
    /**
     * Search training sessions by name for a specific user ID.
     * Rechercher les sessions d'entraînement par nom pour un ID utilisateur spécifique.
     * 
     * @param userId - User ID to search sessions for
     * @param name - Name to search for
     * @return List of training sessions
     */
    List<TrainingSessionDto> searchTrainingSessionsByUserIdAndName(Long userId, String name);
    
    /**
     * Get the most recent training session for a user.
     * Récupérer la session d'entraînement la plus récente pour un utilisateur.
     * 
     * @param user - User to get the most recent session for
     * @return Optional containing the most recent training session
     */
    Optional<TrainingSessionDto> getMostRecentTrainingSessionByUser(User user);
    
    /**
     * Get the most recent training session for a user ID.
     * Récupérer la session d'entraînement la plus récente pour un ID utilisateur.
     * 
     * @param userId - User ID to get the most recent session for
     * @return Optional containing the most recent training session
     */
    Optional<TrainingSessionDto> getMostRecentTrainingSessionByUserId(Long userId);
    
    /**
     * Count training sessions for a specific user.
     * Compter les sessions d'entraînement pour un utilisateur spécifique.
     * 
     * @param user - User to count sessions for
     * @return Number of training sessions
     */
    long countTrainingSessionsByUser(User user);
    
    /**
     * Count training sessions for a specific user ID.
     * Compter les sessions d'entraînement pour un ID utilisateur spécifique.
     * 
     * @param userId - User ID to count sessions for
     * @return Number of training sessions
     */
    long countTrainingSessionsByUserId(Long userId);
    
    /**
     * Update a training session.
     * Mettre à jour une session d'entraînement.
     * 
     * @param sessionId - ID of the session to update
     * @param request - Request containing updated session details
     * @return TrainingSessionDto - Updated training session
     */
    TrainingSessionDto updateTrainingSession(Long sessionId, CreateTrainingSessionRequest request);
    
    /**
     * Delete a training session.
     * Supprimer une session d'entraînement.
     * 
     * @param sessionId - ID of the session to delete
     */
    void deleteTrainingSession(Long sessionId);
    
    /**
     * Check if a training session exists.
     * Vérifier si une session d'entraînement existe.
     * 
     * @param sessionId - ID of the session to check
     * @return true if the session exists, false otherwise
     */
    boolean existsTrainingSession(Long sessionId);
} 