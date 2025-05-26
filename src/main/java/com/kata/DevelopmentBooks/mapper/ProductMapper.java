package com.kata.DevelopmentBooks.mapper;

import com.kata.DevelopmentBooks.dto.ProductAttributeDto;
import com.kata.DevelopmentBooks.dto.ProductDto;
import com.kata.DevelopmentBooks.model.Product;
import com.kata.DevelopmentBooks.model.ProductAttribute;
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
