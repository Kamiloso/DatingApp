import requests

URL = "http://localhost:8080/api/v1/"
FAKE_JWT_TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.fake_payload.fake_signature"

headers = {
    "Authorization": f"Bearer {FAKE_JWT_TOKEN}"
}

while True:
    resource = input("Resource: ")

    try:
        response = requests.get(f"{URL}{resource}", headers=headers, allow_redirects=False)
        response.raise_for_status()
        print(response.text)
    
    except requests.exceptions.RequestException as e:
        print(f"Request failed: {e}")