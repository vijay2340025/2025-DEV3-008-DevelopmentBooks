package com.kata.DevelopmentBooks.mapper;

import com.kata.DevelopmentBooks.dto.CartDto;
import com.kata.DevelopmentBooks.model.Cart;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CartMapper {
    CartDto toCartDto(Cart cart);
}
