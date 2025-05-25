package com.kata.DevelopmentBooks.service.impl;

import com.kata.DevelopmentBooks.dto.CartDto;
import com.kata.DevelopmentBooks.mapper.CartMapper;
import com.kata.DevelopmentBooks.model.Cart;
import com.kata.DevelopmentBooks.repository.CartRepository;
import com.kata.DevelopmentBooks.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    CartRepository cartRepository;

    @Autowired
    CartMapper cartMapper;

    @Override
    public CartDto createCart() {
        Cart cart = new Cart();
        cart.setCartId(UUID.randomUUID().toString());
        cartRepository.save(cart);
        return cartMapper.toCartDto(cart);
    }

    @Override
    public List<CartDto> getAllCart() {
        return cartRepository.findAll()
                .stream().map(cart -> cartMapper.toCartDto(cart))
                .toList();
    }
}
