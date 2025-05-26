package com.kata.DevelopmentBooks.service.impl;

import com.kata.DevelopmentBooks.dto.CartDto;
import com.kata.DevelopmentBooks.mapper.CartMapper;
import com.kata.DevelopmentBooks.model.Cart;
import com.kata.DevelopmentBooks.model.CartItem;
import com.kata.DevelopmentBooks.model.PriceSummary;
import com.kata.DevelopmentBooks.repository.CartRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DiscountServiceImplTest {

    @InjectMocks
    private DiscountServiceImpl discountService;

    @Mock
    private CartRepository cartRepository;

    @Spy
    private CartMapper cartMapper = Mappers.getMapper(CartMapper.class);

    private Cart createCart() {
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

    @Test
    @DisplayName("test apply discounts")
    void testApplyDiscount() {
        Cart cart = createCart();

        when(cartRepository.findByCartId(cart.getCartId())).thenReturn(Optional.of(cart));
        when(cartRepository.save(cart)).thenAnswer(invocation -> invocation.getArgument(0));

        CartDto cartDto = discountService.applyDiscount(cart.getCartId());


        assertEquals(320, cartDto.getPricing().getDiscountedPrice());
        assertEquals(400, cartDto.getPricing().getOriginalPrice());
        assertEquals("EUR", cartDto.getPricing().getCurrency());

        verify(cartRepository).findByCartId(Mockito.anyString());
        verify(cartRepository).save(Mockito.any(Cart.class));
    }
}
