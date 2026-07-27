# Expense Tracker API

A multi-user REST API for tracking personal expenses built in Java with Spring Boot. Users authenticate with a JWT-secured account and can only access their own data.

Built as a solution to the [roadmap.sh Expense Tracker API](https://roadmap.sh/projects/expense-tracker-api) project.

## Tech Stack
Java 21, Spring Boot, Spring Security, JWT, Spring Data JPA, PostgreSQL, Maven

## Architecture
Follows a standard layered architecture: Controller, Service, Repository, Database. DTOs decouple the API shape from the database entities so sensitive fields like hashed passwords never leak into responses. A custom JwtAuthFilter validates tokens on every request, and a GlobalExceptionHandler returns clean HTTP responses instead of raw stack traces.

## Endpoints
| Method | Endpoint | Auth | Description |
|---|---|---|---|
| POST | `/api/auth/signup` | No | Register |
| POST | `/api/auth/login` | No | Login, returns JWT |
| POST | `/api/expenses` | Yes | Create expense |
| GET | `/api/expenses` | Yes | Get all expenses |
| GET | `/api/expenses/{id}` | Yes | Get one expense |
| PUT | `/api/expenses/{id}` | Yes | Update expense |
| DELETE | `/api/expenses/{id}` | Yes | Delete expense |
| GET | `/api/expenses/filter?range=week/month/3months` | Yes | Filter by range |
| GET | `/api/expenses/filter?startDate=YYYY-MM-DD&endDate=YYYY-MM-DD` | Yes | Filter by custom dates |

## Setup
Requires JDK 21, Maven, and PostgreSQL. Create a database named expense_tracker, then set two environment variables: DB_PASSWORD and JWT_SECRET. Run the app, Spring Boot creates the tables automatically. Test with Postman using Bearer Token auth on protected endpoints.

## What I Learned
This was my first Spring Boot project. Building the JwtAuthFilter from scratch taught me how token authentication actually works under the hood. An early version accidentally returned hashed passwords in responses which taught me the practical importance of DTOs. Separating concerns across layers made every bug easier to isolate. Separating Controller/Service/Repository layers made every bug in this project easier to isolate and fix, since each layer had exactly one job. It also taught me good practice when creating apps in the future.
