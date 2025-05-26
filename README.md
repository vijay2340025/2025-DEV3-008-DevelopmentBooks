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

### 📍 3. Get Cart by cartId

**GET** `/carts/{cartId}`

Get a carts along with details

```bash
  curl --location 'http://localhost:8080/bookstore/carts/ea166aa1-ac83-47e6-9a22-f6bf11e62c37' \
  --header 'Content-Type: application/json' \
  --data ''
```

#### ✅ Response
- **200 Ok**
- **Body**:

```json
{
  "cartId": "db8c119a-1d3f-4595-954c-73f862f0d6a0"
}
```

### 📍 4. Detele Cart by cartId

**DELETE** `/carts/{cartId}`

Delete the cart mentioned in the path variable

```bash
  curl --location --request DELETE 'http://localhost:8080/bookstore/carts/ea166aa1-ac83-47e6-9a22-f6bf11e62c37' \
  --header 'Content-Type: application/json' \
  --data ''
```

#### ✅ Response
- **204 No Content**


### 📍 5. Create a Product

**POST** `/products/`

Creates a new product

```bash
    curl --location 'http://localhost:8080/bookstore/products' \
    --header 'Content-Type: application/json' \
    --data '{
        "productId": "prod007",
        "productName": "Clean Code",
        "attributes": {
            "author": "Robert C. Martin",
            "year": 2025,
            "isbn": "978-1234567890"
        },
        "listPrice": 50.0,
        "currency": "EUR"
    }'
```

#### ✅ Response
- **201 Created**