package com.example.muscul_ia.controller;

import com.example.muscul_ia.dto.LoginRequest;
import com.example.muscul_ia.dto.RegisterRequest;
import com.example.muscul_ia.dto.UserDto;
import com.example.muscul_ia.dto.CreateUserWithProfileRequest;
import com.example.muscul_ia.dto.CreateUserWithProfileResponse;
import com.example.muscul_ia.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Authentication controller for login and registration endpoints.
 * Contrôleur d'authentification pour les endpoints de connexion et d'inscription.
 */
@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "Endpoints d'authentification pour l'application Muscul IA")
@CrossOrigin(origins = "*")
public class AuthController {
    @Autowired
    private UserService userService;

    /**
     * Register a new user.
     * Inscrire un nouvel utilisateur.
     */
    @PostMapping("/register")
    @Operation(
        summary = "Inscription",
        description = "Créer un nouveau compte utilisateur"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Inscription réussie",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = UserDto.class),
                examples = @ExampleObject(
                    name = "Succès",
                    value = "{\"id\": 1, \"email\": \"user@example.com\", \"creationDate\": \"2024-01-01T10:00:00\"}"
                )
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Données invalides ou email déjà existant",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    name = "Erreur",
                    value = "{\"error\": \"Email already exists\"}"
                )
            )
        )
    })
    public ResponseEntity<UserDto> register(@Valid @RequestBody RegisterRequest request) {
        System.out.println("=== AUTH: REGISTER ===");
        System.out.println("Request received: " + request);
        System.out.println("Email: " + request.getEmail());
        System.out.println("Password: [HIDDEN]");
        System.out.println("ConfirmPassword: [HIDDEN]");
        
        try {
            UserDto createdUser = userService.register(request);
            System.out.println("User created successfully: " + createdUser.getId() + " - " + createdUser.getEmail());
            System.out.println("=========================");
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        } catch (RuntimeException e) {
            System.out.println("ERROR during registration: " + e.getMessage());
            System.out.println("=========================");
            throw e;
        }
    }

    /**
     * Create a new user with profile in one request.
     * Créer un nouvel utilisateur avec profil en une seule requête.
     */
    @PostMapping("/create-user-with-profile")
    @Operation(
        summary = "Création utilisateur avec profil",
        description = "Créer un utilisateur et son profil en une seule opération"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201",
            description = "Utilisateur et profil créés avec succès",
            content = @Content(
                schema = @Schema(implementation = CreateUserWithProfileResponse.class)
            )
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Données invalides"
        ),
        @ApiResponse(
            responseCode = "409",
            description = "Email déjà utilisé"
        )
    })
    public ResponseEntity<CreateUserWithProfileResponse> createUserWithProfile(@Valid @RequestBody CreateUserWithProfileRequest request) {
        System.out.println("=== AUTH: CREATE USER WITH PROFILE ===");
        System.out.println("Request received: " + request);
        System.out.println("User data: " + request.getUserData());
        System.out.println("Profile data: " + request.getProfileData());
        
        try {
            CreateUserWithProfileResponse response = userService.createUserWithProfile(request);
            System.out.println("User and profile created successfully");
            System.out.println("User: " + response.getUser().getId() + " - " + response.getUser().getEmail());
            System.out.println("Profile: " + response.getProfile().getId() + " for user " + response.getProfile().getUserId());
            System.out.println("=========================");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            System.out.println("ERROR during user and profile creation: " + e.getMessage());
            System.out.println("=========================");
            throw e;
        }
    }

    /**
     * Login an existing user.
     * Connecter un utilisateur existant.
     */
    @PostMapping("/login")
    @Operation(
        summary = "Connexion",
        description = "Se connecter avec email et mot de passe"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Connexion réussie",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = UserDto.class),
                examples = @ExampleObject(
                    name = "Succès",
                    value = "{\"id\": 1, \"email\": \"user@example.com\", \"creationDate\": \"2024-01-01T10:00:00\"}"
                )
            )
        ),
        @ApiResponse(
            responseCode = "401",
            description = "Identifiants invalides",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    name = "Erreur",
                    value = "{\"error\": \"Invalid credentials\"}"
                )
            )
        )
    })
    public ResponseEntity<UserDto> login(@Valid @RequestBody LoginRequest request) {
        System.out.println("=== AUTH: LOGIN ===");
        System.out.println("Request received: " + request);
        System.out.println("Email: " + request.getEmail());
        System.out.println("Password: [HIDDEN]");
        
        try {
            UserDto loggedInUser = userService.login(request);
            System.out.println("User logged in successfully: " + loggedInUser.getId() + " - " + loggedInUser.getEmail());
            System.out.println("===================");
            return ResponseEntity.ok(loggedInUser);
        } catch (RuntimeException e) {
            System.out.println("ERROR during login: " + e.getMessage());
            System.out.println("===================");
            throw e;
        }
    }
} 