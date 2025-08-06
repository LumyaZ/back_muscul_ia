package com.example.muscul_ia.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;


@DisplayName("UserTrainingProgram Entity Tests")
class UserTrainingProgramTest {
    
    private User user;
    private TrainingProgram trainingProgram;
    private UserTrainingProgram userTrainingProgram;
    
    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");
        user.setPassword("password123");
        
        trainingProgram = new TrainingProgram();
        trainingProgram.setId(1L);
        trainingProgram.setName("Test Program");
        trainingProgram.setDescription("Test Description");
        trainingProgram.setDifficultyLevel("Débutant");
        
        userTrainingProgram = new UserTrainingProgram();
    }
    
    @Test
    @DisplayName("Should create UserTrainingProgram with default constructor")
    void shouldCreateUserTrainingProgramWithDefaultConstructor() {
        UserTrainingProgram utp = new UserTrainingProgram();
        
        assertNotNull(utp);
    }
    
    @Test
    @DisplayName("Should create UserTrainingProgram with user and training program")
    void shouldCreateUserTrainingProgramWithUserAndTrainingProgram() {
        UserTrainingProgram utp = new UserTrainingProgram(user, trainingProgram);
        
        assertNotNull(utp);
        assertEquals(user, utp.getUser());
        assertEquals(trainingProgram, utp.getTrainingProgram());
    }
    
    @Test
    @DisplayName("Should set and get ID correctly")
    void shouldSetAndGetIdCorrectly() {
        Long expectedId = 1L;
        
        userTrainingProgram.setId(expectedId);
        
        assertEquals(expectedId, userTrainingProgram.getId());
    }
    
    @Test
    @DisplayName("Should set and get user correctly")
    void shouldSetAndGetUserCorrectly() {
        userTrainingProgram.setUser(user);
        
        assertEquals(user, userTrainingProgram.getUser());
    }
    
    @Test
    @DisplayName("Should set and get training program correctly")
    void shouldSetAndGetTrainingProgramCorrectly() {
        userTrainingProgram.setTrainingProgram(trainingProgram);
        
        assertEquals(trainingProgram, userTrainingProgram.getTrainingProgram());
    }
    
    @Test
    @DisplayName("Should handle null values correctly")
    void shouldHandleNullValuesCorrectly() {
        userTrainingProgram.setUser(null);
        userTrainingProgram.setTrainingProgram(null);
        
        assertNull(userTrainingProgram.getUser());
        assertNull(userTrainingProgram.getTrainingProgram());
    }
    
    @Test
    @DisplayName("Should create relationship with constructor")
    void shouldCreateRelationshipWithConstructor() {
        UserTrainingProgram utp = new UserTrainingProgram(user, trainingProgram);
        
        assertNotNull(utp);
        assertEquals(user, utp.getUser());
        assertEquals(trainingProgram, utp.getTrainingProgram());
    }
} 