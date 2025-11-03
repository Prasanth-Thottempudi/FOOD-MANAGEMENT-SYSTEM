package com.food.request;

import lombok.Data;

@Data
public class LocationUpdate {
    private String agentId;
    private double latitude;
    private double longitude;

    // Getters and setters
}
