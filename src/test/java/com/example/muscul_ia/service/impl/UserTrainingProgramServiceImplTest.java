package com.example.muscul_ia.service.impl;

import com.example.muscul_ia.dto.UserTrainingProgramDto;
import com.example.muscul_ia.entity.User;
import com.example.muscul_ia.entity.TrainingProgram;
import com.example.muscul_ia.entity.UserTrainingProgram;
import com.example.muscul_ia.repository.UserRepository;
import com.example.muscul_ia.repository.TrainingProgramRepository;
import com.example.muscul_ia.repository.UserTrainingProgramRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit tests for UserTrainingProgramServiceImpl.
 * Tests unitaires pour UserTrainingProgramServiceImpl.
 * 
 * This test class verifies the business logic of the UserTrainingProgramService,
 * including subscription management and data retrieval.
 * 
 * Cette classe de test vérifie la logique métier du UserTrainingProgramService,
 * incluant la gestion des abonnements et la récupération de données.
 * 
 * @author Muscul IA Team
 * @version 1.0
 * @since 2024-01-01
 */
@DisplayName("UserTrainingProgramService Implementation Tests")
class UserTrainingProgramServiceImplTest {
    
    @Mock
    private UserTrainingProgramRepository userTrainingProgramRepository;
    
    @Mock
    private UserRepository userRepository;
    
    @Mock
    private TrainingProgramRepository trainingProgramRepository;
    
    @InjectMocks
    private UserTrainingProgramServiceImpl userTrainingProgramService;
    
    private User user;
    private TrainingProgram trainingProgram;
    private UserTrainingProgram userTrainingProgram;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        // Create test user
        user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");
        user.setPassword("password123");
        
        // Create test training program
        trainingProgram = new TrainingProgram();
        trainingProgram.setId(1L);
        trainingProgram.setName("Test Program");
        trainingProgram.setDescription("Test Description");
        trainingProgram.setDifficultyLevel("Débutant");
        
        // Create test user training program
        userTrainingProgram = new UserTrainingProgram(user, trainingProgram);
        userTrainingProgram.setId(1L);
    }
    
    @Test
    @DisplayName("Should subscribe user to program successfully")
    void shouldSubscribeUserToProgramSuccessfully() {
        // Given
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(trainingProgramRepository.findById(1L)).thenReturn(Optional.of(trainingProgram));
        when(userTrainingProgramRepository.findByUserIdAndTrainingProgramId(1L, 1L))
                .thenReturn(Optional.empty());
        when(userTrainingProgramRepository.save(any(UserTrainingProgram.class)))
                .thenReturn(userTrainingProgram);
        
        // When
        UserTrainingProgramDto result = userTrainingProgramService.subscribeUserToProgram(1L, 1L);
        
        // Then
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(user.getId(), result.getUser().getId());
        assertEquals(trainingProgram.getId(), result.getTrainingProgram().getId());
        
        verify(userTrainingProgramRepository).save(any(UserTrainingProgram.class));
    }
    
    @Test
    @DisplayName("Should throw exception when user not found")
    void shouldThrowExceptionWhenUserNotFound() {
        // Given
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        
        // When & Then
        assertThrows(RuntimeException.class, () -> {
            userTrainingProgramService.subscribeUserToProgram(1L, 1L);
        });
    }
    
    @Test
    @DisplayName("Should throw exception when training program not found")
    void shouldThrowExceptionWhenTrainingProgramNotFound() {
        // Given
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(trainingProgramRepository.findById(1L)).thenReturn(Optional.empty());
        
        // When & Then
        assertThrows(RuntimeException.class, () -> {
            userTrainingProgramService.subscribeUserToProgram(1L, 1L);
        });
    }
    
    @Test
    @DisplayName("Should throw exception when user already subscribed")
    void shouldThrowExceptionWhenUserAlreadySubscribed() {
        // Given
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(trainingProgramRepository.findById(1L)).thenReturn(Optional.of(trainingProgram));
        when(userTrainingProgramRepository.findByUserIdAndTrainingProgramId(1L, 1L))
                .thenReturn(Optional.of(userTrainingProgram));
        
        // When & Then
        assertThrows(RuntimeException.class, () -> {
            userTrainingProgramService.subscribeUserToProgram(1L, 1L);
        });
    }
    
    @Test
    @DisplayName("Should unsubscribe user from program successfully")
    void shouldUnsubscribeUserFromProgramSuccessfully() {
        // Given
        when(userTrainingProgramRepository.findByUserIdAndTrainingProgramId(1L, 1L))
                .thenReturn(Optional.of(userTrainingProgram));
        
        // When
        userTrainingProgramService.unsubscribeUserFromProgram(1L, 1L);
        
        // Then
        verify(userTrainingProgramRepository).delete(userTrainingProgram);
    }
    
    @Test
    @DisplayName("Should get user programs successfully")
    void shouldGetUserProgramsSuccessfully() {
        // Given
        List<UserTrainingProgram> userPrograms = Arrays.asList(userTrainingProgram);
        when(userTrainingProgramRepository.findByUserId(1L)).thenReturn(userPrograms);
        
        // When
        List<UserTrainingProgramDto> result = userTrainingProgramService.getUserPrograms(1L);
        
        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId());
    }
    
    @Test
    @DisplayName("Should get program users successfully")
    void shouldGetProgramUsersSuccessfully() {
        // Given
        List<UserTrainingProgram> programUsers = Arrays.asList(userTrainingProgram);
        when(userTrainingProgramRepository.findByTrainingProgramId(1L)).thenReturn(programUsers);
        
        // When
        List<UserTrainingProgramDto> result = userTrainingProgramService.getProgramUsers(1L);
        
        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId());
    }
    
    @Test
    @DisplayName("Should get user program when exists")
    void shouldGetUserProgramWhenExists() {
        // Given
        when(userTrainingProgramRepository.findByUserIdAndTrainingProgramId(1L, 1L))
                .thenReturn(Optional.of(userTrainingProgram));
        
        // When
        UserTrainingProgramDto result = userTrainingProgramService.getUserProgram(1L, 1L);
        
        // Then
        assertNotNull(result);
        assertEquals(1L, result.getId());
    }
    
    @Test
    @DisplayName("Should return null when user program not exists")
    void shouldReturnNullWhenUserProgramNotExists() {
        // Given
        when(userTrainingProgramRepository.findByUserIdAndTrainingProgramId(1L, 1L))
                .thenReturn(Optional.empty());
        
        // When
        UserTrainingProgramDto result = userTrainingProgramService.getUserProgram(1L, 1L);
        
        // Then
        assertNull(result);
    }
} 