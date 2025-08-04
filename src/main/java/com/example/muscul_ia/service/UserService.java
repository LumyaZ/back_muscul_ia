package com.example.muscul_ia.service;

import com.example.muscul_ia.dto.RegisterRequest;
import com.example.muscul_ia.dto.LoginRequest;
import com.example.muscul_ia.dto.UserDto;
import com.example.muscul_ia.dto.CreateUserWithProfileRequest;
import com.example.muscul_ia.dto.CreateUserWithProfileResponse;
import com.example.muscul_ia.entity.User;
import org.springframework.security.core.Authentication;

/**
 * User service for managing user business logic.
 * Service utilisateur pour gérer la logique métier utilisateur.
 */
public interface UserService {
    
    /**
     * Register a new user.
     * Inscrire un nouvel utilisateur.
     */
    UserDto register(RegisterRequest request);
    
    /**
     * Authenticate a user login.
     * Authentifier une connexion utilisateur.
     */
    UserDto login(LoginRequest request);
    
    /**
     * Get current authenticated user.
     * Récupérer l'utilisateur actuellement authentifié.
     */
    User getCurrentUser(Authentication authentication);
    
    /**
     * Create a new user with profile in a single operation.
     * Créer un nouvel utilisateur avec profil en une seule opération.
     */
    CreateUserWithProfileResponse createUserWithProfile(CreateUserWithProfileRequest request);
} 