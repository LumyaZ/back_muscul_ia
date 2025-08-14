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
        trainingProgram = new TrainingProgram();
        trainingProgram.setId(1L);
        trainingProgram.setName("Test Program");

        exercise = new Exercise();
        exercise.setId(1L);
        exercise.setName("Test Exercise");

        programExercise = new ProgramExercise();
        programExercise.setId(1L);
        programExercise.setTrainingProgram(trainingProgram);
        programExercise.setExercise(exercise);
        programExercise.setSetsCount(3);
        programExercise.setRepsCount(12);
        programExercise.setRestDurationSeconds(90);
        programExercise.setWeightKg(50.0);
        programExercise.setDistanceMeters(100.0);
        programExercise.setNotes("Test notes");
        programExercise.setCreatedAt(LocalDateTime.now()); // Initialiser createdAt
    }

    @Test
    @DisplayName("Should create program exercise with default values")
    void shouldCreateProgramExerciseWithDefaultValues() {
        ProgramExercise newProgramExercise = new ProgramExercise();

        assertNotNull(newProgramExercise);
        // Ne pas tester createdAt car il n'est pas initialisé automatiquement
        assertNull(newProgramExercise.getCreatedAt());
        assertNull(newProgramExercise.getUpdatedAt());
    }

    @Test
    @DisplayName("Should create program exercise with parameters")
    void shouldCreateProgramExerciseWithParameters() {
        ProgramExercise newProgramExercise = new ProgramExercise(trainingProgram, exercise);

        assertEquals(trainingProgram, newProgramExercise.getTrainingProgram());
        assertEquals(exercise, newProgramExercise.getExercise());
        assertNotNull(newProgramExercise.getCreatedAt());
    }

    @Test
    @DisplayName("Should set and get all properties correctly")
    void shouldSetAndGetAllPropertiesCorrectly() {
        Long id = 1L;
        Integer setsCount = 3;
        Integer repsCount = 12;
        Integer restDurationSeconds = 90;
        Double weightKg = 50.0;
        Double distanceMeters = 100.0;
        String notes = "Test notes";
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime updatedAt = LocalDateTime.now();

        programExercise.setId(id);
        programExercise.setTrainingProgram(trainingProgram);
        programExercise.setExercise(exercise);
        programExercise.setSetsCount(setsCount);
        programExercise.setRepsCount(repsCount);
        programExercise.setRestDurationSeconds(restDurationSeconds);
        programExercise.setWeightKg(weightKg);
        programExercise.setDistanceMeters(distanceMeters);
        programExercise.setNotes(notes);
        programExercise.setCreatedAt(createdAt);
        programExercise.setUpdatedAt(updatedAt);

        assertEquals(id, programExercise.getId());
        assertEquals(trainingProgram, programExercise.getTrainingProgram());
        assertEquals(exercise, programExercise.getExercise());
        assertEquals(setsCount, programExercise.getSetsCount());
        assertEquals(repsCount, programExercise.getRepsCount());
        assertEquals(restDurationSeconds, programExercise.getRestDurationSeconds());
        assertEquals(weightKg, programExercise.getWeightKg());
        assertEquals(distanceMeters, programExercise.getDistanceMeters());
        assertEquals(notes, programExercise.getNotes());
        assertEquals(createdAt, programExercise.getCreatedAt());
        assertEquals(updatedAt, programExercise.getUpdatedAt());
    }

    @Test
    @DisplayName("Should handle null values correctly")
    void shouldHandleNullValuesCorrectly() {
        programExercise.setSetsCount(null);
        programExercise.setRepsCount(null);
        programExercise.setRestDurationSeconds(null);
        programExercise.setWeightKg(null);
        programExercise.setDistanceMeters(null);
        programExercise.setNotes(null);
        programExercise.setUpdatedAt(null);

        assertNull(programExercise.getSetsCount());
        assertNull(programExercise.getRepsCount());
        assertNull(programExercise.getRestDurationSeconds());
        assertNull(programExercise.getWeightKg());
        assertNull(programExercise.getDistanceMeters());
        assertNull(programExercise.getNotes());
        assertNull(programExercise.getUpdatedAt());
    }

    @Test
    @DisplayName("Should update timestamp on update")
    void shouldUpdateTimestampOnUpdate() {
        LocalDateTime originalCreatedAt = programExercise.getCreatedAt();
        LocalDateTime beforeUpdate = LocalDateTime.now();
        
        programExercise.setNotes("Updated notes");
        programExercise.onUpdate();

        assertEquals(originalCreatedAt, programExercise.getCreatedAt());
        assertNotNull(programExercise.getUpdatedAt());
        assertTrue(programExercise.getUpdatedAt().isAfter(beforeUpdate) || 
                  programExercise.getUpdatedAt().equals(beforeUpdate));
    }

    @Test
    @DisplayName("Should handle relationships correctly")
    void shouldHandleRelationshipsCorrectly() {
        TrainingProgram newProgram = new TrainingProgram();
        newProgram.setId(2L);
        newProgram.setName("New Program");
        
        Exercise newExercise = new Exercise();
        newExercise.setId(2L);
        newExercise.setName("New Exercise");

        programExercise.setTrainingProgram(newProgram);
        programExercise.setExercise(newExercise);

        assertEquals(newProgram, programExercise.getTrainingProgram());
        assertEquals(newExercise, programExercise.getExercise());
    }
} 