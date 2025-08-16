# Guide de Qualité du Code - Back-end

## 📊 Outils de Qualité

### 1. Checkstyle
Vérifie la conformité du code aux standards de codage Java.

**Lancement :**
```bash
./mvnw checkstyle:check
```

**Configuration :** `config/checkstyle.xml`
- Longueur de ligne max : 120 caractères
- Longueur de méthode max : 150 lignes
- Nombre de paramètres max : 7
- Conventions de nommage Java
- Espacement et formatage

### 2. SpotBugs
Détecte les bugs potentiels dans le code Java.

**Lancement :**
```bash
./mvnw spotbugs:check
```

**Rapport :** `target/spotbugs/spotbugsXml.xml`

### 3. JaCoCo (Couverture de Code)
Mesure la couverture de code par les tests.

**Lancement :**
```bash
./mvnw test jacoco:report
```

**Seuil minimum :** 80% de couverture de lignes
**Rapport :** `target/site/jacoco/index.html`

## 📈 Métriques de Performance

### Endpoints de Monitoring
- **Health Check :** `http://localhost:8080/actuator/health`
- **Métriques :** `http://localhost:8080/actuator/metrics`
- **Prometheus :** `http://localhost:8080/actuator/prometheus`

### Métriques disponibles
- Temps de réponse des endpoints
- Nombre de requêtes par seconde
- Utilisation mémoire et CPU
- Connexions base de données
- Erreurs HTTP

## 📝 Logs Structurés

### Configuration
```properties
# Niveaux de logs
logging.level.com.example.muscul_ia=DEBUG
logging.level.org.springframework.security=DEBUG
logging.level.org.hibernate.SQL=DEBUG

# Format structuré
logging.pattern.console=%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{36} - %msg%n
```

### Logs importants à surveiller
- **Authentification :** Tentatives de connexion, échecs
- **Base de données :** Requêtes lentes, erreurs de connexion
- **Sécurité :** Tentatives d'accès non autorisées
- **Performance :** Temps de réponse des API

## 🔧 Scripts Automatisés

### Vérification complète
```bash
./scripts/quality-check.sh
```

Ce script exécute :
1. Checkstyle
2. SpotBugs
3. Tests avec couverture
4. Compilation
5. Analyse des dépendances

### Commandes individuelles
```bash
# Qualité du code
./mvnw checkstyle:check
./mvnw spotbugs:check

# Tests et couverture
./mvnw test jacoco:report

# Compilation
./mvnw clean compile

# Dépendances
./mvnw dependency:analyze
```

## 📋 Standards de Code

### Conventions de nommage
- **Classes :** PascalCase (ex: `UserService`)
- **Méthodes :** camelCase (ex: `getUserById`)
- **Variables :** camelCase (ex: `userName`)
- **Constantes :** UPPER_SNAKE_CASE (ex: `MAX_RETRY_COUNT`)

### Structure des packages
```
com.example.muscul_ia/
├── config/          # Configuration
├── controller/      # Contrôleurs REST
├── service/         # Logique métier
├── repository/      # Accès aux données
├── entity/          # Entités JPA
├── dto/             # Objets de transfert
└── enums/           # Énumérations
```

### Documentation
- **JavaDoc** pour toutes les méthodes publiques
- **README** à jour avec les instructions d'installation
- **Commentaires** pour la logique complexe

## 🚀 Intégration Continue

### GitHub Actions
- Tests automatiques à chaque push
- Vérification de la qualité du code
- Déploiement automatique en staging
- Génération des rapports de couverture

### Seuils de qualité
- **Couverture de code :** ≥ 80%
- **Checkstyle :** 0 erreurs
- **SpotBugs :** 0 bugs critiques
- **Tests :** 100% de succès 