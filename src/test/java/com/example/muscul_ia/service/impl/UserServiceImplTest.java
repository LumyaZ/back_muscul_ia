package com.example.muscul_ia.service.impl;

import com.example.muscul_ia.dto.CreateUserWithProfileRequest;
import com.example.muscul_ia.dto.CreateUserWithProfileResponse;
import com.example.muscul_ia.dto.RegisterRequest;
import com.example.muscul_ia.dto.LoginRequest;
import com.example.muscul_ia.dto.CreateUserProfileRequest;
import com.example.muscul_ia.dto.UserDto;
import com.example.muscul_ia.dto.UserProfileDto;
import com.example.muscul_ia.entity.User;
import com.example.muscul_ia.entity.UserProfile;
import com.example.muscul_ia.repository.UserRepository;
import com.example.muscul_ia.service.UserProfileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit test for UserServiceImpl registration logic.
 * Test unitaire pour la logique d'inscription de UserServiceImpl.
 */
@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserProfileService userProfileService;

    @InjectMocks
    private UserServiceImpl userService;

    private User testUser;
    private UserProfile testUserProfile;
    private RegisterRequest registerRequest;
    private CreateUserProfileRequest profileRequest;
    private CreateUserWithProfileRequest createUserWithProfileRequest;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1L);
        testUser.setEmail("test@example.com");
        testUser.setPassword("hashedPassword");
        testUser.setCreationDate(LocalDateTime.now());

        testUserProfile = new UserProfile(testUser);
        testUserProfile.setId(1L);
        testUserProfile.setFirstName("John");
        testUserProfile.setLastName("Doe");
        testUserProfile.setDateOfBirth(LocalDate.of(1990, 1, 1));
        testUserProfile.setAge(33);
        testUserProfile.setPhoneNumber("+33123456789");
        testUserProfile.setCreatedAt(LocalDateTime.now());
        testUserProfile.setUpdatedAt(LocalDateTime.now());

        registerRequest = new RegisterRequest();
        registerRequest.setEmail("test@example.com");
        registerRequest.setPassword("password123");
        registerRequest.setConfirmPassword("password123");

        profileRequest = new CreateUserProfileRequest();
        profileRequest.setFirstName("John");
        profileRequest.setLastName("Doe");
        profileRequest.setDateOfBirth(LocalDate.of(1990, 1, 1));
        profileRequest.setPhoneNumber("+33123456789");

        createUserWithProfileRequest = new CreateUserWithProfileRequest();
        createUserWithProfileRequest.setUserData(registerRequest);
        createUserWithProfileRequest.setProfileData(profileRequest);
    }

    @Test
    void testRegisterCreatesUserWithHashedPassword() {
        // Arrange / Préparation
        when(passwordEncoder.encode("password")).thenReturn("hashedPassword");
        when(userRepository.save(any(User.class))).thenAnswer(i -> i.getArguments()[0]);

        RegisterRequest request = new RegisterRequest();
        request.setEmail("testuser@email.com");
        request.setPassword("password");
        request.setConfirmPassword("password");

        // Act / Action
        UserDto result = userService.register(request);

        // Assert / Vérification
        assertNotNull(result);
        assertEquals("testuser@email.com", result.getEmail());
        verify(passwordEncoder).encode("password");
        verify(userRepository).save(any(User.class));
    }

    @Test
    void testLoginWithCorrectCredentials() {
        // Arrange / Préparation
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(testUser));
        when(passwordEncoder.matches("password123", "hashedPassword")).thenReturn(true);

        LoginRequest request = new LoginRequest();
        request.setEmail("test@example.com");
        request.setPassword("password123");

        // Act / Action
        UserDto result = userService.login(request);

        // Assert / Vérification
        assertNotNull(result);
        assertEquals("test@example.com", result.getEmail());
        verify(userRepository).findByEmail("test@example.com");
        verify(passwordEncoder).matches("password123", "hashedPassword");
    }

    @Test
    void testRegisterWithExistingEmailThrowsException() {
        // Arrange / Préparation
        when(userRepository.findByEmail("existing@email.com")).thenReturn(Optional.of(testUser));

        RegisterRequest request = new RegisterRequest();
        request.setEmail("existing@email.com");
        request.setPassword("password");
        request.setConfirmPassword("password");

        // Act & Assert / Action et Vérification
        assertThrows(RuntimeException.class, () -> userService.register(request));
        verify(userRepository).findByEmail("existing@email.com");
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testRegisterWithNonMatchingPasswordsThrowsException() {
        // Arrange / Préparation
        RegisterRequest request = new RegisterRequest();
        request.setEmail("test@email.com");
        request.setPassword("password");
        request.setConfirmPassword("differentPassword");

        // Act & Assert / Action et Vérification
        assertThrows(RuntimeException.class, () -> userService.register(request));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testCreateUserWithProfileSuccess() {
        // Arrange / Préparation
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("password123")).thenReturn("hashedPassword");
        when(userRepository.save(any(User.class))).thenReturn(testUser);
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(userProfileService.createProfile(testUser, profileRequest)).thenReturn(new UserProfileDto(testUserProfile));

        // Act / Action
        CreateUserWithProfileResponse result = userService.createUserWithProfile(createUserWithProfileRequest);

        // Assert / Vérification
        assertNotNull(result);
        assertNotNull(result.getUser());
        assertNotNull(result.getProfile());
        assertEquals("test@example.com", result.getUser().getEmail());
        assertEquals("John", result.getProfile().getFirstName());
        assertEquals("Doe", result.getProfile().getLastName());
        
        verify(userRepository).findByEmail("test@example.com");
        verify(passwordEncoder).encode("password123");
        verify(userRepository).save(any(User.class));
        verify(userRepository).findById(1L);
        verify(userProfileService).createProfile(testUser, profileRequest);
    }

    @Test
    void testCreateUserWithProfileUserAlreadyExists() {
        // Arrange / Préparation
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(testUser));

        // Act & Assert / Action et Vérification
        assertThrows(RuntimeException.class, () -> userService.createUserWithProfile(createUserWithProfileRequest));
        verify(userRepository).findByEmail("test@example.com");
        verify(userRepository, never()).save(any(User.class));
        verify(userProfileService, never()).createProfile(any(), any());
    }

    @Test
    void testCreateUserWithProfileUserNotFoundAfterCreation() {
        // Arrange / Préparation
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("password123")).thenReturn("hashedPassword");
        when(userRepository.save(any(User.class))).thenReturn(testUser);
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert / Action et Vérification
        assertThrows(RuntimeException.class, () -> userService.createUserWithProfile(createUserWithProfileRequest));
        verify(userRepository).findByEmail("test@example.com");
        verify(passwordEncoder).encode("password123");
        verify(userRepository).save(any(User.class));
        verify(userRepository).findById(1L);
        verify(userProfileService, never()).createProfile(any(), any());
    }
} 