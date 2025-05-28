package com.kata.bookstore.exception;

import com.kata.bookstore.constants.ExceptionConstants;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(String productId) {
        super(ExceptionConstants.PRODUCT_NOT_FOUND +": " + productId);
    }
}
