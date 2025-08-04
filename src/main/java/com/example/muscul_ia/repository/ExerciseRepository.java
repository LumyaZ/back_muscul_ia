package com.example.muscul_ia.repository;

import com.example.muscul_ia.entity.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Exercise repository for managing exercise data operations.
 * Repository d'exercices pour gérer les opérations de données d'exercices.
 */
@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    
    /**
     * Find all active exercises.
     * Trouver tous les exercices actifs.
     */
    List<Exercise> findByIsActiveTrue();

    /**
     * Find active exercises by category.
     * Trouver les exercices actifs par catégorie.
     */
    List<Exercise> findByCategoryAndIsActiveTrue(String category);
    
    /**
     * Find active exercises by muscle group.
     * Trouver les exercices actifs par groupe musculaire.
     */
    List<Exercise> findByMuscleGroupAndIsActiveTrue(String muscleGroup);
    
    /**
     * Find active exercises by difficulty level.
     * Trouver les exercices actifs par niveau de difficulté.
     */
    List<Exercise> findByDifficultyLevelAndIsActiveTrue(String difficultyLevel);
    
    /**
     * Find active exercises by equipment needed.
     * Trouver les exercices actifs par équipement nécessaire.
     */
    List<Exercise> findByEquipmentNeededAndIsActiveTrue(String equipmentNeeded);
    
    /**
     * Search active exercises by name.
     * Rechercher les exercices actifs par nom.
     */
    @Query("SELECT e FROM Exercise e WHERE LOWER(e.name) LIKE LOWER(CONCAT('%', :name, '%')) AND e.isActive = true")
    List<Exercise> findByNameContainingIgnoreCaseAndIsActiveTrue(@Param("name") String name);
    
    /**
     * Search active exercises by description.
     * Rechercher les exercices actifs par description.
     */
    @Query("SELECT e FROM Exercise e WHERE LOWER(e.description) LIKE LOWER(CONCAT('%', :description, '%')) AND e.isActive = true")
    List<Exercise> findByDescriptionContainingIgnoreCaseAndIsActiveTrue(@Param("description") String description);
    
    /**
     * Find active exercises by category and difficulty level.
     * Trouver les exercices actifs par catégorie et niveau de difficulté.
     */
    List<Exercise> findByCategoryAndDifficultyLevelAndIsActiveTrue(String category, String difficultyLevel);
    
    /**
     * Find active exercises by muscle group and equipment needed.
     * Trouver les exercices actifs par groupe musculaire et équipement nécessaire.
     */
    List<Exercise> findByMuscleGroupAndEquipmentNeededAndIsActiveTrue(String muscleGroup, String equipmentNeeded);
} 