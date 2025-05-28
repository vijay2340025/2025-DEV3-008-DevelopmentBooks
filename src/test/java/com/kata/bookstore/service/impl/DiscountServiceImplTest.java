package com.kata.bookstore.service.impl;

import com.kata.bookstore.dto.CartDto;
import com.kata.bookstore.mapper.CartMapper;
import com.kata.bookstore.model.*;
import com.kata.bookstore.repository.CartRepository;
import com.kata.bookstore.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DiscountServiceImplTest {

    @InjectMocks
    private DiscountServiceImpl discountService;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private ProductRepository productRepository;

    @Spy
    private CartMapper cartMapper = Mappers.getMapper(CartMapper.class);

    private Cart createCartWithFiveItems() {
        CartItem item1 = new CartItem(UUID.randomUUID().toString(), "prod001", 2);
        CartItem item2 = new CartItem(UUID.randomUUID().toString(), "prod002", 2);
        CartItem item3 = new CartItem(UUID.randomUUID().toString(), "prod003", 2);
        CartItem item4 = new CartItem(UUID.randomUUID().toString(), "prod004", 1);
        CartItem item5 = new CartItem(UUID.randomUUID().toString(), "prod005", 1);

        PriceSummary priceSummary = new PriceSummary();
        priceSummary.setCurrency("EUR");
        priceSummary.setOriginalPrice(400.0);

        Cart cart = new Cart();
        String cartId = UUID.randomUUID().toString();
        cart.setCartId(cartId);
        cart.setItems(new ArrayList<>(List.of(item1, item2, item3, item4, item5)));
        cart.setPricing(priceSummary);

        return cart;
    }

    private Cart createCartWithThreeItems() {
        CartItem item1 = new CartItem(UUID.randomUUID().toString(), "prod001", 2);
        CartItem item2 = new CartItem(UUID.randomUUID().toString(), "prod002", 2);
        CartItem item3 = new CartItem(UUID.randomUUID().toString(), "prod003", 1);

        PriceSummary priceSummary = new PriceSummary();
        priceSummary.setCurrency("EUR");
        priceSummary.setOriginalPrice(250.0);

        Cart cart = new Cart();
        String cartId = UUID.randomUUID().toString();
        cart.setCartId(cartId);
        cart.setItems(new ArrayList<>(List.of(item1, item2, item3)));
        cart.setPricing(priceSummary);

        return cart;
    }

    private List<Product> createProducts() {
        return IntStream.rangeClosed(1, 5).boxed()
                .map(integer -> new Product(
                        "prod00" + integer,
                        "Clean Code " + integer,
                        new ProductAttribute(),
                        50.0,
                        "EUR"
                )).toList();
    }

    @Test
    @DisplayName("test apply discounts when 5 items passed")
    void testApplyDiscount_whenFiveItemsPassed() {
        Cart cart = createCartWithFiveItems();

        when(cartRepository.findByCartId(cart.getCartId())).thenReturn(Optional.of(cart));
        when(productRepository.findAll()).thenReturn(createProducts());
        when(cartRepository.save(cart)).thenAnswer(invocation -> invocation.getArgument(0));

        CartDto cartDto = discountService.applyDiscount(cart.getCartId());


        assertEquals(320.0, cartDto.getPricing().getDiscountedPrice());
        assertEquals(400.0, cartDto.getPricing().getOriginalPrice());
        assertEquals("EUR", cartDto.getPricing().getCurrency());

        verify(cartRepository).findByCartId(Mockito.anyString());
        verify(cartRepository).save(Mockito.any(Cart.class));
    }

    @Test
    @DisplayName("test apply discounts when 3 items passed")
    void testApplyDiscount_whenThreeItemsPassed() {
        Cart cart = createCartWithThreeItems();

        when(cartRepository.findByCartId(cart.getCartId())).thenReturn(Optional.of(cart));
        when(productRepository.findAll()).thenReturn(createProducts());
        when(cartRepository.save(cart)).thenAnswer(invocation -> invocation.getArgument(0));

        CartDto cartDto = discountService.applyDiscount(cart.getCartId());


        assertEquals(230.0, cartDto.getPricing().getDiscountedPrice());
        assertEquals(250.0, cartDto.getPricing().getOriginalPrice());
        assertEquals("EUR", cartDto.getPricing().getCurrency());

        verify(cartRepository).findByCartId(Mockito.anyString());
        verify(cartRepository).save(Mockito.any(Cart.class));
    }
}
