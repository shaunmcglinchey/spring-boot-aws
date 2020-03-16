package com.clearpath.cloud.aws.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class ExternalConfiguration {

    @Bean
    S3Client s3Client() {
        return S3Client.builder()
                .region(Region.EU_WEST_2)
                .build();
    }
}
