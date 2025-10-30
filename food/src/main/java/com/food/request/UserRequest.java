package com.food.request;

import lombok.Data;

@Data
public class UserRequest {

    private String name;
    private String email;
    private String password;
    private String mobileNumber;
    private String profileImage;

    private Double latitude;
    private Double longitude;

    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String postalCode;
    private String country;
}
