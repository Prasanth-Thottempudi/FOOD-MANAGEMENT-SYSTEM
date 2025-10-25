package com.food.response;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class CartItemResponse {
    private Long id;
    private FoodItemResponse foodItem;
    private Integer quantity;
    private BigDecimal totalPrice;
    private boolean available;
}