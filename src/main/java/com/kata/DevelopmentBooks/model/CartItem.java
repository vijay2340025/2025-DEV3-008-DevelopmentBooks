package com.kata.DevelopmentBooks.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {
    private String itemId;
    private String productId;
    private int quantity;
}
