package com.kata.DevelopmentBooks.service.impl;

import com.kata.DevelopmentBooks.dto.ProductAttributeDto;
import com.kata.DevelopmentBooks.dto.ProductDto;
import com.kata.DevelopmentBooks.mapper.ProductMapper;
import com.kata.DevelopmentBooks.model.Product;
import com.kata.DevelopmentBooks.model.ProductAttribute;
import com.kata.DevelopmentBooks.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    void testSaveProductMapsAndSaves() {
        ProductDto productDto = createSampleProductDto();
        Product product = createSampleProduct();

        when(productMapper.toProduct(productDto)).thenReturn(product);

        ProductDto result = productService.addProduct(productDto);

        verify(productRepository).save(product);
        assertEquals(productDto, result);
        assertEquals(productDto.getAttributes().getISBN(), product.getAttributes().getISBN());
    }

    private ProductDto createSampleProductDto() {
        return new ProductDto("prod001", "Clean Code",
                createProductAttributeDto(),
                50.0, "EUR");
    }

    private Product createSampleProduct() {
        ProductAttribute productAttribute = createProductAttribute();
        return new Product( "prod001", "Clean Code", productAttribute, 50.0, "EUR");
    }

    private ProductAttribute createProductAttribute() {
        ProductAttribute productAttribute = new ProductAttribute();
        productAttribute.setISBN("978-1234567890");
        productAttribute.setAuthor("Robert C. Martin");
        productAttribute.setYear(2020);
        return productAttribute;
    }

    private ProductAttributeDto createProductAttributeDto() {
        ProductAttributeDto productAttributeDto = new ProductAttributeDto();
        productAttributeDto.setISBN("978-1234567890");
        productAttributeDto.setAuthor("Robert C. Martin");
        productAttributeDto.setYear(2020);
        return productAttributeDto;
    }
}
