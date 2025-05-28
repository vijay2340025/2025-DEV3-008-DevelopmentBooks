package com.kata.bookstore.model;

import lombok.Data;

@Data
public class ProductAttribute {
    private String isbn;
    private String author;
    private int year;
}
