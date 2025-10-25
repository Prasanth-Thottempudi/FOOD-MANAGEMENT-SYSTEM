package com.food.response;

import lombok.Builder;
import lombok.Data;

@Data
public class UserResponse {

	private Long userId;
	private String name;

	private String email;

	
	private String mobileNumber;
	private String profileUrl;
}
