# Compte Rendu : TP2 - Spring MVC, Spring Security & Spring Data JPA

## 1. Introduction
Ce TP porte sur le développement d'une application web de gestion de produits avec Spring Boot. L'objectif principal est de mettre en pratique une architecture MVC complète intégrant la persistance des données avec Spring Data JPA, la sécurité avec Spring Security, et la couche présentation avec Thymeleaf.

## 2. Structure du Projet
L'organisation des dossiers suit la structure standard d'un projet Spring Boot Maven :

```text
bdcc-enset-spring-mvc
├── src
│   └── main
│       ├── java
│       │   └── net
│       │       └── riyad
│       │           └── bdccensetspringmvc
│       │               ├── entities
│       │               │   └── Product.java
│       │               ├── repository
│       │               │   └── ProductRepository.java
│       │               ├── web
│       │               │   └── ProductController.java
│       │               ├── sec
│       │               │   └── SecurityConfig.java
│       │               └── BdccEnsetSpringMvcApplication.java
│       └── resources
│           ├── templates
│           │   ├── layout1.html
│           │   ├── products.html
│           │   ├── new-product.html
│           │   ├── login.html
│           │   └── notAuthorized.html
│           └── application.properties
└── pom.xml
```

## 3. Architecture du Projet
Le projet respecte une architecture multicouche MVC avec la répartition suivante :

- **entities** : Contient la classe `Product` annotée avec JPA (`@Entity`, `@Id`, `@GeneratedValue`) et les contraintes de validation Bean Validation (`@NotEmpty`, `@Size`, `@Min`). Lombok est utilisé pour réduire le code boilerplate (`@Builder`, `@Getter`, `@Setter`, etc.).
- **repository** : Contient `ProductRepository` qui étend `JpaRepository<Product, Long>`. Spring Data JPA génère automatiquement toutes les opérations CRUD sans aucune implémentation manuelle.
- **web** : Contient `ProductController`, le contrôleur Spring MVC qui gère les requêtes HTTP, interagit avec le repository, et retourne les vues Thymeleaf.
- **sec** : Contient `SecurityConfig`, la configuration Spring Security qui définit les utilisateurs en mémoire, le chiffrement des mots de passe (BCrypt), et les règles d'accès par rôles.
- **templates** : Contient les vues Thymeleaf dont `layout1.html` qui sert de template de base partagé par toutes les pages via le Thymeleaf Layout Dialect.

## 4. Fonctionnalités implémentées

### A. Gestion des Produits (Spring MVC + Spring Data JPA)
L'entité `Product` est persistée dans une base H2 en mémoire. Le contrôleur expose les endpoints suivants :

| Méthode | URL | Description | Rôle requis |
|---|---|---|---|
| GET | `/user/index` | Liste des produits | USER |
| GET | `/admin/newProduct` | Formulaire d'ajout | ADMIN |
| POST | `/admin/saveProduct` | Enregistrer un produit | ADMIN |
| POST | `/admin/delete` | Supprimer un produit | ADMIN |

Au démarrage, un `CommandLineRunner` insère automatiquement 4 produits de test grâce au pattern Builder de Lombok.

### B. Validation des Formulaires
Les champs de l'entité `Product` sont soumis à des contraintes de validation :
- `@NotEmpty` et `@Size(min=3, max=50)` sur le nom.
- `@Min(0)` sur le prix.
- `@Min(1)` sur la quantité.

En cas d'erreur, le formulaire est réaffiché avec les messages d'erreur grâce à `BindingResult` et `th:errors` de Thymeleaf.

### C. Sécurité avec Spring Security
La configuration repose sur trois beans principaux :
- `PasswordEncoder` : utilisation de `BCryptPasswordEncoder` pour le hachage sécurisé des mots de passe.
- `InMemoryUserDetailsManager` : trois utilisateurs définis en mémoire avec leurs rôles respectifs.
- `SecurityFilterChain` : règles d'accès par URL consolidées dans un seul bloc `authorizeHttpRequests` (obligatoire en Spring Security 6).

Les utilisateurs disponibles sont :

| Utilisateur | Mot de passe | Rôles |
|---|---|---|
| user1 | 1234 | ROLE_USER |
| user2 | 1234 | ROLE_USER |
| admin | 1234 | ROLE_USER, ROLE_ADMIN |

### D. Layout Thymeleaf avec Thymeleaf Layout Dialect
Approche permettant la réutilisation du template commun (navbar, Bootstrap) sur toutes les pages :
- `layout1.html` définit la structure partagée avec un fragment `layout:fragment="content1"`.
- Chaque page enfant déclare `layout:decorate="layout1"` sur sa balise `<html>` et remplit le fragment `layout:fragment="content1"`.
- L'affichage conditionnel des boutons selon le rôle utilise `sec:authorize="hasRole('ADMIN')"` du dialect Thymeleaf Security.

## 5. Conclusion
Ce TP démontre comment Spring Boot permet de construire rapidement une application web sécurisée en combinant plusieurs frameworks complémentaires. Spring Data JPA élimine le code d'accès aux données répétitif, Spring Security offre un contrôle d'accès robuste avec un minimum de configuration, et Thymeleaf Layout Dialect favorise la réutilisation des templates. L'ensemble illustre les bonnes pratiques d'une architecture MVC moderne avec séparation claire des responsabilités.





# TP2 — Spring MVC, Spring Security & Spring Data JPA

> Application web de gestion de produits développée avec Spring Boot 3.4.3

---

## 🛠️ Technologies utilisées

| Technologie | Version | Rôle |
|---|---|---|
| Spring Boot | 3.4.3 | Framework principal |
| Spring MVC | 6.2.3 | Couche contrôleur & routage HTTP |
| Spring Security | 6.4.3 | Authentification & autorisation par rôles |
| Spring Data JPA | 3.4.3 | Persistance des données (ORM Hibernate) |
| Thymeleaf | 3.1.3 | Moteur de templates HTML |
| Thymeleaf Layout Dialect | 3.3.0 | Héritage de layouts |
| H2 Database | 2.3.232 | Base de données en mémoire |
| Bootstrap | 5.3.3 | Interface utilisateur |
| Lombok | 1.18.36 | Réduction du code boilerplate |
| Java | 21 | Langage |

---

## 📁 Structure du projet

```
bdcc-enset-spring-mvc/
├── src/main/java/net/riyad/bdccensetspringmvc/
│   ├── entities/
│   │   └── Product.java              # Entité JPA
│   ├── repository/
│   │   └── ProductRepository.java    # Spring Data JPA Repository
│   ├── web/
│   │   └── ProductController.java    # Contrôleur MVC
│   ├── sec/
│   │   └── SecurityConfig.java       # Configuration Spring Security
│   └── BdccEnsetSpringMvcApplication.java
├── src/main/resources/
│   ├── templates/
│   │   ├── layout1.html              # Template de base (navbar)
│   │   ├── products.html             # Liste des produits
│   │   ├── new-product.html          # Formulaire d'ajout
│   │   ├── login.html                # Page de connexion
│   │   └── notAuthorized.html        # Page 403
│   └── application.properties
└── pom.xml
```

---

## ⚙️ Configuration

```properties
spring.datasource.url=jdbc:h2:mem:products-db
spring.jpa.hibernate.ddl-auto=update
server.port=8094
spring.h2.console.enabled=true
```

---

## 🔐 Utilisateurs (InMemory)

| Utilisateur | Mot de passe | Rôles |
|---|---|---|
| user1 | 1234 | ROLE_USER |
| user2 | 1234 | ROLE_USER |
| admin | 1234 | ROLE_USER, ROLE_ADMIN |

---

## 🗺️ Endpoints

| Méthode | URL | Description | Rôle requis |
|---|---|---|---|
| GET | `/` | Redirige vers `/user/index` | — |
| GET | `/user/index` | Liste des produits | USER |
| GET | `/admin/newProduct` | Formulaire nouveau produit | ADMIN |
| POST | `/admin/saveProduct` | Enregistrer un produit | ADMIN |
| POST | `/admin/delete` | Supprimer un produit | ADMIN |
| GET | `/login` | Page de connexion | Public |
| GET | `/logout` | Déconnexion | Authentifié |
| GET | `/notAuthorized` | Page accès refusé | — |

---

## 🚀 Lancer l'application

```bash
# Cloner le projet
git clone https://github.com/<votre-username>/bdcc-enset-spring-mvc.git
cd bdcc-enset-spring-mvc

# Lancer avec Maven
./mvnw spring-boot:run
```

L'application sera accessible sur **http://localhost:8094**

La console H2 est disponible sur **http://localhost:8094/h2-console**
- JDBC URL : `jdbc:h2:mem:products-db`
- Username : `sa`
- Password : *(vide)*

---

## 📌 Fonctionnalités

- ✅ Affichage de la liste des produits
- ✅ Ajout d'un nouveau produit avec validation des champs
- ✅ Suppression d'un produit
- ✅ Authentification avec page de login personnalisée
- ✅ Contrôle d'accès par rôles (USER / ADMIN)
- ✅ Affichage conditionnel des boutons selon le rôle (`sec:authorize`)
- ✅ Layout partagé via Thymeleaf Layout Dialect
- ✅ Initialisation automatique de données au démarrage
