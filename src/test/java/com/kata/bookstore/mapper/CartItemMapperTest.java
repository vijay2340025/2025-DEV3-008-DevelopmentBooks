package com.kata.bookstore.mapper;

import com.kata.bookstore.dto.CartItemDto;
import com.kata.bookstore.model.CartItem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CartItemMapperTest {

    @Autowired
    private CartItemMapper cartItemMapper;

    @Test
    @DisplayName("should map CartItem to CartItemDto")
    void shouldMapCartItemToCartItemDto() {
        CartItem cartItem = new CartItem(UUID.randomUUID().toString(), "prod001", 2);
        CartItemDto cartItemDto = cartItemMapper.toCartItemDto(cartItem);

        assertNull(cartItemMapper.toCartItemDto(null));

        assertNotNull(cartItemDto);
        assertEquals(cartItem.getItemId(), cartItemDto.getItemId());
        assertEquals(cartItem.getProductId(), cartItemDto.getProductId());
        assertEquals(cartItem.getQuantity(), cartItemDto.getQuantity());
    }

    @Test
    @DisplayName("should map CartItemDto to CartItem")
    void shouldMapCartItemDtoToCartItem() {
        CartItemDto cartItemDto = new CartItemDto(UUID.randomUUID().toString(), "prod001", 5);
        CartItem cartItem = cartItemMapper.toCartItem(cartItemDto);

        assertNull(cartItemMapper.toCartItem(null));

        assertNotNull(cartItemDto);
        assertEquals(cartItemDto.getItemId(), cartItem.getItemId());
        assertEquals(cartItemDto.getProductId(), cartItem.getProductId());
        assertEquals(cartItemDto.getQuantity(), cartItem.getQuantity());
    }
}