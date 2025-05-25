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

import java.util.List;
import java.util.UUID;

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

    @Test
    @DisplayName("test get all cart")
    void testGetAllCart() {
        Cart cart1 = new Cart();
        String cartId1 = UUID.randomUUID().toString();
        cart1.setCartId(cartId1);

        Cart cart2 = new Cart();
        String cartId2 = UUID.randomUUID().toString();
        cart2.setCartId(cartId2);

        when(cartRepository.findAll()).thenReturn(List.of(cart1, cart2));
        when(cartMapper.toCartDto(cart1)).thenReturn(new CartDto(cartId1));
        when(cartMapper.toCartDto(cart2)).thenReturn(new CartDto(cartId2));
        List<CartDto> result = cartService.getAllCart();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(cartId1, result.get(0).getCartId());
        assertEquals(cartId2, result.get(1).getCartId());
    }
}