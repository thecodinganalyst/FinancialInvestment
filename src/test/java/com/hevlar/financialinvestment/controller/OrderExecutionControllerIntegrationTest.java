package com.hevlar.financialinvestment.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hevlar.financialinvestment.model.Order;
import com.hevlar.financialinvestment.model.OrderBook;
import com.hevlar.financialinvestment.model.OrderExecution;
import com.hevlar.financialinvestment.model.OrderType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(SpringExtension.class)
public class OrderExecutionControllerIntegrationTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    OrderBook orderBook;

    @BeforeAll
    void setup() throws Exception {
        //open order book
        String orderBookJson = mvc.perform(post("/api/v1/orderBooks"))
                .andReturn()
                .getResponse()
                .getContentAsString();
        orderBook = objectMapper.readValue(orderBookJson, OrderBook.class);

        // add order
        ZonedDateTime nowTime = ZonedDateTime.now();
        Order order = new Order(
                nowTime,
                "Isin1",
                20,
                OrderType.LimitOrder,
                new BigDecimal("100.00"),
                orderBook);
        String orderJson = objectMapper.writeValueAsString(order);

        String url = "/api/v1/orderBooks/" + orderBook.getOrderBookId().toString() + "/orders";
        mvc.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(orderJson));

        //close order book
        String closeOrderBookUrl = "/api/v1/orderBooks/" + orderBook.getOrderBookId() + "/close";
        mvc.perform(patch(closeOrderBookUrl))
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    @Test
    void whenAddExecution_thenReturnExecution() throws Exception {
        //Given
        OrderExecution orderExecution = new OrderExecution(1, new BigDecimal("500.00"), orderBook);
        String orderExecutionJson = objectMapper.writeValueAsString(orderExecution);

        String url = "/api/v1/orderBooks/" + orderBook.getOrderBookId().toString() + "/executions";
        mvc.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(orderExecutionJson))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.orderExecutionId").isNotEmpty())
                .andExpect(jsonPath("$.quantity").value(1))
                .andExpect(jsonPath("$.price").value(500.00));
    }
}
