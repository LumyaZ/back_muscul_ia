package com.example.muscul_ia.service.impl;

import com.example.muscul_ia.dto.ProgramExerciseDto;
import com.example.muscul_ia.dto.CreateProgramExerciseRequest;
import com.example.muscul_ia.entity.ProgramExercise;
import com.example.muscul_ia.entity.TrainingProgram;
import com.example.muscul_ia.entity.Exercise;
import com.example.muscul_ia.repository.ProgramExerciseRepository;
import com.example.muscul_ia.repository.TrainingProgramRepository;
import com.example.muscul_ia.repository.ExerciseRepository;
import com.example.muscul_ia.service.ProgramExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Program exercise service implementation for managing program-exercise relationship business logic.
 * Implémentation du service d'exercices de programme pour gérer la logique métier de relation programme-exercice.
 */
@Service
public class ProgramExerciseServiceImpl implements ProgramExerciseService {
    
    @Autowired
    private ProgramExerciseRepository programExerciseRepository;
    
    @Autowired
    private TrainingProgramRepository trainingProgramRepository;
    
    @Autowired
    private ExerciseRepository exerciseRepository;
    
    @Override
    public List<ProgramExerciseDto> getExercisesByProgramId(Long programId) {
        List<ProgramExercise> programExercises = programExerciseRepository.findByTrainingProgramIdWithExercise(programId);
        return programExercises.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    @Override
    public Optional<ProgramExerciseDto> getProgramExerciseById(Long id) {
        return programExerciseRepository.findById(id)
                .map(this::convertToDto);
    }
    
    @Override
    public ProgramExerciseDto addExerciseToProgram(Long programId, CreateProgramExerciseRequest request) {
        TrainingProgram trainingProgram = trainingProgramRepository.findById(programId)
                .orElseThrow(() -> new IllegalArgumentException("Programme d'entraînement non trouvé avec l'ID: " + programId));
        
        Exercise exercise = exerciseRepository.findById(request.getExerciseId())
                .orElseThrow(() -> new IllegalArgumentException("Exercice non trouvé avec l'ID: " + request.getExerciseId()));
        
        ProgramExercise programExercise = new ProgramExercise();
        programExercise.setTrainingProgram(trainingProgram);
        programExercise.setExercise(exercise);
        programExercise.setSetsCount(request.getSetsCount());
        programExercise.setRepsCount(request.getRepsCount());
        programExercise.setRestDurationSeconds(request.getRestDurationSeconds());
        programExercise.setWeightKg(request.getWeightKg());
        programExercise.setDistanceMeters(request.getDistanceMeters());
        programExercise.setNotes(request.getNotes());
        programExercise.setCreatedAt(LocalDateTime.now());
        programExercise.setUpdatedAt(LocalDateTime.now());
        
        ProgramExercise savedProgramExercise = programExerciseRepository.save(programExercise);
        
        return convertToDto(savedProgramExercise);
    }
    
    private ProgramExerciseDto convertToDto(ProgramExercise programExercise) {
        ProgramExerciseDto dto = new ProgramExerciseDto();
        dto.setId(programExercise.getId());
        dto.setTrainingProgramId(programExercise.getTrainingProgram().getId());
        dto.setExerciseId(programExercise.getExercise().getId());
        dto.setExerciseName(programExercise.getExercise().getName());
        dto.setExerciseDescription(programExercise.getExercise().getDescription());
        dto.setExerciseCategory(programExercise.getExercise().getCategory());
        dto.setExerciseMuscleGroup(programExercise.getExercise().getMuscleGroup());
        dto.setExerciseEquipmentNeeded(programExercise.getExercise().getEquipmentNeeded());
        dto.setExerciseDifficultyLevel(programExercise.getExercise().getDifficultyLevel());
        dto.setSetsCount(programExercise.getSetsCount());
        dto.setRepsCount(programExercise.getRepsCount());
        dto.setRestDurationSeconds(programExercise.getRestDurationSeconds());
        dto.setWeightKg(programExercise.getWeightKg()); 
        dto.setDistanceMeters(programExercise.getDistanceMeters());
        dto.setNotes(programExercise.getNotes());
        dto.setCreatedAt(programExercise.getCreatedAt());
        dto.setUpdatedAt(programExercise.getUpdatedAt());
        return dto;
    }
} 