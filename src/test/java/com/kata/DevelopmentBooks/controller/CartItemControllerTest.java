package com.kata.DevelopmentBooks.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kata.DevelopmentBooks.dto.CartDto;
import com.kata.DevelopmentBooks.dto.CartItemDto;
import com.kata.DevelopmentBooks.service.CartItemService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
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

@WebMvcTest(CartItemController.class)
class CartItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private CartItemService cartItemService;

    @Test
    @DisplayName("returns HTTP 201 when cart item created")
    void addCartItem_ShouldReturn201() throws Exception {

        CartDto cartDto = getCartDtoList().getFirst();
        String cartId = cartDto.getCartId();

        when(cartItemService.addCartItem(
                Mockito.anyString(),
                Mockito.any()
        )).thenReturn(cartDto);

        mockMvc.perform(post("/carts/" + cartId + "/lineitems/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cartDto.getItems())))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("returns HTTP 400 when invalid cartItem passed")
    void addCartItem_InvalidInput_ShouldReturn400() throws Exception {

        CartDto cartDto = getCartDtoList().getFirst();
        String cartId = cartDto.getCartId();
        cartDto.getItems().getFirst().setProductId(null);
        cartDto.getItems().getFirst().setQuantity(11);

        when(cartItemService.addCartItem(
                Mockito.anyString(),
                Mockito.any()
        )).thenReturn(cartDto);

        mockMvc.perform(post("/carts/" + cartId + "/lineitems/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cartDto.getItems())))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    private List<CartDto> getCartDtoList() {
        return IntStream.rangeClosed(1, 2).boxed()
                .map(integer -> {
                    CartItemDto cartItemDto = new CartItemDto();
                    cartItemDto.setItemId(UUID.randomUUID().toString());
                    cartItemDto.setQuantity(1);
                    cartItemDto.setProductId("prod00" + integer);

                    CartDto cartDto = new CartDto();
                    cartDto.setCartId(UUID.randomUUID().toString());
                    cartDto.setItems(List.of(cartItemDto));
                    return cartDto;
                }).toList();
    }

}
