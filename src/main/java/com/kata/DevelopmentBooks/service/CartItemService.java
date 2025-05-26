package com.kata.DevelopmentBooks.service;

import com.kata.DevelopmentBooks.dto.CartDto;
import com.kata.DevelopmentBooks.dto.CartItemDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CartItemService {
    CartDto addCartItem(String cartId, List<CartItemDto> cartItemDtoList);
    CartDto deleteCartItem(String cartId, String itemId);
}
