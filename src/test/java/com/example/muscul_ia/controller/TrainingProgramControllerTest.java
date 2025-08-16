package com.example.muscul_ia.controller;

import com.example.muscul_ia.dto.CreateTrainingProgramRequest;
import com.example.muscul_ia.dto.TrainingProgramDto;
import com.example.muscul_ia.service.TrainingProgramService;
import com.example.muscul_ia.service.UserTrainingProgramService;
import com.example.muscul_ia.dto.UserTrainingProgramDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("TrainingProgramController Tests")
class TrainingProgramControllerTest {

    private MockMvc mockMvc;
    private TrainingProgramService trainingProgramService;
    private UserTrainingProgramService userTrainingProgramService;
    private ObjectMapper objectMapper;

    private TrainingProgramDto trainingProgramDto;
    private CreateTrainingProgramRequest createRequest;
    private List<TrainingProgramDto> programList;

    @BeforeEach
    void setUp() {
        trainingProgramService = mock(TrainingProgramService.class);
        userTrainingProgramService = mock(UserTrainingProgramService.class);
        
        TrainingProgramController controller = new TrainingProgramController();
        
        try {
            java.lang.reflect.Field trainingProgramServiceField = TrainingProgramController.class.getDeclaredField("trainingProgramService");
            trainingProgramServiceField.setAccessible(true);
            trainingProgramServiceField.set(controller, trainingProgramService);
            
            java.lang.reflect.Field userTrainingProgramServiceField = TrainingProgramController.class.getDeclaredField("userTrainingProgramService");
            userTrainingProgramServiceField.setAccessible(true);
            userTrainingProgramServiceField.set(controller, userTrainingProgramService);
        } catch (Exception e) {
            throw new RuntimeException("Failed to inject dependencies", e);
        }
        
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        trainingProgramDto = new TrainingProgramDto();
        trainingProgramDto.setId(1L);
        trainingProgramDto.setName("Programme Test");
        trainingProgramDto.setDescription("Description du programme test");
        trainingProgramDto.setDifficultyLevel("Débutant");
        trainingProgramDto.setCategory("Musculation");
        trainingProgramDto.setTargetAudience("Débutants");
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
        
        when(trainingProgramService.createTrainingProgram(any(CreateTrainingProgramRequest.class), eq(1L)))
                .thenReturn(trainingProgramDto);
        
        UserTrainingProgramDto userTrainingProgramDto = new UserTrainingProgramDto();
        userTrainingProgramDto.setId(1L);
        when(userTrainingProgramService.subscribeUserToProgram(eq(1L), eq(1L)))
                .thenReturn(userTrainingProgramDto);

        
        mockMvc.perform(post("/api/training-programs")
                .param("userId", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(trainingProgramDto.getId()))
                .andExpect(jsonPath("$.name").value(trainingProgramDto.getName()))
                .andExpect(jsonPath("$.description").value(trainingProgramDto.getDescription()));

        verify(trainingProgramService, times(1)).createTrainingProgram(any(CreateTrainingProgramRequest.class), eq(1L));
    }

    @Test
    @DisplayName("Should return bad request when creating program fails")
    void shouldReturnBadRequestWhenCreatingProgramFails() throws Exception {
        
        when(trainingProgramService.createTrainingProgram(any(CreateTrainingProgramRequest.class), eq(1L)))
                .thenThrow(new RuntimeException("Creation failed"));

        
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
        
        when(trainingProgramService.getAllActivePrograms()).thenReturn(programList);

        
        mockMvc.perform(get("/api/training-programs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(trainingProgramDto.getId()))
                .andExpect(jsonPath("$[0].name").value(trainingProgramDto.getName()))
                .andExpect(jsonPath("$[0].description").value(trainingProgramDto.getDescription()));

        verify(trainingProgramService, times(1)).getAllActivePrograms();
    }

    @Test
    @DisplayName("Should get public programs successfully")
    void shouldGetPublicProgramsSuccessfully() throws Exception {
        
        when(trainingProgramService.getAllPublicActivePrograms()).thenReturn(programList);

        
        mockMvc.perform(get("/api/training-programs/public"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(trainingProgramDto.getId()))
                .andExpect(jsonPath("$[0].name").value(trainingProgramDto.getName()));

        verify(trainingProgramService, times(1)).getAllPublicActivePrograms();
    }

    @Test
    @DisplayName("Should get program by id when exists")
    void shouldGetProgramByIdWhenExists() throws Exception {
        
        when(trainingProgramService.getProgramById(1L)).thenReturn(Optional.of(trainingProgramDto));

        
        mockMvc.perform(get("/api/training-programs/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(trainingProgramDto.getId()))
                .andExpect(jsonPath("$.name").value(trainingProgramDto.getName()))
                .andExpect(jsonPath("$.description").value(trainingProgramDto.getDescription()));

        verify(trainingProgramService, times(1)).getProgramById(1L);
    }

    @Test
    @DisplayName("Should return not found when program does not exist")
    void shouldReturnNotFoundWhenProgramDoesNotExist() throws Exception {
        
        when(trainingProgramService.getProgramById(999L)).thenReturn(Optional.empty());

        
        mockMvc.perform(get("/api/training-programs/999"))
                .andExpect(status().isNotFound());

        verify(trainingProgramService, times(1)).getProgramById(999L);
    }

    @Test
    @DisplayName("Should get programs by user successfully")
    void shouldGetProgramsByUserSuccessfully() throws Exception {
        
        when(trainingProgramService.getProgramsByUser(1L)).thenReturn(programList);

        
        mockMvc.perform(get("/api/training-programs/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(trainingProgramDto.getId()))
                .andExpect(jsonPath("$[0].name").value(trainingProgramDto.getName()));

        verify(trainingProgramService, times(1)).getProgramsByUser(1L);
    }

    @Test
    @DisplayName("Should update program successfully")
    void shouldUpdateProgramSuccessfully() throws Exception {
        
        when(trainingProgramService.updateProgram(eq(1L), any(CreateTrainingProgramRequest.class), eq(1L)))
                .thenReturn(trainingProgramDto);

        
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
        
        when(trainingProgramService.updateProgram(eq(999L), any(CreateTrainingProgramRequest.class), eq(1L)))
                .thenThrow(new RuntimeException("Program not found"));

        
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
        
        doNothing().when(trainingProgramService).deleteProgram(1L, 1L);

        
        mockMvc.perform(delete("/api/training-programs/1")
                .param("userId", "1"))
                .andExpect(status().isNoContent());

        verify(trainingProgramService, times(1)).deleteProgram(1L, 1L);
    }

    @Test
    @DisplayName("Should return not found when deleting non-existent program")
    void shouldReturnNotFoundWhenDeletingNonExistentProgram() throws Exception {
        
        doNothing().when(trainingProgramService).deleteProgram(999L, 1L);

        
        mockMvc.perform(delete("/api/training-programs/999")
                .param("userId", "1"))
                .andExpect(status().isNoContent());

        verify(trainingProgramService, times(1)).deleteProgram(999L, 1L);
    }

    @Test
    @DisplayName("Should search programs by name successfully")
    void shouldSearchProgramsByNameSuccessfully() throws Exception {
        
        when(trainingProgramService.searchProgramsByName("test")).thenReturn(programList);

        
        mockMvc.perform(get("/api/training-programs/search")
                .param("name", "test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(trainingProgramDto.getId()))
                .andExpect(jsonPath("$[0].name").value(trainingProgramDto.getName()));

        verify(trainingProgramService, times(1)).searchProgramsByName("test");
    }

    @Test
    @DisplayName("Should search public programs by name successfully")
    void shouldSearchPublicProgramsByNameSuccessfully() throws Exception {
        
        when(trainingProgramService.searchPublicProgramsByName("test")).thenReturn(programList);

        
        mockMvc.perform(get("/api/training-programs/public/search")
                .param("name", "test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(trainingProgramDto.getId()))
                .andExpect(jsonPath("$[0].name").value(trainingProgramDto.getName()));

        verify(trainingProgramService, times(1)).searchPublicProgramsByName("test");
    }

    @Test
    @DisplayName("Should get programs by difficulty level successfully")
    void shouldGetProgramsByDifficultyLevelSuccessfully() throws Exception {
        
        when(trainingProgramService.getProgramsByDifficultyLevel("Débutant")).thenReturn(programList);

        
        mockMvc.perform(get("/api/training-programs/difficulty/{level}", "Débutant"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(trainingProgramDto.getId()));

        verify(trainingProgramService, times(1)).getProgramsByDifficultyLevel("Débutant");
    }

    @Test
    @DisplayName("Should get public programs by difficulty level successfully")
    void shouldGetPublicProgramsByDifficultyLevelSuccessfully() throws Exception {
        
        when(trainingProgramService.getPublicProgramsByDifficultyLevel("Débutant")).thenReturn(programList);

        
        mockMvc.perform(get("/api/training-programs/public/difficulty/{level}", "Débutant"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(trainingProgramDto.getId()));

        verify(trainingProgramService, times(1)).getPublicProgramsByDifficultyLevel("Débutant");
    }

    @Test
    @DisplayName("Should get programs by category successfully")
    void shouldGetProgramsByCategorySuccessfully() throws Exception {
        
        when(trainingProgramService.getProgramsByCategory("Musculation")).thenReturn(programList);

        
        mockMvc.perform(get("/api/training-programs/category/{category}", "Musculation"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(trainingProgramDto.getId()));

        verify(trainingProgramService, times(1)).getProgramsByCategory("Musculation");
    }

    @Test
    @DisplayName("Should get public programs by category successfully")
    void shouldGetPublicProgramsByCategorySuccessfully() throws Exception {
        
        when(trainingProgramService.getPublicProgramsByCategory("Musculation")).thenReturn(programList);

        
        mockMvc.perform(get("/api/training-programs/public/category/{category}", "Musculation"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(trainingProgramDto.getId()));

        verify(trainingProgramService, times(1)).getPublicProgramsByCategory("Musculation");
    }

    @Test
    @DisplayName("Should get programs by target audience successfully")
    void shouldGetProgramsByTargetAudienceSuccessfully() throws Exception {
        
        when(trainingProgramService.getProgramsByTargetAudience("Débutants")).thenReturn(programList);

        
        mockMvc.perform(get("/api/training-programs/audience/{audience}", "Débutants"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(trainingProgramDto.getId()));

        verify(trainingProgramService, times(1)).getProgramsByTargetAudience("Débutants");
    }

    @Test
    @DisplayName("Should get public programs by target audience successfully")
    void shouldGetPublicProgramsByTargetAudienceSuccessfully() throws Exception {
        
        when(trainingProgramService.getPublicProgramsByTargetAudience("Débutants")).thenReturn(programList);

        
        mockMvc.perform(get("/api/training-programs/public/audience/{audience}", "Débutants"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(trainingProgramDto.getId()));

        verify(trainingProgramService, times(1)).getPublicProgramsByTargetAudience("Débutants");
    }

    @Test
    @DisplayName("Should search programs by description successfully")
    void shouldSearchProgramsByDescriptionSuccessfully() throws Exception {
        
        when(trainingProgramService.searchProgramsByDescription("musculation")).thenReturn(programList);

        
        mockMvc.perform(get("/api/training-programs/search/description")
                .param("description", "musculation"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(trainingProgramDto.getId()));

        verify(trainingProgramService, times(1)).searchProgramsByDescription("musculation");
    }

    @Test
    @DisplayName("Should search public programs by description successfully")
    void shouldSearchPublicProgramsByDescriptionSuccessfully() throws Exception {
        
        when(trainingProgramService.searchPublicProgramsByDescription("musculation")).thenReturn(programList);

        
        mockMvc.perform(get("/api/training-programs/public/search/description")
                .param("description", "musculation"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(trainingProgramDto.getId()));

        verify(trainingProgramService, times(1)).searchPublicProgramsByDescription("musculation");
    }

    @Test
    @DisplayName("Should get programs by category and difficulty successfully")
    void shouldGetProgramsByCategoryAndDifficultySuccessfully() throws Exception {
        
        when(trainingProgramService.getProgramsByCategoryAndDifficulty("Musculation", "Débutant")).thenReturn(programList);

        
        mockMvc.perform(get("/api/training-programs/category/{category}/difficulty/{difficulty}", "Musculation", "Débutant"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(trainingProgramDto.getId()));

        verify(trainingProgramService, times(1)).getProgramsByCategoryAndDifficulty("Musculation", "Débutant");
    }

    @Test
    @DisplayName("Should get public programs by category and difficulty successfully")
    void shouldGetPublicProgramsByCategoryAndDifficultySuccessfully() throws Exception {
        
        when(trainingProgramService.getPublicProgramsByCategoryAndDifficulty("Musculation", "Débutant")).thenReturn(programList);

    
        mockMvc.perform(get("/api/training-programs/public/category/{category}/difficulty/{difficulty}", "Musculation", "Débutant"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(trainingProgramDto.getId()));

        verify(trainingProgramService, times(1)).getPublicProgramsByCategoryAndDifficulty("Musculation", "Débutant");
    }
} 