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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("ExerciseServiceImpl Tests")
class ExerciseServiceImplTest {

    @Mock
    private ExerciseRepository exerciseRepository;

    @InjectMocks
    private ExerciseServiceImpl exerciseService;

    private Exercise exercise;
    private CreateExerciseRequest createRequest;
    private ExerciseDto exerciseDto;

    /**
     * Set up test data before each test.
     * Configure les données de test avant chaque test.
     */
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

    /**
     * Test successful exercise creation.
     * Teste la création réussie d'un exercice.
     */
    @Test
    @DisplayName("Should create exercise successfully")
    void shouldCreateExerciseSuccessfully() {
        when(exerciseRepository.save(any(Exercise.class))).thenReturn(exercise);

        ExerciseDto result = exerciseService.createExercise(createRequest);

        assertNotNull(result);
        assertEquals(exercise.getName(), result.getName());
        assertEquals(exercise.getDescription(), result.getDescription());
        assertEquals(exercise.getCategory(), result.getCategory());
        verify(exerciseRepository, times(1)).save(any(Exercise.class));
    }

    /**
     * Test successful retrieval of all active exercises.
     * Teste la récupération réussie de tous les exercices actifs.
     */
    @Test
    @DisplayName("Should get all active exercises")
    void shouldGetAllActiveExercises() {
        List<Exercise> exercises = Arrays.asList(exercise);
        when(exerciseRepository.findByIsActiveTrue()).thenReturn(exercises);

        List<ExerciseDto> result = exerciseService.getAllActiveExercises();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(exercise.getName(), result.get(0).getName());
        verify(exerciseRepository, times(1)).findByIsActiveTrue();
    }

    /**
     * Test successful retrieval of exercise by ID.
     * Teste la récupération réussie d'un exercice par ID.
     */
    @Test
    @DisplayName("Should get exercise by id when exists")
    void shouldGetExerciseByIdWhenExists() {
        when(exerciseRepository.findById(1L)).thenReturn(Optional.of(exercise));

        Optional<ExerciseDto> result = exerciseService.getExerciseById(1L);

        assertTrue(result.isPresent());
        assertEquals(exercise.getName(), result.get().getName());
        verify(exerciseRepository, times(1)).findById(1L);
    }

    /**
     * Test exercise retrieval failure when exercise does not exist.
     * Teste l'échec de récupération d'un exercice inexistant.
     */
    @Test
    @DisplayName("Should return empty when exercise not found")
    void shouldReturnEmptyWhenExerciseNotFound() {
        when(exerciseRepository.findById(999L)).thenReturn(Optional.empty());

        Optional<ExerciseDto> result = exerciseService.getExerciseById(999L);

        assertFalse(result.isPresent());
        verify(exerciseRepository, times(1)).findById(999L);
    }



    @Test
    @DisplayName("Should update exercise successfully")
    void shouldUpdateExerciseSuccessfully() {
        when(exerciseRepository.findById(1L)).thenReturn(Optional.of(exercise));
        when(exerciseRepository.save(any(Exercise.class))).thenReturn(exercise);

        ExerciseDto result = exerciseService.updateExercise(1L, createRequest);

        assertNotNull(result);
        assertEquals(exercise.getName(), result.getName());
        verify(exerciseRepository, times(1)).findById(1L);
        verify(exerciseRepository, times(1)).save(any(Exercise.class));
    }

    @Test
    @DisplayName("Should throw exception when updating non-existent exercise")
    void shouldThrowExceptionWhenUpdatingNonExistentExercise() {
        when(exerciseRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            exerciseService.updateExercise(999L, createRequest);
        });
        verify(exerciseRepository, times(1)).findById(999L);
        verify(exerciseRepository, never()).save(any(Exercise.class));
    }

    @Test
    @DisplayName("Should delete exercise successfully")
    void shouldDeleteExerciseSuccessfully() {
        when(exerciseRepository.findById(1L)).thenReturn(Optional.of(exercise));
        when(exerciseRepository.save(any(Exercise.class))).thenReturn(exercise);

        exerciseService.deleteExercise(1L);

        verify(exerciseRepository, times(1)).findById(1L);
        verify(exerciseRepository, times(1)).save(any(Exercise.class));
        assertFalse(exercise.getIsActive());
    }

    @Test
    @DisplayName("Should throw exception when deleting non-existent exercise")
    void shouldThrowExceptionWhenDeletingNonExistentExercise() {
        when(exerciseRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            exerciseService.deleteExercise(999L);
        });
        verify(exerciseRepository, times(1)).findById(999L);
        verify(exerciseRepository, never()).save(any(Exercise.class));
    }

    @Test
    @DisplayName("Should search exercises by name")
    void shouldSearchExercisesByName() {
        List<Exercise> exercises = Arrays.asList(exercise);
        when(exerciseRepository.findByNameContainingIgnoreCaseAndIsActiveTrue("pompes")).thenReturn(exercises);

        List<ExerciseDto> result = exerciseService.searchExercisesByName("pompes");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(exercise.getName(), result.get(0).getName());
        verify(exerciseRepository, times(1)).findByNameContainingIgnoreCaseAndIsActiveTrue("pompes");
    }

    @Test
    @DisplayName("Should get exercises by category")
    void shouldGetExercisesByCategory() {
        List<Exercise> exercises = Arrays.asList(exercise);
        when(exerciseRepository.findByCategoryAndIsActiveTrue("Musculation")).thenReturn(exercises);

        List<ExerciseDto> result = exerciseService.getExercisesByCategory("Musculation");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(exercise.getCategory(), result.get(0).getCategory());
        verify(exerciseRepository, times(1)).findByCategoryAndIsActiveTrue("Musculation");
    }

    @Test
    @DisplayName("Should convert exercise to dto correctly")
    void shouldConvertExerciseToDtoCorrectly() {
        ExerciseDto result = exerciseService.convertToDto(exercise);

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
        List<Exercise> exercises = Arrays.asList(exercise);

        List<ExerciseDto> result = exerciseService.convertToDtoList(exercises);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(exercise.getName(), result.get(0).getName());
        assertEquals(exercise.getCategory(), result.get(0).getCategory());
    }
} 