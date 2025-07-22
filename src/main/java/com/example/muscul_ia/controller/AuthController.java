package com.example.muscul_ia.controller;

import com.example.muscul_ia.dto.LoginRequest;
import com.example.muscul_ia.dto.RegisterRequest;
import com.example.muscul_ia.dto.UserDto;
import com.example.muscul_ia.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Authentication controller for login and registration endpoints.
 * Contrôleur d'authentification pour les endpoints de connexion et d'inscription.
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    /**
     * Register a new user.
     * Inscrire un nouvel utilisateur.
     */
    @PostMapping("/register")
    public UserDto register(@RequestBody RegisterRequest request) {
        return userService.register(request);
    }

    /**
     * Login an existing user.
     * Connecter un utilisateur existant.
     */
    @PostMapping("/login")
    public UserDto login(@RequestBody LoginRequest request) {
        return userService.login(request);
    }
} 