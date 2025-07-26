package com.example.muscul_ia.dto;

import com.example.muscul_ia.entity.UserProfile;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Data Transfer Object for UserProfile entity.
 * Objet de transfert de données pour l'entité UserProfile.
 * 
 * This DTO is used to transfer user profile data between the controller layer
 * and the client. It contains all the essential profile information including
 * personal details, contact information, and timestamps without exposing
 * internal entity details or JPA annotations.
 * 
 * Ce DTO est utilisé pour transférer les données de profil utilisateur entre
 * la couche contrôleur et le client. Il contient toutes les informations
 * essentielles du profil incluant les détails personnels, les informations
 * de contact et les horodatages sans exposer les détails internes de l'entité
 * ou les annotations JPA.
 * 
 * @author Muscul IA Team
 * @version 1.0
 * @since 2024-01-01
 */
public class UserProfileDto {
    
    /**
     * Unique identifier for the user profile.
     * Identifiant unique du profil utilisateur.
     */
    private Long id;
    
    /**
     * ID of the associated user account.
     * ID du compte utilisateur associé.
     */
    private Long userId;
    
    /**
     * User's first name.
     * Prénom de l'utilisateur.
     */
    private String firstName;
    
    /**
     * User's last name.
     * Nom de famille de l'utilisateur.
     */
    private String lastName;
    
    /**
     * User's full name (firstName + lastName).
     * Nom complet de l'utilisateur (prénom + nom).
     */
    private String fullName;
    
    /**
     * User's date of birth.
     * Date de naissance de l'utilisateur.
     */
    private LocalDate dateOfBirth;
    
    /**
     * User's calculated age based on date of birth.
     * Âge calculé de l'utilisateur basé sur la date de naissance.
     */
    private Integer age;
    
    /**
     * User's phone number.
     * Numéro de téléphone de l'utilisateur.
     */
    private String phoneNumber;
    
    /**
     * Timestamp when the profile was created.
     * Horodatage de la création du profil.
     */
    private LocalDateTime createdAt;
    
    /**
     * Timestamp when the profile was last updated.
     * Horodatage de la dernière mise à jour du profil.
     */
    private LocalDateTime updatedAt;

    /**
     * Default constructor.
     * Constructeur par défaut.
     */
    public UserProfileDto() {}

    /**
     * Constructor that creates a DTO from a UserProfile entity.
     * Constructeur qui crée un DTO à partir d'une entité UserProfile.
     * 
     * This constructor extracts all relevant data from the UserProfile entity
     * and creates a clean DTO for client communication.
     * 
     * Ce constructeur extrait toutes les données pertinentes de l'entité
     * UserProfile et crée un DTO propre pour la communication client.
     * 
     * @param userProfile - The UserProfile entity to convert
     */
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
    /**
     * Get the profile ID.
     * Récupérer l'ID du profil.
     * 
     * @return Long - Profile ID
     */
    public Long getId() { 
        return id; 
    }
    
    /**
     * Set the profile ID.
     * Définir l'ID du profil.
     * 
     * @param id - Profile ID
     */
    public void setId(Long id) { 
        this.id = id; 
    }

    /**
     * Get the associated user ID.
     * Récupérer l'ID de l'utilisateur associé.
     * 
     * @return Long - User ID
     */
    public Long getUserId() { 
        return userId; 
    }
    
    /**
     * Set the associated user ID.
     * Définir l'ID de l'utilisateur associé.
     * 
     * @param userId - User ID
     */
    public void setUserId(Long userId) { 
        this.userId = userId; 
    }

    /**
     * Get the first name.
     * Récupérer le prénom.
     * 
     * @return String - First name
     */
    public String getFirstName() { 
        return firstName; 
    }
    
    /**
     * Set the first name.
     * Définir le prénom.
     * 
     * @param firstName - First name
     */
    public void setFirstName(String firstName) { 
        this.firstName = firstName; 
    }

    /**
     * Get the last name.
     * Récupérer le nom de famille.
     * 
     * @return String - Last name
     */
    public String getLastName() { 
        return lastName; 
    }
    
    /**
     * Set the last name.
     * Définir le nom de famille.
     * 
     * @param lastName - Last name
     */
    public void setLastName(String lastName) { 
        this.lastName = lastName; 
    }

    /**
     * Get the full name.
     * Récupérer le nom complet.
     * 
     * @return String - Full name
     */
    public String getFullName() { 
        return fullName; 
    }
    
    /**
     * Set the full name.
     * Définir le nom complet.
     * 
     * @param fullName - Full name
     */
    public void setFullName(String fullName) { 
        this.fullName = fullName; 
    }

    /**
     * Get the date of birth.
     * Récupérer la date de naissance.
     * 
     * @return LocalDate - Date of birth
     */
    public LocalDate getDateOfBirth() { 
        return dateOfBirth; 
    }
    
    /**
     * Set the date of birth.
     * Définir la date de naissance.
     * 
     * @param dateOfBirth - Date of birth
     */
    public void setDateOfBirth(LocalDate dateOfBirth) { 
        this.dateOfBirth = dateOfBirth; 
    }

    /**
     * Get the calculated age.
     * Récupérer l'âge calculé.
     * 
     * @return Integer - Calculated age
     */
    public Integer getAge() { 
        return age; 
    }
    
    /**
     * Set the calculated age.
     * Définir l'âge calculé.
     * 
     * @param age - Calculated age
     */
    public void setAge(Integer age) { 
        this.age = age; 
    }

    /**
     * Get the phone number.
     * Récupérer le numéro de téléphone.
     * 
     * @return String - Phone number
     */
    public String getPhoneNumber() { 
        return phoneNumber; 
    }
    
    /**
     * Set the phone number.
     * Définir le numéro de téléphone.
     * 
     * @param phoneNumber - Phone number
     */
    public void setPhoneNumber(String phoneNumber) { 
        this.phoneNumber = phoneNumber; 
    }

    /**
     * Get the creation timestamp.
     * Récupérer l'horodatage de création.
     * 
     * @return LocalDateTime - Creation timestamp
     */
    public LocalDateTime getCreatedAt() { 
        return createdAt; 
    }
    
    /**
     * Set the creation timestamp.
     * Définir l'horodatage de création.
     * 
     * @param createdAt - Creation timestamp
     */
    public void setCreatedAt(LocalDateTime createdAt) { 
        this.createdAt = createdAt; 
    }

    /**
     * Get the last update timestamp.
     * Récupérer l'horodatage de dernière mise à jour.
     * 
     * @return LocalDateTime - Last update timestamp
     */
    public LocalDateTime getUpdatedAt() { 
        return updatedAt; 
    }
    
    /**
     * Set the last update timestamp.
     * Définir l'horodatage de dernière mise à jour.
     * 
     * @param updatedAt - Last update timestamp
     */
    public void setUpdatedAt(LocalDateTime updatedAt) { 
        this.updatedAt = updatedAt; 
    }
} 