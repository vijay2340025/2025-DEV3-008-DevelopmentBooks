package com.kata.DevelopmentBooks.service;

import com.kata.DevelopmentBooks.dto.ProductDto;
import org.springframework.stereotype.Service;


@Service
public interface ProductService {
    public ProductDto addProduct(ProductDto productDto);
}
