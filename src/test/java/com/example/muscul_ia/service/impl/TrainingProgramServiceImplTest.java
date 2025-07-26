package com.example.muscul_ia.service.impl;

import com.example.muscul_ia.dto.CreateTrainingProgramRequest;
import com.example.muscul_ia.dto.TrainingProgramDto;
import com.example.muscul_ia.entity.Exercise;
import com.example.muscul_ia.entity.ProgramExercise;
import com.example.muscul_ia.entity.TrainingProgram;
import com.example.muscul_ia.entity.User;
import com.example.muscul_ia.repository.ExerciseRepository;
import com.example.muscul_ia.repository.ProgramExerciseRepository;
import com.example.muscul_ia.repository.TrainingProgramRepository;
import com.example.muscul_ia.repository.UserRepository;
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

@DisplayName("TrainingProgramServiceImpl Tests")
class TrainingProgramServiceImplTest {

    @Mock
    private TrainingProgramRepository trainingProgramRepository;

    @Mock
    private ProgramExerciseRepository programExerciseRepository;

    @Mock
    private ExerciseRepository exerciseRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TrainingProgramServiceImpl trainingProgramService;

    private TrainingProgram trainingProgram;
    private User user;
    private Exercise exercise;
    private CreateTrainingProgramRequest createRequest;
    private CreateTrainingProgramRequest.ProgramExerciseRequest exerciseRequest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");

        exercise = new Exercise();
        exercise.setId(1L);
        exercise.setName("Pompes");
        exercise.setCategory("Musculation");

        trainingProgram = new TrainingProgram();
        trainingProgram.setId(1L);
        trainingProgram.setName("Programme Test");
        trainingProgram.setDescription("Description du programme");
        trainingProgram.setDifficultyLevel("Débutant");
        trainingProgram.setDurationWeeks(8);
        trainingProgram.setSessionsPerWeek(3);
        trainingProgram.setEstimatedDurationMinutes(45);
        trainingProgram.setCategory("Musculation");
        trainingProgram.setTargetAudience("Débutants");
        trainingProgram.setEquipmentRequired("Poids du corps");
        trainingProgram.setIsPublic(false);
        trainingProgram.setIsActive(true);
        trainingProgram.setCreatedByUser(user);
        trainingProgram.setCreatedAt(LocalDateTime.now());

        createRequest = new CreateTrainingProgramRequest();
        createRequest.setName("Nouveau Programme");
        createRequest.setDescription("Description du nouveau programme");
        createRequest.setDifficultyLevel("Intermédiaire");
        createRequest.setDurationWeeks(12);
        createRequest.setSessionsPerWeek(4);
        createRequest.setEstimatedDurationMinutes(60);
        createRequest.setCategory("Musculation");
        createRequest.setTargetAudience("Sportifs confirmés");
        createRequest.setEquipmentRequired("Barre, haltères");
        createRequest.setIsPublic(true);

        exerciseRequest = new CreateTrainingProgramRequest.ProgramExerciseRequest();
        exerciseRequest.setExerciseId(1L);
        exerciseRequest.setOrderInProgram(1);
        exerciseRequest.setSetsCount(3);
        exerciseRequest.setRepsCount(12);
        exerciseRequest.setIsOptional(false);

        createRequest.setExercises(Arrays.asList(exerciseRequest));
    }

    @Test
    @DisplayName("Should create training program successfully")
    void shouldCreateTrainingProgramSuccessfully() {
        // Given
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(trainingProgramRepository.save(any(TrainingProgram.class))).thenReturn(trainingProgram);
        when(exerciseRepository.findById(1L)).thenReturn(Optional.of(exercise));
        when(programExerciseRepository.save(any(ProgramExercise.class))).thenReturn(new ProgramExercise());

        // When
        TrainingProgramDto result = trainingProgramService.createTrainingProgram(createRequest, 1L);

        // Then
        assertNotNull(result);
        assertEquals(trainingProgram.getName(), result.getName());
        assertEquals(trainingProgram.getDescription(), result.getDescription());
        verify(userRepository, times(1)).findById(1L);
        verify(trainingProgramRepository, times(1)).save(any(TrainingProgram.class));
        verify(programExerciseRepository, times(1)).save(any(ProgramExercise.class));
    }

    @Test
    @DisplayName("Should throw exception when user not found during creation")
    void shouldThrowExceptionWhenUserNotFoundDuringCreation() {
        // Given
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(RuntimeException.class, () -> {
            trainingProgramService.createTrainingProgram(createRequest, 999L);
        });
        verify(userRepository, times(1)).findById(999L);
        verify(trainingProgramRepository, never()).save(any(TrainingProgram.class));
    }

    @Test
    @DisplayName("Should get all active programs")
    void shouldGetAllActivePrograms() {
        // Given
        List<TrainingProgram> programs = Arrays.asList(trainingProgram);
        when(trainingProgramRepository.findByIsActiveTrue()).thenReturn(programs);

        // When
        List<TrainingProgramDto> result = trainingProgramService.getAllActivePrograms();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(trainingProgram.getName(), result.get(0).getName());
        verify(trainingProgramRepository, times(1)).findByIsActiveTrue();
    }

    @Test
    @DisplayName("Should get all public active programs")
    void shouldGetAllPublicActivePrograms() {
        // Given
        List<TrainingProgram> programs = Arrays.asList(trainingProgram);
        when(trainingProgramRepository.findByIsPublicTrueAndIsActiveTrue()).thenReturn(programs);

        // When
        List<TrainingProgramDto> result = trainingProgramService.getAllPublicActivePrograms();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(trainingProgram.getName(), result.get(0).getName());
        verify(trainingProgramRepository, times(1)).findByIsPublicTrueAndIsActiveTrue();
    }

    @Test
    @DisplayName("Should get program by id when exists")
    void shouldGetProgramByIdWhenExists() {
        // Given
        when(trainingProgramRepository.findById(1L)).thenReturn(Optional.of(trainingProgram));

        // When
        Optional<TrainingProgramDto> result = trainingProgramService.getProgramById(1L);

        // Then
        assertTrue(result.isPresent());
        assertEquals(trainingProgram.getName(), result.get().getName());
        verify(trainingProgramRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Should return empty when program not found")
    void shouldReturnEmptyWhenProgramNotFound() {
        // Given
        when(trainingProgramRepository.findById(999L)).thenReturn(Optional.empty());

        // When
        Optional<TrainingProgramDto> result = trainingProgramService.getProgramById(999L);

        // Then
        assertFalse(result.isPresent());
        verify(trainingProgramRepository, times(1)).findById(999L);
    }

    @Test
    @DisplayName("Should get programs by user")
    void shouldGetProgramsByUser() {
        // Given
        List<TrainingProgram> programs = Arrays.asList(trainingProgram);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(trainingProgramRepository.findByCreatedByUserAndIsActiveTrue(user)).thenReturn(programs);

        // When
        List<TrainingProgramDto> result = trainingProgramService.getProgramsByUser(1L);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(trainingProgram.getName(), result.get(0).getName());
        verify(userRepository, times(1)).findById(1L);
        verify(trainingProgramRepository, times(1)).findByCreatedByUserAndIsActiveTrue(user);
    }

    @Test
    @DisplayName("Should return empty list when user not found")
    void shouldReturnEmptyListWhenUserNotFound() {
        // Given
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        // When
        List<TrainingProgramDto> result = trainingProgramService.getProgramsByUser(999L);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(userRepository, times(1)).findById(999L);
        verify(trainingProgramRepository, never()).findByCreatedByUserAndIsActiveTrue(any());
    }

    @Test
    @DisplayName("Should update program successfully")
    void shouldUpdateProgramSuccessfully() {
        // Given
        when(trainingProgramRepository.findById(1L)).thenReturn(Optional.of(trainingProgram));
        when(trainingProgramRepository.save(any(TrainingProgram.class))).thenReturn(trainingProgram);
        when(exerciseRepository.findById(1L)).thenReturn(Optional.of(exercise));
        when(programExerciseRepository.save(any(ProgramExercise.class))).thenReturn(new ProgramExercise());

        // When
        TrainingProgramDto result = trainingProgramService.updateProgram(1L, createRequest, 1L);

        // Then
        assertNotNull(result);
        assertEquals(trainingProgram.getName(), result.getName());
        verify(trainingProgramRepository, times(1)).findById(1L);
        verify(trainingProgramRepository, times(1)).save(any(TrainingProgram.class));
        verify(programExerciseRepository, times(1)).deleteByTrainingProgram(trainingProgram);
        verify(programExerciseRepository, times(1)).save(any(ProgramExercise.class));
    }

    @Test
    @DisplayName("Should throw exception when updating program with wrong user")
    void shouldThrowExceptionWhenUpdatingProgramWithWrongUser() {
        // Given
        when(trainingProgramRepository.findById(1L)).thenReturn(Optional.of(trainingProgram));

        // When & Then
        assertThrows(RuntimeException.class, () -> {
            trainingProgramService.updateProgram(1L, createRequest, 999L);
        });
        verify(trainingProgramRepository, times(1)).findById(1L);
        verify(trainingProgramRepository, never()).save(any(TrainingProgram.class));
    }

    @Test
    @DisplayName("Should delete program successfully")
    void shouldDeleteProgramSuccessfully() {
        // Given
        when(trainingProgramRepository.findById(1L)).thenReturn(Optional.of(trainingProgram));
        when(trainingProgramRepository.save(any(TrainingProgram.class))).thenReturn(trainingProgram);

        // When
        trainingProgramService.deleteProgram(1L, 1L);

        // Then
        verify(trainingProgramRepository, times(1)).findById(1L);
        verify(trainingProgramRepository, times(1)).save(any(TrainingProgram.class));
        verify(programExerciseRepository, times(1)).deleteByTrainingProgram(trainingProgram);
        assertFalse(trainingProgram.getIsActive());
    }

    @Test
    @DisplayName("Should throw exception when deleting program with wrong user")
    void shouldThrowExceptionWhenDeletingProgramWithWrongUser() {
        // Given
        when(trainingProgramRepository.findById(1L)).thenReturn(Optional.of(trainingProgram));

        // When & Then
        assertThrows(RuntimeException.class, () -> {
            trainingProgramService.deleteProgram(1L, 999L);
        });
        verify(trainingProgramRepository, times(1)).findById(1L);
        verify(trainingProgramRepository, never()).save(any(TrainingProgram.class));
    }

    @Test
    @DisplayName("Should search programs by name")
    void shouldSearchProgramsByName() {
        // Given
        List<TrainingProgram> programs = Arrays.asList(trainingProgram);
        when(trainingProgramRepository.findByNameContainingIgnoreCaseAndIsActiveTrue("test")).thenReturn(programs);

        // When
        List<TrainingProgramDto> result = trainingProgramService.searchProgramsByName("test");

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(trainingProgram.getName(), result.get(0).getName());
        verify(trainingProgramRepository, times(1)).findByNameContainingIgnoreCaseAndIsActiveTrue("test");
    }

    @Test
    @DisplayName("Should get public programs by difficulty level")
    void shouldGetPublicProgramsByDifficultyLevel() {
        // Given
        List<TrainingProgram> programs = Arrays.asList(trainingProgram);
        when(trainingProgramRepository.findByDifficultyLevelAndIsPublicTrueAndIsActiveTrue("Débutant")).thenReturn(programs);

        // When
        List<TrainingProgramDto> result = trainingProgramService.getPublicProgramsByDifficultyLevel("Débutant");

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(trainingProgram.getDifficultyLevel(), result.get(0).getDifficultyLevel());
        verify(trainingProgramRepository, times(1)).findByDifficultyLevelAndIsPublicTrueAndIsActiveTrue("Débutant");
    }

    @Test
    @DisplayName("Should convert training program to dto correctly")
    void shouldConvertTrainingProgramToDtoCorrectly() {
        // When
        TrainingProgramDto result = trainingProgramService.convertToDto(trainingProgram);

        // Then
        assertNotNull(result);
        assertEquals(trainingProgram.getId(), result.getId());
        assertEquals(trainingProgram.getName(), result.getName());
        assertEquals(trainingProgram.getDescription(), result.getDescription());
        assertEquals(trainingProgram.getDifficultyLevel(), result.getDifficultyLevel());
        assertEquals(trainingProgram.getDurationWeeks(), result.getDurationWeeks());
        assertEquals(trainingProgram.getSessionsPerWeek(), result.getSessionsPerWeek());
        assertEquals(trainingProgram.getEstimatedDurationMinutes(), result.getEstimatedDurationMinutes());
        assertEquals(trainingProgram.getCategory(), result.getCategory());
        assertEquals(trainingProgram.getTargetAudience(), result.getTargetAudience());
        assertEquals(trainingProgram.getEquipmentRequired(), result.getEquipmentRequired());
        assertEquals(trainingProgram.getIsPublic(), result.getIsPublic());
        assertEquals(trainingProgram.getIsActive(), result.getIsActive());
        assertEquals(trainingProgram.getCreatedAt(), result.getCreatedAt());
        assertEquals(trainingProgram.getCreatedByUser().getId(), result.getCreatedByUserId());
    }

    @Test
    @DisplayName("Should convert training program list to dto list correctly")
    void shouldConvertTrainingProgramListToDtoListCorrectly() {
        // Given
        List<TrainingProgram> programs = Arrays.asList(trainingProgram);

        // When
        List<TrainingProgramDto> result = trainingProgramService.convertToDtoList(programs);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(trainingProgram.getName(), result.get(0).getName());
        assertEquals(trainingProgram.getCategory(), result.get(0).getCategory());
    }
} 