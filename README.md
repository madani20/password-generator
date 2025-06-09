                                                                                                      
# 🔐 Password Generator API

Une API REST pour générer des mots de passe sécurisés, selon plusieurs stratégies personnalisables : aléatoire, pattern, phrase, ou jeu de caractères défini.

## 🚀 Fonctionnalités

- Génération de mots de passe sécurisés selon 5 stratégies :
  - `RANDOM` : génération aléatoire classique.
  - `PATTERN` : génération basée sur un motif.
  - `CUSTOM_SET` : génération avec un ensemble de caractères défini.
  - `PIN`  :  génération d'un code PIN aléatoire.
  - `PASS_PHRASE` : génération de phrases faciles à retenir.
- Validation complète des options de génération.
- Documentation Swagger intégrée.
- Gestion des erreurs structurée.

---

## 📦 Installation

```bash
git clone https://github.com/votre-utilisateur/password-generator-api.git
cd password-generator-api
./mvnw spring-boot:run

ou avec docker :

docker build -t <nom-de-image:tag> . (pour créer l'image docker, ne pas oublier le point)
vérifier que l'image est créée avec status up:
docker ps -a

puis
docker run -d --name nom-instance -p 8080:8080 <nom-de-image:tag> (lance le conteneur en mode détaché sur le port 8080)



## Une documentation interactive est disponible via Swagger :
http://localhost:8080/swagger-ui.html

