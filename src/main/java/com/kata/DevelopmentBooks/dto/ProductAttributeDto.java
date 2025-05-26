package com.kata.DevelopmentBooks.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductAttributeDto {
    private String isbn;

    private String author;

    @Min(1990)
    @Max(2025)
    private int year;
}
