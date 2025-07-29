package com.example.muscul_ia.repository;

import com.example.muscul_ia.entity.UserTrainingProgram;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for UserTrainingProgram entity.
 * Interface repository pour l'entité UserTrainingProgram.
 * 
 * This repository provides data access methods for managing the simple relationship
 * between users and training programs.
 * 
 * Cette interface repository fournit des méthodes d'accès aux données pour
 * gérer la relation simple entre les utilisateurs et les programmes d'entraînement.
 * 
 * @author Muscul IA Team
 * @version 1.0
 * @since 2024-01-01
 */
@Repository
public interface UserTrainingProgramRepository extends JpaRepository<UserTrainingProgram, Long> {
    
    /**
     * Find all training programs that a user is subscribed to.
     * Trouver tous les programmes d'entraînement auxquels un utilisateur est abonné.
     * 
     * @param userId - ID of the user
     * @return List of UserTrainingProgram relationships
     */
    @Query("SELECT utp FROM UserTrainingProgram utp " +
           "JOIN FETCH utp.trainingProgram tp " +
           "WHERE utp.user.id = :userId")
    List<UserTrainingProgram> findByUserId(@Param("userId") Long userId);
    
    /**
     * Find all users subscribed to a specific training program.
     * Trouver tous les utilisateurs abonnés à un programme d'entraînement spécifique.
     * 
     * @param trainingProgramId - ID of the training program
     * @return List of UserTrainingProgram relationships
     */
    @Query("SELECT utp FROM UserTrainingProgram utp " +
           "JOIN FETCH utp.user u " +
           "WHERE utp.trainingProgram.id = :trainingProgramId")
    List<UserTrainingProgram> findByTrainingProgramId(@Param("trainingProgramId") Long trainingProgramId);
    
    /**
     * Check if a user is subscribed to a specific training program.
     * Vérifier si un utilisateur est abonné à un programme d'entraînement spécifique.
     * 
     * @param userId - ID of the user
     * @param trainingProgramId - ID of the training program
     * @return Optional containing the relationship if it exists
     */
    @Query("SELECT utp FROM UserTrainingProgram utp " +
           "WHERE utp.user.id = :userId AND utp.trainingProgram.id = :trainingProgramId")
    Optional<UserTrainingProgram> findByUserIdAndTrainingProgramId(
            @Param("userId") Long userId, 
            @Param("trainingProgramId") Long trainingProgramId);
} 