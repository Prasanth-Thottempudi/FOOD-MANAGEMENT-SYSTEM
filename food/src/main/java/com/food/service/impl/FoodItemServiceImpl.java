package com.food.service.impl;

import java.io.IOException;
import java.rmi.ServerException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.util.InternalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.multipart.MultipartFile;

import com.food.dao.CartitemRepository;
import com.food.dao.CategoryRepository;
import com.food.dao.FoodItemRepository;
import com.food.entity.CartItem;
import com.food.entity.Category;
import com.food.entity.FoodItem;
import com.food.request.FoodItemRequest;
import com.food.response.FoodItemResponse;
import com.food.response.MinioServiceResponse;
import com.food.response.Response;
import com.food.service.FoodItemService;
import com.food.service.MinioServices;

import io.minio.errors.InsufficientDataException;
import io.minio.errors.InvalidResponseException;
import io.minio.errors.XmlParserException;
import jakarta.transaction.Transactional;

@Service
public class FoodItemServiceImpl implements FoodItemService {

    @Autowired
    private FoodItemRepository foodItemRepository;

    @Autowired
    private CategoryRepository categoryRepository;
    
    @Autowired
    private  CartitemRepository cartItemRepository;


    @Autowired
    private MinioServices minioServices;

    private FoodItemResponse mapToResponse(FoodItem foodItem) {
        FoodItemResponse response = new FoodItemResponse();
        response.setId(foodItem.getId());
        response.setName(foodItem.getName());
        response.setDescription(foodItem.getDescription());
        response.setPrice(foodItem.getPrice());
        response.setImageUrl(foodItem.getImageUrl());
        response.setAvailableQuantity(foodItem.getAvailableQuantity());
        response.setCategoryName(foodItem.getCategory().getName());
        return response;
    }

    @Override
    public FoodItemResponse createFoodItem(MultipartFile file, FoodItemRequest request)
            throws InvalidKeyException, ServerException, InternalException, IOException,
                   NoSuchAlgorithmException, InsufficientDataException, InvalidResponseException,
                   XmlParserException, ErrorResponseException, IllegalArgumentException,
                   io.minio.errors.ErrorResponseException, io.minio.errors.InternalException,
                   io.minio.errors.ServerException {

        // ✅ Convert categoryId from String to Long
        Long categoryId;
        try {
            categoryId = Long.parseLong(request.getCategoryId());
        } catch (NumberFormatException e) {
            throw new RuntimeException("Invalid category ID format: " + request.getCategoryId());
        }

        // ✅ Fetch category from DB
        Category category = categoryRepository.findById(categoryId.toString())
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + categoryId));

        // ✅ Build new FoodItem
        FoodItem foodItem = new FoodItem();
        foodItem.setName(request.getName());
        foodItem.setDescription(request.getDescription());
        foodItem.setPrice(request.getPrice());
        foodItem.setCategory(category);
        foodItem.setAvailableQuantity(
                request.getAvailableQuantity() != null ? request.getAvailableQuantity() : 0);
        foodItem.setAvailable(request.isAvailable()); // ✅ set availability

        // ✅ Upload image to MinIO (if provided)
        if (file != null && !file.isEmpty()) {
            String fileName = file.getOriginalFilename();
            MinioServiceResponse uploadResponse = minioServices.saveImage(file, fileName);
            foodItem.setImageUrl(uploadResponse.getImageUrl());
        }

        // ✅ Save and map to response
        FoodItem savedItem = foodItemRepository.save(foodItem);
        return mapToResponse(savedItem);
    }

    public FoodItemResponse updateFoodItem(Long id, FoodItemRequest request, MultipartFile file) {
        FoodItem existing = foodItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Food item not found with id: " + id));

        existing.setName(request.getName());
        existing.setDescription(request.getDescription());
        existing.setPrice(request.getPrice());
        existing.setAvailableQuantity(request.getAvailableQuantity() != null ? request.getAvailableQuantity() : existing.getAvailableQuantity());

        if (request.getCategoryId() != null) {
            Category category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found with id: " + request.getCategoryId()));
            existing.setCategory(category);
        }

        // Update image if new file is provided
        if (file != null && !file.isEmpty()) {
            try {
                MinioServiceResponse uploadResponse = minioServices.saveImage(file, file.getOriginalFilename());
                existing.setImageUrl(uploadResponse.getImageUrl());
            } catch (Exception e) {
                throw new RuntimeException("Failed to upload image: " + e.getMessage());
            }
        }

        return mapToResponse(foodItemRepository.save(existing));
    }

    @Override
    public FoodItemResponse getFoodItemById(Long id) {
        FoodItem foodItem = foodItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Food item not found with id: " + id));
        return mapToResponse(foodItem);
    }

    @Override
    public List<FoodItemResponse> getAllFoodItems() {
        return foodItemRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Response deleteFoodItem(Long id) {
        FoodItem foodItem = foodItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Food item not found with id: " + id));

        foodItemRepository.delete(foodItem);
        return new Response("Food item deleted successfully", "200");
    }
    
    @Transactional
    public void updateFoodAvailability(Long foodItemId, boolean available) {
        FoodItem foodItem = foodItemRepository.findById(foodItemId)
                .orElseThrow(() -> new RuntimeException("Food item not found"));

        foodItem.setAvailable(available);
        foodItemRepository.save(foodItem);

        List<CartItem> cartItems = cartItemRepository.findByFoodItem(foodItem);
        for (CartItem cartItem : cartItems) {
            cartItem.setAvailable(available);
        }
        cartItemRepository.saveAll(cartItems);
    }
    
    
}
