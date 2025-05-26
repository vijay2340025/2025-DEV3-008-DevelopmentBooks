package com.kata.DevelopmentBooks.repository;

import com.kata.DevelopmentBooks.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {
}
