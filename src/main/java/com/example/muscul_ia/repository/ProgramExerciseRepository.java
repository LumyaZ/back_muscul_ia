package com.example.muscul_ia.repository;

import com.example.muscul_ia.entity.ProgramExercise;
import com.example.muscul_ia.entity.TrainingProgram;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Program exercise repository for managing program-exercise relationship data operations.
 * Repository d'exercices de programme pour gérer les opérations de données de relation programme-exercice.
 */
@Repository
public interface ProgramExerciseRepository extends JpaRepository<ProgramExercise, Long> {
    
    /**
     * Find exercises by training program ID with exercise details.
     * Trouver les exercices par ID de programme d'entraînement avec les détails d'exercice.
     */
    @Query("SELECT pe FROM ProgramExercise pe JOIN FETCH pe.exercise WHERE pe.trainingProgram.id = :programId ORDER BY pe.createdAt ASC")
    List<ProgramExercise> findByTrainingProgramIdWithExercise(@Param("programId") Long programId);
    
    /**
     * Delete all exercises for a training program.
     * Supprimer tous les exercices d'un programme d'entraînement.
     */
    void deleteByTrainingProgram(TrainingProgram trainingProgram);
} 