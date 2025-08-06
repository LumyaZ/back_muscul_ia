package com.example.muscul_ia.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TrainingSessionTest {

    private TrainingSession trainingSession;
    private User user;  
    private TrainingProgram trainingProgram;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");

        trainingProgram = new TrainingProgram();
        trainingProgram.setId(1L);
        trainingProgram.setName("Test Program");

        trainingSession = new TrainingSession();
    }

    @Test
    void testTrainingSessionCreation() {
        trainingSession.setId(1L);
        trainingSession.setUser(user);
        trainingSession.setName("Test Session");
        trainingSession.setDescription("Test Description");
        trainingSession.setSessionDate(LocalDateTime.now());
        trainingSession.setDurationMinutes(60);
        trainingSession.setSessionType("Strength");
        trainingSession.setTrainingProgram(trainingProgram);
        trainingSession.setCreatedAt(LocalDateTime.now());
        trainingSession.setUpdatedAt(LocalDateTime.now());

        assertEquals(1L, trainingSession.getId());
        assertEquals(user, trainingSession.getUser());
        assertEquals("Test Session", trainingSession.getName());
        assertEquals("Test Description", trainingSession.getDescription());
        assertNotNull(trainingSession.getSessionDate());
        assertEquals(60, trainingSession.getDurationMinutes());
        assertEquals("Strength", trainingSession.getSessionType());
        assertEquals(trainingProgram, trainingSession.getTrainingProgram());
        assertNotNull(trainingSession.getCreatedAt());
        assertNotNull(trainingSession.getUpdatedAt());
    }

    @Test
    void testTrainingSessionEquality() {
        TrainingSession session1 = new TrainingSession();
        session1.setId(1L);
        session1.setName("Test Session");
        session1.setSessionType("Strength");

        TrainingSession session2 = new TrainingSession();
        session2.setId(1L);
        session2.setName("Test Session");
        session2.setSessionType("Strength");

        TrainingSession session3 = new TrainingSession();
        session3.setId(2L);
        session3.setName("Different Session");
        session3.setSessionType("Cardio");

        assertEquals(session1, session2);
        assertNotEquals(session1, session3);
        assertNotEquals(session1, null);
        assertNotEquals(session1, new Object());
    }

    @Test
    void testTrainingSessionHashCode() {
        TrainingSession session1 = new TrainingSession();
        session1.setId(1L);
        session1.setName("Test Session");

        TrainingSession session2 = new TrainingSession();
        session2.setId(1L);
        session2.setName("Test Session");

        assertEquals(session1.hashCode(), session2.hashCode());
    }

    @Test
    void testTrainingSessionToString() {
        trainingSession.setId(1L);
        trainingSession.setName("Test Session");
        trainingSession.setSessionType("Strength");

        String toString = trainingSession.toString();
        assertTrue(toString.contains("1"));
        assertTrue(toString.contains("Test Session"));
        assertTrue(toString.contains("Strength"));
    }

    @Test
    void testTrainingSessionWithNullValues() {
        trainingSession.setId(1L);
        trainingSession.setUser(null);
        trainingSession.setName(null);
        trainingSession.setDescription(null);
        trainingSession.setSessionDate(null);
        trainingSession.setSessionType(null);
        trainingSession.setTrainingProgram(null);

        assertEquals(1L, trainingSession.getId());
        assertNull(trainingSession.getUser());
        assertNull(trainingSession.getName());
        assertNull(trainingSession.getDescription());
        assertNull(trainingSession.getSessionDate());
        assertNull(trainingSession.getSessionType());
        assertNull(trainingSession.getTrainingProgram());
    }

    @Test
    void testTrainingSessionNoArgsConstructor() {
        TrainingSession emptySession = new TrainingSession();
        assertNotNull(emptySession);
    }

    @Test
    void testDurationMinutesValidation() {
        trainingSession.setDurationMinutes(-10);
        assertEquals(-10, trainingSession.getDurationMinutes());

        trainingSession.setDurationMinutes(0);
        assertEquals(0, trainingSession.getDurationMinutes());

        trainingSession.setDurationMinutes(300);
        assertEquals(300, trainingSession.getDurationMinutes());
    }

    @Test
    void testSessionTypeValidation() {
        trainingSession.setSessionType("Strength");
        assertEquals("Strength", trainingSession.getSessionType());

        trainingSession.setSessionType("Cardio");
        assertEquals("Cardio", trainingSession.getSessionType());

        trainingSession.setSessionType("Flexibility");
        assertEquals("Flexibility", trainingSession.getSessionType());

        trainingSession.setSessionType("");
        assertEquals("", trainingSession.getSessionType());
    }

    @Test
    void testTrainingSessionWithTrainingProgram() {
        trainingSession.setTrainingProgram(trainingProgram);
        assertEquals(trainingProgram, trainingSession.getTrainingProgram());
        assertEquals(1L, trainingSession.getTrainingProgram().getId());
        assertEquals("Test Program", trainingSession.getTrainingProgram().getName());
    }

    @Test
    void testTrainingSessionWithoutTrainingProgram() {
        trainingSession.setTrainingProgram(null);
        assertNull(trainingSession.getTrainingProgram());
    }
} 