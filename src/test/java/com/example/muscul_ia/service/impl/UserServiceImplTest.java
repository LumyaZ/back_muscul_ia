package com.example.muscul_ia.service.impl;

import com.example.muscul_ia.dto.RegisterRequest;
import com.example.muscul_ia.entity.User;
import com.example.muscul_ia.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

/**
 * Unit test for UserServiceImpl registration logic.
 * Test unitaire pour la logique d'inscription de UserServiceImpl.
 */
class UserServiceImplTest {

    @Test
    void testRegisterCreatesUserWithHashedPassword() {
        // Arrange / Préparation
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        PasswordEncoder passwordEncoder = Mockito.mock(PasswordEncoder.class);
        Mockito.when(passwordEncoder.encode("password")).thenReturn("hashedPassword");
        Mockito.when(userRepository.save(any(User.class))).thenAnswer(i -> i.getArguments()[0]);

        UserServiceImpl userService = new UserServiceImpl();
        userService.userRepository = userRepository;
        userService.passwordEncoder = passwordEncoder;

        RegisterRequest request = new RegisterRequest();
        request.setUsername("testuser");
        request.setPassword("password");
        request.setRole("USER");

        // Act / Action
        userService.register(request);

        // Assert / Vérification
        Mockito.verify(userRepository).save(Mockito.argThat(user ->
                user.getUsername().equals("testuser") &&
                user.getPassword().equals("hashedPassword") &&
                user.getRole().equals("USER")
        ));
    }

    @Test
    void testLoginWithCorrectCredentials() {
        // Arrange / Préparation
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        PasswordEncoder passwordEncoder = Mockito.mock(PasswordEncoder.class);
        UserServiceImpl userService = new UserServiceImpl();
        userService.userRepository = userRepository;
        userService.passwordEncoder = passwordEncoder;

        User user = new User();
        user.setUsername("testuser");
        user.setPassword("hashedPassword");
        user.setRole("USER");
        Mockito.when(userRepository.findByUsername("testuser")).thenReturn(java.util.Optional.of(user));
        Mockito.when(passwordEncoder.matches("password", "hashedPassword")).thenReturn(true);

        com.example.muscul_ia.dto.LoginRequest request = new com.example.muscul_ia.dto.LoginRequest();
        request.setUsername("testuser");
        request.setPassword("password");

        // Act / Action
        assertDoesNotThrow(() -> userService.login(request));
    }

    @Test
    void testRegisterWithExistingUsernameThrowsException() {
        // Arrange / Préparation
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        PasswordEncoder passwordEncoder = Mockito.mock(PasswordEncoder.class);
        UserServiceImpl userService = new UserServiceImpl();
        userService.userRepository = userRepository;
        userService.passwordEncoder = passwordEncoder;

        RegisterRequest request = new RegisterRequest();
        request.setUsername("existinguser");
        request.setPassword("password");
        request.setRole("USER");

        Mockito.when(userRepository.findByUsername("existinguser")).thenReturn(java.util.Optional.of(new User()));

        // Act & Assert / Action & Vérification
        assertThrows(RuntimeException.class, () -> userService.register(request));
    }
} 