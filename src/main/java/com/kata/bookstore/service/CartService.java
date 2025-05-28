package com.kata.bookstore.service;

import com.kata.bookstore.dto.CartDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CartService {
    CartDto createCart();
    List<CartDto> getAllCarts();
    CartDto findByCartId(String cartId);
    void deleteByCartId(String cartId);
}
