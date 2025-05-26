package com.kata.DevelopmentBooks.controller;

import com.kata.DevelopmentBooks.dto.ProductDto;
import com.kata.DevelopmentBooks.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = {"/products"})
@Validated
public class ProductController {

    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping(value = {"/", ""})
    public ResponseEntity<ProductDto> addProduct(@Valid @RequestBody ProductDto productDto) {
        URI location = URI.create(String.format("/bookstore/products/%s", productDto.getProductId()));
        productService.addProduct(productDto);
        return ResponseEntity.created(location).build();
    }

    @GetMapping(value = {"/", ""})
    public ResponseEntity<List<ProductDto>> getProducts() {
        return ResponseEntity.ok(productService.getProducts());
    }
}
