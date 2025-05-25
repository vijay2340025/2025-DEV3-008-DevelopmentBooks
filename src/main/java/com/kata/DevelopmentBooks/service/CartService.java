package com.kata.DevelopmentBooks.service;

import com.kata.DevelopmentBooks.dto.CartDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CartService {
    CartDto createCart();
    List<CartDto> getAllCarts();
    CartDto findByCartId(String cartId);
}
