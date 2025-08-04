package com.example.muscul_ia.controller;

import com.example.muscul_ia.dto.CreateUserProfileRequest;
import com.example.muscul_ia.dto.CreateUserProfileWithEmailRequest;
import com.example.muscul_ia.dto.UpdateUserProfileRequest;
import com.example.muscul_ia.dto.UserProfileDto;
import com.example.muscul_ia.entity.User;
import com.example.muscul_ia.service.UserProfileService;
import com.example.muscul_ia.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

/**
 * User profile controller for managing user profiles.
 * Contrôleur de profils utilisateur pour gérer les profils utilisateur.
 */
@RestController
@RequestMapping("/api/profiles")
@Tag(name = "UserProfile", description = "API pour la gestion des profils utilisateur")
@CrossOrigin(origins = "*")
public class UserProfileController {

    @Autowired
    private UserProfileService userProfileService;

    @Autowired
    private UserService userService;

    /**
     * Create a new user profile (requires authentication).
     * Créer un nouveau profil utilisateur (nécessite une authentification).
     */
    @PostMapping
    @Operation(summary = "Créer un profil utilisateur", description = "Créer un nouveau profil utilisateur pour l'utilisateur authentifié")
    public ResponseEntity<UserProfileDto> createProfile(
            @Parameter(description = "Données du profil à créer") @Valid @RequestBody CreateUserProfileRequest request,
            Authentication authentication) {
        
        User user = userService.getCurrentUser(authentication);
        UserProfileDto createdProfile = userProfileService.createProfile(user, request);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProfile);
    }

    /**
     * Create a new user profile by email (public endpoint).
     * Créer un nouveau profil utilisateur par email (endpoint public).
     */
    @PostMapping("/public")
    @Operation(summary = "Créer un profil utilisateur par email", description = "Créer un nouveau profil utilisateur en utilisant l'email (endpoint public)")
    public ResponseEntity<UserProfileDto> createProfileByEmail(
            @Parameter(description = "Données du profil à créer avec email") @Valid @RequestBody CreateUserProfileWithEmailRequest request) {
        
        UserProfileDto createdProfile = userProfileService.createProfileByEmail(request);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProfile);
    }

    /**
     * Get the current user's profile.
     * Obtenir le profil de l'utilisateur actuel.
     */
    @GetMapping("/me")
    @Operation(summary = "Obtenir son propre profil", description = "Récupérer le profil de l'utilisateur authentifié")
    public ResponseEntity<UserProfileDto> getMyProfile(Authentication authentication) {
        
        User user = userService.getCurrentUser(authentication);
        UserProfileDto profile = userProfileService.getProfileByUser(user);
        
        return ResponseEntity.ok(profile);
    }

    /**
     * Get a user profile by user ID.
     * Obtenir un profil utilisateur par ID utilisateur.
     */
    @GetMapping("/{userId}")
    @Operation(summary = "Obtenir un profil par ID utilisateur", description = "Récupérer le profil d'un utilisateur par son ID")
    public ResponseEntity<UserProfileDto> getProfileByUserId(
            @Parameter(description = "ID de l'utilisateur") @PathVariable Long userId) {
        
        UserProfileDto profile = userProfileService.getProfileByUserId(userId);
        
        return ResponseEntity.ok(profile);
    }

    /**
     * Update the current user's profile.
     * Mettre à jour le profil de l'utilisateur actuel.
     */
    @PutMapping("/me")
    @Operation(summary = "Mettre à jour son propre profil", description = "Modifier le profil de l'utilisateur authentifié")
    public ResponseEntity<UserProfileDto> updateMyProfile(
            @Parameter(description = "Données du profil à mettre à jour") @Valid @RequestBody UpdateUserProfileRequest request,
            Authentication authentication) {
        
        User user = userService.getCurrentUser(authentication);
        UserProfileDto updatedProfile = userProfileService.updateProfile(user, request);
        
        return ResponseEntity.ok(updatedProfile);
    }

    /**
     * Delete the current user's profile.
     * Supprimer le profil de l'utilisateur actuel.
     */
    @DeleteMapping("/me")
    @Operation(summary = "Supprimer son propre profil", description = "Supprimer le profil de l'utilisateur authentifié")
    public ResponseEntity<Void> deleteMyProfile(Authentication authentication) {
        
        User user = userService.getCurrentUser(authentication);
        userProfileService.deleteProfile(user);
        
        return ResponseEntity.noContent().build();
    }
} 