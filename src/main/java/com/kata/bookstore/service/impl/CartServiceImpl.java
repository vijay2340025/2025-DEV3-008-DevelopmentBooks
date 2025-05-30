package com.kata.bookstore.service.impl;

import com.kata.bookstore.dto.CartDto;
import com.kata.bookstore.exception.CartNotFoundException;
import com.kata.bookstore.mapper.CartMapper;
import com.kata.bookstore.model.Cart;
import com.kata.bookstore.repository.CartRepository;
import com.kata.bookstore.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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
    public List<CartDto> getAllCarts() {
        return cartRepository.findAll()
                .stream().map(cart -> cartMapper.toCartDto(cart))
                .toList();
    }

    @Override
    public CartDto findByCartId(String cartId) {
        Optional<Cart> optionalCart = cartRepository.findByCartId(cartId);
        return optionalCart.map(cart -> cartMapper.toCartDto(cart))
                .orElseThrow(() -> new CartNotFoundException(cartId));
    }

    @Override
    public void deleteByCartId(String cartId) {
        Optional<Cart> optionalCart = cartRepository.findByCartId(cartId);
        Cart cart = optionalCart.orElseThrow(() -> new CartNotFoundException(cartId));
        cartRepository.delete(cart);
    }
}
