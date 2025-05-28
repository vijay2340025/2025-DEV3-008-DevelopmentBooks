package com.kata.bookstore.service.impl;


import com.kata.bookstore.dto.ProductDto;
import com.kata.bookstore.exception.ProductNotFoundException;
import com.kata.bookstore.mapper.ProductMapper;
import com.kata.bookstore.model.Product;
import com.kata.bookstore.repository.ProductRepository;
import com.kata.bookstore.service.ProductService;
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

    @Override
    public void deleteProduct(String productId) {
        Optional<Product> optionalProduct = productRepository.getProductByProductId(productId);
        Product product = optionalProduct.orElseThrow(() -> new ProductNotFoundException(productId));
        productRepository.delete(product);
    }
}
