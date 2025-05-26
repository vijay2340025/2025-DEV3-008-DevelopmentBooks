package com.kata.DevelopmentBooks.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDto {
    private String itemId;

    @NotBlank
    private String productId;

    @Min(1)
    @Max(10)
    private int quantity;
}
