package com.kata.DevelopmentBooks.model;

import lombok.Data;

@Data
public class PriceSummary {
    private String currency;
    private double originalPrice;
    private double discountedPrice;
}
