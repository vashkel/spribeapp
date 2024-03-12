# Exchange Rate Service

## Overview
Spring Boot application that provides an Exchange Rate Service. 
The service allows users to retrieve a list of currencies, get exchange rates for specific currencies, and add new currencies for getting exchange rates. 
Exchange rates are fetched from an external API and logged in a PostgreSQL database.

## Prerequisites
- Java 17
- PostgreSQL
- Docker

## Configuration

1. Set up the PostgreSQL Database:
    - **Option 1: Install PostgreSQL Locally:**
        - Install PostgreSQL on your machine and create a database named `testdb`.
    - **Option 2: Run PostgreSQL in Docker:**
        - Use the provided `docker-compose.yml` file to start PostgreSQL in a Docker container.

2. Configure API URL:
    - Open `src/main/resources/application.properties` and configure the external API URL.

## Running the Application

- Build and run the application using Gradle. `gradlew build`.
- Build the Docker image running `docker build -t spribe .` command in the root directory of the project
from the Terminal.
- Run the application using `docker-compose up` command in the root directory of the project
  from the Terminal.


- Access the application at [http://localhost:8080](http://localhost:8080).

## Accessing Endpoints

- **Get List of Currencies:**
    - Endpoint: `/api/currencies`
    - Method: GET

- **Get Exchange Rate for a Currency:**
    - Endpoint: `/api/currencies/{code}`
    - Method: GET

- **Add New Currency for Getting Exchange Rates:**
    - Endpoint: `/api/currencies/{code}`
    - Method: POST

## Scheduled Task

- The application includes a scheduled task that fetches exchange rates every hour and logs them in the database.
