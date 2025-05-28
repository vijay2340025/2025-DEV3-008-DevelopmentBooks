package com.kata.bookstore.service;

import com.kata.bookstore.dto.CartDto;
import com.kata.bookstore.dto.CartItemDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CartItemService {
    CartDto addCartItem(String cartId, List<CartItemDto> cartItemDtoList);
    CartDto deleteCartItem(String cartId, String itemId);
}
