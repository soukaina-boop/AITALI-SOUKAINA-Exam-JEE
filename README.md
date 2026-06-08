# Application de gestion des credits bancaires


Backend Spring Boot realise dans le cadre de l'examen JEE / Architecture Distribuee.

Le projet porte sur la gestion des credits bancaires. Il expose des API REST securisees avec JWT et documentees avec Swagger / OpenAPI.

## Sommaire

- Objectif du projet
- Fonctionnalites realisees
- Architecture technique
- Structure du projet
- Entites JPA
- Couche DAO
- Couche Service
- REST API
- Securite JWT
- Base de donnees H2
- Swagger UI
- Comptes de test
- Tests Postman
- Rapport

## Objectif du projet

L'objectif est de developper uniquement le backend d'une application de gestion des credits bancaires.

L'application permet de gerer :

- Les clients
- Les credits bancaires
- Les types de credits
- Les remboursements
- Les utilisateurs de l'application
- L'authentification avec JWT
- Les autorisations selon les roles

## Fonctionnalites realisees

- Creation des entites JPA
- Creation des repositories Spring Data JPA
- Insertion automatique de donnees de test avec `DataInitializer`
- Creation des DTOs
- Creation des Mappers
- Creation de la couche service metier
- Creation des REST Controllers
- Documentation des API avec Swagger / OpenAPI
- Validation des donnees avec Jakarta Validation
- Securite avec Spring Security et JWT
- Gestion des roles :
  - `ROLE_CLIENT`
  - `ROLE_EMPLOYE`
  - `ROLE_ADMIN`
- Tests des API avec Swagger et Postman

## Technologies utilisees

- Java 17
- Spring Boot
- Spring MVC
- Spring Data JPA
- Hibernate
- H2 Database
- MySQL Connector
- Spring Security
- Json Web Token
- Swagger / OpenAPI
- Lombok
- Maven

## Architecture technique

Le backend suit une architecture en couches.

```text
Frontend / Outils de test
        |
        v
REST Controllers
        |
        v
Couche Service
        |
        v
DTOs et Mappers
        |
        v
Repositories Spring Data JPA
        |
        v
Base de donnees H2 / MySQL
```

### Description des couches

**Couche Web**

Expose les endpoints REST de l'application.

Exemples :

- `AuthRestController`
- `ClientRestController`
- `CreditRestController`
- `RemboursementRestController`

**Couche Service**

Contient la logique metier de l'application.

Exemples :

- Creation d'un client
- Creation d'un credit
- Changement du statut d'un credit
- Ajout d'un remboursement
- Authentification et generation du token JWT

**Couche DAO**

Utilise Spring Data JPA pour acceder aux donnees.

Exemples :

- `ClientRepository`
- `CreditRepository`
- `RemboursementRepository`
- `AppUserRepository`

**Base de donnees**

Le projet utilise H2 en memoire pour les tests. Il contient aussi le connecteur MySQL pour une configuration future.

## Structure du projet

```text
src/main/java/org/aitali/soukaina/examjee
|
+-- config
|   +-- DataInitializer.java
|   +-- OpenApiConfig.java
|
+-- dtos
|   +-- ClientDTO.java
|   +-- CreditDTO.java
|   +-- RemboursementDTO.java
|   +-- LoginRequestDTO.java
|   +-- RegisterRequestDTO.java
|   +-- AuthResponseDTO.java
|
+-- entities
|   +-- Client.java
|   +-- Credit.java
|   +-- CreditPersonnel.java
|   +-- CreditImmobilier.java
|   +-- CreditProfessionnel.java
|   +-- Remboursement.java
|   +-- AppUser.java
|   +-- Role.java
|
+-- exceptions
|   +-- GlobalExceptionHandler.java
|   +-- ResourceNotFoundException.java
|
+-- mappers
|   +-- ClientMapper.java
|   +-- CreditMapper.java
|   +-- RemboursementMapper.java
|
+-- repositories
|   +-- ClientRepository.java
|   +-- CreditRepository.java
|   +-- RemboursementRepository.java
|   +-- AppUserRepository.java
|
+-- security
|   +-- SecurityConfig.java
|   +-- JwtService.java
|   +-- JwtAuthenticationFilter.java
|   +-- AppUserDetailsService.java
|
+-- services
|   +-- CreditBancaireService.java
|   +-- CreditBancaireServiceImpl.java
|   +-- AuthService.java
|
+-- web
    +-- AuthRestController.java
    +-- ClientRestController.java
    +-- CreditRestController.java
    +-- RemboursementRestController.java
```

## Entites JPA

### Client

Un client est defini par :

- `id`
- `nom`
- `email`

Relation :

- Un client peut avoir plusieurs credits.

### Credit

Un credit est defini par :

- `id`
- `dateDemande`
- `statut`
- `dateAcceptation`
- `montant`
- `dureeRemboursement`
- `tauxInteret`

Relation :

- Un credit appartient a un client.
- Un credit peut avoir plusieurs remboursements.

### Types de credits

Le projet gere trois types de credits :

**CreditPersonnel**

- `motif`

**CreditImmobilier**

- `typeBien`

**CreditProfessionnel**

- `motif`
- `raisonSociale`

### Remboursement

Un remboursement est defini par :

- `id`
- `date`
- `montant`
- `type`

Relation :

- Un remboursement appartient a un credit.

## Couche DAO

La couche DAO est basee sur Spring Data JPA.

Repositories crees :

- `ClientRepository`
- `CreditRepository`
- `CreditPersonnelRepository`
- `CreditImmobilierRepository`
- `CreditProfessionnelRepository`
- `RemboursementRepository`
- `AppUserRepository`

Exemples de methodes :

```java
Optional<Client> findByEmail(String email);
List<Credit> findByClientId(Long clientId);
List<Credit> findByStatut(StatutCredit statut);
List<Remboursement> findByCreditId(Long creditId);
```

## Couche Service

La couche service est composee d'une interface et d'une implementation.

Interface :

```text
CreditBancaireService
```

Implementation :

```text
CreditBancaireServiceImpl
```

Methodes principales :

- `saveClient`
- `getClient`
- `getClients`
- `updateClient`
- `deleteClient`
- `saveCredit`
- `getCredit`
- `getCredits`
- `getCreditsByClient`
- `getCreditsByStatut`
- `updateStatutCredit`
- `deleteCredit`
- `saveRemboursement`
- `getRemboursementsByCredit`

## DTOs et Mappers

Les DTOs permettent de separer les donnees exposees par l'API des entites JPA.

DTOs principaux :

- `ClientDTO`
- `CreditDTO`
- `RemboursementDTO`
- `LoginRequestDTO`
- `RegisterRequestDTO`
- `AuthResponseDTO`

Mappers :

- `ClientMapper`
- `CreditMapper`
- `RemboursementMapper`

## REST API

### Authentification

```text
POST /api/auth/login
POST /api/auth/register
```

### Clients

```text
GET    /api/clients
GET    /api/clients/{id}
POST   /api/clients
PUT    /api/clients/{id}
DELETE /api/clients/{id}
GET    /api/clients/{id}/credits
```

### Credits

```text
GET    /api/credits
GET    /api/credits/{id}
POST   /api/credits
PATCH  /api/credits/{id}/statut
DELETE /api/credits/{id}
GET    /api/credits/{id}/remboursements
```

### Remboursements

```text
POST /api/remboursements
```

## Securite JWT

La securite est basee sur :

- Spring Security
- JWT
- Filtre `JwtAuthenticationFilter`
- Service `JwtService`

Apres le login, l'utilisateur recoit un token JWT.

Ce token doit etre envoye dans les requetes protegees avec le header :

```text
Authorization: Bearer <token>
```

## Roles

### ROLE_CLIENT

Le client peut consulter certaines donnees en lecture.

Exemple :

```text
GET /api/clients
GET /api/credits
```

### ROLE_EMPLOYE

L'employe peut consulter, creer et modifier les donnees metier.

Exemple :

```text
POST /api/clients
POST /api/credits
PATCH /api/credits/{id}/statut
POST /api/remboursements
```

### ROLE_ADMIN

L'administrateur possede les autorisations les plus larges.

Exemple :

```text
DELETE /api/clients/{id}
DELETE /api/credits/{id}
```

## Lancement du projet

Ouvrir le projet dans IntelliJ IDEA.

Lancer la classe :

```text
ExamJeeApplication
```

Au demarrage, la classe `DataInitializer` insere automatiquement :

- Des clients
- Des credits
- Des remboursements
- Des utilisateurs de test

Dans la console, on doit voir :

```text
Utilisateurs de test crees : admin/admin123, employe/employe123, client/client123
Clients crees : 2
Credits crees : 3
Remboursements crees : 3
```

## Base de donnees H2

URL :

```text
http://localhost:8080/h2-console
```

Configuration :

```text
Driver Class: org.h2.Driver
JDBC URL: jdbc:h2:mem:credits-db
User Name: sa
Password:
```

Tables principales :

- `CLIENTS`
- `CREDITS`
- `CREDITS_PERSONNELS`
- `CREDITS_IMMOBILIERS`
- `CREDITS_PROFESSIONNELS`
- `REMBOURSEMENTS`
- `APP_USERS`
- `APP_USER_ROLES`

## Swagger UI

Swagger permet de consulter et tester les API REST.

URL :

```text
http://localhost:8080/swagger-ui.html
```

Documentation JSON :

```text
http://localhost:8080/v3/api-docs
```

## Comptes de test

```text
admin   / admin123
employe / employe123
client  / client123
```

## Tests Postman

### 1. Login JWT

Requete :

```text
POST http://localhost:8080/api/auth/login
```

Body JSON :

```json
{
  "username": "client",
  "password": "client123"
}
```

Resultat attendu :

```text
200 OK
```

La reponse contient un token JWT :

```json
{
  "token": "...",
  "type": "Bearer",
  "username": "client",
  "roles": [
    "ROLE_CLIENT"
  ]
}
```

### 2. Acces autorise avec JWT

Requete :

```text
GET http://localhost:8080/api/clients
```

Header :

```text
Authorization: Bearer <token>
```

Resultat attendu :

```text
200 OK
```

### 3. Acces bloque par role

Requete :

```text
POST http://localhost:8080/api/clients
```

Header :

```text
Authorization: Bearer <token_client>
Content-Type: application/json
```

Body JSON :

```json
{
  "nom": "Test Client",
  "email": "test.client@email.com"
}
```

Resultat attendu :

```text
403 Forbidden
```

Ce test montre que l'utilisateur `ROLE_CLIENT` est authentifie, mais qu'il n'a pas le droit de creer un client.

## Validation des donnees

Le projet utilise Jakarta Validation.

Exemples de validations :

- Email valide obligatoire pour un client
- Montant positif pour un credit
- Duree de remboursement positive
- Mot de passe d'au moins 6 caracteres
- Champs obligatoires pour le login

En cas d'erreur, l'API retourne une reponse HTTP `400 Bad Request`.

## Fichier de tests API

Un fichier de requetes HTTP est disponible :

```text
api-tests.http
```

Il permet de tester les endpoints directement depuis IntelliJ.

## Rapport

Le rapport du projet se trouve dans :

```text
rapport/Rapport_Exam_JEE_Backend.docx
```

Il contient :

- L'architecture technique
- Le diagramme de classes
- La description des couches
- Les captures H2
- Les captures Swagger
- Les captures Postman
- La securite JWT
- La conclusion

## Auteur

```text
Nom : AIT ALI Soukaina
Projet : Exam JEE Backend
Annee : 2026
```
