package com.example.muscul_ia.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("TrainingProgram Entity Tests")
class TrainingProgramTest {

    private TrainingProgram trainingProgram;
    private User user;

    @BeforeEach
    void setUp() {
        trainingProgram = new TrainingProgram();
        user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");
    }

    @Test
    @DisplayName("Should create training program with default values")
    void shouldCreateTrainingProgramWithDefaultValues() {
        // Given & When
        TrainingProgram newProgram = new TrainingProgram();

        // Then
        assertNotNull(newProgram);
        assertFalse(newProgram.getIsPublic());
        assertTrue(newProgram.getIsActive());
        assertNotNull(newProgram.getCreatedAt());
        assertNull(newProgram.getUpdatedAt());
    }

    @Test
    @DisplayName("Should create training program with parameters")
    void shouldCreateTrainingProgramWithParameters() {
        // Given
        String name = "Programme Débutant";
        String description = "Programme pour débuter en musculation";
        String difficultyLevel = "Débutant";

        // When
        TrainingProgram newProgram = new TrainingProgram(name, description, difficultyLevel);

        // Then
        assertEquals(name, newProgram.getName());
        assertEquals(description, newProgram.getDescription());
        assertEquals(difficultyLevel, newProgram.getDifficultyLevel());
        assertFalse(newProgram.getIsPublic());
        assertTrue(newProgram.getIsActive());
        assertNotNull(newProgram.getCreatedAt());
    }

    @Test
    @DisplayName("Should set and get all properties correctly")
    void shouldSetAndGetAllPropertiesCorrectly() {
        // Given
        Long id = 1L;
        String name = "Programme Avancé";
        String description = "Programme pour sportifs confirmés";
        String difficultyLevel = "Avancé";
        Integer durationWeeks = 12;
        Integer sessionsPerWeek = 4;
        Integer estimatedDurationMinutes = 60;
        String category = "Musculation";
        String targetAudience = "Sportifs confirmés";
        String equipmentRequired = "Barre, haltères, rack";
        String imageUrl = "https://example.com/image.jpg";
        Boolean isPublic = true;
        Boolean isActive = true;
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime updatedAt = LocalDateTime.now();

        // When
        trainingProgram.setId(id);
        trainingProgram.setName(name);
        trainingProgram.setDescription(description);
        trainingProgram.setDifficultyLevel(difficultyLevel);
        trainingProgram.setDurationWeeks(durationWeeks);
        trainingProgram.setSessionsPerWeek(sessionsPerWeek);
        trainingProgram.setEstimatedDurationMinutes(estimatedDurationMinutes);
        trainingProgram.setCategory(category);
        trainingProgram.setTargetAudience(targetAudience);
        trainingProgram.setEquipmentRequired(equipmentRequired);
        trainingProgram.setImageUrl(imageUrl);
        trainingProgram.setIsPublic(isPublic);
        trainingProgram.setIsActive(isActive);
        trainingProgram.setCreatedAt(createdAt);
        trainingProgram.setUpdatedAt(updatedAt);
        trainingProgram.setCreatedByUser(user);

        // Then
        assertEquals(id, trainingProgram.getId());
        assertEquals(name, trainingProgram.getName());
        assertEquals(description, trainingProgram.getDescription());
        assertEquals(difficultyLevel, trainingProgram.getDifficultyLevel());
        assertEquals(durationWeeks, trainingProgram.getDurationWeeks());
        assertEquals(sessionsPerWeek, trainingProgram.getSessionsPerWeek());
        assertEquals(estimatedDurationMinutes, trainingProgram.getEstimatedDurationMinutes());
        assertEquals(category, trainingProgram.getCategory());
        assertEquals(targetAudience, trainingProgram.getTargetAudience());
        assertEquals(equipmentRequired, trainingProgram.getEquipmentRequired());
        assertEquals(imageUrl, trainingProgram.getImageUrl());
        assertEquals(isPublic, trainingProgram.getIsPublic());
        assertEquals(isActive, trainingProgram.getIsActive());
        assertEquals(createdAt, trainingProgram.getCreatedAt());
        assertEquals(updatedAt, trainingProgram.getUpdatedAt());
        assertEquals(user, trainingProgram.getCreatedByUser());
    }

    @Test
    @DisplayName("Should handle null values correctly")
    void shouldHandleNullValuesCorrectly() {
        // When
        trainingProgram.setDescription(null);
        trainingProgram.setDifficultyLevel(null);
        trainingProgram.setDurationWeeks(null);
        trainingProgram.setSessionsPerWeek(null);
        trainingProgram.setEstimatedDurationMinutes(null);
        trainingProgram.setCategory(null);
        trainingProgram.setTargetAudience(null);
        trainingProgram.setEquipmentRequired(null);
        trainingProgram.setImageUrl(null);
        trainingProgram.setUpdatedAt(null);
        trainingProgram.setCreatedByUser(null);

        // Then
        assertNull(trainingProgram.getDescription());
        assertNull(trainingProgram.getDifficultyLevel());
        assertNull(trainingProgram.getDurationWeeks());
        assertNull(trainingProgram.getSessionsPerWeek());
        assertNull(trainingProgram.getEstimatedDurationMinutes());
        assertNull(trainingProgram.getCategory());
        assertNull(trainingProgram.getTargetAudience());
        assertNull(trainingProgram.getEquipmentRequired());
        assertNull(trainingProgram.getImageUrl());
        assertNull(trainingProgram.getUpdatedAt());
        assertNull(trainingProgram.getCreatedByUser());
    }

    @Test
    @DisplayName("Should update timestamp on update")
    void shouldUpdateTimestampOnUpdate() {
        // Given
        LocalDateTime originalCreatedAt = trainingProgram.getCreatedAt();
        
        // When
        trainingProgram.setName("Updated Name");
        // Simulate @PreUpdate
        trainingProgram.onUpdate();

        // Then
        assertEquals(originalCreatedAt, trainingProgram.getCreatedAt());
        assertNotNull(trainingProgram.getUpdatedAt());
        assertTrue(trainingProgram.getUpdatedAt().isAfter(originalCreatedAt) || 
                  trainingProgram.getUpdatedAt().equals(originalCreatedAt));
    }

    @Test
    @DisplayName("Should handle user relationship correctly")
    void shouldHandleUserRelationshipCorrectly() {
        // Given
        User newUser = new User();
        newUser.setId(2L);
        newUser.setEmail("newuser@example.com");

        // When
        trainingProgram.setCreatedByUser(newUser);

        // Then
        assertEquals(newUser, trainingProgram.getCreatedByUser());
        assertEquals(2L, trainingProgram.getCreatedByUser().getId());
        assertEquals("newuser@example.com", trainingProgram.getCreatedByUser().getEmail());
    }
} 