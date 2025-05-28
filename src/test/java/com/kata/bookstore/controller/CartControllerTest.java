package com.kata.bookstore.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kata.bookstore.dto.CartDto;
import com.kata.bookstore.exception.ApiError;
import com.kata.bookstore.exception.CartNotFoundException;
import com.kata.bookstore.exception.GlobalExceptionHandler;
import com.kata.bookstore.service.CartService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CartController.class)
@Import(GlobalExceptionHandler.class)
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
        when(cartService.getAllCarts()).thenReturn(getCartDtoList());
        MvcResult response = mockMvc.perform(get("/carts")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String responseString = response.getResponse().getContentAsString();
        List<CartDto> cartDtoList = objectMapper.readValue(responseString, new TypeReference<>() {
        });

        Assertions.assertAll(() -> {
            Assertions.assertEquals(2, cartDtoList.size());
            Assertions.assertNotNull(cartDtoList.getFirst().getCartId());
        });
    }

    @Test
    @DisplayName("returns HTTP 200 when a cart requested")
    void getCart_ShouldReturn200() throws Exception {
        List<CartDto> cartDtoList = getCartDtoList();
        CartDto cartDto = cartDtoList.stream().findFirst().orElse(new CartDto());
        String cartId = cartDto.getCartId();

        when(cartService.findByCartId(Mockito.anyString()))
                .thenReturn(cartDto);

        MvcResult response = mockMvc.perform(get("/carts/" + cartId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String responseString = response.getResponse().getContentAsString();
        CartDto cartDtoResponse = objectMapper.readValue(responseString, CartDto.class);

        Assertions.assertEquals(cartDto, cartDtoResponse);
        Assertions.assertEquals(cartId, cartDtoResponse.getCartId());
    }

    @Test
    @DisplayName("returns HTTP 204 when a cart deleted")
    void deleteCart_ShouldReturn204() throws Exception {
        List<CartDto> cartDtoList = getCartDtoList();
        CartDto cartDto = cartDtoList.stream().findFirst().orElse(new CartDto());
        String cartId = cartDto.getCartId();

        doNothing().when(cartService).deleteByCartId(Mockito.anyString());

        mockMvc.perform(delete("/carts/" + cartId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    private List<CartDto> getCartDtoList() {
        return IntStream.rangeClosed(1, 2).boxed()
                .map(integer -> {
                    CartDto cartDto = new CartDto();
                    cartDto.setCartId(UUID.randomUUID().toString());
                    return cartDto;
                }).toList();
    }

    @Test
    @DisplayName("returns HTTP 404 when cart not found")
    void getCartByCarttId_ShouldReturn404() throws Exception {
        String cartId = UUID.randomUUID().toString();
        Mockito.when(cartService.findByCartId(anyString()))
                .thenThrow(new CartNotFoundException(cartId));

        MvcResult mvcResult = mockMvc.perform(get("/carts/{cartId}", cartId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        ApiError apiError = objectMapper.readValue(contentAsString, ApiError.class);

        Assertions.assertAll(() -> {
            assertEquals(404, apiError.getStatus());
            assertEquals("Not Found", apiError.getError());
            assertEquals("Cart not found with ID: " + cartId, apiError.getMessage().getFirst());
        });

    }
}