package com.kata.DevelopmentBooks.controller;

import com.kata.DevelopmentBooks.dto.CartDto;
import com.kata.DevelopmentBooks.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/carts")
public class CartController {

    @Autowired
    CartService cartService;

    @PostMapping(value = {"/", ""})
    public ResponseEntity<CartDto> createCart() {
        CartDto cartDto = cartService.createCart();
        URI location = URI.create("/carts/" + cartDto.getCartId());
        return ResponseEntity.created(location).body(cartDto);
    }

    @GetMapping(value = {"/", ""})
    public ResponseEntity<List<CartDto>> getCarts() {
        return ResponseEntity.ok(cartService.getAllCart());
    }
}
