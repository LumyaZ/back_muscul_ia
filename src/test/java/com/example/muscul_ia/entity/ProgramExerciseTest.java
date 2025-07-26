package com.example.muscul_ia.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("ProgramExercise Entity Tests")
class ProgramExerciseTest {

    private ProgramExercise programExercise;
    private TrainingProgram trainingProgram;
    private Exercise exercise;

    @BeforeEach
    void setUp() {
        programExercise = new ProgramExercise();
        
        trainingProgram = new TrainingProgram();
        trainingProgram.setId(1L);
        trainingProgram.setName("Test Program");
        
        exercise = new Exercise();
        exercise.setId(1L);
        exercise.setName("Test Exercise");
    }

    @Test
    @DisplayName("Should create program exercise with default values")
    void shouldCreateProgramExerciseWithDefaultValues() {
        // Given & When
        ProgramExercise newProgramExercise = new ProgramExercise();

        // Then
        assertNotNull(newProgramExercise);
        assertFalse(newProgramExercise.getIsOptional());
        assertNotNull(newProgramExercise.getCreatedAt());
        assertNull(newProgramExercise.getUpdatedAt());
    }

    @Test
    @DisplayName("Should create program exercise with parameters")
    void shouldCreateProgramExerciseWithParameters() {
        // Given
        Integer orderInProgram = 1;

        // When
        ProgramExercise newProgramExercise = new ProgramExercise(trainingProgram, exercise, orderInProgram);

        // Then
        assertEquals(trainingProgram, newProgramExercise.getTrainingProgram());
        assertEquals(exercise, newProgramExercise.getExercise());
        assertEquals(orderInProgram, newProgramExercise.getOrderInProgram());
        assertFalse(newProgramExercise.getIsOptional());
        assertNotNull(newProgramExercise.getCreatedAt());
    }

    @Test
    @DisplayName("Should set and get all properties correctly")
    void shouldSetAndGetAllPropertiesCorrectly() {
        // Given
        Long id = 1L;
        Integer orderInProgram = 2;
        Integer setsCount = 3;
        Integer repsCount = 12;
        Integer durationSeconds = 60;
        Integer restDurationSeconds = 90;
        Double weightKg = 50.0;
        Double distanceMeters = 100.0;
        String notes = "Test notes";
        Boolean isOptional = true;
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime updatedAt = LocalDateTime.now();

        // When
        programExercise.setId(id);
        programExercise.setTrainingProgram(trainingProgram);
        programExercise.setExercise(exercise);
        programExercise.setOrderInProgram(orderInProgram);
        programExercise.setSetsCount(setsCount);
        programExercise.setRepsCount(repsCount);
        programExercise.setDurationSeconds(durationSeconds);
        programExercise.setRestDurationSeconds(restDurationSeconds);
        programExercise.setWeightKg(weightKg);
        programExercise.setDistanceMeters(distanceMeters);
        programExercise.setNotes(notes);
        programExercise.setIsOptional(isOptional);
        programExercise.setCreatedAt(createdAt);
        programExercise.setUpdatedAt(updatedAt);

        // Then
        assertEquals(id, programExercise.getId());
        assertEquals(trainingProgram, programExercise.getTrainingProgram());
        assertEquals(exercise, programExercise.getExercise());
        assertEquals(orderInProgram, programExercise.getOrderInProgram());
        assertEquals(setsCount, programExercise.getSetsCount());
        assertEquals(repsCount, programExercise.getRepsCount());
        assertEquals(durationSeconds, programExercise.getDurationSeconds());
        assertEquals(restDurationSeconds, programExercise.getRestDurationSeconds());
        assertEquals(weightKg, programExercise.getWeightKg());
        assertEquals(distanceMeters, programExercise.getDistanceMeters());
        assertEquals(notes, programExercise.getNotes());
        assertEquals(isOptional, programExercise.getIsOptional());
        assertEquals(createdAt, programExercise.getCreatedAt());
        assertEquals(updatedAt, programExercise.getUpdatedAt());
    }

    @Test
    @DisplayName("Should handle null values correctly")
    void shouldHandleNullValuesCorrectly() {
        // When
        programExercise.setSetsCount(null);
        programExercise.setRepsCount(null);
        programExercise.setDurationSeconds(null);
        programExercise.setRestDurationSeconds(null);
        programExercise.setWeightKg(null);
        programExercise.setDistanceMeters(null);
        programExercise.setNotes(null);
        programExercise.setUpdatedAt(null);

        // Then
        assertNull(programExercise.getSetsCount());
        assertNull(programExercise.getRepsCount());
        assertNull(programExercise.getDurationSeconds());
        assertNull(programExercise.getRestDurationSeconds());
        assertNull(programExercise.getWeightKg());
        assertNull(programExercise.getDistanceMeters());
        assertNull(programExercise.getNotes());
        assertNull(programExercise.getUpdatedAt());
    }

    @Test
    @DisplayName("Should update timestamp on update")
    void shouldUpdateTimestampOnUpdate() {
        // Given
        LocalDateTime originalCreatedAt = programExercise.getCreatedAt();
        
        // When
        programExercise.setOrderInProgram(5);
        // Simulate @PreUpdate
        programExercise.onUpdate();

        // Then
        assertEquals(originalCreatedAt, programExercise.getCreatedAt());
        assertNotNull(programExercise.getUpdatedAt());
        assertTrue(programExercise.getUpdatedAt().isAfter(originalCreatedAt) || 
                  programExercise.getUpdatedAt().equals(originalCreatedAt));
    }

    @Test
    @DisplayName("Should handle relationships correctly")
    void shouldHandleRelationshipsCorrectly() {
        // Given
        TrainingProgram newProgram = new TrainingProgram();
        newProgram.setId(2L);
        newProgram.setName("New Program");
        
        Exercise newExercise = new Exercise();
        newExercise.setId(2L);
        newExercise.setName("New Exercise");

        // When
        programExercise.setTrainingProgram(newProgram);
        programExercise.setExercise(newExercise);

        // Then
        assertEquals(newProgram, programExercise.getTrainingProgram());
        assertEquals(newExercise, programExercise.getExercise());
        assertEquals(2L, programExercise.getTrainingProgram().getId());
        assertEquals(2L, programExercise.getExercise().getId());
    }

    @Test
    @DisplayName("Should handle optional exercise correctly")
    void shouldHandleOptionalExerciseCorrectly() {
        // When
        programExercise.setIsOptional(true);

        // Then
        assertTrue(programExercise.getIsOptional());
        
        // When
        programExercise.setIsOptional(false);

        // Then
        assertFalse(programExercise.getIsOptional());
    }
} 