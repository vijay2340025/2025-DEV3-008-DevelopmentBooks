package com.kata.bookstore.service.impl;

import com.kata.bookstore.constants.DiscountConstants;
import com.kata.bookstore.dto.CartDto;
import com.kata.bookstore.exception.CartNotFoundException;
import com.kata.bookstore.mapper.CartMapper;
import com.kata.bookstore.model.Cart;
import com.kata.bookstore.model.CartItem;
import com.kata.bookstore.model.Product;
import com.kata.bookstore.repository.CartRepository;
import com.kata.bookstore.repository.ProductRepository;
import com.kata.bookstore.service.DiscountService;
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
    ProductRepository productRepository;

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

    /**
     * Convert cart to LinkedHashMap of Product:Quantity sorted in descending order
     * @param cart
     * @return
     */
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

    /**
     *
     * @param cart
     * Calculate the min discount price in a given cart based on the line items added
     * @return minDiscountPrice
     */
    private Optional<Double> findMinDiscountPrice(Cart cart) {
        ArrayList<Double> possiblePrices = new ArrayList<>();
        Map<String, Double> productPriceMap = getProductPriceMap();

        for (int bucketKey = getCartMap(cart).size(); bucketKey > 1; bucketKey--) {

            int i = bucketKey;
            ArrayList<List<String>> finalDiscountBucket = new ArrayList<>();
            HashMap<String, Integer> newCart = getCartMap(cart);

            while (bucketKey > 1) {
                finalDiscountBucket.add(putBooksIntoBucket(newCart, bucketKey));
                if (bucketKey > newCart.size()) --bucketKey;
            }

            double price = calculatePriceAfterDiscount(finalDiscountBucket, productPriceMap);

            double residualPrice = newCart.entrySet().stream()
                    .mapToDouble(value -> {
                        double unitPrice = productPriceMap.get(value.getKey());
                        return unitPrice * value.getValue();
                    }).sum();

            bucketKey = i;
            possiblePrices.add(price + residualPrice);

            log.info(String.format("Euro: %.2f €%n", price + residualPrice));
            log.info("Bucket = " + finalDiscountBucket);
            log.info("Cart = " + newCart);
        }

        return possiblePrices.stream().min(Double::compareTo);
    }

    /**
     *
     * @param cart
     * @param bucketKey
     * Function to put the books into a corresponding bucket to finalize the best bucket combination
     * @return
     */
    private List<String> putBooksIntoBucket(HashMap<String, Integer> cart, int bucketKey) {
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

    /**
     * Remove the stale items in the cart when quantity becomes 0
     * @param cart
     */
    private void removeStaleItems(HashMap<String, Integer> cart) {
        cart.entrySet().removeIf(bookItem -> bookItem.getValue() == 0);
    }

    private Map<Integer, List<String>> createEmptyBucket() {
        return IntStream.rangeClosed(1, 5).boxed()
                .collect(Collectors.toMap(Function.identity(), ArrayList::new));
    }

    private double calculatePriceAfterDiscount(ArrayList<List<String>> finalDiscountBucket, Map<String, Double> productPriceMap) {
        return finalDiscountBucket.stream().filter(bucket -> !bucket.isEmpty())
                .map(eachBucket -> {
                    Integer discount = DiscountConstants.DISCOUNT.get(eachBucket.size());
                    double totalPrice = findTotalPriceOfBucket(eachBucket, productPriceMap);
                    return totalPrice - (totalPrice * ((double) discount / 100));
                })
                .mapToDouble(i -> i)
                .sum();
    }

    private double findTotalPriceOfBucket(List<String> bucket, Map<String, Double> productPriceMap) {
        return bucket.stream()
                .mapToDouble(productPriceMap::get)
                .sum();
    }

    private Map<String, Double> getProductPriceMap() {
        return productRepository.findAll().stream()
                .collect(Collectors.toMap(
                        Product::getProductId,
                        Product::getListPrice
                ));

    }

}
