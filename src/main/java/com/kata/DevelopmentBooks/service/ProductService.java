package com.kata.DevelopmentBooks.service;

import com.kata.DevelopmentBooks.dto.ProductDto;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface ProductService {
    ProductDto addProduct(ProductDto productDto);
    List<ProductDto> getProducts();
}
