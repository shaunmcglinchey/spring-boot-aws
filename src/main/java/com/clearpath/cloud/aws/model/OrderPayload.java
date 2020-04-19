package com.clearpath.cloud.aws.model;

public class OrderPayload {

    private String payload;

    public OrderPayload(String payload) {
        this.payload = payload;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }
}
