package com.example.muscul_ia.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Exercise Entity Tests")
class ExerciseTest {

    private Exercise exercise;

    @BeforeEach
    void setUp() {
        exercise = new Exercise();
    }

    @Test
    @DisplayName("Should create exercise with default values")
    void shouldCreateExerciseWithDefaultValues() {
        // Given & When
        Exercise newExercise = new Exercise();

        // Then
        assertNotNull(newExercise);
        assertTrue(newExercise.getIsActive());
        assertNotNull(newExercise.getCreatedAt());
        assertNull(newExercise.getUpdatedAt());
    }

    @Test
    @DisplayName("Should create exercise with parameters")
    void shouldCreateExerciseWithParameters() {
        // Given
        String name = "Pompes";
        String description = "Exercice de musculation pour les pectoraux";
        String category = "Musculation";

        // When
        Exercise newExercise = new Exercise(name, description, category);

        // Then
        assertEquals(name, newExercise.getName());
        assertEquals(description, newExercise.getDescription());
        assertEquals(category, newExercise.getCategory());
        assertTrue(newExercise.getIsActive());
        assertNotNull(newExercise.getCreatedAt());
    }

    @Test
    @DisplayName("Should set and get all properties correctly")
    void shouldSetAndGetAllPropertiesCorrectly() {
        // Given
        Long id = 1L;
        String name = "Squats";
        String description = "Exercice pour les jambes";
        String category = "Musculation";
        String muscleGroup = "Jambes";
        String equipmentNeeded = "Poids du corps";
        String difficultyLevel = "Débutant";
        String videoUrl = "https://example.com/video";
        String imageUrl = "https://example.com/image.jpg";
        Boolean isActive = true;
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime updatedAt = LocalDateTime.now();

        // When
        exercise.setId(id);
        exercise.setName(name);
        exercise.setDescription(description);
        exercise.setCategory(category);
        exercise.setMuscleGroup(muscleGroup);
        exercise.setEquipmentNeeded(equipmentNeeded);
        exercise.setDifficultyLevel(difficultyLevel);
        exercise.setVideoUrl(videoUrl);
        exercise.setImageUrl(imageUrl);
        exercise.setIsActive(isActive);
        exercise.setCreatedAt(createdAt);
        exercise.setUpdatedAt(updatedAt);

        // Then
        assertEquals(id, exercise.getId());
        assertEquals(name, exercise.getName());
        assertEquals(description, exercise.getDescription());
        assertEquals(category, exercise.getCategory());
        assertEquals(muscleGroup, exercise.getMuscleGroup());
        assertEquals(equipmentNeeded, exercise.getEquipmentNeeded());
        assertEquals(difficultyLevel, exercise.getDifficultyLevel());
        assertEquals(videoUrl, exercise.getVideoUrl());
        assertEquals(imageUrl, exercise.getImageUrl());
        assertEquals(isActive, exercise.getIsActive());
        assertEquals(createdAt, exercise.getCreatedAt());
        assertEquals(updatedAt, exercise.getUpdatedAt());
    }

    @Test
    @DisplayName("Should handle null values correctly")
    void shouldHandleNullValuesCorrectly() {
        // When
        exercise.setDescription(null);
        exercise.setMuscleGroup(null);
        exercise.setEquipmentNeeded(null);
        exercise.setDifficultyLevel(null);
        exercise.setVideoUrl(null);
        exercise.setImageUrl(null);
        exercise.setUpdatedAt(null);

        // Then
        assertNull(exercise.getDescription());
        assertNull(exercise.getMuscleGroup());
        assertNull(exercise.getEquipmentNeeded());
        assertNull(exercise.getDifficultyLevel());
        assertNull(exercise.getVideoUrl());
        assertNull(exercise.getImageUrl());
        assertNull(exercise.getUpdatedAt());
    }

    @Test
    @DisplayName("Should update timestamp on update")
    void shouldUpdateTimestampOnUpdate() {
        // Given
        LocalDateTime originalCreatedAt = exercise.getCreatedAt();
        
        // When
        exercise.setName("Updated Name");
        // Simulate @PreUpdate
        exercise.onUpdate();

        // Then
        assertEquals(originalCreatedAt, exercise.getCreatedAt());
        assertNotNull(exercise.getUpdatedAt());
        assertTrue(exercise.getUpdatedAt().isAfter(originalCreatedAt) || 
                  exercise.getUpdatedAt().equals(originalCreatedAt));
    }
} 