package com.kata.bookstore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PriceSummaryDto {
    private String currency;
    private double originalPrice;
    private double discountedPrice;
}
