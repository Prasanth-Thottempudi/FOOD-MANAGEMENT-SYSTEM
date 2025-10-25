package com.food.service;

import java.io.IOException;
import java.rmi.ServerException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.apache.logging.log4j.util.InternalException;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.multipart.MultipartFile;

import com.food.entity.User;
import com.food.request.LoginRequest;
import com.food.request.UserRequest;
import com.food.response.Response;
import com.food.response.UserResponse;

import io.minio.errors.InsufficientDataException;
import io.minio.errors.InvalidResponseException;
import io.minio.errors.XmlParserException;

public interface UserService {
	

	UserResponse  saveUser(MultipartFile file,UserRequest request) throws InvalidKeyException, ErrorResponseException, ServerException, InsufficientDataException, InternalException, InvalidResponseException, NoSuchAlgorithmException, XmlParserException, IllegalArgumentException, io.minio.errors.ErrorResponseException, io.minio.errors.InternalException, io.minio.errors.ServerException, IOException;
	
	Response login(LoginRequest request);
	
	UserResponse getUserById(String userId);
	
	List<UserResponse> getAllUsers();
	
	UserResponse updateUser(UserRequest request);
	
	UserResponse deleteUser(String userid);
	
	
	
	

}
