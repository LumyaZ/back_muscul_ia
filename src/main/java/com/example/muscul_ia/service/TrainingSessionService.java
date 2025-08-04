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
 * Training session service for managing training session business logic.
 * Service de sessions d'entraînement pour gérer la logique métier de sessions d'entraînement.
 */
public interface TrainingSessionService {
    
    /**
     * Create a new training session.
     * Créer une nouvelle session d'entraînement.
     */
    TrainingSessionDto createTrainingSession(User user, CreateTrainingSessionRequest request);
    
    /**
     * Get a training session by ID.
     * Récupérer une session d'entraînement par ID.
     */
    Optional<TrainingSessionDto> getTrainingSessionById(Long sessionId);
    
    /**
     * Get all training sessions for a specific user.
     * Récupérer toutes les sessions d'entraînement pour un utilisateur spécifique.
     */
    List<TrainingSessionDto> getTrainingSessionsByUser(User user);
    
    /**
     * Get all training sessions for a specific user ID with pagination.
     * Récupérer toutes les sessions d'entraînement pour un ID utilisateur spécifique avec pagination.
     */
    Page<TrainingSessionDto> getTrainingSessionsByUserId(Long userId, Pageable pageable);
    
    /**
     * Get training sessions by user and date range.
     * Récupérer les sessions d'entraînement par utilisateur et plage de dates.
     */
    List<TrainingSessionDto> getTrainingSessionsByUserAndDateRange(User user, LocalDateTime startDate, LocalDateTime endDate);
    
    /**
     * Get training sessions by user and session type.
     * Récupérer les sessions d'entraînement par utilisateur et type de session.
     */
    List<TrainingSessionDto> getTrainingSessionsByUserAndType(User user, String sessionType);
    
    /**
     * Get training sessions by user and training program.
     * Récupérer les sessions d'entraînement par utilisateur et programme d'entraînement.
     */
    List<TrainingSessionDto> getTrainingSessionsByUserAndTrainingProgram(User user, Long trainingProgramId);
    
    /**
     * Search training sessions by name for a specific user.
     * Rechercher les sessions d'entraînement par nom pour un utilisateur spécifique.
     */
    List<TrainingSessionDto> searchTrainingSessionsByUserAndName(User user, String name);
    
    /**
     * Get the most recent training session for a user.
     * Récupérer la session d'entraînement la plus récente pour un utilisateur.
     */
    Optional<TrainingSessionDto> getMostRecentTrainingSessionByUser(User user);
    
    /**
     * Count training sessions for a specific user.
     * Compter les sessions d'entraînement pour un utilisateur spécifique.
     */
    long countTrainingSessionsByUser(User user);
    
    /**
     * Update a training session.
     * Mettre à jour une session d'entraînement.
     */
    TrainingSessionDto updateTrainingSession(Long sessionId, CreateTrainingSessionRequest request);
    
    /**
     * Delete a training session.
     * Supprimer une session d'entraînement.
     */
    void deleteTrainingSession(Long sessionId);
    
    /**
     * Check if a training session exists.
     * Vérifier si une session d'entraînement existe.
     */
    boolean existsTrainingSession(Long sessionId);
} 