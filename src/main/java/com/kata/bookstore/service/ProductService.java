package com.kata.bookstore.service;

import com.kata.bookstore.dto.ProductDto;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface ProductService {
    ProductDto addProduct(ProductDto productDto);
    List<ProductDto> getProducts();
    ProductDto getProduct(String productId);
    void deleteProduct(String productId);
}
