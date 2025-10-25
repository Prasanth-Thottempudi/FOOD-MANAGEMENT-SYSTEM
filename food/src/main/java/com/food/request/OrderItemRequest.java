package com.food.request;


import lombok.Data;
import java.math.BigDecimal;

@Data
public class OrderItemRequest {
    private Long foodItemId;
    private Integer quantity;
    private String specialInstructions; // optional
}
