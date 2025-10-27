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

import com.food.dao.CategoryRepository;
import com.food.dao.FoodItemRepository;
import com.food.entity.Category;
import com.food.entity.FoodItem;
import com.food.request.CategoryRequest;
import com.food.response.CategoryResponse;
import com.food.response.FoodItemResponse;
import com.food.response.MinioServiceResponse;
import com.food.response.Response;
import com.food.service.CategoryService;
import com.food.service.MinioServices;

import io.minio.errors.InsufficientDataException;
import io.minio.errors.InvalidResponseException;
import io.minio.errors.XmlParserException;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    
    @Autowired
    private FoodItemRepository foodItemRepository;
    
    @Autowired
    private MinioServices minioServices;

    // Map Category entity to response, including food items
    private CategoryResponse mapToResponse(Category category) {
        CategoryResponse response = new CategoryResponse();
        response.setId(category.getId());
        response.setName(category.getName());
        response.setCategoryImageUrl(category.getCategoryImage());

        // Map associated food items
        List<FoodItemResponse> foodItems = foodItemRepository.findByCategoryId(category.getId())
                .stream()
                .map(foodItem -> {
                    FoodItemResponse fiResponse = new FoodItemResponse();
                    fiResponse.setId(foodItem.getId());
                    fiResponse.setName(foodItem.getName());
                    fiResponse.setDescription(foodItem.getDescription());
                    fiResponse.setPrice(foodItem.getPrice());
                    fiResponse.setImageUrl(foodItem.getImageUrl());
                    return fiResponse;
                })
                .collect(Collectors.toList());

        response.setFoodItems(foodItems);
        return response;
    }

    @Override
    public CategoryResponse createCategory(MultipartFile file, CategoryRequest request) throws InvalidKeyException, IOException, ServerException, InsufficientDataException, InternalException, InvalidResponseException, NoSuchAlgorithmException, XmlParserException, ErrorResponseException, IllegalArgumentException, io.minio.errors.ErrorResponseException, io.minio.errors.InternalException, io.minio.errors.ServerException {
        Category category = new Category();
        category.setName(request.getName());

        String fileName = file.getOriginalFilename();
        MinioServiceResponse saveImage = minioServices.saveImage(file, fileName);
        category.setCategoryImage(saveImage.getImageUrl());

        return mapToResponse(categoryRepository.save(category));
    }

    @Override
    public CategoryResponse updateCategory(Long id, CategoryRequest request) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));

        category.setName(request.getName());
        return mapToResponse(categoryRepository.save(category));
    }

    @Override
    public CategoryResponse getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));

        return mapToResponse(category);
    }

    @Override
    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Response deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));

        categoryRepository.delete(category);

        return new Response("Category deleted successfully", "200");
    }
}
