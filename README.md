# IronPulse Backend

Spring Boot 3 REST API for IronPulse. Requires Java 17+, Maven, and MySQL.

## Configure

Create the `gym_management_db` database using `database-setup.sql`. Configure the database password in the `DB_PASSWORD` environment variable. The datasource username defaults to `root`; update `spring.datasource.username` in `src/main/resources/application.properties` if needed.

## Run

From this directory, run `mvn spring-boot:run`. The API listens on port 8080.
