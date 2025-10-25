package com.food.response;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class OrderItemResponse {
    private Long id;
    private String foodItemName;
    private BigDecimal price;
    private Integer quantity;
    private String specialInstructions;
    private Boolean isPrepared;
    private Boolean isDelivered;
}
