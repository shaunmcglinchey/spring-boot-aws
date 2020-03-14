package com.clearpath.cloud.aws;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

import static com.clearpath.cloud.aws.S3MockTest.S3_MOCK_RULE;

public class S3Client {

    final BasicAWSCredentials credentials = new BasicAWSCredentials("foo", "bar");

    public AmazonS3 build() {
        return AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withClientConfiguration(
                        S3_MOCK_RULE.configureClientToIgnoreInvalidSslCertificates(new ClientConfiguration()))
                .withEndpointConfiguration(
                        new AwsClientBuilder.EndpointConfiguration("http://localhost:" + S3_MOCK_RULE.getPort(), "us-east-1"))
                .enablePathStyleAccess()
                .build();
    }
}
