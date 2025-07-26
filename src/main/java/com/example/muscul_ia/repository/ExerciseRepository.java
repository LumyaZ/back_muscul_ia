package com.example.muscul_ia.repository;

import com.example.muscul_ia.entity.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    
    // Trouver tous les exercices actifs
    List<Exercise> findByIsActiveTrue();
    
    // Trouver les exercices par catégorie
    List<Exercise> findByCategoryAndIsActiveTrue(String category);
    
    // Trouver les exercices par groupe musculaire
    List<Exercise> findByMuscleGroupAndIsActiveTrue(String muscleGroup);
    
    // Trouver les exercices par niveau de difficulté
    List<Exercise> findByDifficultyLevelAndIsActiveTrue(String difficultyLevel);
    
    // Trouver les exercices par équipement nécessaire
    List<Exercise> findByEquipmentNeededAndIsActiveTrue(String equipmentNeeded);
    
    // Recherche par nom (insensible à la casse)
    @Query("SELECT e FROM Exercise e WHERE LOWER(e.name) LIKE LOWER(CONCAT('%', :name, '%')) AND e.isActive = true")
    List<Exercise> findByNameContainingIgnoreCaseAndIsActiveTrue(@Param("name") String name);
    
    // Recherche par description (insensible à la casse)
    @Query("SELECT e FROM Exercise e WHERE LOWER(e.description) LIKE LOWER(CONCAT('%', :description, '%')) AND e.isActive = true")
    List<Exercise> findByDescriptionContainingIgnoreCaseAndIsActiveTrue(@Param("description") String description);
    
    // Trouver les exercices par catégorie et niveau de difficulté
    List<Exercise> findByCategoryAndDifficultyLevelAndIsActiveTrue(String category, String difficultyLevel);
    
    // Trouver les exercices par groupe musculaire et équipement
    List<Exercise> findByMuscleGroupAndEquipmentNeededAndIsActiveTrue(String muscleGroup, String equipmentNeeded);
} 