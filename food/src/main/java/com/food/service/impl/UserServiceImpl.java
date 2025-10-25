package com.food.service.impl;


import java.io.IOException;
import java.rmi.ServerException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.util.InternalException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.multipart.MultipartFile;

import com.food.dao.UserRepository;
import com.food.entity.User;
import com.food.exception.AlreadyExistsException;
import com.food.exception.InvalidCredentialsException;
import com.food.exception.ResourceNotFoundException;
import com.food.request.LoginRequest;
import com.food.request.UserRequest;
import com.food.response.MinioServiceResponse;
import com.food.response.Response;
import com.food.response.UserResponse;
import com.food.service.MinioServices;
import com.food.service.UserService;

import io.minio.errors.InsufficientDataException;
import io.minio.errors.InvalidResponseException;
import io.minio.errors.XmlParserException;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
    private  UserRepository userRepository;
	
	@Autowired
	private MinioServices minioServices;

    @Override
    public UserResponse saveUser(MultipartFile profileImage,UserRequest request) throws InvalidKeyException, ErrorResponseException, ServerException, InsufficientDataException, InternalException, InvalidResponseException, NoSuchAlgorithmException, XmlParserException, IllegalArgumentException, io.minio.errors.ErrorResponseException, io.minio.errors.InternalException, io.minio.errors.ServerException, IOException {
        // Check for duplicate email
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new AlreadyExistsException("User with email " + request.getEmail() + " already exists!");
        }

        User user = new User();
        user.setPassword(request.getPassword());
        String profileName = profileImage.getOriginalFilename();

        MinioServiceResponse imageSaved = minioServices.saveImage(profileImage, profileName);
        
        BeanUtils.copyProperties(request, user);

        String imageUrl = imageSaved.getImageUrl();
        user.setProfileUrl(imageUrl);
        User saved = userRepository.save(user);

        return mapToResponse(saved);
    }

    @Override
    public Response login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + request.getUsername()));

     if(!(request.getPassword().equalsIgnoreCase(user.getPassword()))){
    	 throw new InvalidCredentialsException("invalid username and password");
     }

        Response response=new Response();
        response.setSuccessMessage("user logged in successfully!");
        return response;
    }

    @Override
    public UserResponse getUserById(String userId) {
        Long id = Long.parseLong(userId);

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));

        return mapToResponse(user);
    }

    @Override
    public List<UserResponse> getAllUsers() {
       List<User> users = userRepository.findAll()
                .stream().collect(Collectors.toList());

        return  mapToResponseList(users);
    }

    @Override
    public UserResponse updateUser(UserRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + request.getEmail()));

        if (request.getName() != null)
            user.setName(request.getName());

        if (request.getPassword() != null && !request.getPassword().isBlank())
            user.setPassword(request.getPassword());

        User updated = userRepository.save(user);

        return mapToResponse(updated);
    }

    @Override
    public UserResponse deleteUser(String userId) {
        Long id = Long.parseLong(userId);

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));

        userRepository.delete(user);

        return mapToResponse(user);
    }
    private UserResponse mapToResponse(User user) {
        UserResponse response = new UserResponse();
        response.setUserId(user.getId());
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        response.setMobileNumber(user.getMobileNumber());
        response.setProfileUrl(user.getProfileUrl());
        return response;
    }


    private List<UserResponse> mapToResponseList(List<User> users) {
        List<UserResponse> responses = new ArrayList<>();
        for (User user : users) {
            UserResponse response = new UserResponse();
            response.setUserId(user.getId());
            response.setName(user.getName());
            response.setEmail(user.getEmail());
            response.setMobileNumber(user.getMobileNumber());
            response.setProfileUrl(user.getProfileUrl());
            responses.add(response);
        }
        return responses;
    }

  
    
}
