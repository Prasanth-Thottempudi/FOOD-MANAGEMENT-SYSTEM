package com.food.controller;

import io.minio.MinioClient;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;

public class MinioTest {
    public static void main(String[] args) {
        try {
            // Initialize MinIO client
            MinioClient minioClient = MinioClient.builder()
                    .endpoint("http://localhost:9000")   // MinIO server URL
                    .credentials("admin", "admin1234") // Access key and secret key
                    .build();

            String bucketName = "test-bucket";

            // Check if bucket exists
            boolean found = minioClient.bucketExists(
                    BucketExistsArgs.builder().bucket(bucketName).build()
            );

            if (!found) {
                // Create bucket if it doesn't exist
                minioClient.makeBucket(
                        MakeBucketArgs.builder().bucket(bucketName).build()
                );
                System.out.println("Bucket created: " + bucketName);
            } else {
                System.out.println("Bucket already exists: " + bucketName);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
