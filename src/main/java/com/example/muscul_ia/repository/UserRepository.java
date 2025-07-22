package com.example.muscul_ia.repository;

import com.example.muscul_ia.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

/**
 * Repository for User entity.
 * Référentiel pour l'entité User.
 */
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
} 