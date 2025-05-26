package com.kata.DevelopmentBooks.mapper;

import com.kata.DevelopmentBooks.dto.CartDto;
import com.kata.DevelopmentBooks.dto.PriceSummaryDto;
import com.kata.DevelopmentBooks.model.Cart;
import com.kata.DevelopmentBooks.model.CartItem;
import com.kata.DevelopmentBooks.model.PriceSummary;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CartMapperTest {

    @Autowired
    private CartMapper cartMapper;

    @Test
    @DisplayName("should map Cart to CartDto")
    void shouldMapCarToCartDto() {
        PriceSummary pricing = getPriceSummary();
        CartItem item = getCartItem();

        Cart cart = getCart(item, pricing);
        CartDto cartDto = cartMapper.toCartDto(cart);

        assertNotNull(cartDto);
        assertEquals(cart.getCartId(), cartDto.getCartId());

        assertNotNull(cartDto.getItems());
        assertEquals(1, cartDto.getItems().size());
        assertEquals(item.getItemId(), cartDto.getItems().getFirst().getItemId());
        assertEquals(item.getProductId(), cartDto.getItems().getFirst().getProductId());
        assertEquals(item.getQuantity(), cartDto.getItems().getFirst().getQuantity());

        PriceSummaryDto priceDto = cartDto.getPricing();
        assertNotNull(priceDto);
        assertEquals(pricing.getCurrency(), priceDto.getCurrency());
        assertEquals(pricing.getOriginalPrice(), priceDto.getOriginalPrice());
    }

    @Test
    @DisplayName("should map null when null passed")
    void shouldMapNUllToCartDto() {
        Cart cart = getCart(null, null);
        CartDto cartDto = cartMapper.toCartDto(cart);

        assertNull(cartMapper.toCartDto(null));
        assertNull(cartDto.getItems());
        assertNull(cartDto.getPricing());

        List<CartItem> itemList = new ArrayList<>();
        itemList.add(null);
        cart.setItems(itemList);
        assertNull(cartMapper.toCartDto(cart).getItems().getFirst());
    }

    private static Cart getCart(CartItem item, PriceSummary pricing) {
        Cart cart = new Cart();
        cart.setCartId(UUID.randomUUID().toString());
        if (null != item) {
            cart.setItems(List.of(item));
        }
        cart.setPricing(pricing);
        return cart;
    }

    private static CartItem getCartItem() {
        CartItem item = new CartItem();
        item.setQuantity(2);
        item.setItemId(UUID.randomUUID().toString());
        item.setProductId("prod001");
        return item;
    }

    private static PriceSummary getPriceSummary() {
        PriceSummary pricing = new PriceSummary();
        pricing.setCurrency("EUR");
        pricing.setOriginalPrice(100.0);
        return pricing;
    }

}