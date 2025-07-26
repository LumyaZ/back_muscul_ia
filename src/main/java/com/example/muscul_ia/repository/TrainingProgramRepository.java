package com.example.muscul_ia.repository;

import com.example.muscul_ia.entity.TrainingProgram;
import com.example.muscul_ia.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainingProgramRepository extends JpaRepository<TrainingProgram, Long> {
    
    // Trouver tous les programmes actifs
    List<TrainingProgram> findByIsActiveTrue();
    
    // Trouver les programmes publics et actifs
    List<TrainingProgram> findByIsPublicTrueAndIsActiveTrue();
    
    // Trouver les programmes créés par un utilisateur
    List<TrainingProgram> findByCreatedByUserAndIsActiveTrue(User user);
    
    // Trouver les programmes par niveau de difficulté
    List<TrainingProgram> findByDifficultyLevelAndIsActiveTrue(String difficultyLevel);
    
    // Trouver les programmes publics par niveau de difficulté
    List<TrainingProgram> findByDifficultyLevelAndIsPublicTrueAndIsActiveTrue(String difficultyLevel);
    
    // Trouver les programmes par catégorie
    List<TrainingProgram> findByCategoryAndIsActiveTrue(String category);
    
    // Trouver les programmes publics par catégorie
    List<TrainingProgram> findByCategoryAndIsPublicTrueAndIsActiveTrue(String category);
    
    // Trouver les programmes par audience cible
    List<TrainingProgram> findByTargetAudienceAndIsActiveTrue(String targetAudience);
    
    // Trouver les programmes publics par audience cible
    List<TrainingProgram> findByTargetAudienceAndIsPublicTrueAndIsActiveTrue(String targetAudience);
    
    // Recherche par nom (insensible à la casse)
    @Query("SELECT tp FROM TrainingProgram tp WHERE LOWER(tp.name) LIKE LOWER(CONCAT('%', :name, '%')) AND tp.isActive = true")
    List<TrainingProgram> findByNameContainingIgnoreCaseAndIsActiveTrue(@Param("name") String name);
    
    // Recherche publique par nom (insensible à la casse)
    @Query("SELECT tp FROM TrainingProgram tp WHERE LOWER(tp.name) LIKE LOWER(CONCAT('%', :name, '%')) AND tp.isPublic = true AND tp.isActive = true")
    List<TrainingProgram> findByNameContainingIgnoreCaseAndIsPublicTrueAndIsActiveTrue(@Param("name") String name);
    
    // Recherche par description (insensible à la casse)
    @Query("SELECT tp FROM TrainingProgram tp WHERE LOWER(tp.description) LIKE LOWER(CONCAT('%', :description, '%')) AND tp.isActive = true")
    List<TrainingProgram> findByDescriptionContainingIgnoreCaseAndIsActiveTrue(@Param("description") String description);
    
    // Recherche publique par description (insensible à la casse)
    @Query("SELECT tp FROM TrainingProgram tp WHERE LOWER(tp.description) LIKE LOWER(CONCAT('%', :description, '%')) AND tp.isPublic = true AND tp.isActive = true")
    List<TrainingProgram> findByDescriptionContainingIgnoreCaseAndIsPublicTrueAndIsActiveTrue(@Param("description") String description);
    
    // Trouver les programmes par durée (en semaines)
    List<TrainingProgram> findByDurationWeeksAndIsActiveTrue(Integer durationWeeks);
    
    // Trouver les programmes publics par durée (en semaines)
    List<TrainingProgram> findByDurationWeeksAndIsPublicTrueAndIsActiveTrue(Integer durationWeeks);
    
    // Trouver les programmes par nombre de sessions par semaine
    List<TrainingProgram> findBySessionsPerWeekAndIsActiveTrue(Integer sessionsPerWeek);
    
    // Trouver les programmes publics par nombre de sessions par semaine
    List<TrainingProgram> findBySessionsPerWeekAndIsPublicTrueAndIsActiveTrue(Integer sessionsPerWeek);
    
    // Trouver les programmes par catégorie et niveau de difficulté
    List<TrainingProgram> findByCategoryAndDifficultyLevelAndIsActiveTrue(String category, String difficultyLevel);
    
    // Trouver les programmes publics par catégorie et niveau de difficulté
    List<TrainingProgram> findByCategoryAndDifficultyLevelAndIsPublicTrueAndIsActiveTrue(String category, String difficultyLevel);
} 