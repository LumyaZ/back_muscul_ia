package com.example.muscul_ia.controller;

import com.example.muscul_ia.dto.LoginRequest;
import com.example.muscul_ia.dto.RegisterRequest;
import com.example.muscul_ia.dto.UserDto;
import com.example.muscul_ia.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Authentication controller for login and registration endpoints.
 * Contrôleur d'authentification pour les endpoints de connexion et d'inscription.
 */
@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "Endpoints d'authentification pour l'application Muscul IA")
public class AuthController {
    @Autowired
    private UserService userService;

    /**
     * Register a new user.
     * Inscrire un nouvel utilisateur.
     */
    @PostMapping("/register")
    @Operation(
        summary = "Inscription utilisateur",
        description = "Crée un nouveau compte utilisateur avec email et mot de passe"
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
    public ResponseEntity<UserDto> register(
        @RequestBody @Schema(description = "Données d'inscription") RegisterRequest request
    ) {
        try {
            UserDto user = userService.register(request);
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Login an existing user.
     * Connecter un utilisateur existant.
     */
    @PostMapping("/login")
    @Operation(
        summary = "Connexion utilisateur",
        description = "Authentifie un utilisateur avec email et mot de passe"
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
    public ResponseEntity<UserDto> login(
        @RequestBody @Schema(description = "Données de connexion") LoginRequest request
    ) {
        try {
            UserDto user = userService.login(request);
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).build();
        }
    }
} 