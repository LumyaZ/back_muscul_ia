#!/usr/bin/env python3
"""
Script de diagnostic pour l'écosystème Muscul IA
Analyse l'état de tous les services et identifie les problèmes
"""

import requests
import json
import time
import subprocess
import sys
from datetime import datetime

def run_command(command):
    """Exécute une commande et retourne le résultat"""
    try:
        result = subprocess.run(command, shell=True, capture_output=True, text=True)
        return result.returncode, result.stdout, result.stderr
    except Exception as e:
        return -1, "", str(e)

def test_endpoint(url, method="GET", data=None, timeout=10, name="Endpoint"):
    """Test un endpoint et retourne les résultats"""
    print(f"\n🔍 Test {name}")
    print(f"   URL: {url}")
    print(f"   Method: {method}")
    
    try:
        if method == "GET":
            response = requests.get(url, timeout=timeout)
        elif method == "POST":
            response = requests.post(url, json=data, timeout=timeout)
        
        print(f"   Status: {response.status_code}")
        
        if response.status_code == 200:
            print(f"   {name} - OK")
            try:
                result = response.json()
                return True, result
            except:
                return True, response.text
        else:
            print(f"   {name} - Erreur {response.status_code}")
            print(f"   Response: {response.text}")
            return False, response.text
            
    except requests.exceptions.Timeout:
        print(f"   ⏰ {name} - Timeout")
        return False, "Timeout"
    except requests.exceptions.ConnectionError:
        print(f"   🔌 {name} - Connection Error")
        return False, "Connection Error"
    except Exception as e:
        print(f"   💥 {name} - Error: {str(e)}")
        return False, str(e)

def check_docker_services():
    """Vérifie l'état des services Docker"""
    print("\n🐳 État des services Docker")
    print("=" * 50)
    
    # Vérifier l'état des conteneurs
    code, output, error = run_command("docker-compose ps")
    
    if code == 0:
        print(output)
    else:
        print(f"Erreur lors de la vérification des conteneurs: {error}")
        return False
    
    return True

def check_service_health():
    """Vérifie la santé de tous les services"""
    print("\n🏥 Tests de santé des services")
    print("=" * 50)
    
    services = [
        ("Backend Health", "http://localhost:8080/actuator/health", "GET"),
        ("Service IA Health", "http://localhost:8001/health", "GET"),
        ("Ollama API", "http://localhost:11434/api/tags", "GET"),
        ("Frontend", "http://localhost:4200", "GET"),
    ]
    
    results = {}
    
    for name, url, method in services:
        success, response = test_endpoint(url, method, name=name)
        results[name] = {"success": success, "response": response}
    
    return results

def check_database_connection():
    """Vérifie la connexion à la base de données"""
    print("\n🗄️ Test de connexion à la base de données")
    print("=" * 50)
    
    # Test via le backend
    success, response = test_endpoint(
        "http://localhost:8080/actuator/health", 
        "GET", 
        name="Database via Backend"
    )
    
    if success and isinstance(response, dict):
        db_status = response.get("components", {}).get("db", {})
        if db_status.get("status") == "UP":
            print("   Base de données accessible via le backend")
            return True
        else:
            print(f"   Problème de base de données: {db_status}")
            return False
    else:
        print("   Impossible de vérifier la base de données")
        return False

def check_ai_service():
    """Teste le service IA"""
    print("\n🤖 Test du service IA")
    print("=" * 50)
    
    # Test de connexion IA
    success, response = test_endpoint(
        "http://localhost:8001/test-ai-connection",
        "POST",
        name="AI Connection Test"
    )
    
    if success:
        print("   Service IA fonctionne correctement")
        return True
    else:
        print("   Problème avec le service IA")
        return False

def check_ollama_models():
    """Vérifie les modèles disponibles dans Ollama"""
    print("\n🧠 Vérification des modèles Ollama")
    print("=" * 50)
    
    success, response = test_endpoint(
        "http://localhost:11434/api/tags",
        "GET",
        name="Ollama Models"
    )
    
    if success and isinstance(response, dict):
        models = response.get("models", [])
        if models:
            print(f"   Modèles disponibles: {len(models)}")
            for model in models:
                print(f"      - {model.get('name', 'Unknown')}")
            return True
        else:
            print("   ⚠️ Aucun modèle trouvé - téléchargement en cours ?")
            return False
    else:
        print("   Impossible de récupérer les modèles")
        return False

def generate_report(results):
    """Génère un rapport de diagnostic"""
    print("\n📊 Rapport de diagnostic")
    print("=" * 50)
    
    timestamp = datetime.now().strftime("%Y-%m-%d %H:%M:%S")
    print(f"Date: {timestamp}")
    
    # Résumé des services
    print("\nRésumé des services:")
    
    services_status = {
        "MySQL": results.get("Database via Backend", {}).get("success", False),
        "Backend": results.get("Backend Health", {}).get("success", False),
        "Service IA": results.get("Service IA Health", {}).get("success", False),
        "Ollama": results.get("Ollama API", {}).get("success", False),
        "Frontend": results.get("Frontend", {}).get("success", False),
    }
    
    for service, status in services_status.items():
        icon = "✅" if status else "❌"
        print(f"   {icon} {service}")
    
    # Recommandations
    print("\n🔧 Recommandations:")
    
    if not services_status["MySQL"]:
        print("   - Vérifier la base de données MySQL")
        print("   - Redémarrer: docker-compose restart mysql")
    
    if not services_status["Backend"]:
        print("   - Vérifier les logs du backend")
        print("   - Redémarrer: docker-compose restart backend")
    
    if not services_status["Ollama"]:
        print("   - Vérifier les logs d'Ollama")
        print("   - Le modèle peut être en cours de téléchargement")
        print("   - Redémarrer: docker-compose restart ollama")
    
    if not services_status["Service IA"]:
        print("   - Vérifier les logs du service IA")
        print("   - Redémarrer: docker-compose restart ai-service")
    
    if not services_status["Frontend"]:
        print("   - Vérifier les logs du frontend")
        print("   - Redémarrer: docker-compose restart frontend")

def main():
    """Fonction principale"""
    print("🔍 Diagnostic de l'écosystème Muscul IA")
    print("=" * 60)
    
    # Vérifier Docker
    if not check_docker_services():
        print("Impossible de vérifier les services Docker")
        return
    
    # Tests de santé
    health_results = check_service_health()
    
    # Tests spécifiques et collecte des résultats
    db_success = check_database_connection()
    ollama_success = check_ollama_models()
    ai_success = check_ai_service()
    
    # Créer un rapport complet avec tous les résultats
    complete_results = {
        **health_results,
        "Database via Backend": {"success": db_success},
        "Ollama Models": {"success": ollama_success},
        "AI Service Test": {"success": ai_success}
    }
    
    # Rapport final
    generate_report(complete_results)
    
    print("\nDiagnostic terminé !")

if __name__ == "__main__":
    main() 