package com.kata.bookstore.mapper;

import com.kata.bookstore.dto.ProductAttributeDto;
import com.kata.bookstore.dto.ProductDto;
import com.kata.bookstore.model.Product;
import com.kata.bookstore.model.ProductAttribute;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(source = "name", target = "productName")
    ProductDto toProductDto(Product product);

    @Mapping(source = "productName", target = "name")
    Product toProduct(ProductDto product);

    ProductAttributeDto toProductAttributeDto(ProductAttribute value);

    ProductAttribute toProductAttribute(ProductAttributeDto value);

}
