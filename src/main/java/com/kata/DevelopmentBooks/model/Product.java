package com.kata.DevelopmentBooks.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    private String productId;
    private String name;
    private ProductAttribute attributes;
    private double listPrice;
    private String currency;
}
