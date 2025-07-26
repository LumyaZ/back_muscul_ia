package com.example.muscul_ia.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Entity representing a user profile containing personal information.
 * Entité représentant un profil utilisateur contenant les informations personnelles.
 * 
 * This entity stores detailed personal information about users including name,
 * date of birth, age, and contact information. It maintains a one-to-one
 * relationship with the User entity and provides utility methods for age
 * calculation and full name retrieval.
 * 
 * Cette entité stocke les informations personnelles détaillées des utilisateurs
 * incluant le nom, la date de naissance, l'âge et les informations de contact.
 * Elle maintient une relation un-à-un avec l'entité User et fournit des méthodes
 * utilitaires pour le calcul de l'âge et la récupération du nom complet.
 * 
 * @author Muscul IA Team
 * @version 1.0
 * @since 2024-01-01
 */
@Entity
@Table(name = "user_profile")
public class UserProfile {
    
    /**
     * Unique identifier for the user profile.
     * Identifiant unique du profil utilisateur.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * One-to-one relationship with User entity.
     * Relation un-à-un avec l'entité User.
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    /**
     * First name of the user.
     * Prénom de l'utilisateur.
     */
    @Column(name = "first_name", length = 50)
    private String firstName;

    /**
     * Last name of the user.
     * Nom de famille de l'utilisateur.
     */
    @Column(name = "last_name", length = 50)
    private String lastName;

    /**
     * Date of birth of the user.
     * Date de naissance de l'utilisateur.
     */
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    /**
     * Age calculated from date of birth.
     * Âge calculé à partir de la date de naissance.
     */
    @Column(name = "age")
    private Integer age;

    /**
     * Phone number for contact.
     * Numéro de téléphone pour le contact.
     */
    @Column(name = "phone_number", length = 20)
    private String phoneNumber;

    /**
     * Timestamp when the profile was created.
     * Horodatage de la création du profil.
     */
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    /**
     * Timestamp when the profile was last updated.
     * Horodatage de la dernière mise à jour du profil.
     */
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /**
     * Default constructor. Initializes creation timestamp.
     * Constructeur par défaut. Initialise l'horodatage de création.
     */
    public UserProfile() {
        this.createdAt = LocalDateTime.now();
    }

    /**
     * Constructor with associated user.
     * Constructeur avec l'utilisateur associé.
     * 
     * @param user - Associated user entity
     */
    public UserProfile(User user) {
        this();
        this.user = user;
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
     * Get the associated user.
     * Récupérer l'utilisateur associé.
     * 
     * @return User - Associated user
     */
    public User getUser() { 
        return user; 
    }
    
    /**
     * Set the associated user.
     * Définir l'utilisateur associé.
     * 
     * @param user - Associated user
     */
    public void setUser(User user) { 
        this.user = user; 
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
     * Get the date of birth.
     * Récupérer la date de naissance.
     * 
     * @return LocalDate - Date of birth
     */
    public LocalDate getDateOfBirth() { 
        return dateOfBirth; 
    }
    
    /**
     * Set the date of birth and automatically calculate age.
     * Définir la date de naissance et calculer automatiquement l'âge.
     * 
     * @param dateOfBirth - Date of birth
     */
    public void setDateOfBirth(LocalDate dateOfBirth) { 
        this.dateOfBirth = dateOfBirth;
        this.calculateAge();
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
     * Set the age manually.
     * Définir l'âge manuellement.
     * 
     * @param age - Age value
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

    /**
     * Calculate age from date of birth.
     * Calcule l'âge à partir de la date de naissance.
     * 
     * This method calculates the current age based on the date of birth,
     * taking into account whether the birthday has occurred this year.
     * 
     * Cette méthode calcule l'âge actuel basé sur la date de naissance,
     * en tenant compte de si l'anniversaire a eu lieu cette année.
     */
    private void calculateAge() {
        if (this.dateOfBirth != null) {
            this.age = LocalDate.now().getYear() - this.dateOfBirth.getYear();
            if (LocalDate.now().isBefore(this.dateOfBirth.plusYears(this.age))) {
                this.age--;
            }
        }
    }

    /**
     * JPA lifecycle method called before entity update.
     * Sets the updatedAt timestamp to current time.
     * 
     * Méthode de cycle de vie JPA appelée avant la mise à jour de l'entité.
     * Définit l'horodatage updatedAt à l'heure actuelle.
     */
    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Get the full name combining first and last name.
     * Obtient le nom complet combinant prénom et nom.
     * 
     * This method returns the full name by combining first and last name.
     * If only one name is available, it returns that name. If neither
     * is available, it returns null.
     * 
     * Cette méthode retourne le nom complet en combinant prénom et nom.
     * Si un seul nom est disponible, elle retourne ce nom. Si aucun
     * n'est disponible, elle retourne null.
     * 
     * @return String - Full name or null if no name available
     */
    public String getFullName() {
        if (firstName != null && lastName != null) {
            return firstName + " " + lastName;
        } else if (firstName != null) {
            return firstName;
        } else if (lastName != null) {
            return lastName;
        }
        return null;
    }
} 