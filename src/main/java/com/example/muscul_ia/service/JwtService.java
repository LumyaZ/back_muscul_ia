package com.example.muscul_ia.service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

/**
 * Service for JWT token management.
 * Service de gestion des tokens JWT.
 */
@Service
public class JwtService {
    
    @Value("${jwt.secret:404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970}")
    private String secretKey;
    
    @Value("${jwt.expiration:86400000}")
    private long expirationTime;

    /**
     * Get the signing key for JWT operations.
     * Récupérer la clé de signature pour les opérations JWT.
     * 
     * @return SecretKey - Clé de signature
     */
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    /**
     * Generate a JWT token for the given email.
     * Générer un token JWT pour l'email donné.
     * 
     * @param email - Email de l'utilisateur
     * @return String - Token JWT généré
     */
    public String generateToken(String email) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationTime);
        
        return Jwts.builder()
                .claim("email", email)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(getSigningKey())
                .compact();
    }

    /**
     * Extract email from JWT token.
     * Extraire l'email depuis le token JWT.
     * 
     * @param token - Token JWT
     * @return String - Email extrait
     */
    public String extractEmail(String token) {
        return Jwts.parser()
                .setSigningKey(getSigningKey())
                .parseClaimsJws(token)
                .getBody()
                .get("email", String.class);
    }

    /**
     * Validate JWT token.
     * Valider le token JWT.
     * 
     * @param token - Token JWT à valider
     * @return boolean - true si valide, false sinon
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                .setSigningKey(getSigningKey())
                .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Check if token is expired.
     * Vérifier si le token est expiré.
     * 
     * @param token - Token JWT
     * @return boolean - true si expiré, false sinon
     */
    public boolean isTokenExpired(String token) {
        try {
            Claims claims = Jwts.parser()
                .setSigningKey(getSigningKey())
                .parseClaimsJws(token)
                .getBody();
            
            return claims.getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            return true;
        }
    }
} 