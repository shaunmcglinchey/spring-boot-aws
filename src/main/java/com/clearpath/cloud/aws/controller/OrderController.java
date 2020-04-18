package com.clearpath.cloud.aws.controller;

import com.clearpath.cloud.aws.model.OrderRequest;
import com.clearpath.cloud.aws.model.OrderResponse;
import com.clearpath.cloud.aws.model.OrderResult;
import com.clearpath.cloud.aws.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class OrderController {

    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping(
            value = "/order",
            produces = "application/xml",
            consumes = "application/xml"
    )
    public ResponseEntity<OrderResponse> addFood(@Valid @RequestBody OrderRequest orderRequest) {
        this.orderService.order(orderRequest);
        return new ResponseEntity<>(new OrderResponse("1", OrderResult.SUCCESS), HttpStatus.CREATED);
    }

}
