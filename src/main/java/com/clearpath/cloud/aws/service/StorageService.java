package com.clearpath.cloud.aws.service;

import com.clearpath.cloud.aws.exception.UploadFailedException;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.File;

@Service
public class StorageService {

    private S3Client s3Client;

    public StorageService(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    public PutObjectResponse putObject(String bucketName, String key, File file) {
        try {
            return this.s3Client
                    .putObject(PutObjectRequest.builder().bucket(bucketName).key(key).build(), file.toPath());
        } catch (AwsServiceException | SdkClientException e) {
            throw new UploadFailedException("Upload failed");
        }
    }

}
