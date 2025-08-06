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

docs/                # Documentation
├── MONITORING.md    # Guide de monitoring
└── QUALITY.md       # Guide de qualité du code

config/              # Configuration
└── checkstyle.xml   # Règles Checkstyle

scripts/             # Scripts utilitaires
└── quality-check.sh # Vérification qualité
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
  "success": true,
  "message": "Utilisateur créé avec succès",
  "user": {
    "id": 1,
    "email": "user@example.com",
    "creationDate": "2024-01-01T00:00:00Z"
  }
}
```

#### POST /api/auth/login
Connexion utilisateur.

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
  "success": true,
  "message": "Connexion réussie",
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "user": {
    "id": 1,
    "email": "user@example.com"
  }
}
```

## 🧪 Tests et Qualité

### Vérification de la qualité
```bash
# Vérification complète
./scripts/quality-check.sh

# Ou commandes individuelles
./mvnw checkstyle:check
./mvnw spotbugs:check
./mvnw test jacoco:report
```

### Rapports disponibles
- **Couverture de code :** `target/site/jacoco/index.html`
- **SpotBugs :** `target/spotbugs/spotbugsXml.xml`
- **Checkstyle :** Vérifié dans la console

### Seuils de qualité
- **Couverture de code :** ≥ 80%
- **Checkstyle :** 0 erreurs
- **SpotBugs :** 0 bugs critiques
- **Tests :** 100% de succès

## 📊 Monitoring

### Endpoints de monitoring
- **Health Check :** `http://localhost:8080/actuator/health`
- **Métriques :** `http://localhost:8080/actuator/metrics`
- **Prometheus :** `http://localhost:8080/actuator/prometheus`

### Métriques disponibles
- Temps de réponse des endpoints
- Nombre de requêtes par seconde
- Utilisation mémoire et CPU
- Connexions base de données
- Erreurs HTTP

## 📖 Documentation

- **Guide de qualité :** `docs/QUALITY.md`
- **Guide de monitoring :** `docs/MONITORING.md`
- **API Documentation :** `http://localhost:8080/swagger-ui.html`

## 🐳 Docker

### Construction de l'image
```bash
docker build -t muscul-ia-backend .
```

### Lancement avec Docker
```bash
docker run -p 8080:8080 muscul-ia-backend
```

## 🤝 Contribution

1. Fork le projet
2. Créer une branche feature (`git checkout -b feature/AmazingFeature`)
3. Commit les changements (`git commit -m 'Add some AmazingFeature'`)
4. Push vers la branche (`git push origin feature/AmazingFeature`)
5. Ouvrir une Pull Request

### Standards de code
- Suivre les conventions Checkstyle
- Maintenir une couverture de code ≥ 80%
- Documenter les nouvelles fonctionnalités
- Ajouter des tests pour les nouvelles fonctionnalités