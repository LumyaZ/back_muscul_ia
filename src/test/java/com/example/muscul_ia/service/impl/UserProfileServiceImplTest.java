package com.example.muscul_ia.service.impl;

import com.example.muscul_ia.dto.CreateUserProfileRequest;
import com.example.muscul_ia.dto.UpdateUserProfileRequest;
import com.example.muscul_ia.dto.UserProfileDto;
import com.example.muscul_ia.entity.User;
import com.example.muscul_ia.entity.UserProfile;
import com.example.muscul_ia.repository.UserProfileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit tests for UserProfileServiceImpl.
 * Tests unitaires pour UserProfileServiceImpl.
 */
class UserProfileServiceImplTest {

    private UserProfileServiceImpl userProfileService;
    private UserProfileRepository userProfileRepository;
    private User testUser;

    @BeforeEach
    void setUp() {
        userProfileRepository = Mockito.mock(UserProfileRepository.class);
        userProfileService = new UserProfileServiceImpl();
        userProfileService.userProfileRepository = userProfileRepository;

        testUser = new User();
        testUser.setId(1L);
        testUser.setEmail("test@example.com");
        testUser.setCreationDate(LocalDateTime.now());
    }

    @Test
    void testCreateProfileSuccess() {
        // Given
        CreateUserProfileRequest request = new CreateUserProfileRequest();
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setDateOfBirth(LocalDate.of(1990, 1, 1));
        request.setPhoneNumber("+33123456789");

        UserProfile savedProfile = new UserProfile(testUser);
        savedProfile.setId(1L);
        savedProfile.setFirstName("John");
        savedProfile.setLastName("Doe");
        savedProfile.setDateOfBirth(LocalDate.of(1990, 1, 1));
        savedProfile.setPhoneNumber("+33123456789");

        when(userProfileRepository.existsByUser(testUser)).thenReturn(false);
        when(userProfileRepository.save(any(UserProfile.class))).thenReturn(savedProfile);

        // When
        UserProfileDto result = userProfileService.createProfile(testUser, request);

        // Then
        assertNotNull(result);
        assertEquals("John", result.getFirstName());
        assertEquals("Doe", result.getLastName());
        assertEquals(LocalDate.of(1990, 1, 1), result.getDateOfBirth());
        assertEquals("+33123456789", result.getPhoneNumber());
        verify(userProfileRepository).existsByUser(testUser);
        verify(userProfileRepository).save(any(UserProfile.class));
    }

    @Test
    void testCreateProfileUserAlreadyHasProfile() {
        // Given
        CreateUserProfileRequest request = new CreateUserProfileRequest();
        when(userProfileRepository.existsByUser(testUser)).thenReturn(true);

        // When & Then
        assertThrows(RuntimeException.class, () -> {
            userProfileService.createProfile(testUser, request);
        });
        verify(userProfileRepository).existsByUser(testUser);
        verify(userProfileRepository, never()).save(any(UserProfile.class));
    }

    @Test
    void testGetProfileByUserSuccess() {
        // Given
        UserProfile profile = new UserProfile(testUser);
        profile.setId(1L);
        profile.setFirstName("John");
        profile.setLastName("Doe");

        when(userProfileRepository.findByUser(testUser)).thenReturn(Optional.of(profile));

        // When
        Optional<UserProfileDto> result = userProfileService.getProfileByUser(testUser);

        // Then
        assertTrue(result.isPresent());
        assertEquals("John", result.get().getFirstName());
        assertEquals("Doe", result.get().getLastName());
        verify(userProfileRepository).findByUser(testUser);
    }

    @Test
    void testGetProfileByUserNotFound() {
        // Given
        when(userProfileRepository.findByUser(testUser)).thenReturn(Optional.empty());

        // When
        Optional<UserProfileDto> result = userProfileService.getProfileByUser(testUser);

        // Then
        assertFalse(result.isPresent());
        verify(userProfileRepository).findByUser(testUser);
    }

    @Test
    void testGetProfileByUserIdSuccess() {
        // Given
        UserProfile profile = new UserProfile(testUser);
        profile.setId(1L);
        profile.setFirstName("John");
        profile.setLastName("Doe");

        when(userProfileRepository.findByUserId(1L)).thenReturn(Optional.of(profile));

        // When
        Optional<UserProfileDto> result = userProfileService.getProfileByUserId(1L);

        // Then
        assertTrue(result.isPresent());
        assertEquals("John", result.get().getFirstName());
        assertEquals("Doe", result.get().getLastName());
        verify(userProfileRepository).findByUserId(1L);
    }

    @Test
    void testGetProfileByUserIdNotFound() {
        // Given
        when(userProfileRepository.findByUserId(1L)).thenReturn(Optional.empty());

        // When
        Optional<UserProfileDto> result = userProfileService.getProfileByUserId(1L);

        // Then
        assertFalse(result.isPresent());
        verify(userProfileRepository).findByUserId(1L);
    }

    @Test
    void testUpdateProfileSuccess() {
        // Given
        UserProfile existingProfile = new UserProfile(testUser);
        existingProfile.setId(1L);
        existingProfile.setFirstName("John");
        existingProfile.setLastName("Doe");

        UpdateUserProfileRequest request = new UpdateUserProfileRequest();
        request.setFirstName("Jane");
        request.setLastName("Smith");

        UserProfile updatedProfile = new UserProfile(testUser);
        updatedProfile.setId(1L);
        updatedProfile.setFirstName("Jane");
        updatedProfile.setLastName("Smith");

        when(userProfileRepository.findByUser(testUser)).thenReturn(Optional.of(existingProfile));
        when(userProfileRepository.save(any(UserProfile.class))).thenReturn(updatedProfile);

        // When
        UserProfileDto result = userProfileService.updateProfile(testUser, request);

        // Then
        assertNotNull(result);
        assertEquals("Jane", result.getFirstName());
        assertEquals("Smith", result.getLastName());
        verify(userProfileRepository).findByUser(testUser);
        verify(userProfileRepository).save(any(UserProfile.class));
    }

    @Test
    void testUpdateProfileNotFound() {
        // Given
        UpdateUserProfileRequest request = new UpdateUserProfileRequest();
        when(userProfileRepository.findByUser(testUser)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(RuntimeException.class, () -> {
            userProfileService.updateProfile(testUser, request);
        });
        verify(userProfileRepository).findByUser(testUser);
        verify(userProfileRepository, never()).save(any(UserProfile.class));
    }

    @Test
    void testDeleteProfileSuccess() {
        // Given
        UserProfile profile = new UserProfile(testUser);
        profile.setId(1L);
        when(userProfileRepository.findByUser(testUser)).thenReturn(Optional.of(profile));

        // When
        userProfileService.deleteProfile(testUser);

        // Then
        verify(userProfileRepository).findByUser(testUser);
        verify(userProfileRepository).delete(profile);
    }

    @Test
    void testDeleteProfileNotFound() {
        // Given
        when(userProfileRepository.findByUser(testUser)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(RuntimeException.class, () -> {
            userProfileService.deleteProfile(testUser);
        });
        verify(userProfileRepository).findByUser(testUser);
        verify(userProfileRepository, never()).delete(any(UserProfile.class));
    }
} 