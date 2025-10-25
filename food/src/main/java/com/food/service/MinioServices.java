package com.food.service;

import java.io.IOException;
import java.rmi.ServerException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import org.apache.logging.log4j.util.InternalException;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.multipart.MultipartFile;

import com.food.response.MinioServiceResponse;
import com.food.response.Response;

import io.minio.errors.InsufficientDataException;
import io.minio.errors.InvalidResponseException;
import io.minio.errors.XmlParserException;

public interface MinioServices {
	
	public boolean uploadImage(MultipartFile image);
	
	public MinioServiceResponse getImageUrl(String fileName) throws InvalidKeyException, ErrorResponseException, InsufficientDataException, InternalException, InvalidResponseException, NoSuchAlgorithmException, XmlParserException, ServerException, IllegalArgumentException, IOException, io.minio.errors.ErrorResponseException, io.minio.errors.InternalException, io.minio.errors.ServerException;
	
	public MinioServiceResponse saveImage(MultipartFile image,String fileName) throws InvalidKeyException, ErrorResponseException, InsufficientDataException, InternalException, InvalidResponseException, NoSuchAlgorithmException, ServerException, XmlParserException, IllegalArgumentException, IOException, io.minio.errors.ErrorResponseException, io.minio.errors.InternalException, io.minio.errors.ServerException;

	public Response deleteImage(String fileName) throws InvalidKeyException, ErrorResponseException, InsufficientDataException, InternalException, InvalidResponseException, NoSuchAlgorithmException, ServerException, XmlParserException, IllegalArgumentException, IOException, io.minio.errors.ErrorResponseException, io.minio.errors.InternalException, io.minio.errors.ServerException;
	

}