package com.example.muscul_ia.controller;

import com.example.muscul_ia.dto.CreateProgramExerciseRequest;
import com.example.muscul_ia.dto.ProgramExerciseDto;
import com.example.muscul_ia.service.ProgramExerciseService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("ProgramExerciseController Tests")
class ProgramExerciseControllerTest {

    private MockMvc mockMvc;
    private ProgramExerciseService programExerciseService;
    private ObjectMapper objectMapper;

    private ProgramExerciseDto programExerciseDto;
    private List<ProgramExerciseDto> exerciseList;

    @BeforeEach
    void setUp() {
        programExerciseService = mock(ProgramExerciseService.class);
        
        ProgramExerciseController controller = new ProgramExerciseController();
        try {
            java.lang.reflect.Field programExerciseServiceField = ProgramExerciseController.class.getDeclaredField("programExerciseService");
            programExerciseServiceField.setAccessible(true);
            programExerciseServiceField.set(controller, programExerciseService);
        } catch (Exception e) {
            throw new RuntimeException("Failed to inject dependencies", e);
        }
        
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

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
        programExerciseDto.setSetsCount(3);
        programExerciseDto.setRepsCount(12);
        programExerciseDto.setRestDurationSeconds(90);
        programExerciseDto.setWeightKg(0.0);
        programExerciseDto.setDistanceMeters(0.0);
        programExerciseDto.setNotes("Exercice de base");
        programExerciseDto.setCreatedAt(LocalDateTime.now());
        programExerciseDto.setUpdatedAt(LocalDateTime.now());

        exerciseList = Arrays.asList(programExerciseDto);
    }

    @Test
    @DisplayName("Should get exercises by program id successfully")
    void shouldGetExercisesByProgramIdSuccessfully() throws Exception {
        Long programId = 1L;
        when(programExerciseService.getExercisesByProgramId(programId)).thenReturn(exerciseList);

        mockMvc.perform(get("/api/program-exercises/program/{programId}", programId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(programExerciseDto.getId()))
                .andExpect(jsonPath("$[0].trainingProgramId").value(programExerciseDto.getTrainingProgramId()))
                .andExpect(jsonPath("$[0].exerciseId").value(programExerciseDto.getExerciseId()))
                .andExpect(jsonPath("$[0].exerciseName").value(programExerciseDto.getExerciseName()))
                .andExpect(jsonPath("$[0].exerciseDescription").value(programExerciseDto.getExerciseDescription()))
                .andExpect(jsonPath("$[0].exerciseCategory").value(programExerciseDto.getExerciseCategory()))
                .andExpect(jsonPath("$[0].exerciseMuscleGroup").value(programExerciseDto.getExerciseMuscleGroup()))
                .andExpect(jsonPath("$[0].exerciseEquipmentNeeded").value(programExerciseDto.getExerciseEquipmentNeeded()))
                .andExpect(jsonPath("$[0].exerciseDifficultyLevel").value(programExerciseDto.getExerciseDifficultyLevel()))
                .andExpect(jsonPath("$[0].setsCount").value(programExerciseDto.getSetsCount()))
                .andExpect(jsonPath("$[0].repsCount").value(programExerciseDto.getRepsCount()))
                .andExpect(jsonPath("$[0].restDurationSeconds").value(programExerciseDto.getRestDurationSeconds()))
                .andExpect(jsonPath("$[0].weightKg").value(programExerciseDto.getWeightKg()))
                .andExpect(jsonPath("$[0].distanceMeters").value(programExerciseDto.getDistanceMeters()))
                .andExpect(jsonPath("$[0].notes").value(programExerciseDto.getNotes()));

        verify(programExerciseService, times(1)).getExercisesByProgramId(programId);
    }

    @Test
    @DisplayName("Should return empty list when no exercises found for program")
    void shouldReturnEmptyListWhenNoExercisesFoundForProgram() throws Exception {
        Long programId = 999L;
        when(programExerciseService.getExercisesByProgramId(programId)).thenReturn(Arrays.asList());

        mockMvc.perform(get("/api/program-exercises/program/{programId}", programId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());

        verify(programExerciseService, times(1)).getExercisesByProgramId(programId);
    }

    @Test
    @DisplayName("Should get program exercise by id when exists")
    void shouldGetProgramExerciseByIdWhenExists() throws Exception {
        Long exerciseId = 1L;
        when(programExerciseService.getProgramExerciseById(exerciseId)).thenReturn(Optional.of(programExerciseDto));

        mockMvc.perform(get("/api/program-exercises/{id}", exerciseId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(programExerciseDto.getId()))
                .andExpect(jsonPath("$.trainingProgramId").value(programExerciseDto.getTrainingProgramId()))
                .andExpect(jsonPath("$.exerciseId").value(programExerciseDto.getExerciseId()))
                .andExpect(jsonPath("$.exerciseName").value(programExerciseDto.getExerciseName()))
                .andExpect(jsonPath("$.exerciseDescription").value(programExerciseDto.getExerciseDescription()))
                .andExpect(jsonPath("$.exerciseCategory").value(programExerciseDto.getExerciseCategory()))
                .andExpect(jsonPath("$.exerciseMuscleGroup").value(programExerciseDto.getExerciseMuscleGroup()))
                .andExpect(jsonPath("$.exerciseEquipmentNeeded").value(programExerciseDto.getExerciseEquipmentNeeded()))
                .andExpect(jsonPath("$.exerciseDifficultyLevel").value(programExerciseDto.getExerciseDifficultyLevel()))
                .andExpect(jsonPath("$.setsCount").value(programExerciseDto.getSetsCount()))
                .andExpect(jsonPath("$.repsCount").value(programExerciseDto.getRepsCount()))
                .andExpect(jsonPath("$.restDurationSeconds").value(programExerciseDto.getRestDurationSeconds()))
                .andExpect(jsonPath("$.weightKg").value(programExerciseDto.getWeightKg()))
                .andExpect(jsonPath("$.distanceMeters").value(programExerciseDto.getDistanceMeters()))
                .andExpect(jsonPath("$.notes").value(programExerciseDto.getNotes()));

        verify(programExerciseService, times(1)).getProgramExerciseById(exerciseId);
    }

    @Test
    @DisplayName("Should return not found when program exercise does not exist")
    void shouldReturnNotFoundWhenProgramExerciseDoesNotExist() throws Exception {
        Long exerciseId = 999L;
        when(programExerciseService.getProgramExerciseById(exerciseId)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/program-exercises/{id}", exerciseId))
                .andExpect(status().isNotFound());

        verify(programExerciseService, times(1)).getProgramExerciseById(exerciseId);
    }

    @Test
    @DisplayName("Should add exercise to program successfully")
    void shouldAddExerciseToProgramSuccessfully() throws Exception {
        Long programId = 1L;
        CreateProgramExerciseRequest request = new CreateProgramExerciseRequest();
        request.setExerciseId(1L);
        request.setSetsCount(3);
        request.setRepsCount(12);
        request.setRestDurationSeconds(90);
        request.setWeightKg(0.0);
        request.setDistanceMeters(0.0);
        request.setNotes("Exercice de base");

        when(programExerciseService.addExerciseToProgram(eq(programId), any(CreateProgramExerciseRequest.class)))
                .thenReturn(programExerciseDto);

        mockMvc.perform(post("/api/program-exercises/program/{programId}", programId)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(programExerciseDto.getId()))
                .andExpect(jsonPath("$.trainingProgramId").value(programExerciseDto.getTrainingProgramId()))
                .andExpect(jsonPath("$.exerciseId").value(programExerciseDto.getExerciseId()))
                .andExpect(jsonPath("$.exerciseName").value(programExerciseDto.getExerciseName()))
                .andExpect(jsonPath("$.exerciseDescription").value(programExerciseDto.getExerciseDescription()))
                .andExpect(jsonPath("$.exerciseCategory").value(programExerciseDto.getExerciseCategory()))
                .andExpect(jsonPath("$.exerciseMuscleGroup").value(programExerciseDto.getExerciseMuscleGroup()))
                .andExpect(jsonPath("$.exerciseEquipmentNeeded").value(programExerciseDto.getExerciseEquipmentNeeded()))
                .andExpect(jsonPath("$.exerciseDifficultyLevel").value(programExerciseDto.getExerciseDifficultyLevel()))
                .andExpect(jsonPath("$.setsCount").value(programExerciseDto.getSetsCount()))
                .andExpect(jsonPath("$.repsCount").value(programExerciseDto.getRepsCount()))
                .andExpect(jsonPath("$.restDurationSeconds").value(programExerciseDto.getRestDurationSeconds()))
                .andExpect(jsonPath("$.weightKg").value(programExerciseDto.getWeightKg()))
                .andExpect(jsonPath("$.distanceMeters").value(programExerciseDto.getDistanceMeters()))
                .andExpect(jsonPath("$.notes").value(programExerciseDto.getNotes()));

        verify(programExerciseService, times(1)).addExerciseToProgram(eq(programId), any(CreateProgramExerciseRequest.class));
    }

    @Test
    @DisplayName("Should return bad request when adding exercise to program fails")
    void shouldReturnBadRequestWhenAddingExerciseToProgramFails() throws Exception {
        Long programId = 1L;
        CreateProgramExerciseRequest request = new CreateProgramExerciseRequest();
        request.setExerciseId(1L);
        request.setSetsCount(3);
        request.setRepsCount(12);
        request.setRestDurationSeconds(90);
        request.setWeightKg(0.0);
        request.setDistanceMeters(0.0);
        request.setNotes("Exercice de base");

        when(programExerciseService.addExerciseToProgram(eq(programId), any(CreateProgramExerciseRequest.class)))
                .thenThrow(new RuntimeException("Failed to add exercise to program"));

        mockMvc.perform(post("/api/program-exercises/program/{programId}", programId)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

        verify(programExerciseService, times(1)).addExerciseToProgram(eq(programId), any(CreateProgramExerciseRequest.class));
    }
} 