package com.food.response;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderResponse {
    private Long id;
    private String userName;
    private List<OrderItemResponse> orderItems;
    private BigDecimal totalPrice;
    private String status; // e.g., "PENDING", "COMPLETED"
    private String paymentMethod;
    private Boolean paymentCompleted;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
