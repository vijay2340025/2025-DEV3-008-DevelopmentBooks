package com.kata.DevelopmentBooks.service.impl;


import com.kata.DevelopmentBooks.dto.CartDto;
import com.kata.DevelopmentBooks.dto.CartItemDto;
import com.kata.DevelopmentBooks.exception.CartNotFoundException;
import com.kata.DevelopmentBooks.exception.ProductNotFoundException;
import com.kata.DevelopmentBooks.mapper.CartItemMapper;
import com.kata.DevelopmentBooks.mapper.CartMapper;
import com.kata.DevelopmentBooks.model.Cart;
import com.kata.DevelopmentBooks.model.CartItem;
import com.kata.DevelopmentBooks.model.PriceSummary;
import com.kata.DevelopmentBooks.model.Product;
import com.kata.DevelopmentBooks.repository.CartRepository;
import com.kata.DevelopmentBooks.repository.ProductRepository;
import com.kata.DevelopmentBooks.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class CartItemServiceImpl implements CartItemService {

    @Autowired
    CartItemMapper cartItemMapper;

    @Autowired
    CartMapper cartMapper;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    ProductRepository productRepository;

    @Override
    public CartDto addCartItem(String cartId, List<CartItemDto> cartItemDtoList) {

        validateCartItems(cartItemDtoList);
        Cart cart = cartRepository.findByCartId(cartId)
                .orElseThrow(() -> new CartNotFoundException(cartId));

        List<CartItem> items = Optional.ofNullable(cart.getItems())
                .orElseGet(ArrayList::new);

        List<CartItemDto> updatedCartItemsDto = consolidateCartItems(cartItemDtoList);

        updatedCartItemsDto.forEach(cartItemDto -> {
            String productId = cartItemDto.getProductId();
            CartItem existingItem = items.stream()
                    .filter(item -> productId.equals(item.getProductId()))
                    .findFirst()
                    .orElse(null);

            if (existingItem != null) {
                existingItem.setQuantity(existingItem.getQuantity() + cartItemDto.getQuantity());
            } else {
                cartItemDto.setItemId(UUID.randomUUID().toString());
                items.add(cartItemMapper.toCartItem(cartItemDto));
            }

            cart.setItems(items);
        });

        updatePriceSummary(cart);
        Cart updatedCart = cartRepository.save(cart);
        return cartMapper.toCartDto(updatedCart);
    }

    @Override
    public CartDto deleteCartItem(String cartId, String itemId) {
        Optional<Cart> optionalCart = cartRepository.findByCartId(cartId);
        Cart cart = optionalCart.orElseThrow(() -> new CartNotFoundException(cartId));
        List<CartItem> updatedCartItemList = cart.getItems().stream()
                .filter(cartItem -> !cartItem.getItemId().equals(itemId))
                .toList();
        cart.setItems(updatedCartItemList);
        updatePriceSummary(cart);
        return cartMapper.toCartDto(cartRepository.save(cart));
    }

    private void updatePriceSummary(Cart cart) {
        AtomicReference<Double> total = new AtomicReference<>(0d);
        cart.getItems()
                .forEach(cartItem -> {
                    String productId = cartItem.getProductId();
                    Optional<Product> optionalProduct = productRepository.findById(productId);
                    Product product = optionalProduct.orElseThrow(() -> new ProductNotFoundException(productId));
                    total.set(total.get() + product.getListPrice() * cartItem.getQuantity());
                });
        PriceSummary priceSummary = new PriceSummary();
        priceSummary.setCurrency("EUR");
        priceSummary.setOriginalPrice(total.get());
        cart.setPricing(priceSummary);
    }

    private void validateCartItems(List<CartItemDto> cartItemDtoList) {
        cartItemDtoList.forEach(cartItemDto -> {
            Optional<Product> optionalProduct = productRepository.findById(cartItemDto.getProductId());
            optionalProduct.orElseThrow(() -> new ProductNotFoundException(cartItemDto.getProductId()));
        });
    }

    private List<CartItemDto> consolidateCartItems(List<CartItemDto> cartItemDtoList) {
        return cartItemDtoList.stream().collect(
                        Collectors.toMap(
                                CartItemDto::getProductId,
                                itemDto -> {
                                    CartItemDto copy = new CartItemDto();
                                    copy.setProductId(itemDto.getProductId());
                                    copy.setQuantity(itemDto.getQuantity());
                                    copy.setItemId(itemDto.getItemId()); // use first item's ID
                                    return copy;
                                },
                                (existing, duplicate) -> {
                                    existing.setQuantity(existing.getQuantity() + duplicate.getQuantity());
                                    return existing;
                                }
                        )).values()
                .stream()
                .toList();
    }
}
