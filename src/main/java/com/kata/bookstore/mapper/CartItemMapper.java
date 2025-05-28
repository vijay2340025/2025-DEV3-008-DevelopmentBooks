package com.kata.bookstore.mapper;

import com.kata.bookstore.dto.CartItemDto;
import com.kata.bookstore.model.CartItem;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CartItemMapper {

    CartItemDto toCartItemDto(CartItem cartItem);

    CartItem toCartItem(CartItemDto cartItem);
}