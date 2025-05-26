package com.kata.DevelopmentBooks.controller;

import com.kata.DevelopmentBooks.dto.CartDto;
import com.kata.DevelopmentBooks.service.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/carts/{cartId}/discounts")
public class DiscountController {

    @Autowired
    DiscountService discountService;

    @GetMapping(value = {"/", ""})
    public ResponseEntity<CartDto> applyDiscount(@PathVariable String cartId) {
        return ResponseEntity.ok(discountService.applyDiscount(cartId));
    }
}
