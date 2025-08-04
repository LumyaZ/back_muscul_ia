package com.example.muscul_ia.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

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
        ReflectionTestUtils.setField(jwtService, "secretKey", "test-secret-key-for-testing-only");
        ReflectionTestUtils.setField(jwtService, "expirationTime", 86400000L);
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
        assertTrue(token.contains(".")); 
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
        
        assertTrue(jwtService.validateToken(token));
        
        assertFalse(jwtService.isTokenExpired(token));
    }
} 