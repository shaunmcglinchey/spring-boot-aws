package com.clearpath.cloud.aws.controller;

import com.clearpath.cloud.aws.model.OrderRequest;
import com.clearpath.cloud.aws.service.OrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(OrderController.class)
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @Test
    public void whenMissingOrderType_thenResponseStatus_Http400() throws Exception {
        when(orderService.order(any(OrderRequest.class)))
                .thenReturn("foo");

        String orderRequestBody = "<?xml version=\"1.0\"?><OrderRequest></OrderRequest>";

        mockMvc.perform(post("/order")
                .contentType(MediaType.APPLICATION_XML)
                .accept(MediaType.APPLICATION_XML)
                .content(orderRequestBody))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void whenMissingOrder_thenResponseStatus_Http400() throws Exception {
        when(orderService.order(any(OrderRequest.class)))
                .thenReturn("foo");

        String orderRequestBody = "<?xml version=\"1.0\"?><OrderRequest><orderType>Foo</orderType></OrderRequest>";

        mockMvc.perform(post("/order")
                .contentType(MediaType.APPLICATION_XML)
                .accept(MediaType.APPLICATION_XML)
                .content(orderRequestBody))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void whenOrderTypeInvalid_thenResponseStatus_Http400() throws Exception {
        when(orderService.order(any(OrderRequest.class)))
                .thenReturn("foo");

        String orderRequestBody = "<?xml version=\"1.0\"?><OrderRequest><orderType>register</orderType></OrderRequest>";

        mockMvc.perform(post("/order")
                .contentType(MediaType.APPLICATION_XML)
                .accept(MediaType.APPLICATION_XML)
                .content(orderRequestBody))
                .andExpect(status().isOk());
    }


}
