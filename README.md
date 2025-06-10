# 🔐 Password Generator API

Une API REST pour générer des mots de passe sécurisés, selon plusieurs stratégies personnalisables : aléatoire, pattern,
phrase, ou jeu de caractères défini.

## 🚀 Fonctionnalités

- Génération de mots de passe sécurisés selon 5 stratégies :
    - `RANDOM` : génération aléatoire classique.
    - `PATTERN` : génération basée sur un motif.
    - `PIN`    : génération aléatoire d'un code pin.
    - `CUSTOM_SET` : génération avec un ensemble de caractères défini.
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

ou avec docker:

Création de l'image :
    docker build -t <nom-de-image:tag> . (Ne pas oublier le point dans la commande, puis
vérifier que l'image existe avec un status Up en utilisant la commande docker ps -a)
Lancement du conteneur :
    docker run -d --name <nom-du-conteneur> -p 8080:8080 <nom-de-image:tag>
    (lace le conteneur en mode détaché sur le port 8080)


## Une documentation interactive est disponible via Swagger :
http://localhost:8080/swagger-ui.html
