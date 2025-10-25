package com.food.request;

import lombok.Data;
import java.util.List;

@Data
public class OrderRequest {
    private Long userId;
    private List<OrderItemRequest> orderItems;
    private String paymentMethod; // e.g., "CARD", "CASH"
}
