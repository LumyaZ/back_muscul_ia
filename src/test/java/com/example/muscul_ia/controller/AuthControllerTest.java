package com.example.muscul_ia.controller;

import com.example.muscul_ia.dto.LoginRequest;
import com.example.muscul_ia.dto.RegisterRequest;
import com.example.muscul_ia.dto.CreateUserWithProfileRequest;
import com.example.muscul_ia.dto.UserDto;
import com.example.muscul_ia.dto.CreateUserWithProfileResponse;
import com.example.muscul_ia.dto.UserProfileDto;
import com.example.muscul_ia.service.UserService;
import com.example.muscul_ia.service.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Tests for authentication controller.
 * Tests pour le contrôleur d'authentification.
 */
@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthController authController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
        objectMapper = new ObjectMapper();
    }

    /**
     * Test successful user registration.
     * Test d'inscription réussie d'un utilisateur.
     */
    @Test
    void testRegisterSuccess() throws Exception {
        // Given
        RegisterRequest request = new RegisterRequest();
        request.setEmail("test@example.com");
        request.setPassword("password123");
        request.setConfirmPassword("password123");

        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userDto.setEmail("test@example.com");
        userDto.setCreationDate(LocalDateTime.now());

        String token = "jwt.token.here";

        when(userService.register(any(RegisterRequest.class))).thenReturn(userDto);
        when(jwtService.generateToken(any(String.class))).thenReturn(token);

        // When & Then
        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.user.id").value(1))
                .andExpect(jsonPath("$.user.email").value("test@example.com"))
                .andExpect(jsonPath("$.token").value(token));
    }

    /**
     * Test successful user login.
     * Test de connexion réussie d'un utilisateur.
     */
    @Test
    void testLoginSuccess() throws Exception {
        // Given
        LoginRequest request = new LoginRequest();
        request.setEmail("test@example.com");
        request.setPassword("password123");

        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userDto.setEmail("test@example.com");
        userDto.setCreationDate(LocalDateTime.now());

        String token = "jwt.token.here";

        when(userService.login(any(LoginRequest.class))).thenReturn(userDto);
        when(jwtService.generateToken(any(String.class))).thenReturn(token);

        // When & Then
        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.user.id").value(1))
                .andExpect(jsonPath("$.user.email").value("test@example.com"))
                .andExpect(jsonPath("$.token").value(token));
    }

    /**
     * Test successful user creation with profile.
     * Test de création réussie d'un utilisateur avec profil.
     */
    @Test
    void testCreateUserWithProfileSuccess() throws Exception {
        // Given
        CreateUserWithProfileRequest request = new CreateUserWithProfileRequest();
        request.setUserData(new RegisterRequest());
        request.getUserData().setEmail("test@example.com");
        request.getUserData().setPassword("password123");
        request.getUserData().setConfirmPassword("password123");
        
        com.example.muscul_ia.dto.CreateUserProfileRequest profileRequest = new com.example.muscul_ia.dto.CreateUserProfileRequest();
        profileRequest.setFirstName("John");
        profileRequest.setLastName("Doe");
        profileRequest.setDateOfBirth(java.time.LocalDate.of(1990, 1, 1));
        request.setProfileData(profileRequest);

        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userDto.setEmail("test@example.com");
        userDto.setCreationDate(LocalDateTime.now());

        UserProfileDto profileDto = new UserProfileDto();
        profileDto.setId(1L);
        profileDto.setUserId(1L);

        CreateUserWithProfileResponse response = new CreateUserWithProfileResponse(userDto, profileDto);
        String token = "jwt.token.here";

        when(userService.createUserWithProfile(any(CreateUserWithProfileRequest.class))).thenReturn(response);
        when(jwtService.generateToken(any(String.class))).thenReturn(token);

        // When & Then
        mockMvc.perform(post("/api/auth/create-user-with-profile")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.user.id").value(1))
                .andExpect(jsonPath("$.user.email").value("test@example.com"))
                .andExpect(jsonPath("$.profile.id").value(1))
                .andExpect(jsonPath("$.token").value(token));
    }

    /**
     * Test registration with password mismatch.
     * Test d'inscription avec mots de passe différents.
     */
    @Test
    void testRegisterPasswordMismatch() throws Exception {
        // Given
        RegisterRequest request = new RegisterRequest();
        request.setEmail("test@example.com");
        request.setPassword("password123");
        request.setConfirmPassword("differentpassword");

        when(userService.register(any(RegisterRequest.class)))
                .thenThrow(new RuntimeException("Passwords do not match"));

        // When & Then
        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isInternalServerError());
    }

    /**
     * Test login with invalid credentials.
     * Test de connexion avec identifiants invalides.
     */
    @Test
    void testLoginInvalidCredentials() throws Exception {
        // Given
        LoginRequest request = new LoginRequest();
        request.setEmail("test@example.com");
        request.setPassword("wrongpassword");

        when(userService.login(any(LoginRequest.class)))
                .thenThrow(new RuntimeException("Invalid email or password"));

        // When & Then
        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isInternalServerError());
    }
} 