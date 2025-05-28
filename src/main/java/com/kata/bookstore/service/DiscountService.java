package com.kata.bookstore.service;

import com.kata.bookstore.dto.CartDto;
import org.springframework.stereotype.Service;

@Service
public interface DiscountService {

    CartDto applyDiscount(String cartId);
}
