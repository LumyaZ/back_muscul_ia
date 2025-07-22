package com.example.muscul_ia.controller;

import com.example.muscul_ia.dto.CreateUserProfileRequest;
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
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * REST controller for UserProfile operations.
 * Contrôleur REST pour les opérations UserProfile.
 */
@RestController
@RequestMapping("/api/profiles")
@Tag(name = "User Profile", description = "API pour la gestion des profils utilisateur")
@CrossOrigin(origins = "http://localhost:4200")
public class UserProfileController {

    @Autowired
    private UserProfileService userProfileService;

    @Autowired
    private UserService userService;

    /**
     * Create a new user profile.
     * Crée un nouveau profil utilisateur.
     */
    @PostMapping
    @Operation(summary = "Créer un profil utilisateur")
    public ResponseEntity<UserProfileDto> createProfile(
            @Valid @RequestBody CreateUserProfileRequest request) {
        User currentUser = getCurrentUser();
        UserProfileDto createdProfile = userProfileService.createProfile(currentUser, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProfile);
    }

    /**
     * Get current user's profile.
     * Obtient le profil de l'utilisateur actuel.
     */
    @GetMapping("/me")
    @Operation(summary = "Obtenir mon profil")
    public ResponseEntity<UserProfileDto> getMyProfile() {
        User currentUser = getCurrentUser();
        Optional<UserProfileDto> profile = userProfileService.getProfileByUser(currentUser);
        return profile.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Get user profile by ID.
     * Obtient le profil utilisateur par ID.
     */
    @GetMapping("/{userId}")
    @Operation(summary = "Obtenir un profil utilisateur par ID")
    public ResponseEntity<UserProfileDto> getProfileById(
            @Parameter(description = "ID de l'utilisateur") @PathVariable Long userId) {
        Optional<UserProfileDto> profile = userProfileService.getProfileByUserId(userId);
        return profile.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Update current user's profile.
     * Met à jour le profil de l'utilisateur actuel.
     */
    @PutMapping("/me")
    @Operation(summary = "Mettre à jour mon profil")
    public ResponseEntity<UserProfileDto> updateMyProfile(
            @Valid @RequestBody UpdateUserProfileRequest request) {
        User currentUser = getCurrentUser();
        UserProfileDto updatedProfile = userProfileService.updateProfile(currentUser, request);
        return ResponseEntity.ok(updatedProfile);
    }

    /**
     * Delete current user's profile.
     * Supprime le profil de l'utilisateur actuel.
     */
    @DeleteMapping("/me")
    @Operation(summary = "Supprimer mon profil")
    public ResponseEntity<Void> deleteMyProfile() {
        User currentUser = getCurrentUser();
        userProfileService.deleteProfile(currentUser);
        return ResponseEntity.noContent().build();
    }

    /**
     * Get current authenticated user.
     * Obtient l'utilisateur authentifié actuel.
     */
    private User getCurrentUser() {
        // TODO: Get from SecurityContext in real implementation
        // For now, return a mock user for testing
        User mockUser = new User();
        mockUser.setId(1L);
        mockUser.setEmail("test@example.com");
        mockUser.setCreationDate(java.time.LocalDateTime.now());
        return mockUser;
    }
} 