package com.clearpath.cloud.aws.model;

import javax.xml.bind.annotation.XmlRootElement;

//@XmlRootElement
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

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}
