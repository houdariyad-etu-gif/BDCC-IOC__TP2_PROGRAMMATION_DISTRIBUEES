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
