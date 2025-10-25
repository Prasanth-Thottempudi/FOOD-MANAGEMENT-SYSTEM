package com.food.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.minio.MinioClient;

@Configuration
public class MinioConfig {

	

	@Bean
	public MinioClient minioClient() {
		MinioClient minioClient = MinioClient.builder()
		        .endpoint("http://127.0.0.1:9000") // or http://127.0.0.1:9000
		        .credentials("admin", "admin1234")
		        .build();

		return minioClient;
	}
	
	
}