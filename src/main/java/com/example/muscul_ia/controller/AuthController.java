package com.example.muscul_ia.controller;

import com.example.muscul_ia.dto.LoginRequest;
import com.example.muscul_ia.dto.RegisterRequest;
import com.example.muscul_ia.dto.UserDto;
import com.example.muscul_ia.dto.CreateUserWithProfileRequest;
import com.example.muscul_ia.dto.CreateUserWithProfileResponse;
import com.example.muscul_ia.service.UserService;
import com.example.muscul_ia.service.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Authentication controller for login and registration endpoints.
 */
@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "Authentication endpoints for Muscul IA application")
@CrossOrigin(origins = "*")
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private JwtService jwtService;

    /**
     * Register a new user.
     * 
     * @param request - Registration data
     * @return ResponseEntity - Response with user and JWT token
     */
    @PostMapping("/register")
    @Operation(summary = "Register new user")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "User created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid data or email already exists")
    })
    public ResponseEntity<Map<String, Object>> register(@Valid @RequestBody RegisterRequest request) {
        logger.info("Registration attempt for: {}", request.getEmail());
        
        // Password validation
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            logger.error("Registration failed for {}: Passwords do not match", request.getEmail());
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Passwords do not match");
            errorResponse.put("message", "Registration failed");
            
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
        
        UserDto createdUser = userService.register(request);
        String token = jwtService.generateToken(createdUser.getEmail());
        
        logger.info("Registration successful for: {}", request.getEmail());
        
        Map<String, Object> response = new HashMap<>();
        response.put("user", createdUser);
        response.put("token", token);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Create a new user with profile in one request.
     * 
     * @param request - User and profile data
     * @return ResponseEntity - Response with user, profile and JWT token
     */
    @PostMapping("/create-user-with-profile")
    @Operation(summary = "Create user with profile")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "User and profile created successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid data"),
        @ApiResponse(responseCode = "409", description = "Email already exists")
    })
    public ResponseEntity<Map<String, Object>> createUserWithProfile(@Valid @RequestBody CreateUserWithProfileRequest request) {
        logger.info("User creation with profile attempt for: {}", request.getUserData().getEmail());
        
        CreateUserWithProfileResponse response = userService.createUserWithProfile(request);
        String token = jwtService.generateToken(response.getUser().getEmail());
        
        logger.info("User creation with profile successful for: {}", request.getUserData().getEmail());
        
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("user", response.getUser());
        responseMap.put("profile", response.getProfile());
        responseMap.put("token", token);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(responseMap);
    }

    /**
     * Login an existing user.
     * 
     * @param request - Login credentials
     * @return ResponseEntity - Response with user and JWT token
     */
    @PostMapping("/login")
    @Operation(summary = "User login")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Login successful"),
        @ApiResponse(responseCode = "401", description = "Invalid credentials")
    })
    public ResponseEntity<Map<String, Object>> login(@Valid @RequestBody LoginRequest request) {
        logger.info("Login attempt for: {}", request.getEmail());
        
        try {
            UserDto loggedInUser = userService.login(request);
            String token = jwtService.generateToken(loggedInUser.getEmail());
            
            logger.info("Login successful for: {}", request.getEmail());
            
            Map<String, Object> response = new HashMap<>();
            response.put("user", loggedInUser);
            response.put("token", token);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Login failed for {}: {}", request.getEmail(), e.getMessage());
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            errorResponse.put("message", "Login failed");
            
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }
} 