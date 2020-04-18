package com.clearpath.cloud.aws.model;

public class OrderRequest {

    @ValueOfOrderType(enumClass = OrderType.class)
    private String orderType;

    private String order;

    public OrderRequest() {
    }

    public OrderRequest(String orderType, String order) {
        this.orderType = orderType;
        this.order = order;
    }

    public String getOrderType() {
        return orderType;
    }

    public String getOrder() {
        return order;
    }
}
