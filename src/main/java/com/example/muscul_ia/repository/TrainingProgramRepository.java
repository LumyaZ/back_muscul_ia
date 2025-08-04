package com.example.muscul_ia.repository;

import com.example.muscul_ia.entity.TrainingProgram;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Training program repository for managing training program data operations.
 * Repository de programmes d'entraînement pour gérer les opérations de données de programmes d'entraînement.
 */
@Repository
public interface TrainingProgramRepository extends JpaRepository<TrainingProgram, Long> {

    /**
     * Find all training programs.
     * Trouver tous les programmes d'entraînement.
     */
    List<TrainingProgram> findAll();
} 