package com.clearpath.cloud.aws.config;

import org.zalando.logbook.*;

import java.io.IOException;

public final class OrderSink implements Sink {

    private final HttpLogFormatter formatter;
    private final HttpLogWriter writer;

    @Override
    public void write(Precorrelation precorrelation, HttpRequest request) throws IOException {
        this.writer.write(precorrelation, this.formatter.format(precorrelation, request));
    }

    @Override
    public void write(Correlation correlation, HttpRequest request, HttpResponse response) throws IOException {
        this.writer.write(correlation, this.formatter.format(correlation, response));
    }

    public OrderSink(final HttpLogFormatter formatter, final HttpLogWriter writer) {
        this.formatter = formatter;
        this.writer = writer;
    }
}
