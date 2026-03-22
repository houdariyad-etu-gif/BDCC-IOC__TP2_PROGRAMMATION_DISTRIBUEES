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





