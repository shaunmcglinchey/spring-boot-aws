package com.clearpath.cloud.aws.service;

import com.clearpath.cloud.aws.exception.UploadFailedException;
import com.clearpath.cloud.aws.model.OrderRequest;
import com.clearpath.cloud.aws.model.OrderResult;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
public class OrderService {

    private S3Client s3Client;

    public OrderService(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    public OrderResult order(OrderRequest order) {
        return OrderResult.SUCCESS;
    }

    public void putObject(String bucket, String key, byte[] bytes) {
        try {
            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(bucket).key(key).build();

            this.s3Client.putObject(request, RequestBody.fromBytes(bytes));
        } catch (AwsServiceException | SdkClientException e) {
            throw new UploadFailedException("Upload failed: " + e);
        }
    }
}
