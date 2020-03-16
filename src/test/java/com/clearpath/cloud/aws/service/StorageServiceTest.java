package com.clearpath.cloud.aws.service;

import com.clearpath.cloud.aws.exception.UploadFailedException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.File;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class StorageServiceTest {

    private StorageService storageService;

    private S3Client s3Client;

    @Before
    public void setup() {
        s3Client = Mockito.mock(S3Client.class);
        storageService = new StorageService(s3Client);
    }

    @Test(expected = UploadFailedException.class)
    public void givenSdkClientExceptionWhenStorageServicePutThenShouldThrowUploadFailedException() {
        when(s3Client.putObject((PutObjectRequest) any(), (Path) any()))
                .thenThrow(SdkClientException.class);
        storageService.putObject("foo", "bar", new File("src/main/resources/foobar.txt"));
    }

    @Test(expected = UploadFailedException.class)
    public void givenAmazonServiceExceptionWhenStorageServicePutThenShouldThrowUploadFailedException() {
        when(s3Client.putObject((PutObjectRequest) any(), (Path) any()))
                .thenThrow(AwsServiceException.class);
        storageService.putObject("foo", "bar", new File("src/main/resources/foobar.txt"));
    }

    @Test
    public void givenSuccessfulUploadWhenStorageServicePutThenShouldReturnResult() {
        PutObjectResponse stubbedResponse = PutObjectResponse.builder().build();
        when(s3Client.putObject((PutObjectRequest) any(), (Path) any())).thenReturn(stubbedResponse);
        PutObjectResponse putObjectResponse =
                storageService.putObject("foo", "bar", new File("src/main/resources/foobar.txt"));
        assertThat(putObjectResponse).isEqualTo(stubbedResponse);
    }

}
