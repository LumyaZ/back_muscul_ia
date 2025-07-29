package com.example.muscul_ia.controller;

import com.example.muscul_ia.config.TestSecurityConfig;
import com.example.muscul_ia.dto.UserTrainingProgramDto;
import com.example.muscul_ia.service.UserTrainingProgramService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Unit tests for UserTrainingProgramController.
 * Tests unitaires pour UserTrainingProgramController.
 * 
 * This test class verifies the REST endpoints of the UserTrainingProgramController,
 * including request handling, response formatting, and error scenarios.
 * 
 * Cette classe de test vérifie les endpoints REST du UserTrainingProgramController,
 * incluant la gestion des requêtes, le formatage des réponses et les scénarios d'erreur.
 * 
 * @author Muscul IA Team
 * @version 1.0
 * @since 2024-01-01
 */
@SpringBootTest
@AutoConfigureMockMvc
@Import(TestSecurityConfig.class)
@ActiveProfiles("test")
@DisplayName("UserTrainingProgramController Tests")
class UserTrainingProgramControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private UserTrainingProgramService userTrainingProgramService;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    private UserTrainingProgramDto userTrainingProgramDto;
    
    @BeforeEach
    void setUp() {
        // Create test DTO
        userTrainingProgramDto = new UserTrainingProgramDto();
        userTrainingProgramDto.setId(1L);
    }
    
    @Test
    @DisplayName("Should subscribe user to program successfully")
    void shouldSubscribeUserToProgramSuccessfully() throws Exception {
        // Given
        when(userTrainingProgramService.subscribeUserToProgram(1L, 1L))
                .thenReturn(userTrainingProgramDto);
        
        // When & Then
        mockMvc.perform(post("/api/user-training-programs/subscribe")
                        .param("userId", "1")
                        .param("trainingProgramId", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
        
        verify(userTrainingProgramService).subscribeUserToProgram(1L, 1L);
    }
    
    @Test
    @DisplayName("Should return bad request when subscription fails")
    void shouldReturnBadRequestWhenSubscriptionFails() throws Exception {
        // Given
        when(userTrainingProgramService.subscribeUserToProgram(1L, 1L))
                .thenThrow(new RuntimeException("User not found"));
        
        // When & Then
        mockMvc.perform(post("/api/user-training-programs/subscribe")
                        .param("userId", "1")
                        .param("trainingProgramId", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        
        verify(userTrainingProgramService).subscribeUserToProgram(1L, 1L);
    }
    
    @Test
    @DisplayName("Should unsubscribe user from program successfully")
    void shouldUnsubscribeUserFromProgramSuccessfully() throws Exception {
        // Given
        doNothing().when(userTrainingProgramService).unsubscribeUserFromProgram(1L, 1L);
        
        // When & Then
        mockMvc.perform(delete("/api/user-training-programs/unsubscribe")
                        .param("userId", "1")
                        .param("trainingProgramId", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        
        verify(userTrainingProgramService).unsubscribeUserFromProgram(1L, 1L);
    }
    
    @Test
    @DisplayName("Should return bad request when unsubscription fails")
    void shouldReturnBadRequestWhenUnsubscriptionFails() throws Exception {
        // Given
        doThrow(new RuntimeException("User not found"))
                .when(userTrainingProgramService).unsubscribeUserFromProgram(1L, 1L);
        
        // When & Then
        mockMvc.perform(delete("/api/user-training-programs/unsubscribe")
                        .param("userId", "1")
                        .param("trainingProgramId", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        
        verify(userTrainingProgramService).unsubscribeUserFromProgram(1L, 1L);
    }
    
    @Test
    @DisplayName("Should get user programs successfully")
    void shouldGetUserProgramsSuccessfully() throws Exception {
        // Given
        List<UserTrainingProgramDto> programs = Arrays.asList(userTrainingProgramDto);
        when(userTrainingProgramService.getUserPrograms(1L)).thenReturn(programs);
        
        // When & Then
        mockMvc.perform(get("/api/user-training-programs/user/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
        
        verify(userTrainingProgramService).getUserPrograms(1L);
    }
    
    @Test
    @DisplayName("Should return bad request when getting user programs fails")
    void shouldReturnBadRequestWhenGettingUserProgramsFails() throws Exception {
        // Given
        when(userTrainingProgramService.getUserPrograms(1L))
                .thenThrow(new RuntimeException("User not found"));
        
        // When & Then
        mockMvc.perform(get("/api/user-training-programs/user/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        
        verify(userTrainingProgramService).getUserPrograms(1L);
    }
    
    @Test
    @DisplayName("Should get program users successfully")
    void shouldGetProgramUsersSuccessfully() throws Exception {
        // Given
        List<UserTrainingProgramDto> users = Arrays.asList(userTrainingProgramDto);
        when(userTrainingProgramService.getProgramUsers(1L)).thenReturn(users);
        
        // When & Then
        mockMvc.perform(get("/api/user-training-programs/program/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
        
        verify(userTrainingProgramService).getProgramUsers(1L);
    }
    
    @Test
    @DisplayName("Should return bad request when getting program users fails")
    void shouldReturnBadRequestWhenGettingProgramUsersFails() throws Exception {
        // Given
        when(userTrainingProgramService.getProgramUsers(1L))
                .thenThrow(new RuntimeException("Program not found"));
        
        // When & Then
        mockMvc.perform(get("/api/user-training-programs/program/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        
        verify(userTrainingProgramService).getProgramUsers(1L);
    }
    
    @Test
    @DisplayName("Should check user program when exists")
    void shouldCheckUserProgramWhenExists() throws Exception {
        // Given
        when(userTrainingProgramService.getUserProgram(1L, 1L))
                .thenReturn(userTrainingProgramDto);
        
        // When & Then
        mockMvc.perform(get("/api/user-training-programs/check")
                        .param("userId", "1")
                        .param("trainingProgramId", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
        
        verify(userTrainingProgramService).getUserProgram(1L, 1L);
    }
    
    @Test
    @DisplayName("Should return not found when user program not exists")
    void shouldReturnNotFoundWhenUserProgramNotExists() throws Exception {
        // Given
        when(userTrainingProgramService.getUserProgram(1L, 1L))
                .thenReturn(null);
        
        // When & Then
        mockMvc.perform(get("/api/user-training-programs/check")
                        .param("userId", "1")
                        .param("trainingProgramId", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        
        verify(userTrainingProgramService).getUserProgram(1L, 1L);
    }
    
    @Test
    @DisplayName("Should return bad request when checking user program fails")
    void shouldReturnBadRequestWhenCheckingUserProgramFails() throws Exception {
        // Given
        when(userTrainingProgramService.getUserProgram(1L, 1L))
                .thenThrow(new RuntimeException("Error"));
        
        // When & Then
        mockMvc.perform(get("/api/user-training-programs/check")
                        .param("userId", "1")
                        .param("trainingProgramId", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        
        verify(userTrainingProgramService).getUserProgram(1L, 1L);
    }
} 