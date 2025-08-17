package com.example.muscul_ia.service.impl;

import com.example.muscul_ia.dto.CreateExerciseRequest;
import com.example.muscul_ia.dto.ExerciseDto;
import com.example.muscul_ia.entity.Exercise;
import com.example.muscul_ia.repository.ExerciseRepository;
import com.example.muscul_ia.service.ExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Exercise service implementation for managing exercise business logic.
 * Implémentation du service d'exercices pour gérer la logique métier d'exercices.
 */
@Service
public class ExerciseServiceImpl implements ExerciseService {
    
    @Autowired
    private ExerciseRepository exerciseRepository;
    
    @Override
    public ExerciseDto createExercise(CreateExerciseRequest request) {
        Exercise exercise = new Exercise();
        exercise.setName(request.getName());
        exercise.setDescription(request.getDescription());
        exercise.setCategory(request.getCategory());
        exercise.setMuscleGroup(request.getMuscleGroup());
        exercise.setEquipmentNeeded(request.getEquipmentNeeded());
        exercise.setDifficultyLevel(request.getDifficultyLevel());
        exercise.setIsActive(true);
        
        // Définir les timestamps de création et mise à jour
        LocalDateTime now = LocalDateTime.now();
        exercise.setCreatedAt(now);
        exercise.setUpdatedAt(now);
        
        Exercise savedExercise = exerciseRepository.save(exercise);
        return convertToDto(savedExercise);
    }
    
    @Override
    public List<ExerciseDto> getAllActiveExercises() {
        List<Exercise> exercises = exerciseRepository.findByIsActiveTrue();
        return convertToDtoList(exercises);
    }
    
    @Override
    public Optional<ExerciseDto> getExerciseById(Long id) {
        Optional<Exercise> exercise = exerciseRepository.findById(id);
        return exercise.map(this::convertToDto);
    }
    
    @Override
    public ExerciseDto updateExercise(Long id, CreateExerciseRequest request) {
        Optional<Exercise> exerciseOpt = exerciseRepository.findById(id);
        if (exerciseOpt.isPresent()) {
            Exercise exercise = exerciseOpt.get();
            exercise.setName(request.getName());
            exercise.setDescription(request.getDescription());
            exercise.setCategory(request.getCategory());
            exercise.setMuscleGroup(request.getMuscleGroup());
            exercise.setEquipmentNeeded(request.getEquipmentNeeded());
            exercise.setDifficultyLevel(request.getDifficultyLevel());
            
            // Mettre à jour le timestamp de modification
            exercise.setUpdatedAt(LocalDateTime.now());
            
            Exercise updatedExercise = exerciseRepository.save(exercise);
            return convertToDto(updatedExercise);
        }
        throw new RuntimeException("Exercise not found with id: " + id);
    }
    
    @Override
    public void deleteExercise(Long id) {
        Optional<Exercise> exerciseOpt = exerciseRepository.findById(id);
        if (exerciseOpt.isPresent()) {
            Exercise exercise = exerciseOpt.get();
            exercise.setIsActive(false);
            exerciseRepository.save(exercise);
        } else {
            throw new RuntimeException("Exercise not found with id: " + id);
        }
    }
    
    @Override
    public List<ExerciseDto> searchExercisesByName(String name) {
        List<Exercise> exercises = exerciseRepository.findByNameContainingIgnoreCaseAndIsActiveTrue(name);
        return convertToDtoList(exercises);
    }
    
    @Override
    public List<ExerciseDto> getExercisesByCategory(String category) {
        List<Exercise> exercises = exerciseRepository.findByCategoryAndIsActiveTrue(category);
        return convertToDtoList(exercises);
    }
    
    @Override
    public List<ExerciseDto> getExercisesByMuscleGroup(String muscleGroup) {
        List<Exercise> exercises = exerciseRepository.findByMuscleGroupAndIsActiveTrue(muscleGroup);
        return convertToDtoList(exercises);
    }
    
    @Override
    public List<ExerciseDto> getExercisesByDifficultyLevel(String difficultyLevel) {
        List<Exercise> exercises = exerciseRepository.findByDifficultyLevelAndIsActiveTrue(difficultyLevel);
        return convertToDtoList(exercises);
    }
    
    @Override
    public List<ExerciseDto> getExercisesByEquipment(String equipment) {
        List<Exercise> exercises = exerciseRepository.findByEquipmentNeededAndIsActiveTrue(equipment);
        return convertToDtoList(exercises);
    }
    
    @Override
    public List<ExerciseDto> searchExercisesByDescription(String description) {
        List<Exercise> exercises = exerciseRepository.findByDescriptionContainingIgnoreCaseAndIsActiveTrue(description);
        return convertToDtoList(exercises);
    }
    
    @Override
    public List<ExerciseDto> getExercisesByCategoryAndDifficulty(String category, String difficultyLevel) {
        List<Exercise> exercises = exerciseRepository.findByCategoryAndDifficultyLevelAndIsActiveTrue(category, difficultyLevel);
        return convertToDtoList(exercises);
    }
    
    @Override
    public List<ExerciseDto> getExercisesByMuscleGroupAndEquipment(String muscleGroup, String equipment) {
        List<Exercise> exercises = exerciseRepository.findByMuscleGroupAndEquipmentNeededAndIsActiveTrue(muscleGroup, equipment);
        return convertToDtoList(exercises);
    }
    
    @Override
    public ExerciseDto convertToDto(Exercise exercise) {
        ExerciseDto dto = new ExerciseDto();
        dto.setId(exercise.getId());
        dto.setName(exercise.getName());
        dto.setDescription(exercise.getDescription());
        dto.setCategory(exercise.getCategory());
        dto.setMuscleGroup(exercise.getMuscleGroup());
        dto.setEquipmentNeeded(exercise.getEquipmentNeeded());
        dto.setDifficultyLevel(exercise.getDifficultyLevel());  
        dto.setIsActive(exercise.getIsActive());
        dto.setCreatedAt(exercise.getCreatedAt());
        dto.setUpdatedAt(exercise.getUpdatedAt());
        return dto;
    }
    
    @Override
    public List<ExerciseDto> convertToDtoList(List<Exercise> exercises) {
        return exercises.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
} 