package com.example.muscul_ia.service;

import com.example.muscul_ia.dto.CreateUserProfileRequest;
import com.example.muscul_ia.dto.UpdateUserProfileRequest;
import com.example.muscul_ia.dto.UserProfileDto;
import com.example.muscul_ia.entity.User;

import java.util.Optional;

/**
 * Service interface for UserProfile operations.
 * Interface de service pour les opérations UserProfile.
 */
public interface UserProfileService {

    /**
     * Create a new user profile.
     * Crée un nouveau profil utilisateur.
     */
    UserProfileDto createProfile(User user, CreateUserProfileRequest request);

    /**
     * Get user profile by user.
     * Obtient le profil utilisateur par utilisateur.
     */
    Optional<UserProfileDto> getProfileByUser(User user);

    /**
     * Get user profile by user ID.
     * Obtient le profil utilisateur par ID utilisateur.
     */
    Optional<UserProfileDto> getProfileByUserId(Long userId);

    /**
     * Update user profile.
     * Met à jour le profil utilisateur.
     */
    UserProfileDto updateProfile(User user, UpdateUserProfileRequest request);

    /**
     * Delete user profile.
     * Supprime le profil utilisateur.
     */
    void deleteProfile(User user);
} 