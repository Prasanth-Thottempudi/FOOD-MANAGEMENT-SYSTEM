package com.food.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.food.config.MinioConfig;
import com.food.response.MinioServiceResponse;
import com.food.response.Response;
import com.food.service.MinioServices;

import io.minio.BucketExistsArgs;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.errors.ErrorResponseException;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import io.minio.errors.InvalidResponseException;
import io.minio.errors.ServerException;
import io.minio.errors.XmlParserException;
import io.minio.http.Method;

@Service
public class MinioServiceImpl implements MinioServices {

	@Autowired
	private MinioConfig minioConfig;

	

	@Override
	public boolean uploadImage(MultipartFile image) {
		return false;
	}

	@Override
	public MinioServiceResponse getImageUrl(String fileName) throws InvalidKeyException, ErrorResponseException, InsufficientDataException, InternalException, InvalidResponseException, NoSuchAlgorithmException, XmlParserException, ServerException, IllegalArgumentException, IOException {
		MinioServiceResponse response=new MinioServiceResponse();
		MinioClient minioClient = minioConfig.minioClient();
		String presignedUrl = minioClient.getPresignedObjectUrl(
		        GetPresignedObjectUrlArgs.builder()
		                .bucket("nexgenhub")
		                .object(fileName)
		                .method(Method.GET)
		                .expiry(2, TimeUnit.HOURS) 
		                .build());
		response.setResponseMessage("saved image successfully");
		response.setResponseStatus("1");	
		response.setImageUrl(presignedUrl);
		
		return response;
	}

	@Override
	public MinioServiceResponse saveImage(MultipartFile image,String fileName) throws InvalidKeyException, ErrorResponseException, InsufficientDataException, InternalException, InvalidResponseException, NoSuchAlgorithmException, ServerException, XmlParserException, IllegalArgumentException, IOException {
		MinioServiceResponse response=new MinioServiceResponse();
		MinioClient minioClient = minioConfig.minioClient();
		String bucketName = "nexgenhub";
		boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
		if (!found) {
			minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
		}
		InputStream inputStream = image.getInputStream();
		minioClient.putObject(PutObjectArgs.builder().bucket(bucketName).object(fileName)
				.stream(inputStream, image.getSize(), -1).contentType(image.getContentType()).build());
		
		String imageUrl = minioClient.getPresignedObjectUrl(
		        GetPresignedObjectUrlArgs.builder()
		            .method(Method.GET)
		            .bucket(bucketName)
		            .object(fileName)
		            .build()
		    );
		
		response.setResponseMessage("saved image successfully");
		response.setResponseStatus("1");	
		response.setImageUrl(imageUrl);
		return response;
	}

	@Override
	public Response deleteImage(String fileName) throws InvalidKeyException, ErrorResponseException, InsufficientDataException, InternalException, InvalidResponseException, NoSuchAlgorithmException, ServerException, XmlParserException, IllegalArgumentException, IOException {
		Response res=new Response();
		MinioClient minioClient = minioConfig.minioClient();
		String bucketName = "nexgenhub";
		boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
		if (!found) {
			minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
		}
		minioClient.removeObject(
                RemoveObjectArgs.builder()
                        .bucket(bucketName)
                        .object(fileName)
                        .build());
		res.setSuccessMessage("image deleted successfully");
		res.setStatus("1");
		return res;
	}

}