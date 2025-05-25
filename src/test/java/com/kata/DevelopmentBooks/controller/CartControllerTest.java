package com.kata.DevelopmentBooks.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kata.DevelopmentBooks.dto.CartDto;
import com.kata.DevelopmentBooks.service.CartService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CartController.class)
class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private CartService cartService;

    @Test
    @DisplayName("returns HTTP 201 when cart created")
    void createCart_ShouldReturn201() throws Exception {
        when(cartService.createCart()).thenReturn(getCartDtoList().getFirst());
        mockMvc.perform(post("/carts")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    private List<CartDto> getCartDtoList() {
        return IntStream.rangeClosed(1, 2).boxed()
                .map(integer -> {
                    CartDto cartDto = new CartDto();
                    cartDto.setCartId(UUID.randomUUID().toString());
                    return cartDto;
                }).toList();
    }
}