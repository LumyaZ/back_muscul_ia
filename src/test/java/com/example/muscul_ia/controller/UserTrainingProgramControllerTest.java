package com.example.muscul_ia.controller;

import com.example.muscul_ia.dto.UserTrainingProgramDto;
import com.example.muscul_ia.service.UserTrainingProgramService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.mock;

@DisplayName("UserTrainingProgramController Tests")
class UserTrainingProgramControllerTest {
    
    private MockMvc mockMvc;
    private UserTrainingProgramService userTrainingProgramService;
    private ObjectMapper objectMapper;
    
    private UserTrainingProgramDto userTrainingProgramDto;
    
    @BeforeEach
    void setUp() {
        userTrainingProgramService = mock(UserTrainingProgramService.class);
        
        UserTrainingProgramController controller = new UserTrainingProgramController();
        try {
            java.lang.reflect.Field userTrainingProgramServiceField = UserTrainingProgramController.class.getDeclaredField("userTrainingProgramService");
            userTrainingProgramServiceField.setAccessible(true);
            userTrainingProgramServiceField.set(controller, userTrainingProgramService);
        } catch (Exception e) {
            throw new RuntimeException("Failed to inject dependencies", e);
        }
        
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        objectMapper = new ObjectMapper();
        
        userTrainingProgramDto = new UserTrainingProgramDto();
        userTrainingProgramDto.setId(1L);
    }
    
    @Test
    @DisplayName("Should subscribe user to program successfully")
    void shouldSubscribeUserToProgramSuccessfully() throws Exception {
        when(userTrainingProgramService.subscribeUserToProgram(1L, 1L))
                .thenReturn(userTrainingProgramDto);
        
        mockMvc.perform(post("/api/user-training-programs/subscribe")
                        .param("userId", "1")
                        .param("trainingProgramId", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
        
        verify(userTrainingProgramService, times(1)).subscribeUserToProgram(1L, 1L);
    }
    
    @Test
    @DisplayName("Should return bad request when subscription fails")
    void shouldReturnBadRequestWhenSubscriptionFails() throws Exception {
        when(userTrainingProgramService.subscribeUserToProgram(1L, 1L))
                .thenThrow(new RuntimeException("User not found"));
        
        mockMvc.perform(post("/api/user-training-programs/subscribe")
                        .param("userId", "1")
                        .param("trainingProgramId", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        
        verify(userTrainingProgramService, times(1)).subscribeUserToProgram(1L, 1L);
    }
    
    @Test
    @DisplayName("Should unsubscribe user from program successfully")
    void shouldUnsubscribeUserFromProgramSuccessfully() throws Exception {
        doNothing().when(userTrainingProgramService).unsubscribeUserFromProgram(1L, 1L);
        
        mockMvc.perform(delete("/api/user-training-programs/unsubscribe")
                        .param("userId", "1")
                        .param("trainingProgramId", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        
        verify(userTrainingProgramService, times(1)).unsubscribeUserFromProgram(1L, 1L);
    }
    
    @Test
    @DisplayName("Should return bad request when unsubscription fails")
    void shouldReturnBadRequestWhenUnsubscriptionFails() throws Exception {
        doThrow(new RuntimeException("User not found"))
                .when(userTrainingProgramService).unsubscribeUserFromProgram(1L, 1L);
        
        mockMvc.perform(delete("/api/user-training-programs/unsubscribe")
                        .param("userId", "1")
                        .param("trainingProgramId", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        
        verify(userTrainingProgramService, times(1)).unsubscribeUserFromProgram(1L, 1L);
    }
    
    @Test
    @DisplayName("Should get user programs successfully")
    void shouldGetUserProgramsSuccessfully() throws Exception {
        List<UserTrainingProgramDto> programs = Arrays.asList(userTrainingProgramDto);
        when(userTrainingProgramService.getUserPrograms(1L)).thenReturn(programs);
        
        mockMvc.perform(get("/api/user-training-programs/user/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
        
        verify(userTrainingProgramService, times(1)).getUserPrograms(1L);
    }
    
    @Test
    @DisplayName("Should return bad request when getting user programs fails")
    void shouldReturnBadRequestWhenGettingUserProgramsFails() throws Exception {
        when(userTrainingProgramService.getUserPrograms(1L))
                .thenThrow(new RuntimeException("User not found"));
        
        mockMvc.perform(get("/api/user-training-programs/user/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        
        verify(userTrainingProgramService, times(1)).getUserPrograms(1L);
    }
    
    @Test
    @DisplayName("Should get program users successfully")
    void shouldGetProgramUsersSuccessfully() throws Exception {
        List<UserTrainingProgramDto> users = Arrays.asList(userTrainingProgramDto);
        when(userTrainingProgramService.getProgramUsers(1L)).thenReturn(users);
        
        mockMvc.perform(get("/api/user-training-programs/program/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));
        
        verify(userTrainingProgramService, times(1)).getProgramUsers(1L);
    }
    
    @Test
    @DisplayName("Should return bad request when getting program users fails")
    void shouldReturnBadRequestWhenGettingProgramUsersFails() throws Exception {
        when(userTrainingProgramService.getProgramUsers(1L))
                .thenThrow(new RuntimeException("Program not found"));
        
        mockMvc.perform(get("/api/user-training-programs/program/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        
        verify(userTrainingProgramService, times(1)).getProgramUsers(1L);
    }
    
    @Test
    @DisplayName("Should check user program when exists")
    void shouldCheckUserProgramWhenExists() throws Exception {
        when(userTrainingProgramService.getUserProgram(1L, 1L))
                .thenReturn(userTrainingProgramDto);
        
        mockMvc.perform(get("/api/user-training-programs/check")
                        .param("userId", "1")
                        .param("trainingProgramId", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
        
        verify(userTrainingProgramService, times(1)).getUserProgram(1L, 1L);
    }
    
    @Test
    @DisplayName("Should return not found when user program not exists")
    void shouldReturnNotFoundWhenUserProgramNotExists() throws Exception {
        when(userTrainingProgramService.getUserProgram(1L, 1L))
                .thenReturn(null);
        
        mockMvc.perform(get("/api/user-training-programs/check")
                        .param("userId", "1")
                        .param("trainingProgramId", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        
        verify(userTrainingProgramService, times(1)).getUserProgram(1L, 1L);
    }
    
    @Test
    @DisplayName("Should return bad request when checking user program fails")
    void shouldReturnBadRequestWhenCheckingUserProgramFails() throws Exception {
        when(userTrainingProgramService.getUserProgram(1L, 1L))
                .thenThrow(new RuntimeException("Error occurred"));
                                
        mockMvc.perform(get("/api/user-training-programs/check")
                        .param("userId", "1")
                        .param("trainingProgramId", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        
        verify(userTrainingProgramService, times(1)).getUserProgram(1L, 1L);
    }
} 