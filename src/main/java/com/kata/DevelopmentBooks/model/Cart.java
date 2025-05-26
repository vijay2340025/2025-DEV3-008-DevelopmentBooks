package com.kata.DevelopmentBooks.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "cart")
public class Cart {

    @Getter
    @Setter
    @Id
    private String cartId;

    @Getter
    @Setter
    private List<CartItem> items;

    @Getter
    @Setter
    private PriceSummary pricing;
}
