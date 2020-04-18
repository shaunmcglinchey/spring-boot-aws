package com.clearpath.cloud.aws.config;

import com.clearpath.cloud.aws.service.OrderService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zalando.logbook.Sink;
import org.zalando.logbook.json.JsonHttpLogFormatter;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

@Configuration
public class OrderConfiguration {

    @Bean
    public Sink sink() {
        return new OrderSink(new JsonHttpLogFormatter(), new OrderHttpLogWriter(auditService()));
    }

    @Bean
    public OrderService auditService() {
        return new OrderService(auditClient());
    }

    @Bean
    S3Client auditClient() {
        return S3Client.builder()
                .region(Region.EU_WEST_2)
                .build();
    }

}
