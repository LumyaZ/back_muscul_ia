# Muscul IA - Backend API

## 📋 Description
Backend Spring Boot pour l'application Muscul IA - système d'authentification et API REST pour la gestion des profils utilisateur et des programmes d'entraînement de musculation.

## 🏗️ Architecture

### Structure du projet
```
src/main/java/com/example/muscul_ia/
├── config/           # Configuration (Security, CORS)
├── controller/       # Contrôleurs REST
├── dto/             # Data Transfer Objects
├── entity/          # Entités JPA
├── repository/      # Repositories JPA
├── service/         # Services métier
│   └── impl/        # Implémentations des services
└── MusculIaApplication.java
```

### Technologies utilisées
- **Java 17**
- **Spring Boot 3.x**
- **Spring Security** - Authentification et autorisation
- **Spring Data JPA** - Persistance des données
- **MySQL** - Base de données
- **BCrypt** - Hachage des mots de passe
- **Swagger/OpenAPI** - Documentation API
- **Maven** - Gestion des dépendances
- **Flyway** - Migration de base de données

## 🚀 Installation et démarrage

### Prérequis
- Java 17 ou supérieur
- MySQL 8.0 ou supérieur
- Maven 3.6+

### Configuration de la base de données
1. Créer une base de données MySQL :
```sql
CREATE DATABASE muscul_ia_db;
```

2. Configurer les paramètres de connexion dans `application.properties` :
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/muscul_ia_db
spring.datasource.username=root
spring.datasource.password=your_password
```

### Lancement de l'application
```bash
# Compilation et lancement
./mvnw spring-boot:run

# Ou avec Maven
mvn spring-boot:run
```

L'application sera accessible sur : `http://localhost:8080`

## 🔐 Sécurité

### Configuration CORS
- Origines autorisées : `http://localhost:4200` (Angular)
- Méthodes autorisées : GET, POST, PUT, DELETE, OPTIONS
- Headers autorisés : Tous
- Credentials : Activés

### Endpoints publics
- `/api/auth/register` - Inscription utilisateur
- `/api/auth/login` - Connexion utilisateur
- `/api/profiles/**` - Gestion des profils utilisateur
- `/swagger-ui.html` - Documentation API
- `/v3/api-docs/**` - Spécification OpenAPI

### Protection
- CSRF désactivé pour l'API REST
- Formulaire de login Spring désactivé
- Authentification HTTP Basic pour les endpoints protégés

## 📚 API Documentation

### Endpoints d'authentification

#### POST /api/auth/register
Inscription d'un nouvel utilisateur.

**Request Body :**
```json
{
  "email": "user@example.com",
  "password": "password123",
  "confirmPassword": "password123"
}
```

**Response :**
```json
{
  "id": 1,
  "email": "user@example.com",
  "creationDate": "2024-01-01T10:00:00"
}
```

#### POST /api/auth/login
Connexion d'un utilisateur existant.

**Request Body :**
```json
{
  "email": "user@example.com",
  "password": "password123"
}
```

**Response :**
```json
{
  "id": 1,
  "email": "user@example.com",
  "creationDate": "2024-01-01T10:00:00"
}
```

### Endpoints de gestion des profils utilisateur

#### POST /api/profiles
Créer un profil utilisateur.

**Request Body :**
```json
{
  "firstName": "John",
  "lastName": "Doe",
  "dateOfBirth": "1990-01-01",
  "phoneNumber": "+33123456789"
}
```

**Response :**
```json
{
  "id": 1,
  "userId": 1,
  "firstName": "John",
  "lastName": "Doe",
  "fullName": "John Doe",
  "dateOfBirth": "1990-01-01",
  "age": 34,
  "phoneNumber": "+33123456789",
  "createdAt": "2024-01-01T10:00:00",
  "updatedAt": null
}
```

#### GET /api/profiles/me
Obtenir son propre profil.

#### GET /api/profiles/{userId}
Obtenir un profil par ID utilisateur.



#### PUT /api/profiles/me
Mettre à jour son propre profil.

#### DELETE /api/profiles/me
Supprimer son propre profil.

## 🗄️ Modèle de données

### Entité User
```java
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
}
```

### Entité UserProfile
```java
@Entity
@Table(name = "user_profile")
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;
    
    @Column(name = "first_name", length = 50)
    private String firstName;
    
    @Column(name = "last_name", length = 50)
    private String lastName;
    
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;
    
    @Column(name = "age")
    private Integer age;
    
    @Column(name = "phone_number", length = 20)
    private String phoneNumber;
    
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
```

## 🧪 Tests

### Lancement des tests
```bash
# Tous les tests
./mvnw test

# Tests avec rapport de couverture
./mvnw test jacoco:report
```

### Tests disponibles
- **UserServiceImplTest** : Tests de la logique métier d'authentification
  - Inscription avec validation des mots de passe
  - Connexion avec vérification des identifiants
  - Gestion des erreurs (email existant, mots de passe différents)
- **UserProfileServiceImplTest** : Tests de la logique métier des profils utilisateur
  - Création de profil avec validation
  - Récupération de profil par utilisateur et par ID
  - Mise à jour de profil
  - Suppression de profil
  - Gestion des erreurs (profil déjà existant, utilisateur inexistant)
  - Vérification de l'existence d'un profil
  - Récupération de tous les profils

## 🔧 Configuration

### Variables d'environnement
```properties
# Base de données
spring.datasource.url=jdbc:mysql://localhost:3306/muscul_ia_db
spring.datasource.username=root
spring.datasource.password=your_password

# JPA/Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect

# Flyway (migrations)
spring.flyway.enabled=true
spring.flyway.locations=classpath:db/migration

# Logs
logging.level.com.example.muscul_ia=DEBUG
```

## 📝 Logs et monitoring

### Niveaux de logs
- **DEBUG** : Détails des requêtes SQL et opérations JPA
- **INFO** : Démarrage de l'application, connexions
- **WARN** : Avertissements de sécurité, configurations
- **ERROR** : Erreurs d'authentification, exceptions

### Logs importants à surveiller
- Tentatives de connexion échouées
- Erreurs de validation des données
- Problèmes de connexion à la base de données
- Opérations sur les profils utilisateur

## 🚀 Déploiement

### Build pour production
```bash
./mvnw clean package -DskipTests
```

### Variables d'environnement de production
```properties
spring.profiles.active=prod
spring.datasource.url=${DATABASE_URL}
spring.datasource.username=${DATABASE_USERNAME}
spring.datasource.password=${DATABASE_PASSWORD}
```

## 🔄 Évolutions futures

### Fonctionnalités prévues
- [x] Gestion des profils utilisateur
- [ ] API pour les programmes d'entraînement
- [ ] Système de JWT pour l'authentification
- [ ] Gestion des rôles et permissions
- [ ] API pour les exercices et séries
- [ ] Calcul automatique du BMI et recommandations
- [ ] Historique des modifications de profil

### Améliorations techniques
- [ ] Cache Redis pour les sessions
- [ ] Rate limiting pour les API
- [ ] Monitoring avec Micrometer
- [ ] Tests d'intégration
- [ ] Documentation OpenAPI complète
- [ ] Validation avancée des données de profil

## 📞 Support

Pour toute question ou problème :
- Vérifier les logs de l'application
- Consulter la documentation Swagger : `http://localhost:8080/swagger-ui.html`
- Vérifier la configuration de la base de données
- Consulter la documentation API des profils : `USER_PROFILE_API.md`

---

**Version :** 1.1.0  
**Dernière mise à jour :** Janvier 2024