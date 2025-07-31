package com.example.muscul_ia.repository;

import com.example.muscul_ia.entity.TrainingSession;
import com.example.muscul_ia.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repository for TrainingSession entity.
 * Repository pour l'entité TrainingSession.
 * 
 * This repository provides data access methods for training sessions including
 * basic CRUD operations and custom queries for filtering and searching.
 * 
 * Ce repository fournit des méthodes d'accès aux données pour les sessions
 * d'entraînement incluant les opérations CRUD de base et des requêtes
 * personnalisées pour le filtrage et la recherche.
 * 
 * @author Muscul IA Team
 * @version 1.0
 * @since 2024-01-01
 */
@Repository
public interface TrainingSessionRepository extends JpaRepository<TrainingSession, Long> {
    
    /**
     * Find all training sessions for a specific user.
     * Trouver toutes les sessions d'entraînement pour un utilisateur spécifique.
     * 
     * @param user - User to find sessions for
     * @return List of training sessions
     */
    List<TrainingSession> findByUser(User user);
    
    /**
     * Find all training sessions for a specific user with pagination.
     * Trouver toutes les sessions d'entraînement pour un utilisateur spécifique avec pagination.
     * 
     * @param user - User to find sessions for
     * @param pageable - Pagination information
     * @return Page of training sessions
     */
    Page<TrainingSession> findByUser(User user, Pageable pageable);
    
    /**
     * Find all training sessions for a specific user ID.
     * Trouver toutes les sessions d'entraînement pour un ID utilisateur spécifique.
     * 
     * @param userId - User ID to find sessions for
     * @return List of training sessions
     */
    List<TrainingSession> findByUserId(Long userId);
    
    /**
     * Find all training sessions for a specific user ID with pagination.
     * Trouver toutes les sessions d'entraînement pour un ID utilisateur spécifique avec pagination.
     * 
     * @param userId - User ID to find sessions for
     * @param pageable - Pagination information
     * @return Page of training sessions
     */
    Page<TrainingSession> findByUserId(Long userId, Pageable pageable);
    
    /**
     * Find training sessions by user and session date range.
     * Trouver les sessions d'entraînement par utilisateur et plage de dates.
     * 
     * @param user - User to find sessions for
     * @param startDate - Start date of the range
     * @param endDate - End date of the range
     * @return List of training sessions
     */
    List<TrainingSession> findByUserAndSessionDateBetween(User user, LocalDateTime startDate, LocalDateTime endDate);
    
    /**
     * Find training sessions by user ID and session date range.
     * Trouver les sessions d'entraînement par ID utilisateur et plage de dates.
     * 
     * @param userId - User ID to find sessions for
     * @param startDate - Start date of the range
     * @param endDate - End date of the range
     * @return List of training sessions
     */
    List<TrainingSession> findByUserIdAndSessionDateBetween(Long userId, LocalDateTime startDate, LocalDateTime endDate);
    
    /**
     * Find training sessions by user and session type.
     * Trouver les sessions d'entraînement par utilisateur et type de session.
     * 
     * @param user - User to find sessions for
     * @param sessionType - Type of session to find
     * @return List of training sessions
     */
    List<TrainingSession> findByUserAndSessionType(User user, String sessionType);
    
    /**
     * Find training sessions by user ID and session type.
     * Trouver les sessions d'entraînement par ID utilisateur et type de session.
     * 
     * @param userId - User ID to find sessions for
     * @param sessionType - Type of session to find
     * @return List of training sessions
     */
    List<TrainingSession> findByUserIdAndSessionType(Long userId, String sessionType);
    
    /**
     * Find training sessions by user and training program.
     * Trouver les sessions d'entraînement par utilisateur et programme d'entraînement.
     * 
     * @param user - User to find sessions for
     * @param trainingProgramId - Training program ID
     * @return List of training sessions
     */
    List<TrainingSession> findByUserAndTrainingProgramId(User user, Long trainingProgramId);
    
    /**
     * Find training sessions by user ID and training program.
     * Trouver les sessions d'entraînement par ID utilisateur et programme d'entraînement.
     * 
     * @param userId - User ID to find sessions for
     * @param trainingProgramId - Training program ID
     * @return List of training sessions
     */
    List<TrainingSession> findByUserIdAndTrainingProgramId(Long userId, Long trainingProgramId);
    
    /**
     * Find training sessions by name containing the given string (case-insensitive).
     * Trouver les sessions d'entraînement par nom contenant la chaîne donnée (insensible à la casse).
     * 
     * @param user - User to find sessions for
     * @param name - Name to search for
     * @return List of training sessions
     */
    List<TrainingSession> findByUserAndNameContainingIgnoreCase(User user, String name);
    
    /**
     * Find training sessions by name containing the given string (case-insensitive).
     * Trouver les sessions d'entraînement par nom contenant la chaîne donnée (insensible à la casse).
     * 
     * @param userId - User ID to find sessions for
     * @param name - Name to search for
     * @return List of training sessions
     */
    List<TrainingSession> findByUserIdAndNameContainingIgnoreCase(Long userId, String name);
    
    /**
     * Count training sessions for a specific user.
     * Compter les sessions d'entraînement pour un utilisateur spécifique.
     * 
     * @param user - User to count sessions for
     * @return Number of training sessions
     */
    long countByUser(User user);
    
    /**
     * Count training sessions for a specific user ID.
     * Compter les sessions d'entraînement pour un ID utilisateur spécifique.
     * 
     * @param userId - User ID to count sessions for
     * @return Number of training sessions
     */
    long countByUserId(Long userId);
    
    /**
     * Find the most recent training session for a user.
     * Trouver la session d'entraînement la plus récente pour un utilisateur.
     * 
     * @param user - User to find the most recent session for
     * @return Optional containing the most recent training session
     */
    Optional<TrainingSession> findFirstByUserOrderBySessionDateDesc(User user);
    
    /**
     * Find the most recent training session for a user ID.
     * Trouver la session d'entraînement la plus récente pour un ID utilisateur.
     * 
     * @param userId - User ID to find the most recent session for
     * @return Optional containing the most recent training session
     */
    Optional<TrainingSession> findFirstByUserIdOrderBySessionDateDesc(Long userId);
    
    /**
     * Custom query to find training sessions with training program information.
     * Requête personnalisée pour trouver les sessions d'entraînement avec les informations du programme.
     * 
     * @param userId - User ID to find sessions for
     * @return List of training sessions with training program details
     */
    @Query("SELECT ts FROM TrainingSession ts " +
           "LEFT JOIN FETCH ts.trainingProgram " +
           "WHERE ts.user.id = :userId " +
           "ORDER BY ts.sessionDate DESC")
    List<TrainingSession> findByUserIdWithTrainingProgram(@Param("userId") Long userId);
    
    /**
     * Custom query to find training sessions with training program information and pagination.
     * Requête personnalisée pour trouver les sessions d'entraînement avec les informations du programme et pagination.
     * 
     * @param userId - User ID to find sessions for
     * @param pageable - Pagination information
     * @return Page of training sessions with training program details
     */
    @Query("SELECT ts FROM TrainingSession ts " +
           "LEFT JOIN FETCH ts.trainingProgram " +
           "WHERE ts.user.id = :userId " +
           "ORDER BY ts.sessionDate DESC")
    Page<TrainingSession> findByUserIdWithTrainingProgram(@Param("userId") Long userId, Pageable pageable);
    
    /**
     * Custom query to find training sessions by date range with training program information.
     * Requête personnalisée pour trouver les sessions d'entraînement par plage de dates avec les informations du programme.
     * 
     * @param userId - User ID to find sessions for
     * @param startDate - Start date of the range
     * @param endDate - End date of the range
     * @return List of training sessions with training program details
     */
    @Query("SELECT ts FROM TrainingSession ts " +
           "LEFT JOIN FETCH ts.trainingProgram " +
           "WHERE ts.user.id = :userId " +
           "AND ts.sessionDate BETWEEN :startDate AND :endDate " +
           "ORDER BY ts.sessionDate DESC")
    List<TrainingSession> findByUserIdAndDateRangeWithTrainingProgram(
            @Param("userId") Long userId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate);
} 