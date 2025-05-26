package com.kata.DevelopmentBooks.service.impl;

import com.kata.DevelopmentBooks.dto.CartDto;
import com.kata.DevelopmentBooks.exception.CartNotFoundException;
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
import java.util.Optional;
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
    void testGetAllCarts() {
        Cart cart1 = createCart();
        String cartId1 = cart1.getCartId();

        Cart cart2 = createCart();
        String cartId2 = cart2.getCartId();

        when(cartRepository.findAll()).thenReturn(List.of(cart1, cart2));
        when(cartMapper.toCartDto(cart1)).thenReturn(new CartDto(cartId1, null, null));
        when(cartMapper.toCartDto(cart2)).thenReturn(new CartDto(cartId2, null, null));
        List<CartDto> result = cartService.getAllCarts();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(cartId1, result.get(0).getCartId());
        assertEquals(cartId2, result.get(1).getCartId());
    }

    @Test
    @DisplayName("test cart by cartId")
    void testFindByCartId_whenCartExists() {
        String cartId = UUID.randomUUID().toString();
        Cart cart = new Cart();
        cart.setCartId(cartId);
        CartDto cartDto = new CartDto(cartId, null, null);

        when(cartRepository.findByCartId(cartId)).thenReturn(Optional.of(cart));
        when(cartMapper.toCartDto(cart)).thenReturn(cartDto);

        CartDto result = cartService.findByCartId(cartId);
        assertNotNull(result);
        assertEquals(cartId, result.getCartId());
    }

    @Test
    @DisplayName("test exception when cart doesn't exist")
    void testFindByCartId_whenCartDoesNotExist() {
        String cartId = UUID.randomUUID().toString();
        when(cartRepository.findByCartId(cartId)).thenReturn(Optional.empty());

        assertThrows(CartNotFoundException.class, () -> cartService.findByCartId(cartId));
    }

    @Test
    @DisplayName("test delete by cartId")
    void testDeleteByCartId_whenCartExists() {
        String cartId = UUID.randomUUID().toString();
        Cart cart = new Cart();
        cart.setCartId(cartId);

        when(cartRepository.findByCartId(cartId)).thenReturn(Optional.of(cart));
        cartService.deleteByCartId(cartId);

        verify(cartRepository).delete(cart);
    }

    @Test
    @DisplayName("test delete by cartId when doesn't exist")
    void testDeleteByCartId_whenCartDoesNotExist() {
        String cartId = UUID.randomUUID().toString();
        when(cartRepository.findByCartId(cartId)).thenReturn(Optional.empty());

        assertThrows(CartNotFoundException.class, () -> cartService.deleteByCartId(cartId));
    }

    private Cart createCart() {
        Cart cart = new Cart();
        cart.setCartId(UUID.randomUUID().toString());
        return cart;
    }
}