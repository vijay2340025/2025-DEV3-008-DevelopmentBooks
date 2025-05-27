package com.kata.DevelopmentBooks.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kata.DevelopmentBooks.dto.CartDto;
import com.kata.DevelopmentBooks.mapper.CartMapper;
import com.kata.DevelopmentBooks.model.Cart;
import com.kata.DevelopmentBooks.model.CartItem;
import com.kata.DevelopmentBooks.model.PriceSummary;
import com.kata.DevelopmentBooks.service.DiscountService;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DiscountController.class) // Replace with your actual controller class
class DiscountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private DiscountService discountService;

    @Autowired
    ObjectMapper objectMapper;

    @Spy
    private CartMapper cartMapper = Mappers.getMapper(CartMapper.class);

    @Test
    void applyDiscount_shouldReturn200() throws Exception {
        CartDto cartDto = cartMapper.toCartDto(createCart());

        Mockito.when(discountService.applyDiscount(cartDto.getCartId()))
                .thenReturn(cartDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/carts/{cartId}/discounts", cartDto.getCartId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        Mockito.verify(discountService).applyDiscount(cartDto.getCartId());
    }

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
}