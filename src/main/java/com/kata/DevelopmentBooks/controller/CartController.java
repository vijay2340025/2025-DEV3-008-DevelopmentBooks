package com.kata.DevelopmentBooks.controller;

import com.kata.DevelopmentBooks.dto.CartDto;
import com.kata.DevelopmentBooks.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        return ResponseEntity.ok(cartService.getAllCarts());
    }

    @GetMapping(value = {"/{cartId}", "/{cartId}/"})
    public ResponseEntity<CartDto> getCartById(@PathVariable String cartId) {
        return ResponseEntity.ok(cartService.findByCartId(cartId));
    }

    @DeleteMapping(value = {"/{cartId}", "/{cartId}/"})
    public ResponseEntity<Void> deleteCartById(@PathVariable String cartId) {
        cartService.deleteByCartId(cartId);
        return ResponseEntity.noContent().build();
    }
}
