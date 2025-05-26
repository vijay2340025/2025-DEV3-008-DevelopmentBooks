package com.kata.DevelopmentBooks.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductAttributeDto {
    private String isbn;

    private String author;

    private int year;
}
