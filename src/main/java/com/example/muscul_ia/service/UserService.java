package com.example.muscul_ia.service;

import com.example.muscul_ia.dto.RegisterRequest;
import com.example.muscul_ia.dto.LoginRequest;
import com.example.muscul_ia.dto.UserDto;

/**
 * Service interface for user business logic.
 * Interface de service pour la logique métier utilisateur.
 */
public interface UserService {
    UserDto register(RegisterRequest request);
    UserDto login(LoginRequest request);
} 