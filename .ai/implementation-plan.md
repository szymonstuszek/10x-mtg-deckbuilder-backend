# API Endpoint Implementation Plan: 10x-mtg-deck-builder REST API

This document outlines the detailed implementation plan for the REST API endpoints. The plan covers endpoint functionality, request/response details, data flow, security considerations, error handling, performance optimizations, and step-by-step implementation guidance.

---

## 1. Endpoint Overview

The API provides endpoints for various operations including retrieving card data from an external Magic: The Gathering API, managing user decks, and retrieving deck statistics. The main endpoints include:

- **Cards Endpoints**: To list cards with filtering, sorting, and pagination (sourced from magicthegathering.io).
- **User Endpoints**: To retrieve the authenticated user's profile.
- **Deck Endpoints**: To perform CRUD operations on decks (create, read, update, delete), add/update/remove cards in a deck, fetch deck statistics, and retrieve a random non-land card.

The API maps to underlying database tables (`Users`, `Cards`, `Decks`, `Deck_Cards`) and uses DTO Models defined in the type definitions.

---

## 2. Request Details

### HTTP Methods & URL Patterns

- **GET /users/me**: Retrieves the current authenticated user profile.

- **GET /cards**: Lists Magic: The Gathering cards. Query parameters include:
  - **Required**: None.
  - **Optional**: `set`, `page` (default 1), `pageSize` (default 50), `sort`, `order`, `rarity`, `mana_cost`, `color`, `type`, `name` (for card name search).

- **GET /decks**: Lists decks for the authenticated user.

- **POST /decks**: Creates a new deck. The request body uses the **CreateDeckDto** structure:
  - **Required Fields**: `deckName`, `deckFormat`, `deckDescription`.
  - **Optional**: `cards` (a list of card objects with quantities).

- **GET /decks/{deckId}**: Retrieves detailed deck information including cards and statistics.

- **PUT /decks/{deckId}**: Updates deck properties and its card list. The request body follows the **UpdateDeckDto** structure.

- **DELETE /decks/{deckId}**: Deletes a deck.

- **GET /decks/{deckId}/stats**: Retrieves deck statistics.

- **GET /decks/{deckId}/random**: Retrieves a random non-land card from the deck.

---

## 3. Used Types

The following DTO and Command Models (defined in @types.md) will be used:

- **UserDto**, **UpdateUserCommand**
- **CardDto**, **CardCommand**, **PaginationDto**, **CardListResponseDto**
- **DeckDto**, **DeckDetailsDto**, **DeckStatisticsDto**, **CreateDeckDto**, **UpdateDeckDto**
- **RandomCardResponseDto**

These types ensure strict separation between data read from the database/external API and data input from clients.

---

## 4. Response Details

### Expected Response Structure

- **200 OK** for successful GET, PUT, DELETE operations.
- **201 Created** when a resource (e.g., a new deck) is created.

#### Example Response Structures:

- **GET /users/me**:
  ```json
  {
    "sub": "user-sub-identifier",
    "email": "user@example.com"
  }
  ```

- **GET /cards**:
  ```json
  {
    "cards": [ { "id": 1, "apiId": "external-id", "name": "Card Name", ... } ],
    "pagination": { "page": 1, "pageSize": 50, "totalPages": 10, "totalRecords": 500 }
  }
  ```

- **POST /decks**:
  Response includes the created deck details and status **201 Created**.

Errors:
- **400 Bad Request** for input validation errors.
- **401 Unauthorized** for invalid JWT or access without credentials.
- **404 Not Found** when the requested resource does not exist.
- **500 Internal Server Error** for unexpected issues.

---

## 5. Data Flow

1. **Controller Layer**: Receives REST requests, validates input data, maps request bodies to Command Models/DTOs.
2. **Service Layer**: Contains business logic for processing requests. It converts Command Models to entity operations and applies domain validations (e.g., deck copy limits, minimum deck size).
3. **Repository Layer**: Interacts with the PostgreSQL database using Spring Data JPA to manage CRUD operations on entities (Users, Decks, Deck_Cards, etc.).
4. **External API Integration**: The GET /cards endpoint calls the external Magic: The Gathering API (https://docs.magicthegathering.io) to fetch card data.
5. **Mapping**: Use mapping tools like MapStruct or manual conversion between entities, DTOs, and Command Models.

---

## 6. Security Considerations

- **Authentication**: All endpoints (except the public card listings if applicable) require a valid JWT provided via the `Authorization` header. Authentication is handled by Amazon Cognito.
- **Authorization**: Use row-level security via user_sub to ensure that users only access their own decks.
- **Input Validation**: Use Spring Boot validation framework (e.g., `@Valid` and validation annotations) to check incoming request data.
- **Data Exposure**: Separate DTOs from domain models to avoid exposing internal fields.
- **Rate Limiting**: Utilize Spring provided or external solutions to mitigate potential DDoS attacks.

---

## 7. Error Handling

### Potential Error Scenarios & Corresponding Status Codes

- **400 Bad Request**: Input validation failures, malformed JSON, violation of business rules (e.g., more than 4 copies of a card).
- **401 Unauthorized**: Missing or invalid JWT, unauthorized access attempts.
- **404 Not Found**: Resource not found (e.g., non-existent deck or card).
- **500 Internal Server Error**: Unhandled exceptions or database connectivity issues.

### Logging & Monitoring

- Log errors
- Ensure sensitive error details are not exposed in API responses.

---

## 8. Performance Considerations

- **Pagination & Filtering**: Implement pagination for list endpoints to reduce data payloads.
- **Caching**: Cache frequently accessed card data from the external API to reduce latency.
- **Database Indexes**: Rely on defined indexes (e.g., primary keys, composite keys in Deck_Cards) to optimize query performance.
- **Asynchronous Processing**: For any long-running tasks, consider asynchronous processing or background jobs.

---

## 9. Implementation Steps

- consider that for the first phase of the implementation we can use Mocks.

1. **Define Entity Models**:
   - Create JPA entities for Users, Decks, and Deck_Cards.
   - Define relationships, constraints, and indexes.
   - Add JPA annotations for mapping to database tables.

2. **Create DTOs and Command Models**:
   - Define Data Transfer Objects for API responses.
   - Create Command Models for handling incoming requests.
   - Add validation annotations for request validation.

3. **Implement DTO/Command Model Converters**:
   - Create mappers (using MapStruct or manual converters) to map between Command Models, DTOs, and entity objects.

4. **Set Up Repository Layer**:
   - Use Spring Data JPA repositories to manage persistence for Users, Decks, and Deck_Cards.
   - Define custom query methods if needed.

5. **Create Service Layer Logic**:
   - Implement services for handling business logic (e.g., deck creation, card validation, deck statistics computation).
   - Apply validations such as the deck minimum size and card copy limits.

6. **Integrate with External Card API**:
   - Implement a client (e.g., using RestTemplate or WebClient) to fetch card data from the Magic: The Gathering API.
   - Add caching mechanism for frequently accessed card data.

7. **Define Endpoints in Controllers**:
   - Create REST controllers for Users, Cards, and Decks.
   - Annotate with proper Spring MVC annotations (e.g., `@RestController`, `@RequestMapping`).
   - Implement endpoint methods using the service layer.

8. **Input Validation & Exception Handling**:
   - Use Spring's validation framework with `@Valid` and exception handlers (e.g., `@ControllerAdvice`) to catch and format errors.
   - Create custom exceptions and error responses.

9. **Testing & Documentation**:
    - Write unit tests for entities, DTOs, and mappers.
    - Create integration tests for repositories and services.
    - Write end-to-end tests for controllers.
    - Document endpoints using Swagger/OpenAPI.

10. **Logging & Monitoring**:
    - Setup logging (e.g., using SLF4J with Logback) and, if needed, create a dedicated error logging mechanism.
    - Add performance monitoring points.

11. **Configure Application Properties**:
    - Create application.properties/yml with database configurations.
    - Set up external API configurations.
    - Configure server properties.

12. **Configure Authentication**:
    - Integrate Amazon Cognito with Spring Security.
    - Establish JWT filter to validate tokens and populate user context.
    - Create application.properties/yml with database and security configurations:
      ```properties
      # Database Configuration
      spring.datasource.url=jdbc:postgresql://your-rds-instance.region.rds.amazonaws.com:5432/mtg_deck_builder
      spring.datasource.username=your_username
      spring.datasource.password=your_password
      spring.datasource.driver-class-name=org.postgresql.Driver

      # JPA/Hibernate Configuration
      spring.jpa.hibernate.ddl-auto=validate
      spring.jpa.show-sql=true
      spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

      # AWS Cognito Configuration
      aws.cognito.region=your_region
      aws.cognito.userPoolId=your_user_pool_id
      aws.cognito.clientId=your_client_id

      # External API Configuration
      mtg.api.base-url=https://api.magicthegathering.io/v1
      mtg.api.timeout=5000

      # Server Configuration
      server.port=8080
      ```

---

*End of API Endpoint Implementation Plan* 