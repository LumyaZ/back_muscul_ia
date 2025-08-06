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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any; 
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


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
        
        user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");
        user.setPassword("password123");
        
        trainingProgram = new TrainingProgram();
        trainingProgram.setId(1L);
        trainingProgram.setName("Test Program");
        trainingProgram.setDescription("Test Description");
        trainingProgram.setDifficultyLevel("Débutant");
        
        userTrainingProgram = new UserTrainingProgram(user, trainingProgram);
        userTrainingProgram.setId(1L);
    }
    
    @Test
    @DisplayName("Should subscribe user to program successfully")
    void shouldSubscribeUserToProgramSuccessfully() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(trainingProgramRepository.findById(1L)).thenReturn(Optional.of(trainingProgram));
        when(userTrainingProgramRepository.findByUserIdAndTrainingProgramId(1L, 1L))
                .thenReturn(Optional.empty());
        when(userTrainingProgramRepository.save(any(UserTrainingProgram.class)))
                .thenReturn(userTrainingProgram);
        
        UserTrainingProgramDto result = userTrainingProgramService.subscribeUserToProgram(1L, 1L);
        
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(user.getId(), result.getUser().getId());
        assertEquals(trainingProgram.getId(), result.getTrainingProgram().getId());
        
        verify(userTrainingProgramRepository).save(any(UserTrainingProgram.class));
    }
    
    @Test
    @DisplayName("Should throw exception when user not found")
    void shouldThrowExceptionWhenUserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        
        assertThrows(RuntimeException.class, () -> {
            userTrainingProgramService.subscribeUserToProgram(1L, 1L);
        });
    }
    
    @Test
    @DisplayName("Should throw exception when training program not found")
    void shouldThrowExceptionWhenTrainingProgramNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(trainingProgramRepository.findById(1L)).thenReturn(Optional.empty());
        
        assertThrows(RuntimeException.class, () -> {
            userTrainingProgramService.subscribeUserToProgram(1L, 1L);
        });
    }
    
    @Test
    @DisplayName("Should throw exception when user already subscribed")
    void shouldThrowExceptionWhenUserAlreadySubscribed() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(trainingProgramRepository.findById(1L)).thenReturn(Optional.of(trainingProgram));
        when(userTrainingProgramRepository.findByUserIdAndTrainingProgramId(1L, 1L))
                .thenReturn(Optional.of(userTrainingProgram));
        
        assertThrows(RuntimeException.class, () -> {
            userTrainingProgramService.subscribeUserToProgram(1L, 1L);
        });
    }
    
    @Test
    @DisplayName("Should unsubscribe user from program successfully")
    void shouldUnsubscribeUserFromProgramSuccessfully() {
        when(userTrainingProgramRepository.findByUserIdAndTrainingProgramId(1L, 1L))
                .thenReturn(Optional.of(userTrainingProgram));
        
        userTrainingProgramService.unsubscribeUserFromProgram(1L, 1L);
        
        verify(userTrainingProgramRepository).delete(userTrainingProgram);
    }
    
    @Test
    @DisplayName("Should get user programs successfully")
    void shouldGetUserProgramsSuccessfully() {
        List<UserTrainingProgram> userPrograms = Arrays.asList(userTrainingProgram);
        when(userTrainingProgramRepository.findByUserId(1L)).thenReturn(userPrograms);
        
        List<UserTrainingProgramDto> result = userTrainingProgramService.getUserPrograms(1L);
        
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId());
    }
    
    @Test
    @DisplayName("Should get program users successfully")
    void shouldGetProgramUsersSuccessfully() {
        List<UserTrainingProgram> programUsers = Arrays.asList(userTrainingProgram);
        when(userTrainingProgramRepository.findByTrainingProgramId(1L)).thenReturn(programUsers);
        
        List<UserTrainingProgramDto> result = userTrainingProgramService.getProgramUsers(1L);
        
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId());
    }
    
    @Test
    @DisplayName("Should get user program when exists")
    void shouldGetUserProgramWhenExists() {
        when(userTrainingProgramRepository.findByUserIdAndTrainingProgramId(1L, 1L))
                .thenReturn(Optional.of(userTrainingProgram));
        
        UserTrainingProgramDto result = userTrainingProgramService.getUserProgram(1L, 1L);
        
        assertNotNull(result);
        assertEquals(1L, result.getId());
    }
    
    @Test
    @DisplayName("Should return null when user program not exists")
    void shouldReturnNullWhenUserProgramNotExists() {
        when(userTrainingProgramRepository.findByUserIdAndTrainingProgramId(1L, 1L))
                .thenReturn(Optional.empty());
        
        UserTrainingProgramDto result = userTrainingProgramService.getUserProgram(1L, 1L);
        
        assertNull(result);
    }
} 