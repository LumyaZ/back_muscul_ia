package com.example.muscul_ia.controller;

import com.example.muscul_ia.config.TestSecurityConfig;
import com.example.muscul_ia.dto.CreateProgramExerciseRequest;
import com.example.muscul_ia.dto.ProgramExerciseDto;
import com.example.muscul_ia.service.ProgramExerciseService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Import(TestSecurityConfig.class)
@ActiveProfiles("test")
@DisplayName("ProgramExerciseController Tests")
class ProgramExerciseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProgramExerciseService programExerciseService;

    @Autowired
    private ObjectMapper objectMapper;

    private ProgramExerciseDto programExerciseDto;
    private List<ProgramExerciseDto> exerciseList;

    @BeforeEach
    void setUp() {
        programExerciseDto = new ProgramExerciseDto();
        programExerciseDto.setId(1L);
        programExerciseDto.setTrainingProgramId(1L);
        programExerciseDto.setExerciseId(1L);
        programExerciseDto.setExerciseName("Pompes");
        programExerciseDto.setExerciseDescription("Exercice de musculation pour les pectoraux");
        programExerciseDto.setExerciseCategory("Musculation");
        programExerciseDto.setExerciseMuscleGroup("Pectoraux");
        programExerciseDto.setExerciseEquipmentNeeded("Poids du corps");
        programExerciseDto.setExerciseDifficultyLevel("Débutant");
        programExerciseDto.setOrderInProgram(1);
        programExerciseDto.setSetsCount(3);
        programExerciseDto.setRepsCount(12);
        programExerciseDto.setDurationSeconds(60);
        programExerciseDto.setRestDurationSeconds(90);
        programExerciseDto.setWeightKg(0.0);
        programExerciseDto.setDistanceMeters(0.0);
        programExerciseDto.setNotes("Exercice de base");
        programExerciseDto.setIsOptional(false);
        programExerciseDto.setCreatedAt(LocalDateTime.now());
        programExerciseDto.setUpdatedAt(LocalDateTime.now());

        exerciseList = Arrays.asList(programExerciseDto);
    }

    @Test
    @DisplayName("Should get exercises by program id successfully")
    void shouldGetExercisesByProgramIdSuccessfully() throws Exception {
        // Given
        Long programId = 1L;
        when(programExerciseService.getExercisesByProgramId(programId)).thenReturn(exerciseList);

        // When & Then
        mockMvc.perform(get("/api/program-exercises/program/{programId}", programId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(programExerciseDto.getId()))
                .andExpect(jsonPath("$[0].trainingProgramId").value(programExerciseDto.getTrainingProgramId()))
                .andExpect(jsonPath("$[0].exerciseId").value(programExerciseDto.getExerciseId()))
                .andExpect(jsonPath("$[0].exerciseName").value(programExerciseDto.getExerciseName()))
                .andExpect(jsonPath("$[0].exerciseDescription").value(programExerciseDto.getExerciseDescription()))
                .andExpect(jsonPath("$[0].exerciseCategory").value(programExerciseDto.getExerciseCategory()))
                .andExpect(jsonPath("$[0].exerciseMuscleGroup").value(programExerciseDto.getExerciseMuscleGroup()))
                .andExpect(jsonPath("$[0].exerciseEquipmentNeeded").value(programExerciseDto.getExerciseEquipmentNeeded()))
                .andExpect(jsonPath("$[0].exerciseDifficultyLevel").value(programExerciseDto.getExerciseDifficultyLevel()))
                .andExpect(jsonPath("$[0].orderInProgram").value(programExerciseDto.getOrderInProgram()))
                .andExpect(jsonPath("$[0].setsCount").value(programExerciseDto.getSetsCount()))
                .andExpect(jsonPath("$[0].repsCount").value(programExerciseDto.getRepsCount()))
                .andExpect(jsonPath("$[0].durationSeconds").value(programExerciseDto.getDurationSeconds()))
                .andExpect(jsonPath("$[0].restDurationSeconds").value(programExerciseDto.getRestDurationSeconds()))
                .andExpect(jsonPath("$[0].weightKg").value(programExerciseDto.getWeightKg()))
                .andExpect(jsonPath("$[0].distanceMeters").value(programExerciseDto.getDistanceMeters()))
                .andExpect(jsonPath("$[0].notes").value(programExerciseDto.getNotes()))
                .andExpect(jsonPath("$[0].isOptional").value(programExerciseDto.getIsOptional()));

        verify(programExerciseService, times(1)).getExercisesByProgramId(programId);
    }

    @Test
    @DisplayName("Should return empty list when no exercises found for program")
    void shouldReturnEmptyListWhenNoExercisesFoundForProgram() throws Exception {
        // Given
        Long programId = 999L;
        when(programExerciseService.getExercisesByProgramId(programId)).thenReturn(Arrays.asList());

        // When & Then
        mockMvc.perform(get("/api/program-exercises/program/{programId}", programId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());

        verify(programExerciseService, times(1)).getExercisesByProgramId(programId);
    }

    @Test
    @DisplayName("Should get program exercise by id when exists")
    void shouldGetProgramExerciseByIdWhenExists() throws Exception {
        // Given
        Long exerciseId = 1L;
        when(programExerciseService.getProgramExerciseById(exerciseId)).thenReturn(Optional.of(programExerciseDto));

        // When & Then
        mockMvc.perform(get("/api/program-exercises/{id}", exerciseId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(programExerciseDto.getId()))
                .andExpect(jsonPath("$.trainingProgramId").value(programExerciseDto.getTrainingProgramId()))
                .andExpect(jsonPath("$.exerciseId").value(programExerciseDto.getExerciseId()))
                .andExpect(jsonPath("$.exerciseName").value(programExerciseDto.getExerciseName()));

        verify(programExerciseService, times(1)).getProgramExerciseById(exerciseId);
    }

    @Test
    @DisplayName("Should return not found when program exercise does not exist")
    void shouldReturnNotFoundWhenProgramExerciseDoesNotExist() throws Exception {
        // Given
        Long exerciseId = 999L;
        when(programExerciseService.getProgramExerciseById(exerciseId)).thenReturn(Optional.empty());

        // When & Then
        mockMvc.perform(get("/api/program-exercises/{id}", exerciseId))
                .andExpect(status().isNotFound());

        verify(programExerciseService, times(1)).getProgramExerciseById(exerciseId);
    }

    @Test
    @DisplayName("Should handle service exception gracefully")
    void shouldHandleServiceExceptionGracefully() throws Exception {
        // Given
        Long programId = 1L;
        when(programExerciseService.getExercisesByProgramId(programId))
                .thenThrow(new RuntimeException("Database error"));

        // When & Then
        mockMvc.perform(get("/api/program-exercises/program/{programId}", programId))
                .andExpect(status().isInternalServerError());

        verify(programExerciseService, times(1)).getExercisesByProgramId(programId);
    }
} 