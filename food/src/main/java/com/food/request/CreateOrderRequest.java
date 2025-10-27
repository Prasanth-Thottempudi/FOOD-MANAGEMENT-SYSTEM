package com.food.request;

import lombok.Data;

@Data
public  class CreateOrderRequest {
    private Long userId;
    private Long internalOrderId;
    private Long amount; // paise
    private String receipt;

    // getters & setters
}