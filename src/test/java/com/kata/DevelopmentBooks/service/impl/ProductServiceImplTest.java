package com.kata.DevelopmentBooks.service.impl;

import com.kata.DevelopmentBooks.dto.ProductAttributeDto;
import com.kata.DevelopmentBooks.dto.ProductDto;
import com.kata.DevelopmentBooks.exception.ProductNotFoundException;
import com.kata.DevelopmentBooks.mapper.ProductMapper;
import com.kata.DevelopmentBooks.model.Product;
import com.kata.DevelopmentBooks.model.ProductAttribute;
import com.kata.DevelopmentBooks.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {
    @InjectMocks
    private ProductServiceImpl productService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @Test
    @DisplayName("test create product")
    void testCreateProduct() {
        ProductDto productDto = createSampleProductDto();
        Product product = createSampleProduct();

        when(productMapper.toProduct(productDto)).thenReturn(product);

        ProductDto result = productService.addProduct(productDto);

        verify(productRepository).save(product);
        assertEquals(productDto, result);
        assertEquals(productDto.getAttributes().getIsbn(), product.getAttributes().getIsbn());
    }

    private ProductDto createSampleProductDto() {
        return new ProductDto("prod001", "Clean Code",
                createProductAttributeDto(),
                50.0, "EUR");
    }

    private Product createSampleProduct() {
        ProductAttribute productAttribute = createProductAttribute();
        return new Product("prod001", "Clean Code", productAttribute, 50.0, "EUR");
    }

    private ProductAttribute createProductAttribute() {
        ProductAttribute productAttribute = new ProductAttribute();
        productAttribute.setIsbn("978-1234567890");
        productAttribute.setAuthor("Robert C. Martin");
        productAttribute.setYear(2020);
        return productAttribute;
    }

    private ProductAttributeDto createProductAttributeDto() {
        ProductAttributeDto productAttributeDto = new ProductAttributeDto();
        productAttributeDto.setIsbn("978-1234567890");
        productAttributeDto.setAuthor("Robert C. Martin");
        productAttributeDto.setYear(2020);
        return productAttributeDto;
    }

    @Test
    @DisplayName("test get all products")
    void testGetAllProducts() {
        Product product = createSampleProduct();
        ProductDto productDto = createSampleProductDto();

        when(productRepository.findAll()).thenReturn(List.of(product));
        when(productMapper.toProductDto(product)).thenReturn(productDto);

        List<ProductDto> productDtoList = productService.getProducts();

        assertEquals(1, productDtoList.size());
        assertEquals("prod001", productDtoList.getFirst().getProductId());
        verify(productRepository).findAll();
        verify(productMapper).toProductDto(product);
    }

    @Test
    @DisplayName("test get a product by productId")
    void testGetProductByProductId() {
        Product product = createSampleProduct();
        ProductDto productDto = createSampleProductDto();

        when(productRepository.getProductByProductId("prod001")).thenReturn(Optional.of(product));
        when(productMapper.toProductDto(product)).thenReturn(productDto);

        ProductDto result = productService.getProduct("prod001");

        assertEquals("prod001", result.getProductId());
        verify(productRepository).getProductByProductId("prod001");
    }

    @Test
    @DisplayName("test ProductNotFoundException when productId doesn't exists")
    void testGetProductThrowsProductNotFoundException() {
        when(productRepository.getProductByProductId("prod001")).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> productService.getProduct("prod001"));

        verify(productRepository).getProductByProductId("prod001");
    }

    @Test
    @DisplayName("test delete product by productId")
    void testDeleteProduct() {
        Product product = createSampleProduct();
        when(productRepository.getProductByProductId("prod001")).thenReturn(Optional.of(product));

        productService.deleteProduct("prod001");
        verify(productRepository).delete(product);
    }

    @Test
    @DisplayName("test delete throws ProductNotFoundException")
    void testDeleteProductThrowsProductNotFoundException() {
        when(productRepository.getProductByProductId("prod001")).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> productService.deleteProduct("prod001"));
    }
}
