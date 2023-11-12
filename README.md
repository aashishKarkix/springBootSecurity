# Spring Boot Security Example

This repository contains a simple Spring Boot application demonstrating user registration and authentication using a custom authentication controller.

## Build 
./gradlew clean build  -x test

## Request Interceptor
The AuthenticationController includes a request interceptor to handle authorization checks before processing requests. The preHandle method is used to intercept incoming requests and perform checks.

## Authorization Check
The interceptor checks for the presence of the "Authorization" header in the request. If the header is missing, a 401 Unauthorized response is sent back. However, requests to the registration endpoint (/api/v1/auth/register) are allowed without the "Authorization" header.

## Customization
You can customize the interceptor to include additional checks or modify the response as needed for your security requirements.

Feel free to explore and modify the code as needed for your project!

## Authentication Controller

The `AuthenticationController` is responsible for handling user registration and authentication requests. It includes the following endpoints:

### Register User

```

POST /api/v1/auth/register
Registers a new user with the provided user details.

Request
json
{
  "email": "user@example.com",
  "password": "password123",
  "firstName": "xyz",
  "lastName": "xyz"
}

Response json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c",
}

user this bearer token for the below endpoint.

Authenticate User
POST /api/v1/auth/authentication
Authenticates a user with the provided credentials.

Request json
{
  "email": "user@example.com",
  "password": "password123"
}

Response
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c"
}

user this bearer token to access all the endpoints

```
