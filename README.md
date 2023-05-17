# E-Commerce API
This is a homework project given in the Patika.dev - Akbank Java Spring Boot Bootcamp.

## Description
This is a REST API for an e-commerce application developed using Spring Boot Template (github.com/tzesh/SpringBootTemplate). It is built with Spring Boot and PostgreSQL.
- Includes a Swagger UI for testing the API endpoints (http://localhost:8080/api/v1/swagger-ui/index.html)
- Uses JWT for authentication and authorization
- Uses refresh tokens for refreshing the access token
- Uses Spring Security for securing the API endpoints
- Uses MapStruct for mapping DTOs to entities and vice versa
- Uses Lombok for generating boilerplate code
- Uses Spring Data JPA for accessing the database
- Uses ControllerAdvice for handling exceptions

## Project Definition
Design and implement a REST API for an e-commerce application. The application should have the following features:
- The project is a service that provides REST API endpoints for an e-commerce application.
- Application should be able to handle users, products and comments.
- Users cannot register with same email address, telephone number or username.
- User type can be customer or company.
- Exception handling should be done with ControllerAdvice.
- All entities should have audit fields (createdDate, createdBy, lastModifiedDate, lastModifiedBy).

## How to run
1. Clone this repository
2. Create a PostgreSQL database
3. Change the database configuration in `application.properties`
4. Run the application with `mvn spring-boot:run`
5. Open http://localhost:8080/api/v1/swagger-ui/index.html in your browser
6. Register a new user
7. Login with the registered user
8. Copy the JWT token from the response
9. Click the `Authorize` button in the Swagger UI
10. Paste the JWT token in the `Value` field without the `Bearer` prefix
11. Click the `Authorize` button
12. Now you can test the API endpoints
13. To test the refresh token endpoint, click the `Authorize` button again and paste the refresh token in the `Value` field without the `Bearer` prefix

## Screenshots
![Maven Run](https://imgur.com/gLrhDLb.png)
![Swagger UI](https://imgur.com/qS6feD2.png)
