package com.clearpath.cloud.aws.controller;

import com.clearpath.cloud.aws.model.OrderPayload;
import com.clearpath.cloud.aws.model.OrderRequest;
import com.clearpath.cloud.aws.model.OrderResponse;
import com.clearpath.cloud.aws.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.xml.bind.JAXB;
import java.io.StringReader;
import java.util.Map;

@RestController
public class OrderController {

    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping(
            value = "/order",
            produces = { "application/xml", "application/json" },
            consumes = "application/xml"
    )
    public ResponseEntity<OrderResponse> placeOrder(@Valid @RequestBody OrderRequest orderRequest) {
        return new ResponseEntity<>(
                new OrderResponse("1", this.orderService.order(orderRequest)), HttpStatus.CREATED
        );
    }


    @GetMapping(
            value = "/order",
            produces = "application/xml"
    )
    public ResponseEntity<OrderPayload> getOrder(@RequestParam String id) {
        byte[] object = orderService.getObject("clearpath-things", id);
        Map<String, Object> jsonMap = orderService.asJsonMap(object);
        String body = jsonMap.get("body").toString();

        OrderRequest orderRequest = JAXB.unmarshal(new StringReader(body), OrderRequest.class);
        OrderPayload payload = new OrderPayload(orderRequest.getOrder());
        return new ResponseEntity<>(payload, HttpStatus.OK);
    }

}
