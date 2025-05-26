package com.kata.DevelopmentBooks.exception;

import com.kata.DevelopmentBooks.constants.ExceptionConstants;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(String productId) {
        super(ExceptionConstants.PRODUCT_NOT_FOUND +": " + productId);
    }
}
