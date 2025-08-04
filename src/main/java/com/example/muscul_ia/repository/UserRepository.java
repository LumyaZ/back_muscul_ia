package com.example.muscul_ia.repository;

import com.example.muscul_ia.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * User repository for managing user data operations.
 * Repository utilisateur pour gérer les opérations de données utilisateur.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    /**
     * Find user by email.
     * Trouver un utilisateur par email.
     */
    Optional<User> findByEmail(String email);
} 