package com.food.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.food.dao.CartRepository;
import com.food.dao.CartitemRepository;
import com.food.dao.FoodItemRepository;
import com.food.dao.UserRepository;
import com.food.entity.Cart;
import com.food.entity.CartItem;
import com.food.entity.FoodItem;
import com.food.entity.User;
import com.food.exception.UserNotFoundException;
import com.food.response.CartItemResponse;
import com.food.response.CartResponse;
import com.food.response.FoodItemResponse;
import com.food.service.CartService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartitemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final FoodItemRepository foodItemRepository;

    /** --------------------- Existing Methods --------------------- */

    @Override
    public CartResponse getCartByUser(Long userId) {
        Cart cart = fetchOrCreateCart(userId);
        return mapToCartResponse(cart);
    }

    @Override
    public CartResponse addItemToCart(Long userId, Long foodItemId, int quantity) {
        Cart cart = fetchOrCreateCart(userId);
        FoodItem foodItem = foodItemRepository.findById(foodItemId)
                .orElseThrow(() -> new RuntimeException("Food item not found"));

        if (!foodItem.isAvailable())
            throw new RuntimeException("This food item is currently unavailable");

        if (foodItem.getAvailableQuantity() < quantity)
            throw new RuntimeException("Requested quantity not available");

        Optional<CartItem> existingItemOpt = cart.getCartItems().stream()
                .filter(item -> item.getFoodItem().getId().equals(foodItemId))
                .findFirst();

        if (existingItemOpt.isPresent()) {
            CartItem existingItem = existingItemOpt.get();
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
            updateItemPrices(existingItem);
        } else {
            CartItem newItem = CartItem.builder()
                    .cart(cart)
                    .foodItem(foodItem)
                    .quantity(quantity)
                    .available(true)
                    .build();
            updateItemPrices(newItem);
            cart.getCartItems().add(newItem);
        }

        cartRepository.save(cart);
        return mapToCartResponse(cart);
    }

    @Override
    public CartResponse updateCartItem(Long userId, Long cartItemId, int quantity) {
        Cart cart = fetchOrCreateCart(userId);
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        if (!cartItem.getCart().getId().equals(cart.getId()))
            throw new RuntimeException("Unauthorized access");

        if (!cartItem.getFoodItem().isAvailable())
            throw new RuntimeException("This food item is unavailable");

        if (quantity <= 0) {
            cartItemRepository.delete(cartItem);
        } else {
            cartItem.setQuantity(quantity);
            updateItemPrices(cartItem);
            cartItemRepository.save(cartItem);
        }

        return mapToCartResponse(fetchOrCreateCart(userId));
    }

    @Override
    public CartResponse removeCartItem(Long userId, Long cartItemId) {
        Cart cart = fetchOrCreateCart(userId);
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        if (!cartItem.getCart().getId().equals(cart.getId()))
            throw new RuntimeException("Unauthorized access");

        cart.getCartItems().remove(cartItem);
        cartItemRepository.delete(cartItem);
        cartRepository.save(cart);

        return mapToCartResponse(fetchOrCreateCart(userId));
    }

    @Override
    public CartResponse clearCart(Long userId) {
        Cart cart = fetchOrCreateCart(userId);
        cartItemRepository.deleteAll(cart.getCartItems());
        cart.getCartItems().clear();
        cartRepository.save(cart);
        return mapToCartResponse(cart);
    }

    @Override
    public CartResponse syncCartAvailability(Long userId) {
        Cart cart = fetchOrCreateCart(userId);
        for (CartItem item : cart.getCartItems()) {
            FoodItem foodItem = item.getFoodItem();
            item.setAvailable(foodItem.isAvailable() && foodItem.getAvailableQuantity() > 0);
        }
        cartRepository.save(cart);
        return mapToCartResponse(cart);
    }

    /** --------------------- Scheduled Task --------------------- */
    // Runs every 1 second and updates all carts
//    @Scheduled(fixedRate = 1000)
//    public void updateCartItemsAvailability() {
//        List<Cart> carts = cartRepository.findAll();
//        for (Cart cart : carts) {
//            boolean updated = false;
//            for (CartItem item : cart.getCartItems()) {
//                FoodItem foodItem = item.getFoodItem();
//                boolean available = foodItem.isAvailable() && foodItem.getAvailableQuantity() > 0;
//                if (item.isAvailable() != available) {
//                    item.setAvailable(available);
//                    updated = true;
//                }
//            }
//            if (updated) {
//                cartRepository.save(cart);
//            }
//        }
//    }

    /** --------------------- Helper Methods --------------------- */
    private Cart fetchOrCreateCart(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        return cartRepository.findByUser(user)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    newCart.setTotalAmount(BigDecimal.ZERO);
                    newCart.setCartItems(new ArrayList<>());
                    return cartRepository.save(newCart);
                });
    }

    private void updateItemPrices(CartItem item) {
        BigDecimal price = item.getFoodItem().getPrice();
        BigDecimal total = price.multiply(BigDecimal.valueOf(item.getQuantity()));
        item.setTotalPrice(total);
        item.setSubTotal(total);
    }

    private CartResponse mapToCartResponse(Cart cart) {
        CartResponse response = new CartResponse();
        response.setId(cart.getId());
        response.setCreatedAt(cart.getCreatedAt());
        response.setUpdatedAt(cart.getUpdatedAt());

        List<CartItemResponse> itemResponses = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (CartItem item : cart.getCartItems()) {
            CartItemResponse itemResp = new CartItemResponse();
            itemResp.setId(item.getId());
            itemResp.setQuantity(item.getQuantity());
            itemResp.setTotalPrice(item.getTotalPrice());
            itemResp.setAvailable(item.isAvailable());

            FoodItem f = item.getFoodItem();
            FoodItemResponse foodResp = new FoodItemResponse();
            foodResp.setId(f.getId());
            foodResp.setName(f.getName());
            foodResp.setDescription(f.getDescription());
            foodResp.setPrice(f.getPrice());
            foodResp.setImageUrl(f.getImageUrl());
            foodResp.setAvailableQuantity(f.getAvailableQuantity());
            foodResp.setCategoryName(f.getCategory() != null ? f.getCategory().getName() : null);

            itemResp.setFoodItem(foodResp);

            itemResponses.add(itemResp);
            totalAmount = totalAmount.add(item.getTotalPrice());
        }

        response.setCartItems(itemResponses);
        response.setTotalAmount(totalAmount);
        return response;
    }

}
