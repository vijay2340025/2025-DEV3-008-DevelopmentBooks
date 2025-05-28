package com.kata.bookstore.constants;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ExceptionConstantsTest {

    @Test
    @DisplayName("test exception constants")
    void testExceptionConstants() {
        assertEquals("Cart not found with ID", ExceptionConstants.CART_NOT_FOUND);
        assertEquals("Product not found with ID", ExceptionConstants.PRODUCT_NOT_FOUND);
    }
}