package com.food.service;

import com.food.request.FoodItemRequest;
import com.food.response.FoodItemResponse;
import com.food.response.Response;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FoodItemService {
    FoodItemResponse createFoodItem(MultipartFile file, FoodItemRequest request) throws Exception;
    List<FoodItemResponse> getAllFoodItems();
    FoodItemResponse getFoodItemById(Long id);
    Response deleteFoodItem(Long id);
}
