package com.example.muscul_ia.service.impl;

import com.example.muscul_ia.dto.CreateUserProfileRequest;
import com.example.muscul_ia.dto.CreateUserProfileWithEmailRequest;
import com.example.muscul_ia.dto.UpdateUserProfileRequest;
import com.example.muscul_ia.dto.UserProfileDto;
import com.example.muscul_ia.entity.User;
import com.example.muscul_ia.entity.UserProfile;
import com.example.muscul_ia.repository.UserProfileRepository;
import com.example.muscul_ia.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
@ExtendWith(MockitoExtension.class)
class UserProfileServiceImplTest {

    @Mock
    private UserProfileRepository userProfileRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserProfileServiceImpl userProfileService;

    private User testUser;
    private UserProfile testUserProfile;
    private CreateUserProfileRequest createRequest;
    private CreateUserProfileWithEmailRequest createWithEmailRequest;
    private UpdateUserProfileRequest updateRequest;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1L);
        testUser.setEmail("test@example.com");
        testUser.setCreationDate(LocalDateTime.now());

        testUserProfile = new UserProfile(testUser);
        testUserProfile.setId(1L);
        testUserProfile.setFirstName("John");
        testUserProfile.setLastName("Doe");
        testUserProfile.setDateOfBirth(LocalDate.of(1990, 1, 1));
        testUserProfile.setPhoneNumber("+33123456789");

        createRequest = new CreateUserProfileRequest();
        createRequest.setFirstName("John");
        createRequest.setLastName("Doe");
        createRequest.setDateOfBirth(LocalDate.of(1990, 1, 1));
        createRequest.setPhoneNumber("+33123456789");

        createWithEmailRequest = new CreateUserProfileWithEmailRequest();
        createWithEmailRequest.setEmail("test@example.com");
        createWithEmailRequest.setFirstName("John");
        createWithEmailRequest.setLastName("Doe");
        createWithEmailRequest.setDateOfBirth(LocalDate.of(1990, 1, 1));
        createWithEmailRequest.setPhoneNumber("+33123456789");

        updateRequest = new UpdateUserProfileRequest();
        updateRequest.setFirstName("Jane");
        updateRequest.setLastName("Smith");
    }

    @Test
    void testCreateProfileSuccess() {
        when(userProfileRepository.existsByUser(testUser)).thenReturn(false);
        when(userProfileRepository.save(any(UserProfile.class))).thenReturn(testUserProfile);

        UserProfileDto result = userProfileService.createProfile(testUser, createRequest);

        assertNotNull(result);
        assertEquals(testUserProfile.getId(), result.getId());
        assertEquals(testUserProfile.getFirstName(), result.getFirstName());
        assertEquals(testUserProfile.getLastName(), result.getLastName());
        verify(userProfileRepository).save(any(UserProfile.class));
    }

    @Test
    void testCreateProfileByEmailSuccess() {
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(testUser));
        when(userProfileRepository.existsByUser(testUser)).thenReturn(false);
        when(userProfileRepository.save(any(UserProfile.class))).thenReturn(testUserProfile);

        UserProfileDto result = userProfileService.createProfileByEmail(createWithEmailRequest);

        assertNotNull(result);
        assertEquals(testUserProfile.getId(), result.getId());
        assertEquals(testUserProfile.getFirstName(), result.getFirstName());
        assertEquals(testUserProfile.getLastName(), result.getLastName());
        verify(userRepository).findByEmail("test@example.com");
        verify(userProfileRepository).save(any(UserProfile.class));
    }

    @Test
    void testCreateProfileByEmailUserNotFound() {
        when(userRepository.findByEmail("nonexistent@example.com")).thenReturn(Optional.empty());

        createWithEmailRequest.setEmail("nonexistent@example.com");

        assertThrows(RuntimeException.class, () -> {
            userProfileService.createProfileByEmail(createWithEmailRequest);
        });

        verify(userRepository).findByEmail("nonexistent@example.com");
        verify(userProfileRepository, never()).save(any(UserProfile.class));
    }

    @Test
    void testCreateProfileByEmailUserAlreadyHasProfile() {
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(testUser));
        when(userProfileRepository.existsByUser(testUser)).thenReturn(true);

        assertThrows(RuntimeException.class, () -> {
            userProfileService.createProfileByEmail(createWithEmailRequest);
        });

        verify(userRepository).findByEmail("test@example.com");
        verify(userProfileRepository).existsByUser(testUser);
        verify(userProfileRepository, never()).save(any(UserProfile.class));
    }

    @Test
    void testGetProfileByUserSuccess() {
        when(userProfileRepository.findByUser(testUser)).thenReturn(Optional.of(testUserProfile));

        UserProfileDto result = userProfileService.getProfileByUser(testUser);

        assertNotNull(result);
        assertEquals(testUserProfile.getId(), result.getId());
        assertEquals(testUserProfile.getFirstName(), result.getFirstName());
    }

    @Test
    void testGetProfileByUserIdSuccess() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(userProfileRepository.findByUser(testUser)).thenReturn(Optional.of(testUserProfile));

        UserProfileDto result = userProfileService.getProfileByUserId(1L);

        assertNotNull(result);
        assertEquals(testUserProfile.getId(), result.getId());
        assertEquals(testUserProfile.getFirstName(), result.getFirstName());
    }

    @Test
    void testUpdateProfileSuccess() {
        UserProfile updatedProfile = new UserProfile(testUser);
        updatedProfile.setId(1L);
        updatedProfile.setFirstName("Jane");
        updatedProfile.setLastName("Smith");

        when(userProfileRepository.findByUser(testUser)).thenReturn(Optional.of(testUserProfile));
        when(userProfileRepository.save(any(UserProfile.class))).thenReturn(updatedProfile);

        UserProfileDto result = userProfileService.updateProfile(testUser, updateRequest);

        assertNotNull(result);
        assertEquals("Jane", result.getFirstName());
        assertEquals("Smith", result.getLastName());
        verify(userProfileRepository).save(any(UserProfile.class));
    }

    @Test
    void testDeleteProfileSuccess() {
        when(userProfileRepository.findByUser(testUser)).thenReturn(Optional.of(testUserProfile));
        doNothing().when(userProfileRepository).delete(testUserProfile);

        assertDoesNotThrow(() -> userProfileService.deleteProfile(testUser));

        verify(userProfileRepository).delete(testUserProfile);
    }
} 