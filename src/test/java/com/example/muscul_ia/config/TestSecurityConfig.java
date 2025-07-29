package com.example.muscul_ia.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.context.ActiveProfiles;

/**
 * Test security configuration that disables authentication for testing.
 * Configuration de sécurité de test qui désactive l'authentification pour les tests.
 */
@TestConfiguration
@EnableWebSecurity
@ActiveProfiles("test")
public class TestSecurityConfig {

    /**
     * Security filter chain configuration for tests - allows all requests.
     * Configuration de la chaîne de filtres de sécurité pour les tests - autorise toutes les requêtes.
     */
    @Bean
    @Primary
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeHttpRequests(authz -> authz
                .anyRequest().permitAll()
            );

        return http.build();
    }
} 