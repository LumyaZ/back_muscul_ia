package com.example.muscul_ia.repository;

import com.example.muscul_ia.entity.ProgramExercise;
import com.example.muscul_ia.entity.TrainingProgram;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProgramExerciseRepository extends JpaRepository<ProgramExercise, Long> {
    
    // Trouver tous les exercices d'un programme
    List<ProgramExercise> findByTrainingProgramOrderByOrderInProgramAsc(TrainingProgram trainingProgram);
    
    // Trouver tous les exercices d'un programme par ID
    @Query("SELECT pe FROM ProgramExercise pe WHERE pe.trainingProgram.id = :programId ORDER BY pe.orderInProgram ASC")
    List<ProgramExercise> findByTrainingProgramIdOrderByOrderInProgramAsc(@Param("programId") Long programId);
    
    // Trouver les exercices obligatoires d'un programme
    List<ProgramExercise> findByTrainingProgramAndIsOptionalFalseOrderByOrderInProgramAsc(TrainingProgram trainingProgram);
    
    // Trouver les exercices optionnels d'un programme
    List<ProgramExercise> findByTrainingProgramAndIsOptionalTrueOrderByOrderInProgramAsc(TrainingProgram trainingProgram);
    
    // Trouver les exercices d'un programme par ordre
    List<ProgramExercise> findByTrainingProgramAndOrderInProgramBetweenOrderByOrderInProgramAsc(
            TrainingProgram trainingProgram, Integer startOrder, Integer endOrder);
    
    // Compter le nombre d'exercices dans un programme
    long countByTrainingProgram(TrainingProgram trainingProgram);
    
    // Compter le nombre d'exercices obligatoires dans un programme
    long countByTrainingProgramAndIsOptionalFalse(TrainingProgram trainingProgram);
    
    // Trouver l'ordre maximum dans un programme
    @Query("SELECT MAX(pe.orderInProgram) FROM ProgramExercise pe WHERE pe.trainingProgram.id = :programId")
    Integer findMaxOrderInProgram(@Param("programId") Long programId);
    
    // Trouver les exercices d'un programme avec l'exercice joint
    @Query("SELECT pe FROM ProgramExercise pe JOIN FETCH pe.exercise WHERE pe.trainingProgram.id = :programId ORDER BY pe.orderInProgram ASC")
    List<ProgramExercise> findByTrainingProgramIdWithExercise(@Param("programId") Long programId);
    
    // Supprimer tous les exercices d'un programme
    void deleteByTrainingProgram(TrainingProgram trainingProgram);
    
    // Supprimer tous les exercices d'un programme par ID
    @Query("DELETE FROM ProgramExercise pe WHERE pe.trainingProgram.id = :programId")
    void deleteByTrainingProgramId(@Param("programId") Long programId);
} 