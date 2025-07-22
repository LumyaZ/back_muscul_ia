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
 * REST controller for UserProfile operations.
 * Contrôleur REST pour les opérations UserProfile.
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
        
        System.out.println("=== CREATE PROFILE (AUTHENTICATED) ===");
        System.out.println("Request received: " + request);
        System.out.println("Authentication: " + (authentication != null ? authentication.getName() : "null"));
        
        User user = userService.getCurrentUser(authentication);
        UserProfileDto createdProfile = userProfileService.createProfile(user, request);
        
        System.out.println("Profile created successfully: " + createdProfile);
        System.out.println("=====================================");
        
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
        
        System.out.println("=== CREATE PROFILE BY EMAIL (PUBLIC) ===");
        System.out.println("Request received: " + request);
        System.out.println("Email: " + request.getEmail());
        System.out.println("FirstName: " + request.getFirstName());
        System.out.println("LastName: " + request.getLastName());
        System.out.println("DateOfBirth: " + request.getDateOfBirth());
        System.out.println("PhoneNumber: " + request.getPhoneNumber());
        
        UserProfileDto createdProfile = userProfileService.createProfileByEmail(request);
        
        System.out.println("Profile created successfully: " + createdProfile);
        System.out.println("=========================================");
        
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProfile);
    }

    /**
     * Get the current user's profile.
     * Obtenir le profil de l'utilisateur actuel.
     */
    @GetMapping("/me")
    @Operation(summary = "Obtenir son propre profil", description = "Récupérer le profil de l'utilisateur authentifié")
    public ResponseEntity<UserProfileDto> getMyProfile(Authentication authentication) {
        
        System.out.println("=== GET MY PROFILE ===");
        System.out.println("Authentication: " + (authentication != null ? authentication.getName() : "null"));
        
        User user = userService.getCurrentUser(authentication);
        UserProfileDto profile = userProfileService.getProfileByUser(user);
        
        System.out.println("Profile retrieved: " + profile);
        System.out.println("=====================");
        
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
        
        System.out.println("=== GET PROFILE BY USER ID ===");
        System.out.println("UserId requested: " + userId);
        
        UserProfileDto profile = userProfileService.getProfileByUserId(userId);
        
        System.out.println("Profile retrieved: " + profile);
        System.out.println("=============================");
        
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
        
        System.out.println("=== UPDATE MY PROFILE ===");
        System.out.println("Request received: " + request);
        System.out.println("Authentication: " + (authentication != null ? authentication.getName() : "null"));
        
        User user = userService.getCurrentUser(authentication);
        UserProfileDto updatedProfile = userProfileService.updateProfile(user, request);
        
        System.out.println("Profile updated successfully: " + updatedProfile);
        System.out.println("===========================");
        
        return ResponseEntity.ok(updatedProfile);
    }

    /**
     * Delete the current user's profile.
     * Supprimer le profil de l'utilisateur actuel.
     */
    @DeleteMapping("/me")
    @Operation(summary = "Supprimer son propre profil", description = "Supprimer le profil de l'utilisateur authentifié")
    public ResponseEntity<Void> deleteMyProfile(Authentication authentication) {
        
        System.out.println("=== DELETE MY PROFILE ===");
        System.out.println("Authentication: " + (authentication != null ? authentication.getName() : "null"));
        
        User user = userService.getCurrentUser(authentication);
        userProfileService.deleteProfile(user);
        
        System.out.println("Profile deleted successfully");
        System.out.println("===========================");
        
        return ResponseEntity.noContent().build();
    }
} 