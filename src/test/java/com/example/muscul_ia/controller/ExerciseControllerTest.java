package com.example.muscul_ia.controller;

import com.example.muscul_ia.config.TestSecurityConfig;
import com.example.muscul_ia.dto.CreateExerciseRequest;
import com.example.muscul_ia.dto.ExerciseDto;
import com.example.muscul_ia.service.ExerciseService;
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
@DisplayName("ExerciseController Tests")
class ExerciseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExerciseService exerciseService;

    @Autowired
    private ObjectMapper objectMapper;

    private ExerciseDto exerciseDto;
    private CreateExerciseRequest createRequest;
    private List<ExerciseDto> exerciseList;

    @BeforeEach
    void setUp() {
        exerciseDto = new ExerciseDto();
        exerciseDto.setId(1L);
        exerciseDto.setName("Pompes");
        exerciseDto.setDescription("Exercice de musculation pour les pectoraux");
        exerciseDto.setCategory("Musculation");
        exerciseDto.setMuscleGroup("Pectoraux");
        exerciseDto.setEquipmentNeeded("Poids du corps");
        exerciseDto.setDifficultyLevel("Débutant");
        exerciseDto.setIsActive(true);
        exerciseDto.setCreatedAt(LocalDateTime.now());

        createRequest = new CreateExerciseRequest();
        createRequest.setName("Squats");
        createRequest.setDescription("Exercice pour les jambes");
        createRequest.setCategory("Musculation");
        createRequest.setMuscleGroup("Jambes");
        createRequest.setEquipmentNeeded("Poids du corps");
        createRequest.setDifficultyLevel("Débutant");

        exerciseList = Arrays.asList(exerciseDto);
    }

    @Test
    @DisplayName("Should create exercise successfully")
    void shouldCreateExerciseSuccessfully() throws Exception {
        // Given
        when(exerciseService.createExercise(any(CreateExerciseRequest.class))).thenReturn(exerciseDto);

        // When & Then
        mockMvc.perform(post("/api/exercises")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(exerciseDto.getId()))
                .andExpect(jsonPath("$.name").value(exerciseDto.getName()))
                .andExpect(jsonPath("$.description").value(exerciseDto.getDescription()))
                .andExpect(jsonPath("$.category").value(exerciseDto.getCategory()));

        verify(exerciseService, times(1)).createExercise(any(CreateExerciseRequest.class));
    }

    @Test
    @DisplayName("Should return bad request when creating exercise fails")
    void shouldReturnBadRequestWhenCreatingExerciseFails() throws Exception {
        // Given
        when(exerciseService.createExercise(any(CreateExerciseRequest.class)))
                .thenThrow(new RuntimeException("Creation failed"));

        // When & Then
        mockMvc.perform(post("/api/exercises")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createRequest)))
                .andExpect(status().isBadRequest());

        verify(exerciseService, times(1)).createExercise(any(CreateExerciseRequest.class));
    }

    @Test
    @DisplayName("Should get all exercises successfully")
    void shouldGetAllExercisesSuccessfully() throws Exception {
        // Given
        when(exerciseService.getAllActiveExercises()).thenReturn(exerciseList);

        // When & Then
        mockMvc.perform(get("/api/exercises"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(exerciseDto.getId()))
                .andExpect(jsonPath("$[0].name").value(exerciseDto.getName()))
                .andExpect(jsonPath("$[0].category").value(exerciseDto.getCategory()));

        verify(exerciseService, times(1)).getAllActiveExercises();
    }

    @Test
    @DisplayName("Should get exercise by id when exists")
    void shouldGetExerciseByIdWhenExists() throws Exception {
        // Given
        when(exerciseService.getExerciseById(1L)).thenReturn(Optional.of(exerciseDto));

        // When & Then
        mockMvc.perform(get("/api/exercises/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(exerciseDto.getId()))
                .andExpect(jsonPath("$.name").value(exerciseDto.getName()))
                .andExpect(jsonPath("$.category").value(exerciseDto.getCategory()));

        verify(exerciseService, times(1)).getExerciseById(1L);
    }

    @Test
    @DisplayName("Should return not found when exercise does not exist")
    void shouldReturnNotFoundWhenExerciseDoesNotExist() throws Exception {
        // Given
        when(exerciseService.getExerciseById(999L)).thenReturn(Optional.empty());

        // When & Then
        mockMvc.perform(get("/api/exercises/999"))
                .andExpect(status().isNotFound());

        verify(exerciseService, times(1)).getExerciseById(999L);
    }

    @Test
    @DisplayName("Should update exercise successfully")
    void shouldUpdateExerciseSuccessfully() throws Exception {
        // Given
        when(exerciseService.updateExercise(eq(1L), any(CreateExerciseRequest.class)))
                .thenReturn(exerciseDto);

        // When & Then
        mockMvc.perform(put("/api/exercises/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(exerciseDto.getId()))
                .andExpect(jsonPath("$.name").value(exerciseDto.getName()));

        verify(exerciseService, times(1)).updateExercise(eq(1L), any(CreateExerciseRequest.class));
    }

    @Test
    @DisplayName("Should return not found when updating non-existent exercise")
    void shouldReturnNotFoundWhenUpdatingNonExistentExercise() throws Exception {
        // Given
        when(exerciseService.updateExercise(eq(999L), any(CreateExerciseRequest.class)))
                .thenThrow(new RuntimeException("Exercise not found"));

        // When & Then
        mockMvc.perform(put("/api/exercises/999")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createRequest)))
                .andExpect(status().isNotFound());

        verify(exerciseService, times(1)).updateExercise(eq(999L), any(CreateExerciseRequest.class));
    }

    @Test
    @DisplayName("Should delete exercise successfully")
    void shouldDeleteExerciseSuccessfully() throws Exception {
        // Given
        doNothing().when(exerciseService).deleteExercise(1L);

        // When & Then
        mockMvc.perform(delete("/api/exercises/1"))
                .andExpect(status().isNoContent());

        verify(exerciseService, times(1)).deleteExercise(1L);
    }

    @Test
    @DisplayName("Should return not found when deleting non-existent exercise")
    void shouldReturnNotFoundWhenDeletingNonExistentExercise() throws Exception {
        // Given
        doThrow(new RuntimeException("Exercise not found")).when(exerciseService).deleteExercise(999L);

        // When & Then
        mockMvc.perform(delete("/api/exercises/999"))
                .andExpect(status().isNotFound());

        verify(exerciseService, times(1)).deleteExercise(999L);
    }

    @Test
    @DisplayName("Should search exercises by name successfully")
    void shouldSearchExercisesByNameSuccessfully() throws Exception {
        // Given
        when(exerciseService.searchExercisesByName("pompes")).thenReturn(exerciseList);

        // When & Then
        mockMvc.perform(get("/api/exercises/search")
                .param("name", "pompes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].name").value(exerciseDto.getName()));

        verify(exerciseService, times(1)).searchExercisesByName("pompes");
    }

    @Test
    @DisplayName("Should get exercises by category successfully")
    void shouldGetExercisesByCategorySuccessfully() throws Exception {
        // Given
        when(exerciseService.getExercisesByCategory("Musculation")).thenReturn(exerciseList);

        // When & Then
        mockMvc.perform(get("/api/exercises/category/Musculation"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].category").value(exerciseDto.getCategory()));

        verify(exerciseService, times(1)).getExercisesByCategory("Musculation");
    }

    @Test
    @DisplayName("Should get exercises by muscle group successfully")
    void shouldGetExercisesByMuscleGroupSuccessfully() throws Exception {
        // Given
        when(exerciseService.getExercisesByMuscleGroup("Pectoraux")).thenReturn(exerciseList);

        // When & Then
        mockMvc.perform(get("/api/exercises/muscle-group/Pectoraux"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].muscleGroup").value(exerciseDto.getMuscleGroup()));

        verify(exerciseService, times(1)).getExercisesByMuscleGroup("Pectoraux");
    }

    @Test
    @DisplayName("Should get exercises by difficulty level successfully")
    void shouldGetExercisesByDifficultyLevelSuccessfully() throws Exception {
        // Given
        when(exerciseService.getExercisesByDifficultyLevel("Débutant")).thenReturn(exerciseList);

        // When & Then
        mockMvc.perform(get("/api/exercises/difficulty/Débutant"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].difficultyLevel").value(exerciseDto.getDifficultyLevel()));

        verify(exerciseService, times(1)).getExercisesByDifficultyLevel("Débutant");
    }

    @Test
    @DisplayName("Should get exercises by equipment successfully")
    void shouldGetExercisesByEquipmentSuccessfully() throws Exception {
        // Given
        when(exerciseService.getExercisesByEquipment("Poids du corps")).thenReturn(exerciseList);

        // When & Then
        mockMvc.perform(get("/api/exercises/equipment/Poids du corps"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].equipmentNeeded").value(exerciseDto.getEquipmentNeeded()));

        verify(exerciseService, times(1)).getExercisesByEquipment("Poids du corps");
    }

    @Test
    @DisplayName("Should search exercises by description successfully")
    void shouldSearchExercisesByDescriptionSuccessfully() throws Exception {
        // Given
        when(exerciseService.searchExercisesByDescription("musculation")).thenReturn(exerciseList);

        // When & Then
        mockMvc.perform(get("/api/exercises/search/description")
                .param("description", "musculation"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].description").value(exerciseDto.getDescription()));

        verify(exerciseService, times(1)).searchExercisesByDescription("musculation");
    }

    @Test
    @DisplayName("Should get exercises by category and difficulty successfully")
    void shouldGetExercisesByCategoryAndDifficultySuccessfully() throws Exception {
        // Given
        when(exerciseService.getExercisesByCategoryAndDifficulty("Musculation", "Débutant"))
                .thenReturn(exerciseList);

        // When & Then
        mockMvc.perform(get("/api/exercises/category/Musculation/difficulty/Débutant"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].category").value(exerciseDto.getCategory()))
                .andExpect(jsonPath("$[0].difficultyLevel").value(exerciseDto.getDifficultyLevel()));

        verify(exerciseService, times(1)).getExercisesByCategoryAndDifficulty("Musculation", "Débutant");
    }

    @Test
    @DisplayName("Should get exercises by muscle group and equipment successfully")
    void shouldGetExercisesByMuscleGroupAndEquipmentSuccessfully() throws Exception {
        // Given
        when(exerciseService.getExercisesByMuscleGroupAndEquipment("Pectoraux", "Poids du corps"))
                .thenReturn(exerciseList);

        // When & Then
        mockMvc.perform(get("/api/exercises/muscle-group/Pectoraux/equipment/Poids du corps"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].muscleGroup").value(exerciseDto.getMuscleGroup()))
                .andExpect(jsonPath("$[0].equipmentNeeded").value(exerciseDto.getEquipmentNeeded()));

        verify(exerciseService, times(1)).getExercisesByMuscleGroupAndEquipment("Pectoraux", "Poids du corps");
    }
} 