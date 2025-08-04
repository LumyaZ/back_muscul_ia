package com.example.muscul_ia.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private LocalDateTime creationDate;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private UserProfile userProfile;

    public User() {
        this.creationDate = LocalDateTime.now();
    }

    public User(String email, String password) {
        this();
        this.email = email;
        this.password = password;
    }

    // Getters and setters
    public Long getId() { 
        return id; 
    }
    
    public void setId(Long id) { 
        this.id = id; 
    }
    
    public String getEmail() { 
        return email; 
    }
    
    public void setEmail(String email) { 
        this.email = email; 
    }
    
    public String getPassword() { 
        return password; 
    }
    
    public void setPassword(String password) { 
        this.password = password; 
    }
    
    public LocalDateTime getCreationDate() { 
        return creationDate; 
    }
    
    public void setCreationDate(LocalDateTime creationDate) { 
        this.creationDate = creationDate; 
    }
    
    public UserProfile getUserProfile() { 
        return userProfile; 
    }
    
    public void setUserProfile(UserProfile userProfile) { 
        this.userProfile = userProfile; 
    }
} 