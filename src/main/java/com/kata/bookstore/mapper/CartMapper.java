package com.kata.bookstore.mapper;

import com.kata.bookstore.dto.CartDto;
import com.kata.bookstore.model.Cart;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CartMapper {
    CartDto toCartDto(Cart cart);
}
