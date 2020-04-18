package com.clearpath.cloud.aws.model;

public class OrderResponse {

    private String id;

    private OrderResult orderResult;

    public OrderResponse(String id, OrderResult orderResult) {
        this.id = id;
        this.orderResult = orderResult;
    }

    public String getId() {
        return id;
    }

    public OrderResult getOrderResult() {
        return orderResult;
    }
}
