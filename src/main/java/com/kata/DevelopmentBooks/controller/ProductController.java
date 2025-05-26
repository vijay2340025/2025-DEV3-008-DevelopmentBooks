package com.kata.DevelopmentBooks.controller;

import com.kata.DevelopmentBooks.dto.ProductDto;
import com.kata.DevelopmentBooks.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping(value = {"/products"})
public class ProductController {

    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping(value = {"/", ""})
    public ResponseEntity<ProductDto> addProduct(@RequestBody ProductDto productDto) {
        URI location = URI.create(String.format("/bookstore/products/%s", productDto.getProductId()));
        productService.addProduct(productDto);
        return ResponseEntity.created(location).build();
    }
}
