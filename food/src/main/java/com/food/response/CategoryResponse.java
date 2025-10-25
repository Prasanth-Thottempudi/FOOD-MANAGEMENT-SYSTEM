package com.food.response;

import java.util.List;

import com.food.entity.FoodItem;

import lombok.Data;

@Data
public class CategoryResponse {
    private Long id;
    private String name;
    private String categoryImageUrl;
    
    private List<FoodItemResponse> foodItems;
}
