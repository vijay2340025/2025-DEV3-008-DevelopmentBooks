package com.kata.bookstore.model;

import lombok.Data;

@Data
public class PriceSummary {
    private String currency;
    private double originalPrice;
    private double discountedPrice;
}
