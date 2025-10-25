package com.food.request;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class FoodItemRequest {
    private String name;             // Name of the food item
    private String description;      // Description of the food item
    private BigDecimal price;        // Price
    private String categoryId;       // Associated category ID
    private Integer availableQuantity; // Quantity available
}
