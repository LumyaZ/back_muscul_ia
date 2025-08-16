# Multi-stage build pour optimiser la taille de l'image
FROM maven:3.9.6-eclipse-temurin-17 AS build

# Définir le répertoire de travail
WORKDIR /app

# Copier les fichiers de configuration Maven
COPY pom.xml .
COPY .mvn .mvn
COPY mvnw .

# Télécharger les dépendances (cache layer)
RUN mvn dependency:go-offline -B

# Copier le code source et la configuration
COPY src src
COPY config config

# Compiler l'application
RUN mvn clean package -DskipTests

# Stage de production
FROM eclipse-temurin:17-jre-jammy

# Installer curl pour les healthchecks
RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*

# Créer un utilisateur non-root pour la sécurité
RUN groupadd -r spring && useradd -r -g spring spring

# Définir le répertoire de travail
WORKDIR /app

# Copier le JAR depuis le stage de build
COPY --from=build /app/target/*.jar app.jar

# Changer les permissions
RUN chown spring:spring app.jar

# Passer à l'utilisateur non-root
USER spring

# Exposer le port
EXPOSE 8080

# Variables d'environnement pour la configuration
ENV SPRING_PROFILES_ACTIVE=prod
ENV JAVA_OPTS="-Xmx512m -Xms256m"

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=60s --retries=3 \
  CMD curl -f http://localhost:8080/actuator/health || exit 1

# Commande de démarrage
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"] 