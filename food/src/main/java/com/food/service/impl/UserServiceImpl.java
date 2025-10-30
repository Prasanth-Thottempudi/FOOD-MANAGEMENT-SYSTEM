package com.food.service.impl;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.food.dao.UserRepository;
import com.food.entity.Address;
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
import org.springframework.web.ErrorResponseException;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MinioServices minioServices;

    @Override
    public UserResponse saveUser(MultipartFile profileImage, UserRequest request)
            throws InvalidKeyException, ErrorResponseException, InsufficientDataException, 
                   InvalidResponseException, NoSuchAlgorithmException, XmlParserException, 
                   IOException, io.minio.errors.ErrorResponseException, 
                   io.minio.errors.InternalException, io.minio.errors.ServerException {

        // Check for duplicate email
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new AlreadyExistsException("User with email " + request.getEmail() + " already exists!");
        }

        User user = new User();
        user.setPassword(request.getPassword());
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setMobileNumber(request.getMobileNumber());
        user.setLatitude(request.getLatitude());
        user.setLongitude(request.getLongitude());

        // Map Address if provided
        if (request.getAddressLine1() != null || request.getAddressLine2() != null) {
            Address address = new Address();
            address.setAddressLine1(request.getAddressLine1());
            address.setAddressLine2(request.getAddressLine2());
            address.setCity(request.getCity());
            address.setState(request.getState());
            address.setPostalCode(request.getPostalCode());
            address.setCountry(request.getCountry());

            // Cascade will save the address automatically
            user.setAddress(address);
        }

        // Handle profile image
        if (profileImage != null && !profileImage.isEmpty()) {
            String profileName = profileImage.getOriginalFilename();
            MinioServiceResponse imageSaved = minioServices.saveImage(profileImage, profileName);
            user.setProfileUrl(imageSaved.getImageUrl());
        }

        // Save user (Address will be saved automatically due to CascadeType.ALL)
        User saved = userRepository.save(user);
        return mapToResponse(saved);
    }

    @Override
    public Response login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + request.getUsername()));

        if (!request.getPassword().equals(user.getPassword())) {
            throw new InvalidCredentialsException("Invalid username or password");
        }

        Response response = new Response();
        response.setSuccessMessage("User logged in successfully!");
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
        List<User> users = userRepository.findAll();
        return mapToResponseList(users);
    }

    @Override
    public UserResponse updateUser(UserRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + request.getEmail()));

        if (request.getName() != null) user.setName(request.getName());
        if (request.getPassword() != null && !request.getPassword().isBlank()) user.setPassword(request.getPassword());
        if (request.getMobileNumber() != null) user.setMobileNumber(request.getMobileNumber());
        if (request.getLatitude() != null) user.setLatitude(request.getLatitude());
        if (request.getLongitude() != null) user.setLongitude(request.getLongitude());

        // Update or create Address
        if (request.getAddressLine1() != null || request.getAddressLine2() != null) {
            Address address = user.getAddress() != null ? user.getAddress() : new Address();

            if (request.getAddressLine1() != null) address.setAddressLine1(request.getAddressLine1());
            if (request.getAddressLine2() != null) address.setAddressLine2(request.getAddressLine2());
            if (request.getCity() != null) address.setCity(request.getCity());
            if (request.getState() != null) address.setState(request.getState());
            if (request.getPostalCode() != null) address.setPostalCode(request.getPostalCode());
            if (request.getCountry() != null) address.setCountry(request.getCountry());

            user.setAddress(address);
        }

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
        response.setLatitude(user.getLatitude());
        response.setLongitude(user.getLongitude());

        if (user.getAddress() != null) {
            response.setAddressLine1(user.getAddress().getAddressLine1());
            response.setAddressLine2(user.getAddress().getAddressLine2());
            response.setCity(user.getAddress().getCity());
            response.setState(user.getAddress().getState());
            response.setPostalCode(user.getAddress().getPostalCode());
            response.setCountry(user.getAddress().getCountry());
        }

        return response;
    }

    private List<UserResponse> mapToResponseList(List<User> users) {
        List<UserResponse> responses = new ArrayList<>();
        for (User user : users) {
            responses.add(mapToResponse(user));
        }
        return responses;
    }
}
