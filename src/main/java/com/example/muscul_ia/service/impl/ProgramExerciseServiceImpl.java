package com.example.muscul_ia.service.impl;

import com.example.muscul_ia.dto.ProgramExerciseDto;
import com.example.muscul_ia.entity.ProgramExercise;
import com.example.muscul_ia.repository.ProgramExerciseRepository;
import com.example.muscul_ia.service.ProgramExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProgramExerciseServiceImpl implements ProgramExerciseService {
    
    @Autowired
    private ProgramExerciseRepository programExerciseRepository;
    
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
        dto.setOrderInProgram(programExercise.getOrderInProgram());
        dto.setSetsCount(programExercise.getSetsCount());
        dto.setRepsCount(programExercise.getRepsCount());
        dto.setDurationSeconds(programExercise.getDurationSeconds());
        dto.setRestDurationSeconds(programExercise.getRestDurationSeconds());
        dto.setWeightKg(programExercise.getWeightKg());
        dto.setDistanceMeters(programExercise.getDistanceMeters());
        dto.setNotes(programExercise.getNotes());
        dto.setIsOptional(programExercise.getIsOptional());
        dto.setCreatedAt(programExercise.getCreatedAt());
        dto.setUpdatedAt(programExercise.getUpdatedAt());
        return dto;
    }
} 