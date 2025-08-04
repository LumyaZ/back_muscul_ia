package com.example.muscul_ia.service.impl;

import com.example.muscul_ia.dto.ProgramExerciseDto;
import com.example.muscul_ia.entity.Exercise;
import com.example.muscul_ia.entity.ProgramExercise;
import com.example.muscul_ia.entity.TrainingProgram;
import com.example.muscul_ia.entity.User;
import com.example.muscul_ia.repository.ProgramExerciseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("ProgramExerciseServiceImpl Tests")
class ProgramExerciseServiceImplTest {

    @Mock
    private ProgramExerciseRepository programExerciseRepository;

    @InjectMocks
    private ProgramExerciseServiceImpl programExerciseService;

    private ProgramExercise programExercise;
    private Exercise exercise;
    private TrainingProgram trainingProgram;
    private User user;
    private List<ProgramExercise> programExerciseList;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");

        trainingProgram = new TrainingProgram();
        trainingProgram.setId(1L);
        trainingProgram.setName("Programme Test");
        trainingProgram.setCreatedByUser(user);

        exercise = new Exercise();
        exercise.setId(1L);
        exercise.setName("Pompes");
        exercise.setDescription("Exercice de musculation pour les pectoraux");
        exercise.setCategory("Musculation");
        exercise.setMuscleGroup("Pectoraux");
        exercise.setEquipmentNeeded("Poids du corps");
        exercise.setDifficultyLevel("Débutant");

        programExercise = new ProgramExercise();
        programExercise.setId(1L);
        programExercise.setTrainingProgram(trainingProgram);
        programExercise.setExercise(exercise);
        programExercise.setSetsCount(3);
        programExercise.setRepsCount(12);
        programExercise.setRestDurationSeconds(90);
        programExercise.setWeightKg(0.0);
        programExercise.setDistanceMeters(0.0);
        programExercise.setNotes("Exercice de base");
        programExercise.setCreatedAt(LocalDateTime.now());
        programExercise.setUpdatedAt(LocalDateTime.now());

        programExerciseList = Arrays.asList(programExercise);
    }

    @Test
    @DisplayName("Should get exercises by program id successfully")
    void shouldGetExercisesByProgramIdSuccessfully() {
        // Given
        Long programId = 1L;
        when(programExerciseRepository.findByTrainingProgramIdWithExercise(programId))
                .thenReturn(programExerciseList);

        // When
        List<ProgramExerciseDto> result = programExerciseService.getExercisesByProgramId(programId);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        
        ProgramExerciseDto dto = result.get(0);
        assertEquals(programExercise.getId(), dto.getId());
        assertEquals(programExercise.getTrainingProgram().getId(), dto.getTrainingProgramId());
        assertEquals(programExercise.getExercise().getId(), dto.getExerciseId());
        assertEquals(programExercise.getExercise().getName(), dto.getExerciseName());
        assertEquals(programExercise.getExercise().getDescription(), dto.getExerciseDescription());
        assertEquals(programExercise.getExercise().getCategory(), dto.getExerciseCategory());
        assertEquals(programExercise.getExercise().getMuscleGroup(), dto.getExerciseMuscleGroup());
        assertEquals(programExercise.getExercise().getEquipmentNeeded(), dto.getExerciseEquipmentNeeded());
        assertEquals(programExercise.getExercise().getDifficultyLevel(), dto.getExerciseDifficultyLevel());
        assertEquals(programExercise.getSetsCount(), dto.getSetsCount());
        assertEquals(programExercise.getRepsCount(), dto.getRepsCount());
        assertEquals(programExercise.getRestDurationSeconds(), dto.getRestDurationSeconds());
        assertEquals(programExercise.getWeightKg(), dto.getWeightKg());
        assertEquals(programExercise.getDistanceMeters(), dto.getDistanceMeters());
        assertEquals(programExercise.getNotes(), dto.getNotes());
        assertEquals(programExercise.getCreatedAt(), dto.getCreatedAt());
        assertEquals(programExercise.getUpdatedAt(), dto.getUpdatedAt());

        verify(programExerciseRepository, times(1)).findByTrainingProgramIdWithExercise(programId);
    }

    @Test
    @DisplayName("Should return empty list when no exercises found for program")
    void shouldReturnEmptyListWhenNoExercisesFoundForProgram() {
        // Given
        Long programId = 999L;
        when(programExerciseRepository.findByTrainingProgramIdWithExercise(programId))
                .thenReturn(Arrays.asList());

        // When
        List<ProgramExerciseDto> result = programExerciseService.getExercisesByProgramId(programId);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(programExerciseRepository, times(1)).findByTrainingProgramIdWithExercise(programId);
    }

    @Test
    @DisplayName("Should get program exercise by id when exists")
    void shouldGetProgramExerciseByIdWhenExists() {
        // Given
        Long exerciseId = 1L;
        when(programExerciseRepository.findById(exerciseId))
                .thenReturn(Optional.of(programExercise));

        // When
        Optional<ProgramExerciseDto> result = programExerciseService.getProgramExerciseById(exerciseId);

        // Then
        assertTrue(result.isPresent());
        
        ProgramExerciseDto dto = result.get();
        assertEquals(programExercise.getId(), dto.getId());
        assertEquals(programExercise.getTrainingProgram().getId(), dto.getTrainingProgramId());
        assertEquals(programExercise.getExercise().getId(), dto.getExerciseId());
        assertEquals(programExercise.getExercise().getName(), dto.getExerciseName());

        verify(programExerciseRepository, times(1)).findById(exerciseId);
    }

    @Test
    @DisplayName("Should return empty when program exercise does not exist")
    void shouldReturnEmptyWhenProgramExerciseDoesNotExist() {
        // Given
        Long exerciseId = 999L;
        when(programExerciseRepository.findById(exerciseId))
                .thenReturn(Optional.empty());

        // When
        Optional<ProgramExerciseDto> result = programExerciseService.getProgramExerciseById(exerciseId);

        // Then
        assertFalse(result.isPresent());

        verify(programExerciseRepository, times(1)).findById(exerciseId);
    }

    @Test
    @DisplayName("Should handle null values in program exercise gracefully")
    void shouldHandleNullValuesInProgramExerciseGracefully() {
        // Given
        ProgramExercise programExerciseWithNulls = new ProgramExercise();
        programExerciseWithNulls.setId(2L);
        programExerciseWithNulls.setTrainingProgram(trainingProgram);
        programExerciseWithNulls.setExercise(exercise);
        programExerciseWithNulls.setSetsCount(null);
        programExerciseWithNulls.setRepsCount(null);
        programExerciseWithNulls.setRestDurationSeconds(null);
        programExerciseWithNulls.setWeightKg(null);
        programExerciseWithNulls.setDistanceMeters(null);
        programExerciseWithNulls.setNotes(null);
        programExerciseWithNulls.setCreatedAt(LocalDateTime.now());
        programExerciseWithNulls.setUpdatedAt(LocalDateTime.now());

        Long programId = 1L;
        when(programExerciseRepository.findByTrainingProgramIdWithExercise(programId))
                .thenReturn(Arrays.asList(programExerciseWithNulls));

        // When
        List<ProgramExerciseDto> result = programExerciseService.getExercisesByProgramId(programId);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        
        ProgramExerciseDto dto = result.get(0);
        assertEquals(programExerciseWithNulls.getId(), dto.getId());
        assertNull(dto.getSetsCount());
        assertNull(dto.getRepsCount());
        assertNull(dto.getRestDurationSeconds());
        assertNull(dto.getWeightKg());
        assertNull(dto.getDistanceMeters());
        assertNull(dto.getNotes());

        verify(programExerciseRepository, times(1)).findByTrainingProgramIdWithExercise(programId);
    }

    @Test
    @DisplayName("Should handle repository exception gracefully")
    void shouldHandleRepositoryExceptionGracefully() {
        // Given
        Long programId = 1L;
        when(programExerciseRepository.findByTrainingProgramIdWithExercise(programId))
                .thenThrow(new RuntimeException("Database error"));

        // When & Then
        assertThrows(RuntimeException.class, () -> {
            programExerciseService.getExercisesByProgramId(programId);
        });

        verify(programExerciseRepository, times(1)).findByTrainingProgramIdWithExercise(programId);
    }
} 