package com.example.muscul_ia.service.impl;

import com.example.muscul_ia.dto.CreateTrainingProgramRequest;
import com.example.muscul_ia.dto.TrainingProgramDto;
import com.example.muscul_ia.entity.Exercise;
import com.example.muscul_ia.entity.TrainingProgram;
import com.example.muscul_ia.entity.User;
import com.example.muscul_ia.repository.ExerciseRepository;
import com.example.muscul_ia.repository.ProgramExerciseRepository;
import com.example.muscul_ia.repository.TrainingProgramRepository;
import com.example.muscul_ia.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;  
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
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

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");

        exercise = new Exercise();
        exercise.setId(1L);
        exercise.setName("Pompes");
        exercise.setDescription("Exercice de pompes");
        exercise.setCategory("Musculation");

        trainingProgram = new TrainingProgram();
        trainingProgram.setId(1L);
        trainingProgram.setName("Programme Test");
        trainingProgram.setDescription("Description du programme");
        trainingProgram.setDifficultyLevel("Débutant");
        trainingProgram.setCategory("Musculation");
        trainingProgram.setTargetAudience("Débutants");
        trainingProgram.setCreatedByUser(user);
        trainingProgram.setCreatedAt(LocalDateTime.now());

        createRequest = new CreateTrainingProgramRequest();
        createRequest.setName("Nouveau Programme");
        createRequest.setDescription("Description du nouveau programme");
        createRequest.setDifficultyLevel("Intermédiaire");
        createRequest.setCategory("Musculation");
        createRequest.setTargetAudience("Sportifs confirmés");
    }

    @Test
    @DisplayName("Should create training program successfully")
    void shouldCreateTrainingProgramSuccessfully() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(trainingProgramRepository.save(any(TrainingProgram.class))).thenReturn(trainingProgram);

        TrainingProgramDto result = trainingProgramService.createTrainingProgram(createRequest, 1L);

        assertNotNull(result);
        assertEquals(trainingProgram.getName(), result.getName());
        assertEquals(trainingProgram.getDescription(), result.getDescription());
        verify(userRepository, times(1)).findById(1L);
        verify(trainingProgramRepository, times(1)).save(any(TrainingProgram.class));
    }

    @Test
    @DisplayName("Should throw exception when user not found during creation")
    void shouldThrowExceptionWhenUserNotFoundDuringCreation() {
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            trainingProgramService.createTrainingProgram(createRequest, 999L);
        });
        verify(userRepository, times(1)).findById(999L);
        verify(trainingProgramRepository, never()).save(any(TrainingProgram.class));
    }

    @Test
    @DisplayName("Should get all programs")
    void shouldGetAllPrograms() {
        List<TrainingProgram> programs = Arrays.asList(trainingProgram);
        when(trainingProgramRepository.findAll()).thenReturn(programs);

        List<TrainingProgramDto> result = trainingProgramService.getAllActivePrograms();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(trainingProgram.getName(), result.get(0).getName());
        verify(trainingProgramRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should get program by id when exists")
    void shouldGetProgramByIdWhenExists() {
        when(trainingProgramRepository.findById(1L)).thenReturn(Optional.of(trainingProgram));

        Optional<TrainingProgramDto> result = trainingProgramService.getProgramById(1L);

        assertTrue(result.isPresent());
        assertEquals(trainingProgram.getName(), result.get().getName());
        verify(trainingProgramRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Should return empty when program not found")
    void shouldReturnEmptyWhenProgramNotFound() {
        when(trainingProgramRepository.findById(999L)).thenReturn(Optional.empty());

        Optional<TrainingProgramDto> result = trainingProgramService.getProgramById(999L);

        assertFalse(result.isPresent());
        verify(trainingProgramRepository, times(1)).findById(999L);
    }

    @Test
    @DisplayName("Should search programs by name")
    void shouldSearchProgramsByName() {
        List<TrainingProgram> programs = Arrays.asList(trainingProgram);
        when(trainingProgramRepository.findAll()).thenReturn(programs);

        List<TrainingProgramDto> result = trainingProgramService.searchProgramsByName("Test");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(trainingProgram.getName(), result.get(0).getName());
        verify(trainingProgramRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should convert training program to dto correctly")
    void shouldConvertTrainingProgramToDtoCorrectly() {
        TrainingProgramDto result = trainingProgramService.convertToDto(trainingProgram);

        assertNotNull(result);
        assertEquals(trainingProgram.getId(), result.getId());
        assertEquals(trainingProgram.getName(), result.getName());
        assertEquals(trainingProgram.getDescription(), result.getDescription());
        assertEquals(trainingProgram.getDifficultyLevel(), result.getDifficultyLevel());
        assertEquals(trainingProgram.getCategory(), result.getCategory());
        assertEquals(trainingProgram.getTargetAudience(), result.getTargetAudience());
        assertEquals(trainingProgram.getCreatedByUser().getId(), result.getCreatedByUserId());
        assertEquals(trainingProgram.getCreatedAt(), result.getCreatedAt());
    }

    @Test
    @DisplayName("Should convert training program list to dto list correctly")
    void shouldConvertTrainingProgramListToDtoListCorrectly() {
        List<TrainingProgram> programs = Arrays.asList(trainingProgram);
        
        List<TrainingProgramDto> result = trainingProgramService.convertToDtoList(programs);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(trainingProgram.getName(), result.get(0).getName());
    }
} 