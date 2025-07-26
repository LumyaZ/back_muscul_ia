package com.example.muscul_ia.service;

import com.example.muscul_ia.dto.RegisterRequest;
import com.example.muscul_ia.dto.LoginRequest;
import com.example.muscul_ia.dto.UserDto;
import com.example.muscul_ia.dto.CreateUserWithProfileRequest;
import com.example.muscul_ia.dto.CreateUserWithProfileResponse;
import com.example.muscul_ia.entity.User;
import org.springframework.security.core.Authentication;

/**
 * Service interface for user business logic.
 * Interface de service pour la logique métier utilisateur.
 * 
 * This service provides methods to manage user authentication and registration,
 * including user creation, login validation, and profile management operations.
 * It handles the core user lifecycle from registration to authentication.
 * 
 * Ce service fournit des méthodes pour gérer l'authentification et l'inscription
 * des utilisateurs, incluant la création d'utilisateur, la validation de connexion,
 * et les opérations de gestion de profil. Il gère le cycle de vie principal de
 * l'utilisateur de l'inscription à l'authentification.
 * 
 * @author Muscul IA Team
 * @version 1.0
 * @since 2024-01-01
 */
public interface UserService {
    
    /**
     * Register a new user.
     * Inscrire un nouvel utilisateur.
     * 
     * @param request - Registration request containing user credentials
     * @return UserDto - Created user information
     * @throws RuntimeException if email already exists or invalid data
     */
    UserDto register(RegisterRequest request);
    
    /**
     * Authenticate a user login.
     * Authentifier une connexion utilisateur.
     * 
     * @param request - Login request containing credentials
     * @return UserDto - Authenticated user information
     * @throws RuntimeException if invalid credentials or user not found
     */
    UserDto login(LoginRequest request);
    
    /**
     * Get current authenticated user.
     * Récupérer l'utilisateur actuellement authentifié.
     * 
     * @param authentication - Spring Security authentication object
     * @return User - Current authenticated user entity
     * @throws RuntimeException if no authenticated user found
     */
    User getCurrentUser(Authentication authentication);
    
    /**
     * Create a new user with profile in a single operation.
     * Créer un nouvel utilisateur avec profil en une seule opération.
     * 
     * @param request - Request containing user and profile data
     * @return CreateUserWithProfileResponse - Created user and profile information
     * @throws RuntimeException if email already exists or invalid data
     */
    CreateUserWithProfileResponse createUserWithProfile(CreateUserWithProfileRequest request);
} 