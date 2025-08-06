package com.example.muscul_ia.dto;

import com.example.muscul_ia.entity.UserProfile;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Data Transfer Object for UserProfile entity.
 * Objet de transfert de données pour l'entité UserProfile.
 */
public class UserProfileDto {
    
    private Long id;
    
    private Long userId;
    
    private String firstName;
    
    private String lastName;
    
    private String fullName;
    
    private LocalDate dateOfBirth;
    
    private Integer age;
    
    private String phoneNumber;
    
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;

    // Constructors
    public UserProfileDto() {}

    public UserProfileDto(UserProfile userProfile) {
        this.id = userProfile.getId();
        this.userId = userProfile.getUser().getId();
        this.firstName = userProfile.getFirstName();
        this.lastName = userProfile.getLastName();
        this.fullName = userProfile.getFullName();
        this.dateOfBirth = userProfile.getDateOfBirth();
        this.age = userProfile.getAge();
        this.phoneNumber = userProfile.getPhoneNumber();
        this.createdAt = userProfile.getCreatedAt();
        this.updatedAt = userProfile.getUpdatedAt();
    }

    // Getters and setters
    public Long getId() { 
        return id; 
    }
    
    public void setId(Long id) { 
        this.id = id; 
    }

    public Long getUserId() { 
        return userId; 
    }
    
    public void setUserId(Long userId) { 
        this.userId = userId; 
    }

    public String getFirstName() { 
        return firstName; 
    }
    
    public void setFirstName(String firstName) { 
        this.firstName = firstName; 
    }

    public String getLastName() { 
        return lastName; 
    }
    
    public void setLastName(String lastName) { 
        this.lastName = lastName; 
    }

    public String getFullName() { 
        return fullName; 
    }
    
    public void setFullName(String fullName) { 
        this.fullName = fullName; 
    }

    public LocalDate getDateOfBirth() { 
        return dateOfBirth; 
    }
    
    public void setDateOfBirth(LocalDate dateOfBirth) { 
        this.dateOfBirth = dateOfBirth; 
    }

    public Integer getAge() { 
        return age; 
    }
    
    public void setAge(Integer age) { 
        this.age = age; 
    }

    public String getPhoneNumber() { 
        return phoneNumber; 
    }
    
    public void setPhoneNumber(String phoneNumber) { 
        this.phoneNumber = phoneNumber; 
    }

    public LocalDateTime getCreatedAt() { 
        return createdAt; 
    }
    
    public void setCreatedAt(LocalDateTime createdAt) { 
        this.createdAt = createdAt; 
    }

    public LocalDateTime getUpdatedAt() { 
        return updatedAt; 
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) { 
        this.updatedAt = updatedAt; 
    }
} 