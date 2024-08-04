# Store Management API

## Overview
    The Store Management API is a Spring Boot application designed for managing product information in a store. 
    This API allows for CRUD operations on products, including fetching, creating, updating, and deleting products. 
    It also includes role-based access control to secure certain endpoints.

## Features
    - Product Management: Create, read, update, and delete products.
    - Price Update: Update the price of a product.
    - Role-Based Access Control: Secure endpoints based on user roles (USER and ADMIN).
    - Pagination and Sorting: Fetch paginated lists of products.

## Endpoints
    - **Get Product by ID
    Endpoint: GET /api/v1/products/{product-id}
    Roles: USER, ADMIN
    - **Get All Products
    Endpoint: GET /api/v1/products
    Roles: USER, ADMIN
    Parameters:  page, size
    - **Create Product
    Endpoint: POST /api/v1/products
    Roles: ADMIN
    - **Update Product
    Endpoint: PUT /api/v1/products/{product-id}
    Roles: ADMIN
    - **Update Product Price
    Endpoint: PUT /api/v1/products/{product-id}/price
    Roles: ADMIN
    - **Delete Product
    Endpoint: DELETE /api/v1/products/{product-id}
    Roles: ADMIN

## Security
    - Basic Authentication is used.
    - Roles:
        USER: Can read and list products.
        ADMIN: Can create, update, and delete products, update product prices.
    - Example credentials:
        Username: admin Password: admin
        Username: user Password: password

## Database Configuration
This project uses an H2 in-memory database for testing convenience. 
There is some initial data loaded for testing purposes (2 product categories and 3 products).
The initial data is loaded from src/main/resources/data.sql.

## Setup
    - Prerequisites
        Java 9 or higher
        Maven
    - Running the Application
        - Clone the repository:
        https://github.com/claudiamacea/store-management.git
        cd store-management
        - Build the project:
            ./mvnw clean install
        - Run the application:
            ./mvnw spring-boot:run
## The application will start on http://localhost:8080 by default.

## Testing
    - Unit tests for the ProductController are located in src/test/java/com/claudiamacea/store_management/product/controller/ProductControllerTest.java. You can run the tests using:
        ./mvnw test