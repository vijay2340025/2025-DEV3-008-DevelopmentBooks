package com.kata.DevelopmentBooks.repository;

import com.kata.DevelopmentBooks.model.Cart;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends MongoRepository<Cart, String> {
}
