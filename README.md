# 🛒 Cart API - DevelopmentBooks

This API provides basic functionality for managing shopping carts in the DevelopmentBooks application.

## 🧱 Tech Stack

- Java 21+
- Spring Boot
- Maven
- Postman

### Build the Project

```bash
  mvn clean install
```

### Run the Project
```bash  
  mvn spring-boot:run
```

## 📌 Endpoints ##

---



### 📍 1. Create a New Cart

**POST** `/carts/`

Creates a new cart and returns the cart details.

```bash
  curl -X POST http://localhost:8080/carts/ \
  -H "Content-Type: application/json"
```

#### ✅ Response
- **201 Created**
- **Location Header**: URI to the newly created cart
- **Body**:

```json
{
    "cartId": "ea166aa1-ac83-47e6-9a22-f6bf11e62c37"
}
```

### 📍 2. Get All Carts

**GET** `/carts/`

Get all carts along with details

```bash
  curl --location 'http://localhost:8080/bookstore/carts/' \
  --header 'Content-Type: application/json' \
  --data ''
```

#### ✅ Response
- **200 Ok**
- **Body**:

```json
[
  {
    "cartId": "ea166aa1-ac83-47e6-9a22-f6bf11e62c37"
  },
  {
    "cartId": "db8c119a-1d3f-4595-954c-73f862f0d6a0"
  }
]
```