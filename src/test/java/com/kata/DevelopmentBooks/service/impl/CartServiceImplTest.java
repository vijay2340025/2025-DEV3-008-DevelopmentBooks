package com.kata.DevelopmentBooks.service.impl;

import com.kata.DevelopmentBooks.dto.CartDto;
import com.kata.DevelopmentBooks.mapper.CartMapper;
import com.kata.DevelopmentBooks.model.Cart;
import com.kata.DevelopmentBooks.repository.CartRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CartServiceImplTest {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private CartMapper cartMapper;

    @InjectMocks
    private CartServiceImpl cartService;

    @Captor
    ArgumentCaptor<Cart> cartCaptor;

    @Test
    @DisplayName("test createCart")
    void testCreateCart() {
        CartDto cartDto = new CartDto();

        when(cartMapper.toCartDto(any(Cart.class))).thenReturn(cartDto);
        CartDto result = cartService.createCart();

        verify(cartRepository).save(cartCaptor.capture());
        assertNotNull(cartCaptor.getValue().getCartId());
        assertSame(cartDto, result);
    }
}