package com.kata.DevelopmentBooks.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    @NotBlank
    private String productId;

    @NotBlank
    private String productName;

    @Valid
    private ProductAttributeDto attributes;

    @DecimalMin(value = "0.0", inclusive = false)
    private double listPrice;

    @NotBlank
    private String currency;
}
