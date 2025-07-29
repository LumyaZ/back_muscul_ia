package com.example.muscul_ia.controller;

import com.example.muscul_ia.config.TestSecurityConfig;
import com.example.muscul_ia.dto.CreateTrainingProgramRequest;
import com.example.muscul_ia.dto.TrainingProgramDto;
import com.example.muscul_ia.service.TrainingProgramService;
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
@DisplayName("TrainingProgramController Tests")
class TrainingProgramControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TrainingProgramService trainingProgramService;

    @Autowired
    private ObjectMapper objectMapper;

    private TrainingProgramDto trainingProgramDto;
    private CreateTrainingProgramRequest createRequest;
    private List<TrainingProgramDto> programList;

    @BeforeEach
    void setUp() {
        trainingProgramDto = new TrainingProgramDto();
        trainingProgramDto.setId(1L);
        trainingProgramDto.setName("Programme Test");
        trainingProgramDto.setDescription("Description du programme test");
        trainingProgramDto.setDifficultyLevel("Débutant");
        trainingProgramDto.setDurationWeeks(8);
        trainingProgramDto.setSessionsPerWeek(3);
        trainingProgramDto.setEstimatedDurationMinutes(45);
        trainingProgramDto.setCategory("Musculation");
        trainingProgramDto.setTargetAudience("Débutants");
        trainingProgramDto.setEquipmentRequired("Poids du corps");
        trainingProgramDto.setIsPublic(false);
        trainingProgramDto.setIsActive(true);
        trainingProgramDto.setCreatedByUserId(1L);
        trainingProgramDto.setCreatedAt(LocalDateTime.now());

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

        programList = Arrays.asList(trainingProgramDto);
    }

    @Test
    @DisplayName("Should create training program successfully")
    void shouldCreateTrainingProgramSuccessfully() throws Exception {
        // Given
        when(trainingProgramService.createTrainingProgram(any(CreateTrainingProgramRequest.class), eq(1L)))
                .thenReturn(trainingProgramDto);

        // When & Then
        mockMvc.perform(post("/api/training-programs")
                .param("userId", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(trainingProgramDto.getId()))
                .andExpect(jsonPath("$.name").value(trainingProgramDto.getName()))
                .andExpect(jsonPath("$.description").value(trainingProgramDto.getDescription()))
                .andExpect(jsonPath("$.difficultyLevel").value(trainingProgramDto.getDifficultyLevel()));

        verify(trainingProgramService, times(1)).createTrainingProgram(any(CreateTrainingProgramRequest.class), eq(1L));
    }

    @Test
    @DisplayName("Should return bad request when creating program fails")
    void shouldReturnBadRequestWhenCreatingProgramFails() throws Exception {
        // Given
        when(trainingProgramService.createTrainingProgram(any(CreateTrainingProgramRequest.class), eq(1L)))
                .thenThrow(new RuntimeException("Creation failed"));

        // When & Then
        mockMvc.perform(post("/api/training-programs")
                .param("userId", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createRequest)))
                .andExpect(status().isBadRequest());

        verify(trainingProgramService, times(1)).createTrainingProgram(any(CreateTrainingProgramRequest.class), eq(1L));
    }

    @Test
    @DisplayName("Should get all programs successfully")
    void shouldGetAllProgramsSuccessfully() throws Exception {
        // Given
        when(trainingProgramService.getAllActivePrograms()).thenReturn(programList);

        // When & Then
        mockMvc.perform(get("/api/training-programs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(trainingProgramDto.getId()))
                .andExpect(jsonPath("$[0].name").value(trainingProgramDto.getName()))
                .andExpect(jsonPath("$[0].category").value(trainingProgramDto.getCategory()));

        verify(trainingProgramService, times(1)).getAllActivePrograms();
    }

    @Test
    @DisplayName("Should get public programs successfully")
    void shouldGetPublicProgramsSuccessfully() throws Exception {
        // Given
        when(trainingProgramService.getAllPublicActivePrograms()).thenReturn(programList);

        // When & Then
        mockMvc.perform(get("/api/training-programs/public"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(trainingProgramDto.getId()))
                .andExpect(jsonPath("$[0].name").value(trainingProgramDto.getName()));

        verify(trainingProgramService, times(1)).getAllPublicActivePrograms();
    }

    @Test
    @DisplayName("Should get program by id when exists")
    void shouldGetProgramByIdWhenExists() throws Exception {
        // Given
        when(trainingProgramService.getProgramById(1L)).thenReturn(Optional.of(trainingProgramDto));

        // When & Then
        mockMvc.perform(get("/api/training-programs/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(trainingProgramDto.getId()))
                .andExpect(jsonPath("$.name").value(trainingProgramDto.getName()))
                .andExpect(jsonPath("$.category").value(trainingProgramDto.getCategory()));

        verify(trainingProgramService, times(1)).getProgramById(1L);
    }

    @Test
    @DisplayName("Should return not found when program does not exist")
    void shouldReturnNotFoundWhenProgramDoesNotExist() throws Exception {
        // Given
        when(trainingProgramService.getProgramById(999L)).thenReturn(Optional.empty());

        // When & Then
        mockMvc.perform(get("/api/training-programs/999"))
                .andExpect(status().isNotFound());

        verify(trainingProgramService, times(1)).getProgramById(999L);
    }

    @Test
    @DisplayName("Should get programs by user successfully")
    void shouldGetProgramsByUserSuccessfully() throws Exception {
        // Given
        when(trainingProgramService.getProgramsByUser(1L)).thenReturn(programList);

        // When & Then
        mockMvc.perform(get("/api/training-programs/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(trainingProgramDto.getId()))
                .andExpect(jsonPath("$[0].name").value(trainingProgramDto.getName()));

        verify(trainingProgramService, times(1)).getProgramsByUser(1L);
    }

    @Test
    @DisplayName("Should update program successfully")
    void shouldUpdateProgramSuccessfully() throws Exception {
        // Given
        when(trainingProgramService.updateProgram(eq(1L), any(CreateTrainingProgramRequest.class), eq(1L)))
                .thenReturn(trainingProgramDto);

        // When & Then
        mockMvc.perform(put("/api/training-programs/1")
                .param("userId", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(trainingProgramDto.getId()))
                .andExpect(jsonPath("$.name").value(trainingProgramDto.getName()));

        verify(trainingProgramService, times(1)).updateProgram(eq(1L), any(CreateTrainingProgramRequest.class), eq(1L));
    }

    @Test
    @DisplayName("Should return not found when updating non-existent program")
    void shouldReturnNotFoundWhenUpdatingNonExistentProgram() throws Exception {
        // Given
        when(trainingProgramService.updateProgram(eq(999L), any(CreateTrainingProgramRequest.class), eq(1L)))
                .thenThrow(new RuntimeException("Program not found"));

        // When & Then
        mockMvc.perform(put("/api/training-programs/999")
                .param("userId", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createRequest)))
                .andExpect(status().isNotFound());

        verify(trainingProgramService, times(1)).updateProgram(eq(999L), any(CreateTrainingProgramRequest.class), eq(1L));
    }

    @Test
    @DisplayName("Should delete program successfully")
    void shouldDeleteProgramSuccessfully() throws Exception {
        // Given
        doNothing().when(trainingProgramService).deleteProgram(1L, 1L);

        // When & Then
        mockMvc.perform(delete("/api/training-programs/1")
                .param("userId", "1"))
                .andExpect(status().isNoContent());

        verify(trainingProgramService, times(1)).deleteProgram(1L, 1L);
    }

    @Test
    @DisplayName("Should return not found when deleting non-existent program")
    void shouldReturnNotFoundWhenDeletingNonExistentProgram() throws Exception {
        // Given
        doThrow(new RuntimeException("Program not found")).when(trainingProgramService).deleteProgram(999L, 1L);

        // When & Then
        mockMvc.perform(delete("/api/training-programs/999")
                .param("userId", "1"))
                .andExpect(status().isNotFound());

        verify(trainingProgramService, times(1)).deleteProgram(999L, 1L);
    }

    @Test
    @DisplayName("Should search programs by name successfully")
    void shouldSearchProgramsByNameSuccessfully() throws Exception {
        // Given
        when(trainingProgramService.searchProgramsByName("test")).thenReturn(programList);

        // When & Then
        mockMvc.perform(get("/api/training-programs/search")
                .param("name", "test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].name").value(trainingProgramDto.getName()));

        verify(trainingProgramService, times(1)).searchProgramsByName("test");
    }

    @Test
    @DisplayName("Should search public programs by name successfully")
    void shouldSearchPublicProgramsByNameSuccessfully() throws Exception {
        // Given
        when(trainingProgramService.searchPublicProgramsByName("test")).thenReturn(programList);

        // When & Then
        mockMvc.perform(get("/api/training-programs/public/search")
                .param("name", "test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].name").value(trainingProgramDto.getName()));

        verify(trainingProgramService, times(1)).searchPublicProgramsByName("test");
    }

    @Test
    @DisplayName("Should get programs by difficulty level successfully")
    void shouldGetProgramsByDifficultyLevelSuccessfully() throws Exception {
        // Given
        when(trainingProgramService.getProgramsByDifficultyLevel("Débutant")).thenReturn(programList);

        // When & Then
        mockMvc.perform(get("/api/training-programs/difficulty/Débutant"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].difficultyLevel").value(trainingProgramDto.getDifficultyLevel()));

        verify(trainingProgramService, times(1)).getProgramsByDifficultyLevel("Débutant");
    }

    @Test
    @DisplayName("Should get public programs by difficulty level successfully")
    void shouldGetPublicProgramsByDifficultyLevelSuccessfully() throws Exception {
        // Given
        when(trainingProgramService.getPublicProgramsByDifficultyLevel("Débutant")).thenReturn(programList);

        // When & Then
        mockMvc.perform(get("/api/training-programs/public/difficulty/Débutant"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].difficultyLevel").value(trainingProgramDto.getDifficultyLevel()));

        verify(trainingProgramService, times(1)).getPublicProgramsByDifficultyLevel("Débutant");
    }

    @Test
    @DisplayName("Should get programs by category successfully")
    void shouldGetProgramsByCategorySuccessfully() throws Exception {
        // Given
        when(trainingProgramService.getProgramsByCategory("Musculation")).thenReturn(programList);

        // When & Then
        mockMvc.perform(get("/api/training-programs/category/Musculation"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].category").value(trainingProgramDto.getCategory()));

        verify(trainingProgramService, times(1)).getProgramsByCategory("Musculation");
    }

    @Test
    @DisplayName("Should get public programs by category successfully")
    void shouldGetPublicProgramsByCategorySuccessfully() throws Exception {
        // Given
        when(trainingProgramService.getPublicProgramsByCategory("Musculation")).thenReturn(programList);

        // When & Then
        mockMvc.perform(get("/api/training-programs/public/category/Musculation"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].category").value(trainingProgramDto.getCategory()));

        verify(trainingProgramService, times(1)).getPublicProgramsByCategory("Musculation");
    }

    @Test
    @DisplayName("Should get programs by target audience successfully")
    void shouldGetProgramsByTargetAudienceSuccessfully() throws Exception {
        // Given
        when(trainingProgramService.getProgramsByTargetAudience("Débutants")).thenReturn(programList);

        // When & Then
        mockMvc.perform(get("/api/training-programs/audience/Débutants"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].targetAudience").value(trainingProgramDto.getTargetAudience()));

        verify(trainingProgramService, times(1)).getProgramsByTargetAudience("Débutants");
    }

    @Test
    @DisplayName("Should get public programs by target audience successfully")
    void shouldGetPublicProgramsByTargetAudienceSuccessfully() throws Exception {
        // Given
        when(trainingProgramService.getPublicProgramsByTargetAudience("Débutants")).thenReturn(programList);

        // When & Then
        mockMvc.perform(get("/api/training-programs/public/audience/Débutants"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].targetAudience").value(trainingProgramDto.getTargetAudience()));

        verify(trainingProgramService, times(1)).getPublicProgramsByTargetAudience("Débutants");
    }

    @Test
    @DisplayName("Should get programs by duration successfully")
    void shouldGetProgramsByDurationSuccessfully() throws Exception {
        // Given
        when(trainingProgramService.getProgramsByDuration(8)).thenReturn(programList);

        // When & Then
        mockMvc.perform(get("/api/training-programs/duration/8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].durationWeeks").value(trainingProgramDto.getDurationWeeks()));

        verify(trainingProgramService, times(1)).getProgramsByDuration(8);
    }

    @Test
    @DisplayName("Should get public programs by duration successfully")
    void shouldGetPublicProgramsByDurationSuccessfully() throws Exception {
        // Given
        when(trainingProgramService.getPublicProgramsByDuration(8)).thenReturn(programList);

        // When & Then
        mockMvc.perform(get("/api/training-programs/public/duration/8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].durationWeeks").value(trainingProgramDto.getDurationWeeks()));

        verify(trainingProgramService, times(1)).getPublicProgramsByDuration(8);
    }

    @Test
    @DisplayName("Should get programs by sessions per week successfully")
    void shouldGetProgramsBySessionsPerWeekSuccessfully() throws Exception {
        // Given
        when(trainingProgramService.getProgramsBySessionsPerWeek(3)).thenReturn(programList);

        // When & Then
        mockMvc.perform(get("/api/training-programs/sessions/3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].sessionsPerWeek").value(trainingProgramDto.getSessionsPerWeek()));

        verify(trainingProgramService, times(1)).getProgramsBySessionsPerWeek(3);
    }

    @Test
    @DisplayName("Should get public programs by sessions per week successfully")
    void shouldGetPublicProgramsBySessionsPerWeekSuccessfully() throws Exception {
        // Given
        when(trainingProgramService.getPublicProgramsBySessionsPerWeek(3)).thenReturn(programList);

        // When & Then
        mockMvc.perform(get("/api/training-programs/public/sessions/3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].sessionsPerWeek").value(trainingProgramDto.getSessionsPerWeek()));

        verify(trainingProgramService, times(1)).getPublicProgramsBySessionsPerWeek(3);
    }

    @Test
    @DisplayName("Should search programs by description successfully")
    void shouldSearchProgramsByDescriptionSuccessfully() throws Exception {
        // Given
        when(trainingProgramService.searchProgramsByDescription("test")).thenReturn(programList);

        // When & Then
        mockMvc.perform(get("/api/training-programs/search/description")
                .param("description", "test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].description").value(trainingProgramDto.getDescription()));

        verify(trainingProgramService, times(1)).searchProgramsByDescription("test");
    }

    @Test
    @DisplayName("Should search public programs by description successfully")
    void shouldSearchPublicProgramsByDescriptionSuccessfully() throws Exception {
        // Given
        when(trainingProgramService.searchPublicProgramsByDescription("test")).thenReturn(programList);

        // When & Then
        mockMvc.perform(get("/api/training-programs/public/search/description")
                .param("description", "test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].description").value(trainingProgramDto.getDescription()));

        verify(trainingProgramService, times(1)).searchPublicProgramsByDescription("test");
    }

    @Test
    @DisplayName("Should get programs by category and difficulty successfully")
    void shouldGetProgramsByCategoryAndDifficultySuccessfully() throws Exception {
        // Given
        when(trainingProgramService.getProgramsByCategoryAndDifficulty("Musculation", "Débutant"))
                .thenReturn(programList);

        // When & Then
        mockMvc.perform(get("/api/training-programs/category/Musculation/difficulty/Débutant"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].category").value(trainingProgramDto.getCategory()))
                .andExpect(jsonPath("$[0].difficultyLevel").value(trainingProgramDto.getDifficultyLevel()));

        verify(trainingProgramService, times(1)).getProgramsByCategoryAndDifficulty("Musculation", "Débutant");
    }

    @Test
    @DisplayName("Should get public programs by category and difficulty successfully")
    void shouldGetPublicProgramsByCategoryAndDifficultySuccessfully() throws Exception {
        // Given
        when(trainingProgramService.getPublicProgramsByCategoryAndDifficulty("Musculation", "Débutant"))
                .thenReturn(programList);

        // When & Then
        mockMvc.perform(get("/api/training-programs/public/category/Musculation/difficulty/Débutant"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].category").value(trainingProgramDto.getCategory()))
                .andExpect(jsonPath("$[0].difficultyLevel").value(trainingProgramDto.getDifficultyLevel()));

        verify(trainingProgramService, times(1)).getPublicProgramsByCategoryAndDifficulty("Musculation", "Débutant");
    }
} 