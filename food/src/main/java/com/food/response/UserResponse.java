package com.food.response;

import lombok.Data;

@Data
public class UserResponse {

    private Long userId;
    private String name;
    private String email;
    private String mobileNumber;
    private String profileUrl;

    // Location
    private Double latitude;
    private Double longitude;

    // Address
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String postalCode;
    private String country;
}
