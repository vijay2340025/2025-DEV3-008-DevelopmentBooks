package com.kata.bookstore.service.impl;

import com.kata.bookstore.dto.CartDto;
import com.kata.bookstore.dto.CartItemDto;
import com.kata.bookstore.exception.CartNotFoundException;
import com.kata.bookstore.exception.ProductNotFoundException;
import com.kata.bookstore.mapper.CartItemMapper;
import com.kata.bookstore.mapper.CartMapper;
import com.kata.bookstore.model.Cart;
import com.kata.bookstore.model.CartItem;
import com.kata.bookstore.model.Product;
import com.kata.bookstore.repository.CartRepository;
import com.kata.bookstore.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartItemServiceImplTest {

    @Mock
    private CartItemMapper cartItemMapper;

    @Mock
    private CartMapper cartMapper;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private CartItemServiceImpl cartItemService;

    private Cart createEmptyCart() {
        Cart cart = new Cart();
        cart.setCartId(UUID.randomUUID().toString());
        return cart;
    }

    private Product createProduct() {
        Product product = new Product();
        product.setProductId("prod001");
        product.setCurrency("EUR");
        product.setName("Clean Code");
        return product;
    }

    @Test
    @DisplayName("test add new cart item")
    void testAddNewItemToCart() {
        Cart cart = createEmptyCart();
        Product product = createProduct();
        String cartId = cart.getCartId();

        CartItemDto cartItemDto = new CartItemDto();
        cartItemDto.setProductId("prod001");
        cartItemDto.setQuantity(2);

        CartItem cartItem = new CartItem();
        cartItem.setProductId("prod001");
        cartItem.setQuantity(2);

        when(cartRepository.findByCartId(cartId)).thenReturn(Optional.of(cart));
        when(productRepository.findById("prod001")).thenReturn(Optional.of(product));
        when(cartItemMapper.toCartItem(Mockito.any(CartItemDto.class))).thenReturn(cartItem);
        when(cartRepository.save(Mockito.any(Cart.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(cartMapper.toCartDto(Mockito.any(Cart.class))).thenReturn(new CartDto());

        CartDto result = cartItemService.addCartItem(cartId, List.of(cartItemDto));

        assertNotNull(result);
        verify(cartRepository).findByCartId(cartId);
        verify(cartMapper).toCartDto(cart);
    }

    @Test
    @DisplayName("test update new cart item when already exist in cart")
    void testUpdateExistingItemQuantity() {
        Cart cart = createEmptyCart();
        Product product = createProduct();
        String cartId = cart.getCartId();

        CartItem existingItem = new CartItem(UUID.randomUUID().toString(), "prod001", 2);
        cart.setItems(List.of(existingItem));

        CartItemDto cartItemDto = new CartItemDto(UUID.randomUUID().toString(), "prod001", 3);
        when(cartRepository.findByCartId(cartId)).thenReturn(Optional.of(cart));
        when(productRepository.findById("prod001")).thenReturn(Optional.of(product));
        when(cartMapper.toCartDto(Mockito.any())).thenReturn(new CartDto());
        when(cartRepository.save(Mockito.any(Cart.class))).thenReturn(cart);

        CartDto result = cartItemService.addCartItem(cartId, List.of(cartItemDto));

        assertNotNull(result);
        assertEquals(5, existingItem.getQuantity());
        verify(cartRepository).findByCartId(cartId);
        verify(cartMapper).toCartDto(cart);
    }

    @Test
    @DisplayName("test ProductNotFound when invalid product used")
    void testProductNotFound() {
        CartItemDto cartItemDto = new CartItemDto(UUID.randomUUID().toString(), "prod001", 2);
        when(productRepository.findById("prod001")).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> {
            cartItemService.addCartItem(UUID.randomUUID().toString(), List.of(cartItemDto));
        });
    }

    @Test
    @DisplayName("test CartNotFound when cart doesn't exists")
    void testCartNotFound() {
        String cartId = UUID.randomUUID().toString();
        CartItemDto cartItemDto = new CartItemDto(UUID.randomUUID().toString(), "prod001", 2);
        when(productRepository.findById("prod001")).thenReturn(Optional.of(new Product()));
        when(cartRepository.findByCartId(cartId)).thenReturn(Optional.empty());

        assertThrows(CartNotFoundException.class, () -> {
            cartItemService.addCartItem(cartId, List.of(cartItemDto));
        });

        verify(cartRepository).findByCartId(cartId);
    }

    @Test
    @DisplayName("test delete cart item")
    void testDeleteCartItem() {
        String cartId = UUID.randomUUID().toString();
        String itemId = UUID.randomUUID().toString();

        Cart cart = createEmptyCart();
        CartItem cartItem = new CartItem();
        cartItem.setItemId(UUID.randomUUID().toString());
        cartItem.setProductId("prod001");
        cartItem.setQuantity(3);
        cart.setItems(List.of(cartItem));

        when(cartRepository.findByCartId(cartId))
                .thenReturn(Optional.of(cart));
        when(productRepository.findById("prod001"))
                .thenReturn(Optional.of(createProduct()));

        cartItemService.deleteCartItem(cartId, itemId);

        verify(cartRepository).save(cart);
    }
    @Test
    @DisplayName("test delete cart when product doesn't exist")
    void testDeleteCartItem_whenProductNotFound() {
        String cartId = UUID.randomUUID().toString();
        String itemId = UUID.randomUUID().toString();

        Cart cart = createEmptyCart();
        CartItem cartItem = new CartItem();
        cartItem.setItemId(UUID.randomUUID().toString());
        cartItem.setProductId("prod001");
        cartItem.setQuantity(3);
        cart.setItems(List.of(cartItem));

        when(cartRepository.findByCartId(cartId))
                .thenReturn(Optional.of(cart));

        assertThrows(ProductNotFoundException.class, () -> {
            cartItemService.deleteCartItem(cartId, itemId);
        });

    }

}