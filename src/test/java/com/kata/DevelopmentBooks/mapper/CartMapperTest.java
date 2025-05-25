package com.kata.DevelopmentBooks.mapper;

import com.kata.DevelopmentBooks.dto.CartDto;
import com.kata.DevelopmentBooks.model.Cart;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CartMapperTest {

    @Autowired
    private CartMapper cartMapper;

    @Test
    @DisplayName("should map Cart to CartDto")
    void shouldMapCarToCartDto() {
        Cart cart = getCart();
        CartDto cartDto = cartMapper.toCartDto(cart);

        assertNotNull(cartDto);
        assertEquals(cart.getCartId(), cartDto.getCartId());
    }

    @Test
    @DisplayName("should map null when null passed")
    void shouldMapNUllToCartDto() {
        assertNull(cartMapper.toCartDto(null));
    }

    private static Cart getCart() {
        Cart cart = new Cart();
        cart.setCartId(UUID.randomUUID().toString());
        return cart;
    }

}