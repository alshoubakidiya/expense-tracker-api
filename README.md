# Expense Tracker API

A secure, multi user REST API for tracking personal expenses, built from scratch in Java with Spring Boot. Every user authenticates with a JWT-secured account and can only ever access their own expenses.

This was built as a solution to the [roadmap.sh Expense Tracker API](https://roadmap.sh/projects/expense-tracker-api) project.

## Features

- **User authentication** - signup and login with BCrypt-hashed passwords (passwords are never stored or returned in plain text).
- **JWT-based authorization** - every request to a protected endpoint is verified using a custom Spring Security filter; users can only ever see or edit their own expenses.
- **Full expense CRUD** - create, read (all or by ID), update, and delete expenses.
- **Partial updates** - the update endpoint only changes the fields you send, leaving the rest untouched.
- **Date-range filtering** - retrieve expenses from the past week, past month, past 3 months, or a custom date range.
- **Fixed expense categories** - via a Java enum (`GROCERIES`, `LEISURE`, `ELECTRONICS`, `UTILITIES`, `CLOTHING`, `HEALTH`, `OTHERS`), ready to power a dropdown option list on a possible future frontend.
- **Centralized error handling** - a global exception handler returns cleanly coded HTTP responses (404, 401, etc.) instead of raw stack traces.

## Tech Stack

| Category | Technology |
|---|---|
| Language | Java 21 |
| Framework | Spring Boot |
| Security | Spring Security, JWT (jjwt) |
| Persistence | Spring Data JPA (Hibernate) |
| Database | PostgreSQL |
| Build Tool | Maven |
| Testing Tool | Postman |

## Architecture

The project follows a standard layered architecture to keep concerns cleanly separated:

```
Controller  ->  Service  ->  Repository  ->  Database
   (HTTP)      (business logic)  (data access)
```

- **Controllers** handle HTTP requests/responses only, no business logic lives here.
- **Services** contain the actual logic (password hashing, ownership checks, date range calculations) and coordinate between repositories.
- **Repositories** (Spring Data JPA interfaces) handle all database access with zero handwritten SQL.
- **DTOs** (`*Request` / `*Response` classes) decouple the API's public shape from the internal database entities, this ensures sensitive fields, like a user's hashed password, can never accidentally leak into an API response.
- **A custom `JwtAuthFilter`** intercepts every request, validates the JWT, and notifies Spring Security's context so downstream code always knows exactly who's making the request.
- **A `GlobalExceptionHandler`** catches custom exceptions across the whole app and converts them into proper HTTP status codes.

## API Endpoints

| Method | Endpoint | Auth Required | Description |
|---|---|---|---|
| POST | `/api/auth/signup` | No | Register a new user |
| POST | `/api/auth/login` | No | Log in and receive a JWT |
| POST | `/api/expenses` | Yes | Create a new expense |
| GET | `/api/expenses` | Yes | Get all expenses for the logged-in user |
| GET | `/api/expenses/{id}` | Yes | Get a single expense by ID |
| PUT | `/api/expenses/{id}` | Yes | Update an expense (partial updates supported) |
| DELETE | `/api/expenses/{id}` | Yes | Delete an expense |
| GET | `/api/expenses/filter?range=week` | Yes | Get expenses from the past week |
| GET | `/api/expenses/filter?range=month` | Yes | Get expenses from the past month |
| GET | `/api/expenses/filter?range=3months` | Yes | Get expenses from the past 3 months |
| GET | `/api/expenses/filter?startDate=YYYY-MM-DD&endDate=YYYY-MM-DD` | Yes | Get expenses within a custom date range |

## Getting Started

### Prerequisites

- [IntelliJ IDEA](https://www.jetbrains.com/idea/) (or any Java IDE)
- JDK 21
- Maven
- [PostgreSQL](https://www.postgresql.org/download/) installed and running locally
- [Postman](https://www.postman.com/downloads/) (or similar) for testing endpoints

### Setup

1. **Clone the repository**
   ```bash
   git clone https://github.com/alshoubakidiya/expense-tracker-api.git
   ```

2. **Create a PostgreSQL database** named `expense_tracker` (preferably using pgAdmin).

3. **Set the required environment variables** - this project reads secrets from the environment rather than hardcoding them, so you'll need to set:
   - `DB_PASSWORD` - your PostgreSQL password
   - `JWT_SECRET` - any long, random string used to sign JWTs

   In IntelliJ: **Run -> Edit Configurations -> Environment Variables**, and add both.

4. **Run the application** - open the project in IntelliJ and run `ExpenseTrackerApiApplication`. Spring Boot will automatically create the required tables on startup.

5. **Test the API** using Postman. See the endpoints table above for the full list of routes. Example signup request:
   ```json
   POST /api/auth/signup
   {
     "username": "your_username",
     "password": "your_password"
   }
   ```

   After logging in, copy the returned `token` and attach it as a **Bearer Token** in the Authorization tab of any request to a protected endpoint.

   Example expense creation:
   ```json
   POST /api/expenses
   {
     "description": "Groceries",
     "amount": 45.99,
     "category": "GROCERIES",
     "date": "2026-07-18"
   }
   ```

## What I Learned

This was my first Spring Boot project that I built to focus on applying my theoretical knowledge on creating an app and learning how to navigate planning a bigger project. A few highlights:

- **Hashing is different than encryption** - I learned that authentication should never rely on decrypting a stored password, only comparing hashes.
- **How JWT authentication actually works under the hood** - building the custom `JwtAuthFilter` from scratch, rather than relying on a pre-built solution, made me understand the underlying authentication layers (token -> validation -> `SecurityContextHolder`).
- **The cost of skipping DTOs** - an early version of this API accidentally returned the users hashed password in API responses by returning info directly which is a security hazard. I learned to create classes to control information moving between layers.
- **Layered architecture in practice** - separating Controller/Service/Repository layers made every bug in this project easier to isolate and fix, since each layer had exactly one job. It also taught me good practice when creating apps in the future.

## Possible Future Improvements I will work on

- A frontend (in progress) to interact with the API visually instead of through Postman.
- Automated tests (JUnit/Mockito) to replace manual Postman verification.
- Dockerized deployment via Docker Compose.
