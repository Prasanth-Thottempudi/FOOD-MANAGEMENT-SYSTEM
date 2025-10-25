package com.food.service;

import java.io.IOException;
import java.rmi.ServerException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.apache.logging.log4j.util.InternalException;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.multipart.MultipartFile;

import com.food.request.CategoryRequest;
import com.food.response.CategoryResponse;
import com.food.response.Response;

import io.minio.errors.InsufficientDataException;
import io.minio.errors.InvalidResponseException;
import io.minio.errors.XmlParserException;

public interface CategoryService {
    CategoryResponse createCategory(MultipartFile file,CategoryRequest request) throws InvalidKeyException, ErrorResponseException, ServerException, InsufficientDataException, InternalException, InvalidResponseException, NoSuchAlgorithmException, XmlParserException, IllegalArgumentException, io.minio.errors.ErrorResponseException, io.minio.errors.InternalException, io.minio.errors.ServerException, IOException;

    CategoryResponse updateCategory(String id, CategoryRequest request);

    CategoryResponse getCategoryById(String id);

    List<CategoryResponse> getAllCategories();

    Response deleteCategory(String id);
}
