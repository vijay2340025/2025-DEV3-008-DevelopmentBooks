package com.kata.DevelopmentBooks.mapper;

import com.kata.DevelopmentBooks.dto.ProductAttributeDto;
import com.kata.DevelopmentBooks.dto.ProductDto;
import com.kata.DevelopmentBooks.model.Product;
import com.kata.DevelopmentBooks.model.ProductAttribute;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductMapperTest {

    @Autowired
    private ProductMapper productMapper;

    @Test
    @DisplayName("should map Product to ProductDto")
    void shouldMapProductToProductDto() {
        ProductAttribute attribute = getAttribute();
        Product product = getProduct(attribute);

        ProductDto productDto = productMapper.toProductDto(product);

        assertNotNull(productDto);
        assertEquals(product.getName(), productDto.getProductName());
        assertEquals(product.getListPrice(), productDto.getListPrice());
        assertEquals(product.getCurrency(), productDto.getCurrency());

        ProductAttributeDto attributesDto = productDto.getAttributes();
        assertNotNull(attributesDto);

        assertEquals(attribute.getISBN(), attributesDto.getISBN());
        assertEquals(attribute.getAuthor(), attributesDto.getAuthor());
        assertEquals(attribute.getYear(), attributesDto.getYear());
    }

    private static ProductAttribute getAttribute() {
        ProductAttribute attribute = new ProductAttribute();
        attribute.setISBN("978-1234567890");
        attribute.setAuthor("Vijay");
        attribute.setYear(2020);
        return attribute;
    }

    private static Product getProduct(ProductAttribute attribute) {
        Product product = new Product();
        product.setProductId("prod001");
        product.setName("Clean Code");
        product.setAttributes(attribute);
        product.setListPrice(50.0);
        product.setCurrency("EUR");
        return product;
    }

    @Test
    @DisplayName("should map ProductDto to Product")
    void shouldMapProductDtoToProduct() {
        ProductAttributeDto attributeDto = getAttributeDto();
        ProductDto productDto = getProductDto(attributeDto);

        Product product = productMapper.toProduct(productDto);

        assertNotNull(product);
        assertEquals(productDto.getProductId(), product.getProductId());
        assertEquals(productDto.getProductName(), product.getName());
        assertEquals(productDto.getListPrice(), product.getListPrice());
        assertEquals(productDto.getCurrency(), product.getCurrency());

        ProductAttribute attributes = product.getAttributes();
        assertNotNull(attributes);
        assertEquals(attributeDto.getISBN(), attributes.getISBN());
        assertEquals(attributeDto.getAuthor(), attributes.getAuthor());
        assertEquals(attributeDto.getYear(), attributes.getYear());

        assertNull(productMapper.toProduct(getProductDto(null)).getAttributes());
    }

    private ProductDto getProductDto(ProductAttributeDto attributeDto) {
        return new ProductDto("prod001", "Clean Code", attributeDto, 50, "EUR");
    }

    private ProductAttributeDto getAttributeDto() {
        return new ProductAttributeDto("978-0987654321", "Vijay", 2023);
    }

    @Test
    @DisplayName("should map ProductAttribute to ProductAttributeDto")
    void shouldMapProductAttributeToProductAttributeDto() {
        ProductAttribute attribute = getAttribute();

        ProductAttributeDto attributeDto = productMapper.toProductAttributeDto(attribute);

        assertNotNull(attributeDto);
        assertEquals(attribute.getISBN(), attributeDto.getISBN());
        assertEquals(attribute.getAuthor(), attributeDto.getAuthor());
        assertEquals(attribute.getYear(), attributeDto.getYear());
    }

    @Test
    @DisplayName("should return null when Product is null")
    void shouldReturnNullWhenMappingNullProduct() {
        ProductDto dto = productMapper.toProductDto(null);
        assertNull(dto);
    }

    @Test
    @DisplayName("should return null when ProductDto is null")
    void shouldReturnNullWhenMappingNullProductDto() {
        Product product = productMapper.toProduct(null);
        assertNull(product);
    }

    @Test
    @DisplayName("should return null when ProductAttribute is null")
    void shouldReturnNullWhenMappingNullProductAttribute() {
        ProductAttributeDto attrDto = productMapper.toProductAttributeDto(null);
        assertNull(attrDto);
    }
}

