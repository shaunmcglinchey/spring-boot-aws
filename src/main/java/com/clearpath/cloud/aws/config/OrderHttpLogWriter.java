package com.clearpath.cloud.aws.config;

import com.clearpath.cloud.aws.service.OrderService;
import org.springframework.beans.factory.annotation.Value;
import org.zalando.logbook.Correlation;
import org.zalando.logbook.HttpLogWriter;
import org.zalando.logbook.Precorrelation;

import java.io.IOException;
import java.util.logging.Logger;

public class OrderHttpLogWriter implements HttpLogWriter {

    Logger LOGGER = Logger.getLogger(OrderHttpLogWriter.class.getName());

    @Value("${aws.bucket}")
    private String bucket;

    private OrderService orderService;

    public OrderHttpLogWriter(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public void write(Precorrelation precorrelation, String request) throws IOException {
        LOGGER.info("***** Order Request:");
        LOGGER.info(precorrelation.getId());
        LOGGER.info(request);
        orderService.putObject("clearpath-things", precorrelation.getId(), request.getBytes());
    }

    @Override
    public void write(Correlation correlation, String response) throws IOException {
        LOGGER.info("***** Order Response:");
        LOGGER.info(correlation.getId());
        LOGGER.info(response);
    }
}
