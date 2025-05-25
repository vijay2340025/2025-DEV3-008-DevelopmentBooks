package com.kata.DevelopmentBooks.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "cart")
public class Cart {

    @Getter
    @Setter
    @Id
    private String cartId;
}
