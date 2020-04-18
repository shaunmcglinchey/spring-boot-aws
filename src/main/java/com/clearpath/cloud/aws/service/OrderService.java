package com.clearpath.cloud.aws.service;

import com.clearpath.cloud.aws.model.OrderRequest;
import com.clearpath.cloud.aws.model.OrderResult;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    public OrderResult order(OrderRequest foodOrder) {
        return OrderResult.SUCCESS;
    }
}
