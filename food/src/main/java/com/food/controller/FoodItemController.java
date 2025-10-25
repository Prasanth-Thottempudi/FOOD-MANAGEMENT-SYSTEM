package com.food.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.food.request.FoodItemRequest;
import com.food.response.FoodItemResponse;
import com.food.response.Response;
import com.food.service.FoodItemService;

@RestController
@RequestMapping("/api/food-items")
public class FoodItemController {

    @Autowired
    private FoodItemService foodItemService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    // Create Food Item
    @PostMapping(value = "/save-food", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<FoodItemResponse> createFoodItem(
            @RequestPart("file") MultipartFile file,
            @RequestPart("data") String data) throws Exception {

        FoodItemRequest request = objectMapper.readValue(data, FoodItemRequest.class);
        FoodItemResponse response = foodItemService.createFoodItem(file, request);
        return ResponseEntity.ok(response);
    }

    // Update Food Item
//    @PutMapping(value = "/update/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<FoodItemResponse> updateFoodItem(
//            @PathVariable String id,
//            @RequestPart(value = "file", required = false) MultipartFile file,
//            @RequestPart("data") String data) throws Exception {
//
//        FoodItemRequest request = objectMapper.readValue(data, FoodItemRequest.class);
//        FoodItemResponse response = foodItemService.updateFoodItem(id, file, request);
//        return ResponseEntity.ok(response);
//    }

    // Get Food Item by ID
    @GetMapping("/get-foodItem-by-id/{id}")
    public ResponseEntity<FoodItemResponse> getFoodItemById(@PathVariable Long id) {
        FoodItemResponse response = foodItemService.getFoodItemById(id);
        return ResponseEntity.ok(response);
    }

    // Get All Food Items
    @GetMapping("/get-all-food-items")
    public ResponseEntity<List<FoodItemResponse>> getAllFoodItems() {
        List<FoodItemResponse> foodItems = foodItemService.getAllFoodItems();
        return ResponseEntity.ok(foodItems);
    }

    // Delete Food Item
    @DeleteMapping("/delete-food-item-by-id/{id}")
    public ResponseEntity<Response> deleteFoodItem(@PathVariable Long id) {
        Response response = foodItemService.deleteFoodItem(id);
        return ResponseEntity.ok(response);
    }
}
