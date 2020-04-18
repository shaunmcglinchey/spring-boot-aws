package com.clearpath.cloud.aws.service;

import com.clearpath.cloud.aws.model.OrderRequest;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    public String order(OrderRequest foodOrder) {
        return foodOrder.getOrder();
    }
}
