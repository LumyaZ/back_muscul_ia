package com.example.muscul_ia.controller;

import com.example.muscul_ia.dto.LoginRequest;
import com.example.muscul_ia.dto.RegisterRequest;
import com.example.muscul_ia.dto.UserDto;
import com.example.muscul_ia.dto.CreateUserWithProfileRequest;
import com.example.muscul_ia.dto.CreateUserWithProfileResponse;
import com.example.muscul_ia.service.UserService;
import com.example.muscul_ia.service.JwtService;
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

import java.util.HashMap;
import java.util.Map;

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
    
    @Autowired
    private JwtService jwtService;

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
            responseCode = "201",
            description = "Inscription réussie",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = Map.class),
                examples = @ExampleObject(
                    name = "Succès",
                    value = "{\"user\": {\"id\": 1, \"email\": \"user@example.com\", \"creationDate\": \"2024-01-01T10:00:00\"}, \"token\": \"jwt_token_here\"}"
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
    /**
     * Register a new user.
     * Inscrire un nouvel utilisateur.
     * 
     * @param request - Données d'inscription
     * @return ResponseEntity - Réponse avec utilisateur et token JWT
     */
    public ResponseEntity<Map<String, Object>> register(@Valid @RequestBody RegisterRequest request) {
        try {
            UserDto createdUser = userService.register(request);
            String token = jwtService.generateToken(createdUser.getEmail());
            
            Map<String, Object> response = new HashMap<>();
            response.put("user", createdUser);
            response.put("token", token);
            
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
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
    /**
     * Create a new user with profile in one request.
     * Créer un nouvel utilisateur avec profil en une seule requête.
     * 
     * @param request - Données utilisateur et profil
     * @return ResponseEntity - Réponse avec utilisateur, profil et token JWT
     */
    public ResponseEntity<Map<String, Object>> createUserWithProfile(@Valid @RequestBody CreateUserWithProfileRequest request) {
        try {
            CreateUserWithProfileResponse response = userService.createUserWithProfile(request);
            String token = jwtService.generateToken(response.getUser().getEmail());
            
            Map<String, Object> responseMap = new HashMap<>();
            responseMap.put("user", response.getUser());
            responseMap.put("profile", response.getProfile());
            responseMap.put("token", token);
            
            return ResponseEntity.status(HttpStatus.CREATED).body(responseMap);
        } catch (RuntimeException e) {
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
                schema = @Schema(implementation = Map.class),
                examples = @ExampleObject(
                    name = "Succès",
                    value = "{\"user\": {\"id\": 1, \"email\": \"user@example.com\"}, \"token\": \"jwt_token_here\"}"
                )
            )
        ),
        @ApiResponse(
            responseCode = "401",
            description = "Identifiants invalides"
        )
    })
    /**
     * Login an existing user.
     * Connecter un utilisateur existant.
     * 
     * @param request - Données de connexion
     * @return ResponseEntity - Réponse avec utilisateur et token JWT
     */
    public ResponseEntity<Map<String, Object>> login(@Valid @RequestBody LoginRequest request) {
        try {
            UserDto loggedInUser = userService.login(request);
            String token = jwtService.generateToken(loggedInUser.getEmail());
            
            Map<String, Object> response = new HashMap<>();
            response.put("user", loggedInUser);
            response.put("token", token);
            
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            throw e;
        }
    }
} 