package com.kata.DevelopmentBooks.service;

import com.kata.DevelopmentBooks.dto.CartDto;
import org.springframework.stereotype.Service;

@Service
public interface CartService {
    CartDto createCart();
}
