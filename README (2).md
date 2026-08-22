<div align="center">

# 🔐 JWT Auth API

### Stateless Authentication with Spring Security, JWT & MySQL

*Register • Login • BCrypt • JWT • Role-based Access*

![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.x-6DB33F)
![Spring Security](https://img.shields.io/badge/Spring%20Security-6-green)
![Auth](https://img.shields.io/badge/Auth-JWT%20(JJWT)-orange)
![Database](https://img.shields.io/badge/Database-MySQL-blue)
![Java](https://img.shields.io/badge/Java-21%2B-orange)

</div>

---

## 📌 Overview

**JWT Auth API** is a secure backend service implementing complete **stateless authentication and authorization** with Spring Boot and Spring Security. Users register and log in to receive a signed **JWT (JSON Web Token)**, which they present on every subsequent request — no server-side sessions needed. Passwords are hashed with **BCrypt**, tokens are generated and validated using the **JJWT** library, and endpoints are protected with role-based access rules.

> 🎯 *The capstone of my Java journey — security applied on top of everything learned in:* [jdbc-to-jpa](https://github.com/zasha12/jdbc-to-jpa) → [hibernate-demo](https://github.com/zasha12/hibernate-demo) → [spring-data-jpa-demo](https://github.com/zasha12/spring-data-jpa-demo) → [employee-management-api](https://github.com/zasha12/employee-management-api)

## ✨ Features

- ✅ **User registration** — `POST /api/auth/register` with validation
- ✅ **Login** — `POST /api/auth/login` authenticates against MySQL & returns a signed JWT
- ✅ **BCrypt password hashing** — plain-text passwords are never stored
- ✅ **JWT lifecycle** — generation, parsing, signature & expiry validation (JJWT)
- ✅ **Security filter chain** — custom `JwtAuthFilter` intercepts requests and sets the `SecurityContext`
- ✅ **Role-based authorization** — protect endpoints per role (e.g. `ROLE_USER`, `ROLE_ADMIN`)
- ✅ **Persistent users** — Spring Data JPA + MySQL
- ✅ **Lombok** — clean entities/DTOs without boilerplate
- ✅ **Tests** — JUnit 5 with Spring Security test support

## 🧰 Tech Stack

| Layer | Technology |
|---|---|
| Framework | Spring Boot 4 (Web MVC) |
| Security | Spring Security + JJWT (jjwt-api / impl / jackson) |
| Persistence | Spring Data JPA / Hibernate |
| Database | MySQL |
| Utilities | Lombok |
| Testing | JUnit 5, Spring Security Test |
| Build | Maven |
| Language | Java 21+ |

## 🏗️ Authentication Flow

```
1️⃣ REGISTER
   POST /api/auth/register  ──▶  hash password (BCrypt)  ──▶  save user (MySQL)

2️⃣ LOGIN
   POST /api/auth/login  ──▶  AuthenticationManager verifies credentials
                          ──▶  JwtUtil.generateToken(user)  ──▶  { "token": "eyJhbGci..." }

3️⃣ PROTECTED REQUEST
   GET /api/secure/...  +  Authorization: Bearer <token>
        │
        ▼
   JwtAuthFilter ──▶ validate signature & expiry ──▶ load user ──▶ set SecurityContext ──▶ ✅
```

## 🔌 API Reference

| Method | Endpoint | Access | Description |
|---|---|---|---|
| `POST` | `/api/auth/register` | Public | Create a new account |
| `POST` | `/api/auth/login` | Public | Authenticate, receive JWT |
| `GET` | `/api/secure/profile` | Authenticated | Example protected endpoint |

> 📝 *Adjust the table to match your actual endpoints and roles.*

## 🚀 Getting Started

### Prerequisites
- **JDK 21+** — [download here](https://adoptium.net/)
- **Maven 3.9+** (or the included `mvnw` wrapper)
- **MySQL 8+** running locally

### 1. Create the database

```sql
CREATE DATABASE securitydb;
```

### 2. Configure the connection & JWT secret

In `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/securitydb
spring.datasource.username=YOUR_DB_USER
spring.datasource.password=YOUR_DB_PASSWORD
spring.jpa.hibernate.ddl-auto=update

# JWT — in real deployments load the secret from an environment variable!
app.jwt.secret=${JWT_SECRET}
app.jwt.expiration-ms=86400000
```

### 3. Run

```bash
git clone https://github.com/zasha12/jwt-auth-api.git
cd jwt-auth-api

./mvnw spring-boot:run        # Linux / macOS
mvnw spring-boot:run          # Windows
```

The API starts at **http://localhost:8080**.

### 4. Try it out

```bash
# Register
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"username":"aarav","password":"Secret@123"}'

# Login -> returns the JWT
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"aarav","password":"Secret@123"}'

# Call a protected endpoint with the token
curl http://localhost:8080/api/secure/profile \
  -H "Authorization: Bearer <paste-your-token>"
```

## 📁 Project Structure

```
jwt-auth-api/
├── src/main/java/com/zasha12/security/
│   ├── SecurityApplication.java          # Spring Boot entry point
│   ├── config/
│   │   └── SecurityConfig.java           # SecurityFilterChain, AuthenticationManager
│   ├── security/
│   │   ├── JwtAuthFilter.java            # OncePerRequestFilter validating tokens
│   │   └── JwtUtil.java                  # token generation / parsing / validation
│   ├── controller/
│   │   └── AuthController.java           # register + login endpoints
│   ├── service/
│   │   └── AuthService.java              # business logic
│   ├── model/
│   │   └── User.java                     # @Entity (username, passwordHash, role)
│   ├── repository/
│   │   └── UserRepository.java
│   └── dto/
│       ├── AuthRequest.java
│       └── AuthResponse.java
├── src/main/resources/
│   └── application.properties
├── src/test/java/...                     # security + web tests
└── pom.xml
```

> 📝 *Rename classes/packages to match your actual source tree.*

## 🎓 Security Concepts Practised

- Password storage with **BCrypt** (adaptive hashing, salted)
- **Stateless vs session-based** authentication trade-offs
- Anatomy of a **JWT** — header, payload, signature; HS256 signing
- Custom filter placement in the **Spring Security filter chain**
- Role-based access control with `authorizeHttpRequests`
- Why secrets (JWT key, DB password) belong in **environment variables**, never in git

## 🛣️ Roadmap

- [ ] Refresh tokens & logout (token invalidation)
- [ ] Email verification & password reset
- [ ] Rate limiting on login attempts
- [ ] OAuth2 / social login
- [ ] Docker Compose — app + MySQL
- [ ] CI with GitHub Actions

## 📄 License

This project is licensed under the **MIT License** — see the [LICENSE](LICENSE) file for details.

## 👤 Author

**Kartikeya Dhasmana**
- GitHub: [@zasha12](https://github.com/zasha12)
- LinkedIn: [Kartikeya Dhasmana](https://www.linkedin.com/in/kartikeya-dhasmana-b91389245/)
- Email: kartikeyadhasmana@gmail.com

---

<div align="center">

⭐ *If this project helped you understand JWT auth in Spring, star it!*

</div>
