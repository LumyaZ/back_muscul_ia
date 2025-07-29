package com.example.muscul_ia.controller;

import com.example.muscul_ia.config.TestSecurityConfig;
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
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Import(TestSecurityConfig.class)
@ActiveProfiles("test")
@DisplayName("AuthController Tests")
class AuthControllerTest {

    @MockBean
    private UserService userService;

    @MockBean
    private JwtService jwtService;

    @Autowired
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    @DisplayName("Should register user successfully")
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
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.user.id").value(1))
                .andExpect(jsonPath("$.user.email").value("test@example.com"))
                .andExpect(jsonPath("$.token").value(token));
    }

    @Test
    @DisplayName("Should login user successfully")
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

    @Test
    @DisplayName("Should create user with profile successfully")
    void testCreateUserWithProfileSuccess() throws Exception {
        // Given
        CreateUserWithProfileRequest request = new CreateUserWithProfileRequest();
        request.setUserData(new RegisterRequest());
        request.getUserData().setEmail("test@example.com");
        request.getUserData().setPassword("password123");
        request.getUserData().setConfirmPassword("password123");

        var profileRequest = new com.example.muscul_ia.dto.CreateUserProfileRequest();
        profileRequest.setFirstName("John");
        profileRequest.setLastName("Doe");
        profileRequest.setDateOfBirth(java.time.LocalDate.of(1990, 1, 1));
        profileRequest.setPhoneNumber("+33123456789");
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
        mockMvc.perform(post("/api/auth/register-with-profile")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.user.email").value("test@example.com"))
                .andExpect(jsonPath("$.profile.id").value(1))
                .andExpect(jsonPath("$.token").value(token));
    }

    @Test
    @DisplayName("Should return bad request when passwords do not match")
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
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Should return bad request when login fails")
    void testLoginFailure() throws Exception {
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
                .andExpect(status().isBadRequest());
    }
} 