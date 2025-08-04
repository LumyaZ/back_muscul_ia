package com.example.muscul_ia.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserProfileTest {

    private UserProfile userProfile;
    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");

        userProfile = new UserProfile();
    }

    @Test
    void testUserProfileCreation() {
        userProfile.setId(1L);
        userProfile.setUser(user);
        userProfile.setFirstName("John");
        userProfile.setLastName("Doe");
        userProfile.setDateOfBirth(LocalDate.of(1990, 1, 1));
        userProfile.setAge(33);
        userProfile.setPhoneNumber("+33123456789");
        userProfile.setCreatedAt(LocalDateTime.now());
        userProfile.setUpdatedAt(LocalDateTime.now());

        assertEquals(1L, userProfile.getId());
        assertEquals(user, userProfile.getUser());
        assertEquals("John", userProfile.getFirstName());
        assertEquals("Doe", userProfile.getLastName());
        assertEquals(LocalDate.of(1990, 1, 1), userProfile.getDateOfBirth());
        assertEquals(33, userProfile.getAge());
        assertEquals("+33123456789", userProfile.getPhoneNumber());
        assertNotNull(userProfile.getCreatedAt());
        assertNotNull(userProfile.getUpdatedAt());
    }

    @Test
    void testUserProfileEquality() {
        UserProfile profile1 = new UserProfile();
        profile1.setId(1L);
        profile1.setFirstName("John");
        profile1.setLastName("Doe");

        UserProfile profile2 = new UserProfile();
        profile2.setId(1L);
        profile2.setFirstName("John");
        profile2.setLastName("Doe");

        UserProfile profile3 = new UserProfile();
        profile3.setId(2L);
        profile3.setFirstName("Jane");
        profile3.setLastName("Smith");

        assertEquals(profile1, profile2);
        assertNotEquals(profile1, profile3);
        assertNotEquals(profile1, null);
        assertNotEquals(profile1, new Object());
    }

    @Test
    void testUserProfileHashCode() {
        UserProfile profile1 = new UserProfile();
        profile1.setId(1L);
        profile1.setFirstName("John");

        UserProfile profile2 = new UserProfile();
        profile2.setId(1L);
        profile2.setFirstName("John");

        assertEquals(profile1.hashCode(), profile2.hashCode());
    }

    @Test
    void testUserProfileToString() {
        userProfile.setId(1L);
        userProfile.setFirstName("John");
        userProfile.setLastName("Doe");

        String toString = userProfile.toString();
        assertTrue(toString.contains("1"));
        assertTrue(toString.contains("John"));
        assertTrue(toString.contains("Doe"));
    }

    @Test
    void testUserProfileWithNullValues() {
        userProfile.setId(1L);
        userProfile.setUser(null);
        userProfile.setFirstName(null);
        userProfile.setLastName(null);
        userProfile.setDateOfBirth(null);
        userProfile.setPhoneNumber(null);

        assertEquals(1L, userProfile.getId());
        assertNull(userProfile.getUser());
        assertNull(userProfile.getFirstName());
        assertNull(userProfile.getLastName());
        assertNull(userProfile.getDateOfBirth());
        assertNull(userProfile.getPhoneNumber());
    }

    @Test
    void testUserProfileNoArgsConstructor() {
        UserProfile emptyProfile = new UserProfile();
        assertNotNull(emptyProfile);
    }
} 