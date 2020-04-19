package com.clearpath.cloud.aws.service;

import com.clearpath.cloud.aws.exception.UploadFailedException;
import com.clearpath.cloud.aws.model.OrderRequest;
import com.clearpath.cloud.aws.model.OrderResult;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.core.exception.SdkServiceException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.core.sync.ResponseTransformer;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.Map;
import java.util.logging.Logger;

@Service
public class OrderService {

    private Logger logger = Logger.getLogger(OrderService.class.getName());

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
                    .bucket(bucket)
                    .key(key)
                    .build();

            this.s3Client.putObject(request, RequestBody.fromBytes(bytes));
        } catch (AwsServiceException | SdkClientException e) {
            throw new UploadFailedException("Upload failed: " + e);
        }
    }

    public byte[] getObject(String bucket, String key) {
        try {
            logger.info("Retrieving file from S3 for key: {}/{}" + key);

            ResponseBytes<GetObjectResponse> s3Object = s3Client.getObject(
                    GetObjectRequest.builder()
                            .bucket(bucket)
                            .key(key)
                            .build(),
                    ResponseTransformer.toBytes());

            final byte[] bytes = s3Object.asByteArray();
            return bytes;
        } catch (SdkClientException ase) {
            logger.info("Caught an AmazonServiceException, which " + "means your request made it "
                    + "to Amazon S3, but was rejected with an error response" + " for some reason: " + ase);
            throw ase;
        } catch (SdkServiceException ace) {
            logger.info("Caught an AmazonClientException, which " + "means the client encountered "
                    + "an internal error while trying to " + "communicate with S3, "
                    + "such as not being able to access the network: " + ace);
            throw ace;
        }
    }

    public Map<String, Object> asJsonMap(byte[] object) {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> jsonMap;
        try {
            jsonMap = mapper.readValue(new String(object), new TypeReference<Map<String,Object>>(){});
            return jsonMap;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
