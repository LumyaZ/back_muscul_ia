import requests
import json
import time

def test_endpoint(url, method="GET", data=None, name="Endpoint"):
    """Test un endpoint et affiche les résultats"""
    print(f"\n=== Test {name} ===")
    print(f"URL: {url}")
    print(f"Method: {method}")
    
    try:
        if method == "GET":
            response = requests.get(url, timeout=10)
        elif method == "POST":
            response = requests.post(url, json=data, timeout=120)
        
        print(f"Status Code: {response.status_code}")
        print(f"Headers: {dict(response.headers)}")
        
        if response.status_code == 200:
            try:
                result = response.json()
                print(f"Response: {json.dumps(result, indent=2, ensure_ascii=False)}")
            except:
                print(f"Response: {response.text}")
        else:
            print(f"Error Response: {response.text}")
            
    except requests.exceptions.Timeout:
        print("Timeout - Le service ne répond pas dans les temps")
    except requests.exceptions.ConnectionError:
        print("Connection Error - Impossible de se connecter au service")
    except Exception as e:
        print(f"Error: {str(e)}")
    
    print("-" * 50)

def main():
    print("🧪 Test des endpoints du service IA Muscul IA")
    print("=" * 60)
    
    base_url = "http://localhost:8001"
    
    # Test 1: Endpoint racine
    test_endpoint(f"{base_url}/", "GET", name="Root Endpoint")
    
    # Test 2: Health check
    test_endpoint(f"{base_url}/health", "GET", name="Health Check")
    
    # Test 3: Test connexion IA
    test_endpoint(f"{base_url}/test-ai-connection", "POST", name="AI Connection Test")
    
    # Test 4: Génération de programme
    try:
        with open("test_ai_request.json", "r", encoding="utf-8") as f:
            data = json.load(f)
        
        print(f"\n=== Test Generate Training Program ===")
        print(f"URL: {base_url}/generate-training-program")
        print(f"Method: POST")
        print(f"Data: {json.dumps(data, indent=2)}")
        print("⏳ Génération en cours... (cela peut prendre 1-2 minutes)")
        
        test_endpoint(
            f"{base_url}/generate-training-program", 
            "POST", 
            data=data, 
            name="Generate Training Program"
        )
    except FileNotFoundError:
        print("\nFichier test_ai_request.json non trouvé")
    except json.JSONDecodeError:
        print("\nErreur de parsing JSON dans test_ai_request.json")
    
    # Test 5: Test direct Ollama 
    print("\n=== Test direct Ollama ===")
    try:
        ollama_response = requests.post(
            "http://localhost:11434/api/generate",
            json={
                "model": "llama2:7b",
                "prompt": "Réponds simplement 'OK' si tu reçois ce message.",
                "stream": False
            },
            timeout=60
        )
        print(f"Ollama Status: {ollama_response.status_code}")
        if ollama_response.status_code == 200:
            result = ollama_response.json()
            print(f"Ollama Response: {result.get('response', 'Pas de réponse')}")
        else:
            print(f"Ollama Error: {ollama_response.text}")
    except Exception as e:
        print(f"Ollama Error: {str(e)}")

if __name__ == "__main__":
    main() 