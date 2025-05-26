package com.kata.DevelopmentBooks.exception;

import com.kata.DevelopmentBooks.constants.ExceptionConstants;


public class CartNotFoundException extends RuntimeException {
    public CartNotFoundException(String cartId) {
        super(ExceptionConstants.CART_NOT_FOUND + ": " + cartId);
    }
}
