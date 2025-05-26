package com.kata.DevelopmentBooks.controller;

import com.kata.DevelopmentBooks.dto.CartDto;
import com.kata.DevelopmentBooks.dto.CartItemDto;
import com.kata.DevelopmentBooks.service.CartItemService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carts/{cartId}/lineitems")
@Validated
public class CartItemController {

    @Autowired
    CartItemService cartItemService;

    @PostMapping(value = {"/", ""})
    public ResponseEntity<CartDto> addLineItem(@PathVariable String cartId, @Valid @RequestBody List<CartItemDto> cartItemDtoList) {
        CartDto cartDto = cartItemService.addCartItem(cartId, cartItemDtoList);
        return ResponseEntity.status(HttpStatus.CREATED).body(cartDto);
    }

    @DeleteMapping(value = {"/{itemId}"})
    public ResponseEntity<CartDto> deleteLineItemById(@PathVariable String cartId, @PathVariable String itemId) {
        CartDto cartDto = cartItemService.deleteCartItem(cartId, itemId);
        return ResponseEntity.ok().body(cartDto);
    }
}
