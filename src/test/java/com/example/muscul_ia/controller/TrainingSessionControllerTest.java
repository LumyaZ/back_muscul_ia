package com.example.muscul_ia.controller;

import com.example.muscul_ia.dto.CreateTrainingSessionRequest;
import com.example.muscul_ia.dto.TrainingSessionDto;
import com.example.muscul_ia.entity.User;
import com.example.muscul_ia.service.TrainingSessionService;
import com.example.muscul_ia.service.UserService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("TrainingSessionController Tests")
class TrainingSessionControllerTest {

    private MockMvc mockMvc;
    private TrainingSessionService trainingSessionService;
    private UserService userService;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        trainingSessionService = mock(TrainingSessionService.class);
        userService = mock(UserService.class);
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        TrainingSessionController controller = new TrainingSessionController();
        try {
            java.lang.reflect.Field trainingSessionServiceField = TrainingSessionController.class.getDeclaredField("trainingSessionService");
            trainingSessionServiceField.setAccessible(true);
            trainingSessionServiceField.set(controller, trainingSessionService);
            
            java.lang.reflect.Field userServiceField = TrainingSessionController.class.getDeclaredField("userService");
            userServiceField.setAccessible(true);
            userServiceField.set(controller, userService);
        } catch (Exception e) {
            throw new RuntimeException("Failed to inject services", e);
        }
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    @DisplayName("Should create training session successfully")
    void createTrainingSession_Success() throws Exception {
        CreateTrainingSessionRequest request = new CreateTrainingSessionRequest();
        request.setName("Test Session");
        request.setDescription("Test Description");
        request.setSessionType("STRENGTH");
        request.setDurationMinutes(60);
        request.setSessionDate(LocalDateTime.now());
        request.setTrainingProgramId(1L);

        TrainingSessionDto response = new TrainingSessionDto();
        response.setId(1L);
        response.setName("Test Session");
        response.setDescription("Test Description");

        when(trainingSessionService.createTrainingSession(any(User.class), any(CreateTrainingSessionRequest.class)))
            .thenReturn(response);

        mockMvc.perform(post("/api/training-sessions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Test Session"));
    }

    @Test
    @DisplayName("Should get training session by ID successfully")
    void getTrainingSessionById_Success() throws Exception {
        TrainingSessionDto response = new TrainingSessionDto();
        response.setId(1L);
        response.setName("Test Session");

        when(trainingSessionService.getTrainingSessionById(1L)).thenReturn(java.util.Optional.of(response));

        mockMvc.perform(get("/api/training-sessions/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Test Session"));
    }

    @Test
    @DisplayName("Should get user training sessions successfully")
    void getUserTrainingSessions_Success() throws Exception {
        TrainingSessionDto session1 = new TrainingSessionDto();
        session1.setId(1L);
        session1.setName("Session 1");
        session1.setDescription("Description 1");
        session1.setSessionType("STRENGTH");
        session1.setDurationMinutes(60);
        
        TrainingSessionDto session2 = new TrainingSessionDto();
        session2.setId(2L);
        session2.setName("Session 2");
        session2.setDescription("Description 2");
        session2.setSessionType("CARDIO");
        session2.setDurationMinutes(45);
        
        List<TrainingSessionDto> sessions = Arrays.asList(session1, session2);

        when(trainingSessionService.getTrainingSessionsByUser(any(User.class))).thenReturn(sessions);

        mockMvc.perform(get("/api/training-sessions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Session 1"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Session 2"));
    }

    @Test
    @DisplayName("Should update training session successfully")
    void updateTrainingSession_Success() throws Exception {
        CreateTrainingSessionRequest request = new CreateTrainingSessionRequest();
        request.setName("Updated Session");
        request.setDescription("Updated Description");
        request.setSessionDate(LocalDateTime.now());
        request.setDurationMinutes(60);

        TrainingSessionDto response = new TrainingSessionDto();
        response.setId(1L);
        response.setName("Updated Session");
        response.setDescription("Updated Description");

        when(trainingSessionService.updateTrainingSession(1L, request)).thenReturn(response);

        mockMvc.perform(put("/api/training-sessions/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Updated Session"));
    }

    @Test
    @DisplayName("Should delete training session successfully")
    void deleteTrainingSession_Success() throws Exception {
        mockMvc.perform(delete("/api/training-sessions/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Should search training sessions by name successfully")
    void searchTrainingSessionsByName_Success() throws Exception {
        TrainingSessionDto session = new TrainingSessionDto();
        session.setId(1L);
        session.setName("Search Session");
        session.setDescription("Description");
        session.setSessionType("STRENGTH");
        session.setDurationMinutes(60);
        
        List<TrainingSessionDto> sessions = Arrays.asList(session);

        when(trainingSessionService.searchTrainingSessionsByUserAndName(any(User.class), anyString()))
            .thenReturn(sessions);

        mockMvc.perform(get("/api/training-sessions/search")
                .param("name", "Search"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Search Session"));
    }

    @Test
    @DisplayName("Should get training sessions count successfully")
    void getTrainingSessionsCount_Success() throws Exception {
        when(trainingSessionService.countTrainingSessionsByUser(any(User.class))).thenReturn(5L);

        mockMvc.perform(get("/api/training-sessions/count"))
                .andExpect(status().isOk())
                .andExpect(content().string("5"));
    }

    @Test
    @DisplayName("Should get user training sessions with pagination successfully")
    void getUserTrainingSessionsWithPagination_Success() throws Exception { 
        TrainingSessionDto session1 = new TrainingSessionDto();
        session1.setId(1L);
        session1.setName("Session 1");
        session1.setDescription("Description 1");
        session1.setSessionType("STRENGTH");
        session1.setDurationMinutes(60);
        
        TrainingSessionDto session2 = new TrainingSessionDto();
        session2.setId(2L);
        session2.setName("Session 2");
        session2.setDescription("Description 2");
        session2.setSessionType("CARDIO");
        session2.setDurationMinutes(45);
        
        Arrays.asList(session1, session2);

       
        mockMvc.perform(get("/api/training-sessions/user/paginated")
                .param("page", "0")
                .param("size", "10"))
                .andExpect(status().isOk());
    }
} 