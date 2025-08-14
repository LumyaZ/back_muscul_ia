package com.example.muscul_ia.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        TrainingProgram newProgram = new TrainingProgram();

        assertNotNull(newProgram);
        // Avec Lombok, createdAt est initialisé par @PrePersist, donc il peut être null jusqu'à la persistance
        // On vérifie juste que l'objet est créé correctement
        assertNotNull(newProgram);
        assertNull(newProgram.getUpdatedAt());
    }

    @Test
    @DisplayName("Should create training program with parameters")
    void shouldCreateTrainingProgramWithParameters() {
        String name = "Programme Débutant";
        String description = "Programme pour débuter en musculation";
        String difficultyLevel = "Débutant";

        TrainingProgram newProgram = new TrainingProgram();
        newProgram.setName(name);
        newProgram.setDescription(description);
        newProgram.setDifficultyLevel(difficultyLevel);
        
        // Appeler manuellement onCreate() pour simuler @PrePersist
        newProgram.onCreate();

        assertEquals(name, newProgram.getName());
        assertEquals(description, newProgram.getDescription());
        assertEquals(difficultyLevel, newProgram.getDifficultyLevel());
        assertNotNull(newProgram.getCreatedAt());
    }

    @Test
    @DisplayName("Should set and get all properties correctly")
    void shouldSetAndGetAllPropertiesCorrectly() {
        Long id = 1L;
        String name = "Programme Avancé";
        String description = "Programme pour sportifs confirmés";
        String difficultyLevel = "Avancé";
        String category = "Musculation";
        String targetAudience = "Sportifs confirmés";
        LocalDateTime createdAt = LocalDateTime.now();
        LocalDateTime updatedAt = LocalDateTime.now();

        trainingProgram.setId(id);
        trainingProgram.setName(name);
        trainingProgram.setDescription(description);
        trainingProgram.setDifficultyLevel(difficultyLevel);
        trainingProgram.setCategory(category);
        trainingProgram.setTargetAudience(targetAudience);
        trainingProgram.setCreatedAt(createdAt);
        trainingProgram.setUpdatedAt(updatedAt);
        trainingProgram.setCreatedByUser(user);

        assertEquals(id, trainingProgram.getId());
        assertEquals(name, trainingProgram.getName());
        assertEquals(description, trainingProgram.getDescription());
        assertEquals(difficultyLevel, trainingProgram.getDifficultyLevel());
        assertEquals(category, trainingProgram.getCategory());
        assertEquals(targetAudience, trainingProgram.getTargetAudience());
        assertEquals(createdAt, trainingProgram.getCreatedAt());
        assertEquals(updatedAt, trainingProgram.getUpdatedAt());
        assertEquals(user, trainingProgram.getCreatedByUser());
    }

    @Test
    @DisplayName("Should handle null values correctly")
    void shouldHandleNullValuesCorrectly() {
        trainingProgram.setDescription(null);
        trainingProgram.setDifficultyLevel(null);
        trainingProgram.setCategory(null);
        trainingProgram.setTargetAudience(null);
        trainingProgram.setUpdatedAt(null);

        assertNull(trainingProgram.getDescription());
        assertNull(trainingProgram.getDifficultyLevel());
        assertNull(trainingProgram.getCategory());
        assertNull(trainingProgram.getTargetAudience());
        assertNull(trainingProgram.getUpdatedAt());
    }

    @Test
    @DisplayName("Should update timestamp on update")
    void shouldUpdateTimestampOnUpdate() {
        // Initialiser createdAt pour éviter NullPointerException
        trainingProgram.setCreatedAt(LocalDateTime.now());
        LocalDateTime originalCreatedAt = trainingProgram.getCreatedAt();
        
        trainingProgram.setName("Updated Name");
        trainingProgram.onUpdate();

        assertEquals(originalCreatedAt, trainingProgram.getCreatedAt());
        assertNotNull(trainingProgram.getUpdatedAt());
        assertTrue(trainingProgram.getUpdatedAt().isAfter(originalCreatedAt) || 
                  trainingProgram.getUpdatedAt().equals(originalCreatedAt));
    }

    @Test
    @DisplayName("Should handle user relationship correctly")
    void shouldHandleUserRelationshipCorrectly() {
        trainingProgram.setCreatedByUser(user);

        assertEquals(user, trainingProgram.getCreatedByUser());
        assertEquals(1L, trainingProgram.getCreatedByUser().getId());
        assertEquals("test@example.com", trainingProgram.getCreatedByUser().getEmail());
    }

    @Test
    @DisplayName("Should handle equality correctly")
    void shouldHandleEqualityCorrectly() {
        TrainingProgram program1 = new TrainingProgram();
        program1.setId(1L);
        program1.setName("Program 1");

        TrainingProgram program2 = new TrainingProgram();
        program2.setId(1L);
        program2.setName("Program 1");

        TrainingProgram program3 = new TrainingProgram();
        program3.setId(2L);
        program3.setName("Program 2");

        assertEquals(program1, program2);
        assertNotEquals(program1, program3);
        assertNotEquals(program1, null);
        assertNotEquals(program1, new Object());
    }

    @Test
    @DisplayName("Should handle hashCode correctly")
    void shouldHandleHashCodeCorrectly() {
        TrainingProgram program1 = new TrainingProgram();
        program1.setId(1L);
        program1.setName("Program 1");

        TrainingProgram program2 = new TrainingProgram();
        program2.setId(1L);
        program2.setName("Program 1");

        assertEquals(program1.hashCode(), program2.hashCode());
    }

    @Test
    @DisplayName("Should handle toString correctly")
    void shouldHandleToStringCorrectly() {
        trainingProgram.setId(1L);
        trainingProgram.setName("Test Program");
        
        String toString = trainingProgram.toString();

        assertTrue(toString.contains("1"));
        assertTrue(toString.contains("Test Program"));
    }
} 