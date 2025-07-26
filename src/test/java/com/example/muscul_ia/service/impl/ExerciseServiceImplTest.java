package com.example.muscul_ia.service.impl;

import com.example.muscul_ia.dto.CreateExerciseRequest;
import com.example.muscul_ia.dto.ExerciseDto;
import com.example.muscul_ia.entity.Exercise;
import com.example.muscul_ia.repository.ExerciseRepository;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DisplayName("ExerciseServiceImpl Tests")
class ExerciseServiceImplTest {

    @Mock
    private ExerciseRepository exerciseRepository;

    @InjectMocks
    private ExerciseServiceImpl exerciseService;

    private Exercise exercise;
    private CreateExerciseRequest createRequest;
    private ExerciseDto exerciseDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        exercise = new Exercise();
        exercise.setId(1L);
        exercise.setName("Pompes");
        exercise.setDescription("Exercice de musculation");
        exercise.setCategory("Musculation");
        exercise.setMuscleGroup("Pectoraux");
        exercise.setEquipmentNeeded("Poids du corps");
        exercise.setDifficultyLevel("Débutant");
        exercise.setIsActive(true);
        exercise.setCreatedAt(LocalDateTime.now());

        createRequest = new CreateExerciseRequest();
        createRequest.setName("Squats");
        createRequest.setDescription("Exercice pour les jambes");
        createRequest.setCategory("Musculation");
        createRequest.setMuscleGroup("Jambes");
        createRequest.setEquipmentNeeded("Poids du corps");
        createRequest.setDifficultyLevel("Débutant");

        exerciseDto = new ExerciseDto();
        exerciseDto.setId(1L);
        exerciseDto.setName("Pompes");
        exerciseDto.setDescription("Exercice de musculation");
        exerciseDto.setCategory("Musculation");
    }

    @Test
    @DisplayName("Should create exercise successfully")
    void shouldCreateExerciseSuccessfully() {
        // Given
        when(exerciseRepository.save(any(Exercise.class))).thenReturn(exercise);

        // When
        ExerciseDto result = exerciseService.createExercise(createRequest);

        // Then
        assertNotNull(result);
        assertEquals(exercise.getName(), result.getName());
        assertEquals(exercise.getDescription(), result.getDescription());
        assertEquals(exercise.getCategory(), result.getCategory());
        verify(exerciseRepository, times(1)).save(any(Exercise.class));
    }

    @Test
    @DisplayName("Should get all active exercises")
    void shouldGetAllActiveExercises() {
        // Given
        List<Exercise> exercises = Arrays.asList(exercise);
        when(exerciseRepository.findByIsActiveTrue()).thenReturn(exercises);

        // When
        List<ExerciseDto> result = exerciseService.getAllActiveExercises();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(exercise.getName(), result.get(0).getName());
        verify(exerciseRepository, times(1)).findByIsActiveTrue();
    }

    @Test
    @DisplayName("Should get exercise by id when exists")
    void shouldGetExerciseByIdWhenExists() {
        // Given
        when(exerciseRepository.findById(1L)).thenReturn(Optional.of(exercise));

        // When
        Optional<ExerciseDto> result = exerciseService.getExerciseById(1L);

        // Then
        assertTrue(result.isPresent());
        assertEquals(exercise.getName(), result.get().getName());
        verify(exerciseRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Should return empty when exercise not found")
    void shouldReturnEmptyWhenExerciseNotFound() {
        // Given
        when(exerciseRepository.findById(999L)).thenReturn(Optional.empty());

        // When
        Optional<ExerciseDto> result = exerciseService.getExerciseById(999L);

        // Then
        assertFalse(result.isPresent());
        verify(exerciseRepository, times(1)).findById(999L);
    }

    @Test
    @DisplayName("Should get exercise entity by id")
    void shouldGetExerciseEntityById() {
        // Given
        when(exerciseRepository.findById(1L)).thenReturn(Optional.of(exercise));

        // When
        Optional<Exercise> result = exerciseService.getExerciseEntityById(1L);

        // Then
        assertTrue(result.isPresent());
        assertEquals(exercise, result.get());
        verify(exerciseRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Should update exercise successfully")
    void shouldUpdateExerciseSuccessfully() {
        // Given
        when(exerciseRepository.findById(1L)).thenReturn(Optional.of(exercise));
        when(exerciseRepository.save(any(Exercise.class))).thenReturn(exercise);

        // When
        ExerciseDto result = exerciseService.updateExercise(1L, createRequest);

        // Then
        assertNotNull(result);
        assertEquals(exercise.getName(), result.getName());
        verify(exerciseRepository, times(1)).findById(1L);
        verify(exerciseRepository, times(1)).save(any(Exercise.class));
    }

    @Test
    @DisplayName("Should throw exception when updating non-existent exercise")
    void shouldThrowExceptionWhenUpdatingNonExistentExercise() {
        // Given
        when(exerciseRepository.findById(999L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(RuntimeException.class, () -> {
            exerciseService.updateExercise(999L, createRequest);
        });
        verify(exerciseRepository, times(1)).findById(999L);
        verify(exerciseRepository, never()).save(any(Exercise.class));
    }

    @Test
    @DisplayName("Should delete exercise successfully")
    void shouldDeleteExerciseSuccessfully() {
        // Given
        when(exerciseRepository.findById(1L)).thenReturn(Optional.of(exercise));
        when(exerciseRepository.save(any(Exercise.class))).thenReturn(exercise);

        // When
        exerciseService.deleteExercise(1L);

        // Then
        verify(exerciseRepository, times(1)).findById(1L);
        verify(exerciseRepository, times(1)).save(any(Exercise.class));
        assertFalse(exercise.getIsActive());
    }

    @Test
    @DisplayName("Should throw exception when deleting non-existent exercise")
    void shouldThrowExceptionWhenDeletingNonExistentExercise() {
        // Given
        when(exerciseRepository.findById(999L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(RuntimeException.class, () -> {
            exerciseService.deleteExercise(999L);
        });
        verify(exerciseRepository, times(1)).findById(999L);
        verify(exerciseRepository, never()).save(any(Exercise.class));
    }

    @Test
    @DisplayName("Should search exercises by name")
    void shouldSearchExercisesByName() {
        // Given
        List<Exercise> exercises = Arrays.asList(exercise);
        when(exerciseRepository.findByNameContainingIgnoreCaseAndIsActiveTrue("pompes")).thenReturn(exercises);

        // When
        List<ExerciseDto> result = exerciseService.searchExercisesByName("pompes");

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(exercise.getName(), result.get(0).getName());
        verify(exerciseRepository, times(1)).findByNameContainingIgnoreCaseAndIsActiveTrue("pompes");
    }

    @Test
    @DisplayName("Should get exercises by category")
    void shouldGetExercisesByCategory() {
        // Given
        List<Exercise> exercises = Arrays.asList(exercise);
        when(exerciseRepository.findByCategoryAndIsActiveTrue("Musculation")).thenReturn(exercises);

        // When
        List<ExerciseDto> result = exerciseService.getExercisesByCategory("Musculation");

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(exercise.getCategory(), result.get(0).getCategory());
        verify(exerciseRepository, times(1)).findByCategoryAndIsActiveTrue("Musculation");
    }

    @Test
    @DisplayName("Should convert exercise to dto correctly")
    void shouldConvertExerciseToDtoCorrectly() {
        // When
        ExerciseDto result = exerciseService.convertToDto(exercise);

        // Then
        assertNotNull(result);
        assertEquals(exercise.getId(), result.getId());
        assertEquals(exercise.getName(), result.getName());
        assertEquals(exercise.getDescription(), result.getDescription());
        assertEquals(exercise.getCategory(), result.getCategory());
        assertEquals(exercise.getMuscleGroup(), result.getMuscleGroup());
        assertEquals(exercise.getEquipmentNeeded(), result.getEquipmentNeeded());
        assertEquals(exercise.getDifficultyLevel(), result.getDifficultyLevel());
        assertEquals(exercise.getIsActive(), result.getIsActive());
        assertEquals(exercise.getCreatedAt(), result.getCreatedAt());
    }

    @Test
    @DisplayName("Should convert exercise list to dto list correctly")
    void shouldConvertExerciseListToDtoListCorrectly() {
        // Given
        List<Exercise> exercises = Arrays.asList(exercise);

        // When
        List<ExerciseDto> result = exerciseService.convertToDtoList(exercises);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(exercise.getName(), result.get(0).getName());
        assertEquals(exercise.getCategory(), result.get(0).getCategory());
    }
} 