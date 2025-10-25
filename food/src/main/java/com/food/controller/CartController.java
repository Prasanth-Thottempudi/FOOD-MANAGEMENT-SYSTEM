package com.food.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.food.service.CartService;
import com.food.response.CartResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    /** Get the cart for a user */
    @GetMapping("/{userId}")
    public ResponseEntity<CartResponse> getCart(@PathVariable Long userId) {
        return ResponseEntity.ok(cartService.getCartByUser(userId));
    }

    /** Add a food item to the cart */
    @PostMapping("/{userId}/add/{foodItemId}")
    public ResponseEntity<CartResponse> addItem(
            @PathVariable Long userId,
            @PathVariable Long foodItemId,
            @RequestParam int quantity) {
        return ResponseEntity.ok(cartService.addItemToCart(userId, foodItemId, quantity));
    }

    /** Update quantity of a cart item */
    @PutMapping("/{userId}/update/{cartItemId}")
    public ResponseEntity<CartResponse> updateItem(
            @PathVariable Long userId,
            @PathVariable Long cartItemId,
            @RequestParam int quantity) {
        return ResponseEntity.ok(cartService.updateCartItem(userId, cartItemId, quantity));
    }

    /** Remove a cart item */
    @DeleteMapping("/{userId}/remove/{cartItemId}")
    public ResponseEntity<CartResponse> removeItem(
            @PathVariable Long userId,
            @PathVariable Long cartItemId) {
        return ResponseEntity.ok(cartService.removeCartItem(userId, cartItemId));
    }
    @DeleteMapping("/{userId}/clear")
    public ResponseEntity<CartResponse> clearCart(@PathVariable Long userId) {
        CartResponse clearedCart = cartService.clearCart(userId);
        return ResponseEntity.ok(clearedCart);
    }


    /** Sync cart availability with current food item stock */
    @PutMapping("/{userId}/sync-availability")
    public ResponseEntity<CartResponse> syncAvailability(@PathVariable Long userId) {
        return ResponseEntity.ok(cartService.syncCartAvailability(userId));
    }
}
