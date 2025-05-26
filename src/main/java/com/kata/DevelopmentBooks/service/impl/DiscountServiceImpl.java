package com.kata.DevelopmentBooks.service.impl;

import com.kata.DevelopmentBooks.constants.DiscountConstants;
import com.kata.DevelopmentBooks.dto.CartDto;
import com.kata.DevelopmentBooks.exception.CartNotFoundException;
import com.kata.DevelopmentBooks.mapper.CartMapper;
import com.kata.DevelopmentBooks.model.Cart;
import com.kata.DevelopmentBooks.model.CartItem;
import com.kata.DevelopmentBooks.repository.CartRepository;
import com.kata.DevelopmentBooks.service.DiscountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@Slf4j
public class DiscountServiceImpl implements DiscountService {

    @Autowired
    CartRepository cartRepository;

    @Autowired
    CartMapper cartMapper;

    @Override
    public CartDto applyDiscount(String cartId) {
        Optional<Cart> optionalCart = cartRepository.findByCartId(cartId);
        Cart cart = optionalCart.orElseThrow(() -> new CartNotFoundException(cartId));
        Optional<Double> minDiscountPrice = findMinDiscountPrice(cart);
        Double discountPrice = minDiscountPrice.orElseThrow(() -> new RuntimeException("Exception in calculating discount"));
        cart.getPricing().setDiscountedPrice(discountPrice);
        return cartMapper.toCartDto(cartRepository.save(cart));
    }

    private static HashMap<String, Integer> getCartMap(Cart cart) {
        return cart.getItems().stream()
                .collect(Collectors.toMap(
                        CartItem::getProductId,
                        CartItem::getQuantity
                ))
                .entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }

    private Optional<Double> findMinDiscountPrice(Cart c) {
        ArrayList<Double> possiblePrices = new ArrayList<>();

        for (int bucketKey = getCartMap(c).size(); bucketKey > 1; bucketKey--) {

            int i = bucketKey;
            ArrayList<List<String>> finalDiscountBucket = new ArrayList<>();
            HashMap<String, Integer> cart = getCartMap(c);

            while (bucketKey > 1) {
                finalDiscountBucket.add(putBooksIntoBucket(cart, bucketKey));
                if (bucketKey > cart.size()) --bucketKey;
            }

            double price = calculatePriceAfterDiscount(finalDiscountBucket);

            double sum = cart.values().stream().map(integer -> integer * 50).mapToDouble(ii -> ii)
                    .sum();

            bucketKey = i;
            possiblePrices.add(price + sum);

            log.info(String.format("Euro: %.2f €%n", price + sum));
            log.info("Bucket = " + finalDiscountBucket);
            log.info("Cart = " + cart);
        }

        return possiblePrices.stream().min(Double::compareTo);
    }

    private static List<String> putBooksIntoBucket(HashMap<String, Integer> cart, int bucketKey) {
        Map<Integer, List<String>> bucket = createEmptyBucket();
        Iterator<Map.Entry<String, Integer>> cartIterator = cart.entrySet().iterator();
        List<String> selectedBucket = bucket.get(bucketKey);
        while (cartIterator.hasNext() && selectedBucket.size() < bucketKey) {
            Map.Entry<String, Integer> bookItem = cartIterator.next();
            if (cart.size() >= bucketKey) {
                selectedBucket.add(bookItem.getKey());
                cart.put(bookItem.getKey(), bookItem.getValue() - 1);
            }
        }
        removeStaleItems(cart);
        return selectedBucket;
    }

    private static void removeStaleItems(HashMap<String, Integer> cart) {
        cart.entrySet().removeIf(bookItem -> bookItem.getValue() == 0);
    }

    private static Map<Integer, List<String>> createEmptyBucket() {
        return IntStream.rangeClosed(1, 5).boxed()
                .collect(Collectors.toMap(Function.identity(), ArrayList::new));
    }

    private static double calculatePriceAfterDiscount(ArrayList<List<String>> finalDiscountBucket) {
        return finalDiscountBucket.stream().filter(bucket -> !bucket.isEmpty())
                .map(eachBucket -> {
                    Integer discount = DiscountConstants.DISCOUNT.get(eachBucket.size());
                    long totalPrice = eachBucket.size() * 50L;
                    return totalPrice - (totalPrice * ((double) discount / 100));
                })
                .mapToDouble(i -> i)
                .sum();
    }
}
