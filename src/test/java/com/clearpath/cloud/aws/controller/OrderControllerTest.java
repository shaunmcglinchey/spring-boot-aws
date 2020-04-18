package com.clearpath.cloud.aws.controller;

import com.clearpath.cloud.aws.model.OrderRequest;
import com.clearpath.cloud.aws.model.OrderResult;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;

@RunWith(SpringRunner.class)
@WebMvcTest(OrderController.class)
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @Test
    public void whenOrderTypeMissing_then_Http400() throws Exception {
        java.lang.String orderRequestBody = "<?xml version=\"1.0\"?><OrderRequest><order>foo</order></OrderRequest>";

        mockMvc.perform(post("/order")
                .contentType(MediaType.APPLICATION_XML)
                .accept(MediaType.APPLICATION_XML)
                .content(orderRequestBody))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void whenOrderTypeInvalid_then_Http400() throws Exception {
        java.lang.String orderRequestBody = "<?xml version=\"1.0\"?><OrderRequest><order>foo</order>" +
                "<orderType>BAR</orderType></OrderRequest>";

        mockMvc.perform(post("/order")
                .contentType(MediaType.APPLICATION_XML)
                .accept(MediaType.APPLICATION_XML)
                .content(orderRequestBody))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void whenOrderTypeValid_then_Http201_Success() throws Exception {
        when(orderService.order(any(OrderRequest.class)))
                .thenReturn(OrderResult.SUCCESS);

        java.lang.String orderRequestBody = "<?xml version=\"1.0\"?><OrderRequest><orderType>register</orderType>" +
                "<order>bar</order></OrderRequest>";

        mockMvc.perform(post("/order")
                .contentType(MediaType.APPLICATION_XML)
                .accept(MediaType.APPLICATION_XML)
                .content(orderRequestBody))
                .andExpect(status().isCreated())
                .andExpect(xpath("/OrderResponse/orderResult").string("SUCCESS"));
    }

    @Test
    public void whenOrderTypeValid_then_Http201_Rejected() throws Exception {
        when(orderService.order(any(OrderRequest.class)))
                .thenReturn(OrderResult.REJECTED);

        java.lang.String orderRequestBody = "<?xml version=\"1.0\"?><OrderRequest><orderType>register</orderType>" +
                "<order>bar</order></OrderRequest>";

        mockMvc.perform(post("/order")
                .contentType(MediaType.APPLICATION_XML)
                .accept(MediaType.APPLICATION_XML)
                .content(orderRequestBody))
                .andExpect(status().isCreated())
                .andExpect(xpath("/OrderResponse/orderResult").string("REJECTED"));
    }


}
