package com.food.service;

import com.food.response.CartResponse;

public interface CartService {

    /** Get the cart for a specific user */
    CartResponse getCartByUser(Long userId);

    /** Add a food item to the user's cart */
    CartResponse addItemToCart(Long userId, Long foodItemId, int quantity);

    /** Update quantity of an existing cart item */
    CartResponse updateCartItem(Long userId, Long cartItemId, int quantity);

    /** Remove a specific cart item */
    CartResponse removeCartItem(Long userId, Long cartItemId);

    /** Clear all items from the user's cart */
    CartResponse clearCart(Long userId);

    /** Sync cart item availability based on food item stock */
    CartResponse syncCartAvailability(Long userId);
}
