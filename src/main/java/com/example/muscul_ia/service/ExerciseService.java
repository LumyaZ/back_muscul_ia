package com.example.muscul_ia.service;

import com.example.muscul_ia.dto.CreateExerciseRequest;
import com.example.muscul_ia.dto.ExerciseDto;
import com.example.muscul_ia.entity.Exercise;

import java.util.List;
import java.util.Optional;

public interface ExerciseService {
    
    // Créer un nouvel exercice
    ExerciseDto createExercise(CreateExerciseRequest request);
    
    // Récupérer tous les exercices actifs
    List<ExerciseDto> getAllActiveExercises();
    
    // Récupérer un exercice par ID
    Optional<ExerciseDto> getExerciseById(Long id);
    
    // Récupérer un exercice par ID (entité)
    Optional<Exercise> getExerciseEntityById(Long id);
    
    // Mettre à jour un exercice
    ExerciseDto updateExercise(Long id, CreateExerciseRequest request);
    
    // Supprimer un exercice (désactiver)
    void deleteExercise(Long id);
    
    // Rechercher des exercices par nom
    List<ExerciseDto> searchExercisesByName(String name);
    
    // Récupérer les exercices par catégorie
    List<ExerciseDto> getExercisesByCategory(String category);
    
    // Récupérer les exercices par groupe musculaire
    List<ExerciseDto> getExercisesByMuscleGroup(String muscleGroup);
    
    // Récupérer les exercices par niveau de difficulté
    List<ExerciseDto> getExercisesByDifficultyLevel(String difficultyLevel);
    
    // Récupérer les exercices par équipement nécessaire
    List<ExerciseDto> getExercisesByEquipment(String equipment);
    
    // Rechercher des exercices par description
    List<ExerciseDto> searchExercisesByDescription(String description);
    
    // Récupérer les exercices par catégorie et niveau de difficulté
    List<ExerciseDto> getExercisesByCategoryAndDifficulty(String category, String difficultyLevel);
    
    // Récupérer les exercices par groupe musculaire et équipement
    List<ExerciseDto> getExercisesByMuscleGroupAndEquipment(String muscleGroup, String equipment);
    
    // Convertir une entité en DTO
    ExerciseDto convertToDto(Exercise exercise);
    
    // Convertir une liste d'entités en DTOs
    List<ExerciseDto> convertToDtoList(List<Exercise> exercises);
} 