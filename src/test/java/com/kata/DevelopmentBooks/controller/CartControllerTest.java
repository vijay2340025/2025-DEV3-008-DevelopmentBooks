package com.kata.DevelopmentBooks.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kata.DevelopmentBooks.dto.CartDto;
import com.kata.DevelopmentBooks.service.CartService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

    @Test
    @DisplayName("returns HTTP 200 when all carts requested")
    void getAllCart_ShouldReturn200() throws Exception {
        when(cartService.getAllCart()).thenReturn(getCartDtoList());
        MvcResult response = mockMvc.perform(get("/carts")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String responseString = response.getResponse().getContentAsString();
        List<CartDto> cartDtoList = objectMapper.readValue(responseString, new TypeReference<>(){});

        Assertions.assertAll(() -> {
            Assertions.assertEquals(2, cartDtoList.size());
            Assertions.assertNotNull(cartDtoList.getFirst().getCartId());
        });
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