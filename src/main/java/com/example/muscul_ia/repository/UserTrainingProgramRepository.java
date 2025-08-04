package com.example.muscul_ia.repository;

import com.example.muscul_ia.entity.UserTrainingProgram;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * User training program repository for managing user-training program relationship data operations.
 * Repository de programmes d'entraînement utilisateur pour gérer les opérations de données de relation utilisateur-programme.
 */
@Repository
public interface UserTrainingProgramRepository extends JpaRepository<UserTrainingProgram, Long> {
    
    /**
     * Find user training programs by user ID with training program details.
     * Trouver les programmes d'entraînement utilisateur par ID utilisateur avec les détails du programme d'entraînement.
     */
    @Query("SELECT utp FROM UserTrainingProgram utp " +
           "JOIN FETCH utp.trainingProgram tp " +
           "WHERE utp.user.id = :userId")
    List<UserTrainingProgram> findByUserId(@Param("userId") Long userId);
    
    /**
     * Find user training programs by training program ID with user details.
     * Trouver les programmes d'entraînement utilisateur par ID de programme d'entraînement avec les détails utilisateur.
     */
    @Query("SELECT utp FROM UserTrainingProgram utp " +
           "JOIN FETCH utp.user u " +
           "WHERE utp.trainingProgram.id = :trainingProgramId")
    List<UserTrainingProgram> findByTrainingProgramId(@Param("trainingProgramId") Long trainingProgramId);
    
    /**
     * Find user training program by user ID and training program ID.
     * Trouver le programme d'entraînement utilisateur par ID utilisateur et ID de programme d'entraînement.
     */
    @Query("SELECT utp FROM UserTrainingProgram utp " +
           "WHERE utp.user.id = :userId AND utp.trainingProgram.id = :trainingProgramId")
    Optional<UserTrainingProgram> findByUserIdAndTrainingProgramId(
            @Param("userId") Long userId, 
            @Param("trainingProgramId") Long trainingProgramId);
} 