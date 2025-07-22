#!/bin/bash

echo "🔍 Démarrage des vérifications de qualité du code Back-end..."

# Couleurs pour les messages
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Fonction pour afficher les résultats
print_result() {
    if [ $1 -eq 0 ]; then
        echo -e "${GREEN}✅ $2${NC}"
    else
        echo -e "${RED}❌ $2${NC}"
        exit 1
    fi
}

# 1. Vérification Checkstyle
echo "📋 Vérification Checkstyle..."
./mvnw checkstyle:check
print_result $? "Checkstyle terminé"

# 2. Vérification SpotBugs
echo "🐛 Vérification SpotBugs..."
./mvnw spotbugs:check
print_result $? "SpotBugs terminé"

# 3. Tests unitaires avec couverture
echo "🧪 Exécution des tests avec couverture..."
./mvnw clean test jacoco:report
print_result $? "Tests et couverture terminés"

# 4. Compilation
echo "🔨 Compilation du projet..."
./mvnw clean compile
print_result $? "Compilation terminée"

# 5. Vérification des dépendances
echo "📦 Vérification des dépendances..."
./mvnw dependency:analyze
print_result $? "Analyse des dépendances terminée"

echo -e "${GREEN}🎉 Toutes les vérifications de qualité sont passées !${NC}"

# Affichage des rapports
echo -e "${YELLOW}📊 Rapports disponibles :${NC}"
echo "  - Couverture de code : target/site/jacoco/index.html"
echo "  - SpotBugs : target/spotbugs/spotbugsXml.xml"
echo "  - Checkstyle : Vérifié dans la console" 