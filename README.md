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

## 📌 User Journey ##

---


###  1. Create a New Cart

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
    "cartId": "2b88a4bb-afb7-45ae-99c0-bddf907e7058"
}
```

### 2. Create a Product

**POST** `/products/`

Creates a new product

```bash
    curl --location 'http://localhost:8080/bookstore/products' \
    --header 'Content-Type: application/json' \
    --data '{
        "productId": "prod001",
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

### 3. Get all Products

**GET** `/products/`

Creates a new product

```bash
    curl --location 'http://localhost:8080/bookstore/products' \
  --data ''
```

#### ✅ Response
- **201 Created**
- **Body**:

```json
[
  {
    "productId": "prod002",
    "productName": "Clean Code II",
    "attributes": {
      "isbn": "978-1234567890",
      "author": "Robert C. Martin",
      "year": 2025
    },
    "listPrice": 50.0,
    "currency": "EUR"
  },
  {
    "productId": "prod001",
    "productName": "Clean Code",
    "attributes": {
      "isbn": "978-1234567890",
      "author": "Robert C. Martin",
      "year": 2025
    },
    "listPrice": 50.0,
    "currency": "EUR"
  }
]
```

### 4. Add items to cart

**POST** `carts/{cartId}/lineitems/`

Add lineitems to cart

```bash
   curl --location 'http://localhost:8080/bookstore/carts/2b88a4bb-afb7-45ae-99c0-bddf907e7058/lineitems/' \
--header 'Content-Type: application/json' \
--data '[
    {
        "productId": "prod001",
        "quantity": 2
    },
    {
        "productId": "prod002",
        "quantity": 1
    },
    {
        "productId": "prod003",
        "quantity": 2
    },
    {
        "productId": "prod004",
        "quantity": 1
    },
    {
        "productId": "prod005",
        "quantity": 2
    }
]'
```

#### ✅ Response
- **200 Created**
- **Body**:

```json
{
  "cartId": "2b88a4bb-afb7-45ae-99c0-bddf907e7058",
  "items": [
    {
      "itemId": "33656253-30f4-497c-a5bd-1aff3d237e48",
      "productId": "prod003",
      "quantity": 2
    },
    {
      "itemId": "c2a0e11c-2fe7-405f-8ff2-1b5e7f38c1de",
      "productId": "prod004",
      "quantity": 1
    },
    {
      "itemId": "3bbabea9-2111-40f0-99c2-a8ff5164adc8",
      "productId": "prod005",
      "quantity": 2
    },
    {
      "itemId": "39d785f8-2803-4c56-924e-295c5724ee34",
      "productId": "prod001",
      "quantity": 2
    },
    {
      "itemId": "046cee0d-9c8b-47ec-b423-51fbbf8f479e",
      "productId": "prod002",
      "quantity": 1
    }
  ],
  "pricing": {
    "currency": "EUR",
    "originalPrice": 400.0,
    "discountedPrice": 0.0
  }
}
```

### 5. Apply Discounts

**GET** `carts/{cartId}/discounts/`

Add discounts to the cart

```bash
   curl --location 'http://localhost:8080/bookstore/carts/2b88a4bb-afb7-45ae-99c0-bddf907e7058/lineitems/' \
--header 'Content-Type: application/json' \
--data '[
    {
        "productId": "prod001",
        "quantity": 2
    },
    {
        "productId": "prod002",
        "quantity": 1
    },
    {
        "productId": "prod003",
        "quantity": 2
    },
    {
        "productId": "prod004",
        "quantity": 1
    },
    {
        "productId": "prod005",
        "quantity": 2
    }
]'
```

#### ✅ Response
- **200 OK **
- **Body**:

```json
{
  "cartId": "2b88a4bb-afb7-45ae-99c0-bddf907e7058",
  "items": [
    {
      "itemId": "33656253-30f4-497c-a5bd-1aff3d237e48",
      "productId": "prod003",
      "quantity": 2
    },
    {
      "itemId": "c2a0e11c-2fe7-405f-8ff2-1b5e7f38c1de",
      "productId": "prod004",
      "quantity": 1
    },
    {
      "itemId": "3bbabea9-2111-40f0-99c2-a8ff5164adc8",
      "productId": "prod005",
      "quantity": 2
    },
    {
      "itemId": "39d785f8-2803-4c56-924e-295c5724ee34",
      "productId": "prod001",
      "quantity": 2
    },
    {
      "itemId": "046cee0d-9c8b-47ec-b423-51fbbf8f479e",
      "productId": "prod002",
      "quantity": 1
    }
  ],
  "pricing": {
    "currency": "EUR",
    "originalPrice": 400.0,
    "discountedPrice": 320.0
  }
}
```



