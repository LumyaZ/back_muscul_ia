package com.example.muscul_ia.service;

import com.example.muscul_ia.dto.CreateTrainingProgramRequest;
import com.example.muscul_ia.dto.TrainingProgramDto;
import com.example.muscul_ia.entity.TrainingProgram;

import java.util.List;
import java.util.Optional;

public interface TrainingProgramService {
    
    // Créer un nouveau programme d'entraînement
    TrainingProgramDto createTrainingProgram(CreateTrainingProgramRequest request, Long userId);
    
    // Récupérer tous les programmes actifs
    List<TrainingProgramDto> getAllActivePrograms();
    
    // Récupérer tous les programmes publics et actifs
    List<TrainingProgramDto> getAllPublicActivePrograms();
    
    // Récupérer un programme par ID
    Optional<TrainingProgramDto> getProgramById(Long id);
    
    // Récupérer un programme par ID (entité)
    Optional<TrainingProgram> getProgramEntityById(Long id);
    
    // Récupérer les programmes créés par un utilisateur
    List<TrainingProgramDto> getProgramsByUser(Long userId);
    
    // Mettre à jour un programme
    TrainingProgramDto updateProgram(Long id, CreateTrainingProgramRequest request, Long userId);
    
    // Supprimer un programme (désactiver)
    void deleteProgram(Long id, Long userId);
    
    // Rechercher des programmes par nom
    List<TrainingProgramDto> searchProgramsByName(String name);
    
    // Rechercher des programmes publics par nom
    List<TrainingProgramDto> searchPublicProgramsByName(String name);
    
    // Récupérer les programmes par niveau de difficulté
    List<TrainingProgramDto> getProgramsByDifficultyLevel(String difficultyLevel);
    
    // Récupérer les programmes publics par niveau de difficulté
    List<TrainingProgramDto> getPublicProgramsByDifficultyLevel(String difficultyLevel);
    
    // Récupérer les programmes par catégorie
    List<TrainingProgramDto> getProgramsByCategory(String category);
    
    // Récupérer les programmes publics par catégorie
    List<TrainingProgramDto> getPublicProgramsByCategory(String category);
    
    // Récupérer les programmes par audience cible
    List<TrainingProgramDto> getProgramsByTargetAudience(String targetAudience);
    
    // Récupérer les programmes publics par audience cible
    List<TrainingProgramDto> getPublicProgramsByTargetAudience(String targetAudience);
    
    // Récupérer les programmes par durée (en semaines)
    List<TrainingProgramDto> getProgramsByDuration(Integer durationWeeks);
    
    // Récupérer les programmes publics par durée (en semaines)
    List<TrainingProgramDto> getPublicProgramsByDuration(Integer durationWeeks);
    
    // Récupérer les programmes par nombre de sessions par semaine
    List<TrainingProgramDto> getProgramsBySessionsPerWeek(Integer sessionsPerWeek);
    
    // Récupérer les programmes publics par nombre de sessions par semaine
    List<TrainingProgramDto> getPublicProgramsBySessionsPerWeek(Integer sessionsPerWeek);
    
    // Rechercher des programmes par description
    List<TrainingProgramDto> searchProgramsByDescription(String description);
    
    // Rechercher des programmes publics par description
    List<TrainingProgramDto> searchPublicProgramsByDescription(String description);
    
    // Récupérer les programmes par catégorie et niveau de difficulté
    List<TrainingProgramDto> getProgramsByCategoryAndDifficulty(String category, String difficultyLevel);
    
    // Récupérer les programmes publics par catégorie et niveau de difficulté
    List<TrainingProgramDto> getPublicProgramsByCategoryAndDifficulty(String category, String difficultyLevel);
    
    // Convertir une entité en DTO
    TrainingProgramDto convertToDto(TrainingProgram program);
    
    // Convertir une liste d'entités en DTOs
    List<TrainingProgramDto> convertToDtoList(List<TrainingProgram> programs);
} 