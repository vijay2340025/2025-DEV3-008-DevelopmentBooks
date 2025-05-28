package com.kata.bookstore.exception;

import com.kata.bookstore.constants.ExceptionConstants;


public class CartNotFoundException extends RuntimeException {
    public CartNotFoundException(String cartId) {
        super(ExceptionConstants.CART_NOT_FOUND + ": " + cartId);
    }
}
