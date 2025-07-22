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
        request.setEmail("testuser@email.com");
        request.setPassword("password");
        request.setConfirmPassword("password");

        // Act / Action
        userService.register(request);

        // Assert / Vérification
        Mockito.verify(userRepository).save(Mockito.argThat(user ->
                user.getEmail().equals("testuser@email.com") &&
                user.getPassword().equals("hashedPassword") &&
                user.getCreationDate() != null
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
        user.setEmail("testuser@email.com");
        user.setPassword("hashedPassword");
        user.setCreationDate(java.time.LocalDateTime.now());
        Mockito.when(userRepository.findByEmail("testuser@email.com")).thenReturn(java.util.Optional.of(user));
        Mockito.when(passwordEncoder.matches("password", "hashedPassword")).thenReturn(true);

        com.example.muscul_ia.dto.LoginRequest request = new com.example.muscul_ia.dto.LoginRequest();
        request.setEmail("testuser@email.com");
        request.setPassword("password");

        // Act / Action
        assertDoesNotThrow(() -> userService.login(request));
    }

    @Test
    void testRegisterWithExistingEmailThrowsException() {
        // Arrange / Préparation
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        PasswordEncoder passwordEncoder = Mockito.mock(PasswordEncoder.class);
        UserServiceImpl userService = new UserServiceImpl();
        userService.userRepository = userRepository;
        userService.passwordEncoder = passwordEncoder;

        RegisterRequest request = new RegisterRequest();
        request.setEmail("existing@email.com");
        request.setPassword("password");
        request.setConfirmPassword("password");

        Mockito.when(userRepository.findByEmail("existing@email.com")).thenReturn(java.util.Optional.of(new User()));

        // Act & Assert / Action & Vérification
        assertThrows(RuntimeException.class, () -> userService.register(request));
    }

    @Test
    void testRegisterWithNonMatchingPasswordsThrowsException() {
        // Arrange / Préparation
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        PasswordEncoder passwordEncoder = Mockito.mock(PasswordEncoder.class);
        UserServiceImpl userService = new UserServiceImpl();
        userService.userRepository = userRepository;
        userService.passwordEncoder = passwordEncoder;

        RegisterRequest request = new RegisterRequest();
        request.setEmail("testuser@email.com");
        request.setPassword("password1");
        request.setConfirmPassword("password2");

        // Act & Assert / Action & Vérification
        assertThrows(RuntimeException.class, () -> userService.register(request));
    }
} 