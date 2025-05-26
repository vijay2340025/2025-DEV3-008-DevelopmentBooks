package com.kata.DevelopmentBooks.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    private String productId;

    private String productName;

    private ProductAttributeDto attributes;

    private double listPrice;

    private String currency;
}
