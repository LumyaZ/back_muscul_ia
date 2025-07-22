package com.example.muscul_ia.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

/**
 * Security configuration for password encoding.
 * Configuration de sécurité pour l'encodage des mots de passe.
 */
@Configuration
public class SecurityConfig {
    /**
     * Bean for password encoding using BCrypt.
     * Bean pour l'encodage des mots de passe avec BCrypt.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Security filter chain configuration for HTTP security.
     * Configuration de la chaîne de filtres de sécurité HTTP.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authz -> authz
                .requestMatchers(
                    "/swagger-ui.html",
                    "/swagger-ui/**",
                    "/v3/api-docs/**"
                ).permitAll()
                .anyRequest().authenticated()
            )
            .formLogin()
            .and()
            .httpBasic();
        return http.build();
    }
} 