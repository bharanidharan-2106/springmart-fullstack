# SpringMart – Microservices-Based E-Commerce Platform

## Overview

SpringMart is an enterprise-grade e-commerce platform built using Angular and Spring Boot Microservices architecture. The application is designed to demonstrate modern full-stack development practices, including microservices, service discovery, API gateway patterns, secure authentication, inter-service communication, resilience patterns, and distributed data management.

The platform enables customers to browse products, search catalogs, manage shopping carts, place orders, submit reviews, and manage profiles. Merchants can manage products and inventory, while administrators can oversee users, products, vendors, and platform analytics.

---

## Key Features

### Customer Features

* User Registration and Authentication
* Product Browsing
* Product Search and Filtering
* Shopping Cart Management
* Order Placement and Tracking
* Product Reviews and Ratings
* Profile and Address Management

### Merchant Features

* Product Management
* Inventory Management
* Order Processing
* Sales Reporting

### Administrator Features

* User Management
* Vendor Approval
* Product Moderation
* Platform Monitoring
* Analytics Dashboard

---

## Technology Stack

### Frontend

| Layer             | Technology         |
| ----------------- | ------------------ |
| Framework         | Angular            |
| Styling           | Bootstrap 5        |
| UI Components     | Angular Material   |
| Authentication    | JWT                |
| API Communication | Angular HttpClient |

### Backend

| Layer                       | Technology           |
| --------------------------- | -------------------- |
| Framework                   | Spring Boot          |
| Security                    | Spring Security      |
| API Gateway                 | Spring Cloud Gateway |
| Service Discovery           | Netflix Eureka       |
| Inter-Service Communication | OpenFeign            |
| ORM                         | Hibernate / JPA      |
| Validation                  | Jakarta Validation   |
| Resilience                  | Resilience4j         |

### Databases

| Service             | Database   |
| ------------------- | ---------- |
| Auth & User Service | PostgreSQL |
| Product Service     | MongoDB    |
| Cart Service        | PostgreSQL |
| Order Service       | PostgreSQL |
| Inventory Service   | PostgreSQL |
| Review Service      | MongoDB    |
| Analytics Service   | PostgreSQL |

### Additional Technologies

* JWT Authentication
* Spring Cloud Load Balancer
* Rate Limiting
* Circuit Breaker Pattern
* Centralized Logging
* Distributed Monitoring
* Elasticsearch (Search Service)

---

## System Architecture

```text
Angular Frontend
        |
        |
   API Gateway
        |
---------------------------------------------------------
|      |       |       |       |        |        |      |
Auth  User  Product  Cart   Order  Inventory  Review Analytics
        |
    Eureka Server
```

---

## Microservices Overview

### Eureka Server

#### Responsibilities

* Service Registration
* Service Discovery
* Health Monitoring

#### Database

None

---

### API Gateway

#### Responsibilities

* Request Routing
* JWT Validation
* Authorization
* Global Filters
* Rate Limiting
* Request Logging
* Circuit Breaker
* Load Balancing

#### Technologies

* Spring Cloud Gateway
* Spring Security
* Resilience4j

#### Database

None

---

### Auth and User Service

#### Responsibilities

* User Registration
* User Login
* JWT Token Generation
* Refresh Token Management
* Password Reset
* Profile Management
* Address Management

#### Database

PostgreSQL

#### Tables

```sql
users
roles
permissions
refresh_tokens
user_profiles
addresses
```

---

### Product Service

#### Responsibilities

* Product Catalog Management
* Category Management
* Brand Management
* Product Information Management

#### Database

MongoDB

#### Collections

```text
products
categories
brands
```

---

### Search Service

#### Responsibilities

* Product Search
* Category Search
* Brand Search
* Product Filtering

#### Database

Elasticsearch

#### Features

```text
Search by Name
Search by Category
Search by Brand
Price Filtering
Rating Filtering
```

---

### Cart Service

#### Responsibilities

* Add Product to Cart
* Remove Product from Cart
* Update Quantity
* View Cart

#### Database

PostgreSQL

#### Tables

```sql
cart
cart_items
```

#### OpenFeign Communication

```text
Cart Service
      |
      | OpenFeign
      v
Product Service
```

Used for:

* Product Validation
* Product Information Retrieval

---

### Inventory Service

#### Responsibilities

* Stock Management
* Inventory Tracking
* Inventory Updates

#### Database

PostgreSQL

#### Tables

```sql
inventory
stock_transactions
```

---

### Order Service

#### Responsibilities

* Create Orders
* Cancel Orders
* View Order History

#### Database

PostgreSQL

#### Tables

```sql
orders
order_items
```

#### OpenFeign Communication

```text
Order Service
      |
      |---- Product Service
      |
      |---- Inventory Service
      |
      |---- User Service
```

Used for:

* Product Validation
* Inventory Validation
* Customer Information Retrieval

---

### Review Service

#### Responsibilities

* Product Reviews
* Product Ratings

#### Database

MongoDB

#### Collections

```text
reviews
ratings
```

#### APIs

```http
POST /reviews
GET /reviews/{productId}
```

#### OpenFeign Communication

```text
Review Service
      |
      | OpenFeign
      v
Product Service
```

Used for:

* Product Validation before Review Submission

---

### Analytics Service

#### Responsibilities

* Sales Analytics
* Customer Analytics
* Product Analytics
* Dashboard Statistics

#### Database

PostgreSQL

#### Tables

```sql
sales_summary
product_statistics
customer_statistics
```

---

## Security

### Authentication

* JWT-based Authentication
* Access Token Management
* Refresh Token Support
* Secure Password Storage

### Authorization

Role-Based Access Control (RBAC)

Supported Roles:

* CUSTOMER
* MERCHANT
* ADMIN

---

## Resilience and Reliability

### Circuit Breaker

Implemented using Resilience4j to handle service failures gracefully.

### Service Discovery

Implemented using Netflix Eureka for dynamic service registration and discovery.

### Load Balancing

Client-side load balancing through Spring Cloud.

---

## Rate Limiting

Implemented at the API Gateway level.

### Anonymous Users

```text
100 Requests / Minute
```

### Authenticated Users

```text
500 Requests / Minute
```

Purpose:

* Prevent Abuse
* Protect Backend Services
* Improve Platform Stability

---

## Inter-Service Communication

OpenFeign is used for communication between microservices.

Examples:

* Cart Service → Product Service
* Order Service → Product Service
* Order Service → Inventory Service
* Order Service → User Service
* Review Service → Product Service

---

## Author

Bharanidharan

Full Stack Java Developer

Spring Boot | Microservices | Angular | PostgreSQL | MongoDB
