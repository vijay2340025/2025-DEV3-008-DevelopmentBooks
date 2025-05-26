package com.kata.DevelopmentBooks.mapper;

import com.kata.DevelopmentBooks.dto.CartItemDto;
import com.kata.DevelopmentBooks.model.CartItem;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CartItemMapper {

    CartItemDto toCartItemDto(CartItem cartItem);

    CartItem toCartItem(CartItemDto cartItem);
}