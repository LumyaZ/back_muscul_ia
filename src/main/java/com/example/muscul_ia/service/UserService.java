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
 */
public interface UserService {
    UserDto register(RegisterRequest request);
    UserDto login(LoginRequest request);
    User getCurrentUser(Authentication authentication);
    CreateUserWithProfileResponse createUserWithProfile(CreateUserWithProfileRequest request);
} 