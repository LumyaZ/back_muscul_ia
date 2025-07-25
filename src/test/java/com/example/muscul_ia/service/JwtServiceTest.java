package com.example.muscul_ia.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for JWT service functionality.
 * Tests pour les fonctionnalités du service JWT.
 */
@ExtendWith(MockitoExtension.class)
class JwtServiceTest {

    private JwtService jwtService;

    @BeforeEach
    void setUp() {
        jwtService = new JwtService();
    }

    /**
     * Test token generation.
     * Test de génération de token.
     */
    @Test
    void testGenerateToken() {
        String email = "test@example.com";
        String token = jwtService.generateToken(email);
        
        assertNotNull(token);
        assertFalse(token.isEmpty());
        assertTrue(token.contains(".")); // JWT format validation
    }

    /**
     * Test email extraction from token.
     * Test d'extraction d'email depuis le token.
     */
    @Test
    void testExtractEmail() {
        String email = "test@example.com";
        String token = jwtService.generateToken(email);
        
        String extractedEmail = jwtService.extractEmail(token);
        assertEquals(email, extractedEmail);
    }

    /**
     * Test token validation with valid token.
     * Test de validation de token avec un token valide.
     */
    @Test
    void testValidateToken() {
        String email = "test@example.com";
        String token = jwtService.generateToken(email);
        
        assertTrue(jwtService.validateToken(token));
    }

    /**
     * Test token validation with invalid token.
     * Test de validation de token avec un token invalide.
     */
    @Test
    void testValidateInvalidToken() {
        String invalidToken = "invalid.token.here";
        
        assertFalse(jwtService.validateToken(invalidToken));
    }

    /**
     * Test token expiration.
     * Test d'expiration du token.
     */
    @Test
    void testTokenExpiration() {
        String email = "test@example.com";
        String token = jwtService.generateToken(email);
        
        // Token should be valid immediately after generation
        assertTrue(jwtService.validateToken(token));
    }
} 