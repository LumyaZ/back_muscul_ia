package com.example.muscul_ia.repository;

import com.example.muscul_ia.entity.TrainingSession;

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
 * Training session repository for managing training session data operations.
 * Repository de sessions d'entraînement pour gérer les opérations de données de sessions d'entraînement.
 */
@Repository
public interface TrainingSessionRepository extends JpaRepository<TrainingSession, Long> {
    
    /**
     * Find training sessions by user ID and session type.
     * Trouver les sessions d'entraînement par ID utilisateur et type de session.
     */
    List<TrainingSession> findByUserIdAndSessionType(Long userId, String sessionType);
    
    /**
     * Find training sessions by user ID and training program ID.
     * Trouver les sessions d'entraînement par ID utilisateur et ID de programme d'entraînement.
     */
    List<TrainingSession> findByUserIdAndTrainingProgramId(Long userId, Long trainingProgramId);
    
    /**
     * Find training sessions by user ID and name containing.
     * Trouver les sessions d'entraînement par ID utilisateur et nom contenant.
     */
    List<TrainingSession> findByUserIdAndNameContainingIgnoreCase(Long userId, String name);
    
    /**
     * Count training sessions by user ID.
     * Compter les sessions d'entraînement par ID utilisateur.
     */
    long countByUserId(Long userId);
    
    /**
     * Find first training session by user ID ordered by session date desc.
     * Trouver la première session d'entraînement par ID utilisateur ordonnée par date de session décroissante.
     */
    Optional<TrainingSession> findFirstByUserIdOrderBySessionDateDesc(Long userId);
    
    /**
     * Find training sessions by user ID with training program details.
     * Trouver les sessions d'entraînement par ID utilisateur avec les détails du programme d'entraînement.
     */
    @Query("SELECT ts FROM TrainingSession ts " +
           "LEFT JOIN FETCH ts.trainingProgram " +
           "WHERE ts.user.id = :userId " +
           "ORDER BY ts.sessionDate DESC")
    List<TrainingSession> findByUserIdWithTrainingProgram(@Param("userId") Long userId);
    
    /**
     * Find training sessions by user ID with training program details and pagination.
     * Trouver les sessions d'entraînement par ID utilisateur avec les détails du programme d'entraînement et pagination.
     */
    @Query("SELECT ts FROM TrainingSession ts " +
           "LEFT JOIN FETCH ts.trainingProgram " +
           "WHERE ts.user.id = :userId " +
           "ORDER BY ts.sessionDate DESC")
    Page<TrainingSession> findByUserIdWithTrainingProgram(@Param("userId") Long userId, Pageable pageable);
    
    /**
     * Find training sessions by user ID and date range with training program details.
     * Trouver les sessions d'entraînement par ID utilisateur et plage de dates avec les détails du programme d'entraînement.
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