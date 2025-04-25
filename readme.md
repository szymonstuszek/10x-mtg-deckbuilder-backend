# 10x MTG Deckbuilder Backend

This project is the backend service for the 10x MTG Deckbuilder application, built using Spring Boot. It uses an in-memory H2 database for local development and PostgreSQL for production.

## Profiles

The project uses Spring Boot profiles to differentiate between local development and production environments:

- **Local Profile**: Uses an H2 in-memory database.
  - Configuration file: `application-local.properties`
  - Data auto-initialization using `data.sql` is enabled.

- **Production Profile**: Uses a PostgreSQL database.
  - Configuration file: `application-prod.properties`
  - Data initialization using `data.sql` is disabled (using `spring.sql.init.mode=never`).
  - The PostgreSQL datasource URL, username, and password are provided via environment variables.

## Running the Application

### Locally

To run the application locally using the H2 database, use:

```
mvn spring-boot:run -Dspring-boot.run.profiles=local
```

### In Production

To run the application with the production profile (PostgreSQL), use:

```
mvn spring-boot:run -Dspring-boot.run.profiles=prod
```

Make sure the following environment variables are set in production:

- `PROD_DATASOURCE_URL`
- `PROD_USERNAME`
- `PROD_PASSWORD`

## License

[MIT License](LICENSE) 