#!/bin/bash

echo "Vérification complète de la qualité du code..."

# Tests unitaires
echo "Tests unitaires..."
mvn test
if [ $? -ne 0 ]; then
    echo "Tests unitaires échoués"
    exit 1
fi

# Couverture de code
echo "Couverture de code..."
mvn jacoco:report
if [ $? -ne 0 ]; then
    echo "Rapport de couverture échoué"
    exit 1
fi

# Checkstyle
echo "Vérification du style de code..."
mvn checkstyle:check
if [ $? -ne 0 ]; then
    echo "Checkstyle échoué"
    exit 1
fi

# SpotBugs
echo "🐛 Analyse des bugs potentiels..."
mvn spotbugs:check
if [ $? -ne 0 ]; then
    echo "SpotBugs échoué"
    exit 1
fi

echo "Toutes les vérifications de qualité réussies !"
echo ""
echo "Rapports disponibles :"
echo "  - Couverture de code : target/site/jacoco/index.html"
echo "  - SpotBugs : target/spotbugs/spotbugsXml.xml"
echo "  - Checkstyle : Console output" 