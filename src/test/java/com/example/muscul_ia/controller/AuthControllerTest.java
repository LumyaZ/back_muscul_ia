package com.example.muscul_ia.controller;

import com.example.muscul_ia.dto.LoginRequest;
import com.example.muscul_ia.dto.RegisterRequest;
import com.example.muscul_ia.dto.CreateUserWithProfileRequest;
import com.example.muscul_ia.dto.CreateUserProfileRequest;
import com.example.muscul_ia.dto.UserDto;
import com.example.muscul_ia.dto.CreateUserWithProfileResponse;
import com.example.muscul_ia.dto.UserProfileDto;
import com.example.muscul_ia.service.UserService;
import com.example.muscul_ia.service.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("AuthController Tests")
class AuthControllerTest {

    private UserService userService;
    private JwtService jwtService;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    /**
     * Set up test environment before each test.
     * Configure l'environnement de test avant chaque test.
     */
    @BeforeEach
    void setUp() {
        userService = mock(UserService.class);
        jwtService = mock(JwtService.class);
        
        AuthController controller = new AuthController();
        // Utiliser la réflexion pour injecter les services
        try {
            java.lang.reflect.Field userServiceField = AuthController.class.getDeclaredField("userService");
            userServiceField.setAccessible(true);
            userServiceField.set(controller, userService);
            
            java.lang.reflect.Field jwtServiceField = AuthController.class.getDeclaredField("jwtService");
            jwtServiceField.setAccessible(true);
            jwtServiceField.set(controller, jwtService);
        } catch (Exception e) {
            throw new RuntimeException("Failed to inject dependencies", e);
        }
        
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    /**
     * Test successful user registration.
     * Teste l'inscription réussie d'un utilisateur.
     */
    @Test
    @DisplayName("Should register user successfully")
    void testRegisterSuccess() throws Exception {
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
        when(jwtService.generateToken(anyString())).thenReturn(token);

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
     * Teste la connexion réussie d'un utilisateur.
     */
    @Test
    @DisplayName("Should login user successfully")
    void testLoginSuccess() throws Exception {
        LoginRequest request = new LoginRequest();
        request.setEmail("test@example.com");
        request.setPassword("password123");

        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userDto.setEmail("test@example.com");
        userDto.setCreationDate(LocalDateTime.now());

        String token = "jwt.token.here";

        when(userService.login(any(LoginRequest.class))).thenReturn(userDto);
        when(jwtService.generateToken(anyString())).thenReturn(token);

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
     * Teste la création réussie d'un utilisateur avec profil.
     */
    @Test
    @DisplayName("Should create user with profile successfully")
    void testCreateUserWithProfileSuccess() throws Exception {
        RegisterRequest userData = new RegisterRequest();
        userData.setEmail("test@example.com");
        userData.setPassword("password123");
        userData.setConfirmPassword("password123");

        CreateUserProfileRequest profileData = new CreateUserProfileRequest();
        profileData.setFirstName("John");
        profileData.setLastName("Doe");
        profileData.setDateOfBirth(java.time.LocalDate.of(1990, 1, 1));
        profileData.setPhoneNumber("+33123456789");

        CreateUserWithProfileRequest request = new CreateUserWithProfileRequest(userData, profileData);

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
        when(jwtService.generateToken(anyString())).thenReturn(token);

        mockMvc.perform(post("/api/auth/create-user-with-profile")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.user.email").value("test@example.com"))
                .andExpect(jsonPath("$.profile.id").value(1))
                .andExpect(jsonPath("$.token").value(token));
    }

    /**
     * Test registration failure when passwords do not match.
     * Teste l'échec de l'inscription quand les mots de passe ne correspondent pas.
     */
    @Test
    @DisplayName("Should return bad request when passwords do not match")
    void testRegisterPasswordMismatch() throws Exception {
        RegisterRequest request = new RegisterRequest();
        request.setEmail("test@example.com");
        request.setPassword("password123");
        request.setConfirmPassword("differentpassword");

        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    /**
     * Test login failure.
     * Teste l'échec de la connexion.
     */
    @Test
    @DisplayName("Should return bad request when login fails")
    void testLoginFailure() throws Exception {
        LoginRequest request = new LoginRequest();
        request.setEmail("test@example.com");
        request.setPassword("wrongpassword");

        when(userService.login(any(LoginRequest.class))).thenThrow(new RuntimeException("Invalid credentials"));

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
} 