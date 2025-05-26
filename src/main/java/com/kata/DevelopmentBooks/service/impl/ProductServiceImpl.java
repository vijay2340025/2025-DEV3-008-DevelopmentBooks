package com.kata.DevelopmentBooks.service.impl;


import com.kata.DevelopmentBooks.dto.ProductDto;
import com.kata.DevelopmentBooks.exception.ProductNotFoundException;
import com.kata.DevelopmentBooks.mapper.ProductMapper;
import com.kata.DevelopmentBooks.model.Product;
import com.kata.DevelopmentBooks.repository.ProductRepository;
import com.kata.DevelopmentBooks.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    @Override
    public ProductDto addProduct(ProductDto productDto) {
        Product product = productMapper.toProduct(productDto);
        productRepository.save(product);
        return productDto;
    }

    @Override
    public List<ProductDto> getProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(product -> productMapper.toProductDto(product))
                .collect(Collectors.toList());
    }

    @Override
    public ProductDto getProduct(String productId) {
        Optional<Product> productByProductId = productRepository.getProductByProductId(productId);
        return productByProductId.map(product -> productMapper.toProductDto(product))
                .orElseThrow(() -> new ProductNotFoundException(productId));
    }
}
