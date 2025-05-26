package com.kata.DevelopmentBooks.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kata.DevelopmentBooks.dto.ProductDto;
import com.kata.DevelopmentBooks.exception.ApiError;
import com.kata.DevelopmentBooks.service.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;
import java.util.stream.IntStream;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("returns HTTP 201 when valid product added")
    void addProduct_ValidInput_ShouldReturn201() throws Exception {
        ProductDto productDto = getProductDtoList().stream().findAny()
                .orElse(new ProductDto());

        when(productService.addProduct(Mockito.any(ProductDto.class))).thenReturn(productDto);

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDto)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/bookstore/products/prod001"));
    }

    private static List<ProductDto> getProductDtoList() {
        return IntStream.rangeClosed(1, 5).boxed()
                .map(integer -> {
                    ProductDto productDto = new ProductDto();
                    productDto.setProductId("prod00" + integer);
                    productDto.setProductName("Clean Code " + 1);
                    productDto.setListPrice(25.0);
                    productDto.setCurrency("EUR");
                    return productDto;
                }).toList();
    }

    @Test
    @DisplayName("returns HTTP 400 when invalid products are added")
    void addProduct_InvalidInput_ShouldReturn400BadRequest() throws Exception {
        ProductDto productDto = getProductDtoList().stream().findAny()
                .orElse(new ProductDto());
        productDto.setProductId("");
        productDto.setProductName("");
        productDto.setListPrice(0);
        productDto.setCurrency("");

        MvcResult mvcResult = mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDto)))
                .andExpect(status().isBadRequest())
                .andReturn();

        String contentAsString = mvcResult.getResponse().getContentAsString();
        ApiError apiError = objectMapper.readValue(contentAsString, ApiError.class);

        Assertions.assertAll(() -> {
            Assertions.assertEquals(4, apiError.getMessage().size());
            Assertions.assertTrue(apiError.getMessage().contains("productId: must not be blank"));
            Assertions.assertTrue(apiError.getMessage().contains("productName: must not be blank"));
            Assertions.assertTrue(apiError.getMessage().contains("listPrice: must be greater than 0.0"));
            Assertions.assertTrue(apiError.getMessage().contains("currency: must not be blank"));
        });
    }
}