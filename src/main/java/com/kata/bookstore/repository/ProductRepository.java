package com.kata.bookstore.repository;

import com.kata.bookstore.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {
    Optional<Product> getProductByProductId(String productId);
}
