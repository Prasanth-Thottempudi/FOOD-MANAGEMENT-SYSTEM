package com.food.response;

import lombok.Data;

@Data
public class MinioServiceResponse {
	
	public String responseMessage;
	private String responseStatus;
	private String imageUrl;

}