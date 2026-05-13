# Random Data API

A publicly accessible RESTful API built with Java and Spring Boot that generates randomized test data for People, Companies, and Grades. Useful for testing, prototyping, and populating front-end applications with realistic data.

## Live API

[https://random-data-api-iuit.onrender.com/](https://random-data-api-iuit.onrender.com/)

## What It Does

- Generates randomized **People** data (names, emails, addresses, etc.)
- Generates randomized **Company** data (company names, industries, contact info, etc.)
- Generates randomized **Grades** data (student grades, scores)
- Persists generated data to MongoDB for retrieval
- Supports both synchronous REST endpoints (Spring WebMVC) and non-blocking async endpoints (Spring WebFlux)

## Tech Stack

| Technology | Purpose |
|---|---|
| Java 17 | Core programming language |
| Spring Boot | Application framework |
| Spring WebMVC | Synchronous REST endpoints |
| Spring WebFlux | Non-blocking asynchronous endpoints |
| MongoDB | Data persistence |
| Dotenv-java | Secure environment variable management |
| Docker | Containerization with multi-stage build |
| Render | Cloud deployment |
| Maven | Dependency management and build tool |

## Endpoints

### People
```
GET    /api/people        - Get 10 people
GET    /api/people/{id}   - Get a person by ID
```

### Companies
```
GET    /api/companies        - Get 10 companies
GET    /api/companies/{id}   - Get a company by ID
```

### Grades
```
GET    /api/grades        - Get 10 grades
GET    /api/grades/{id}   - Get a grade by ID
```

## Setup

### Prerequisites

- Java 17+
- Maven
- MongoDB Atlas account (or local MongoDB instance)
- Docker (optional, for containerized runs)

### 1. Clone the Repository

```bash
git clone https://github.com/TresorBitangimana/Random-Data-Api.git
cd Random-Data-Api
```

### 2. Create a `.env` File

In the root of the project, create a `.env` file with your MongoDB connection string:

```env
CONNECTION_STRING=your_mongodb_connection_string
```

> You can find your connection string in MongoDB Atlas under **Database → Connect → Drivers**.

### 3. Add `.env` to `.gitignore`

Make sure your credentials are never pushed to GitHub:

```bash
echo ".env" >> .gitignore
```

### 4. Build and Run

```bash
mvn clean install
mvn spring-boot:run
```

The API will be available at `http://localhost:8080`

## Running with Docker

### Build and run with Docker Compose

```bash
docker compose up --build
```

### Or build and run manually

```bash
# Build the image
docker build -t random-data-api .

# Run the container
docker run -p 8080:8080 --env-file .env random-data-api
```

## Dependencies

- [Spring Boot](https://spring.io/projects/spring-boot)
- [Spring Data MongoDB](https://spring.io/projects/spring-data-mongodb)
- [Spring WebFlux](https://docs.spring.io/spring-framework/reference/web/webflux.html)
- [Dotenv-java](https://github.com/cdimascio/dotenv-java)