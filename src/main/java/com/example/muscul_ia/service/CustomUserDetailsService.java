package com.example.muscul_ia.service;

import com.example.muscul_ia.entity.User;
import com.example.muscul_ia.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("=== CUSTOM USER DETAILS SERVICE ===");
        System.out.println("Loading user by email: " + email);
        
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        System.out.println("User found: " + user.getEmail() + " (ID: " + user.getId() + ")");

        // Créer des autorités par défaut pour l'utilisateur
        var authorities = Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
        
        System.out.println("Authorities: " + authorities);

        return new org.springframework.security.core.userdetails.User(
            user.getEmail(), 
            user.getPassword(), 
            authorities
        );
    }
} 