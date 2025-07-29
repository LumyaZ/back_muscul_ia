package com.example.muscul_ia.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for UserTrainingProgram entity.
 * Tests unitaires pour l'entité UserTrainingProgram.
 * 
 * This test class verifies the behavior of the UserTrainingProgram entity,
 * including its constructors, getters, and setters.
 * 
 * Cette classe de test vérifie le comportement de l'entité UserTrainingProgram,
 * incluant ses constructeurs, getters et setters.
 * 
 * @author Muscul IA Team
 * @version 1.0
 * @since 2024-01-01
 */
@DisplayName("UserTrainingProgram Entity Tests")
class UserTrainingProgramTest {
    
    private User user;
    private TrainingProgram trainingProgram;
    private UserTrainingProgram userTrainingProgram;
    
    @BeforeEach
    void setUp() {
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
        userTrainingProgram = new UserTrainingProgram();
    }
    
    @Test
    @DisplayName("Should create UserTrainingProgram with default constructor")
    void shouldCreateUserTrainingProgramWithDefaultConstructor() {
        // Given & When
        UserTrainingProgram utp = new UserTrainingProgram();
        
        // Then
        assertNotNull(utp);
    }
    
    @Test
    @DisplayName("Should create UserTrainingProgram with user and training program")
    void shouldCreateUserTrainingProgramWithUserAndTrainingProgram() {
        // Given & When
        UserTrainingProgram utp = new UserTrainingProgram(user, trainingProgram);
        
        // Then
        assertNotNull(utp);
        assertEquals(user, utp.getUser());
        assertEquals(trainingProgram, utp.getTrainingProgram());
    }
    
    @Test
    @DisplayName("Should set and get ID correctly")
    void shouldSetAndGetIdCorrectly() {
        // Given
        Long expectedId = 1L;
        
        // When
        userTrainingProgram.setId(expectedId);
        
        // Then
        assertEquals(expectedId, userTrainingProgram.getId());
    }
    
    @Test
    @DisplayName("Should set and get user correctly")
    void shouldSetAndGetUserCorrectly() {
        // Given & When
        userTrainingProgram.setUser(user);
        
        // Then
        assertEquals(user, userTrainingProgram.getUser());
    }
    
    @Test
    @DisplayName("Should set and get training program correctly")
    void shouldSetAndGetTrainingProgramCorrectly() {
        // Given & When
        userTrainingProgram.setTrainingProgram(trainingProgram);
        
        // Then
        assertEquals(trainingProgram, userTrainingProgram.getTrainingProgram());
    }
    
    @Test
    @DisplayName("Should handle null values correctly")
    void shouldHandleNullValuesCorrectly() {
        // Given & When
        userTrainingProgram.setUser(null);
        userTrainingProgram.setTrainingProgram(null);
        
        // Then
        assertNull(userTrainingProgram.getUser());
        assertNull(userTrainingProgram.getTrainingProgram());
    }
    
    @Test
    @DisplayName("Should create relationship with constructor")
    void shouldCreateRelationshipWithConstructor() {
        // Given & When
        UserTrainingProgram utp = new UserTrainingProgram(user, trainingProgram);
        
        // Then
        assertNotNull(utp);
        assertEquals(user, utp.getUser());
        assertEquals(trainingProgram, utp.getTrainingProgram());
    }
} 