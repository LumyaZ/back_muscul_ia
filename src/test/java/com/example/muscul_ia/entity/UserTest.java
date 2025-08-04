package com.example.muscul_ia.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserTest {

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
    }

    @Test
    void testUserCreation() {
        user.setId(1L);
        user.setEmail("test@example.com");
        user.setPassword("hashedPassword");
        user.setCreationDate(LocalDateTime.now());

        assertEquals(1L, user.getId());
        assertEquals("test@example.com", user.getEmail());
        assertEquals("hashedPassword", user.getPassword());
        assertNotNull(user.getCreationDate());
    }

    @Test
    void testUserEquality() {
        User user1 = new User();
        user1.setId(1L);
        user1.setEmail("test@example.com");

        User user2 = new User();
        user2.setId(1L);
        user2.setEmail("test@example.com");

        User user3 = new User();
        user3.setId(2L);
        user3.setEmail("different@example.com");

        assertEquals(user1, user2);
        assertNotEquals(user1, user3);
        assertNotEquals(user1, null);
        assertNotEquals(user1, new Object());
    }

    @Test
    void testUserHashCode() {
        User user1 = new User();
        user1.setId(1L);
        user1.setEmail("test@example.com");

        User user2 = new User();
        user2.setId(1L);
        user2.setEmail("test@example.com");

        assertEquals(user1.hashCode(), user2.hashCode());
    }

    @Test
    void testUserToString() {
        user.setId(1L);
        user.setEmail("test@example.com");

        String toString = user.toString();
        assertTrue(toString.contains("1"));
        assertTrue(toString.contains("test@example.com"));
    }

    @Test
    void testUserWithNullValues() {
        user.setId(1L);
        user.setEmail(null);
        user.setPassword(null);
        user.setCreationDate(null);

        assertEquals(1L, user.getId());
        assertNull(user.getEmail());
        assertNull(user.getPassword());
        assertNull(user.getCreationDate());
    }

    @Test
    void testUserNoArgsConstructor() {
        User emptyUser = new User();
        assertNotNull(emptyUser);
    }
} 