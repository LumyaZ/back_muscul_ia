package com.example.muscul_ia.service.impl;

import com.example.muscul_ia.dto.RegisterRequest;
import com.example.muscul_ia.dto.LoginRequest;
import com.example.muscul_ia.dto.UserDto;
import com.example.muscul_ia.entity.User;
import com.example.muscul_ia.repository.UserRepository;
import com.example.muscul_ia.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Implementation of UserService for user business logic.
 * Implémentation de UserService pour la logique métier utilisateur.
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDto register(RegisterRequest request) {
        // Vérifie la confirmation du mot de passe
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new RuntimeException("Passwords do not match");
        }
        // Vérifie si l'utilisateur existe déjà
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }
        // Hash le mot de passe
        String hashedPassword = passwordEncoder.encode(request.getPassword());
        // Crée l'utilisateur
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(hashedPassword);
        user.setCreationDate(java.time.LocalDateTime.now());
        // Sauvegarde en base
        userRepository.save(user);
        // Retourne le DTO
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setCreationDate(user.getCreationDate());
        return dto;
    }

    @Override
    public UserDto login(LoginRequest request) {
        // Recherche l'utilisateur par email
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Vérifie le mot de passe (hashé)
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        // Retourne le DTO utilisateur (sans le mot de passe)
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setCreationDate(user.getCreationDate());
        return dto;
    }
} 