package com.hevlar.financialinvestment.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hevlar.financialinvestment.model.Order;
import com.hevlar.financialinvestment.model.OrderBook;
import com.hevlar.financialinvestment.model.OrderType;
import com.hevlar.financialinvestment.validation.ErrorDto;
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
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(SpringExtension.class)
public class OrderControllerIntegrationTest {

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    OrderBook orderBook;

    @BeforeAll
    void setup() throws Exception {
        String orderBookJson = mvc.perform(post("/api/v1/orderBooks"))
                .andReturn()
                .getResponse()
                .getContentAsString();
        orderBook = objectMapper.readValue(orderBookJson, OrderBook.class);
    }

    @Test
    void whenAddOrder_thenReturnOrder() throws Exception {
        //Given
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
        mvc.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(orderJson))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.orderId").isNotEmpty())
                .andExpect(jsonPath("$.instrumentId").value("Isin1"))
                .andExpect(jsonPath("$.quantity").value(20))
                .andExpect(jsonPath("$.orderType").value(OrderType.LimitOrder.name()))
                .andExpect(jsonPath("$.price").value(100.0));
    }

    @Test
    void givenInvalidInput_whenAddOrder_thenFail() throws Exception {
        //Given
        Order order = new Order(
                null,
                null,
                null,
                null,
                null,
                null);
        String orderJson = objectMapper.writeValueAsString(order);

        String url = "/api/v1/orderBooks/" + orderBook.getOrderBookId().toString() + "/orders";
        MvcResult result = mvc.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(orderJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(6))
                .andReturn();

        // then
        String jsonResult = result.getResponse().getContentAsString();
        List<ErrorDto> errorDtos = Arrays.stream(objectMapper.readValue(jsonResult, ErrorDto[].class)).toList();

        List<ErrorDto> expectedErrorDtos = List.of(
                new ErrorDto("order", "entryDate", "null", "entry date cannot be null"),
                new ErrorDto("order", "instrumentId", "null", "instrument id cannot be null"),
                new ErrorDto("order", "quantity", "null", "quantity cannot be null"),
                new ErrorDto("order", "orderType", "null", "order type cannot be null"),
                new ErrorDto("order", "orderBook", "null", "order book cannot be null"),
                new ErrorDto("order", null, null, "Limit order must have a price, market order should not have a price")
        );
        assertThat(errorDtos, containsInAnyOrder(expectedErrorDtos.toArray()));
    }

    @Test
    void givenLimitOrderWithoutPrice_whenAddOrder_thenFail() throws Exception {
        //Given
        ZonedDateTime nowTime = ZonedDateTime.now();
        Order order = new Order(
                nowTime,
                "Isin1",
                10,
                OrderType.LimitOrder,
                orderBook);
        String orderJson = objectMapper.writeValueAsString(order);

        String url = "/api/v1/orderBooks/" + orderBook.getOrderBookId().toString() + "/orders";
        MvcResult result = mvc.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(orderJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(1))
                .andReturn();

        // then
        String jsonResult = result.getResponse().getContentAsString();
        List<ErrorDto> errorDtos = Arrays.stream(objectMapper.readValue(jsonResult, ErrorDto[].class)).toList();

        List<ErrorDto> expectedErrorDtos = List.of(
                new ErrorDto("order", null, null, "Limit order must have a price, market order should not have a price")
        );
        assertThat(errorDtos, containsInAnyOrder(expectedErrorDtos.toArray()));
    }

    @Test
    void givenMarketOrderWithPrice_whenAddOrder_thenFail() throws Exception {
        //Given
        ZonedDateTime nowTime = ZonedDateTime.now();
        Order order = new Order(
                nowTime,
                "Isin1",
                10,
                OrderType.MarketOrder,
                new BigDecimal("100.00"),
                orderBook);
        String orderJson = objectMapper.writeValueAsString(order);

        String url = "/api/v1/orderBooks/" + orderBook.getOrderBookId().toString() + "/orders";
        MvcResult result = mvc.perform(post(url).contentType(MediaType.APPLICATION_JSON).content(orderJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(1))
                .andReturn();

        // then
        String jsonResult = result.getResponse().getContentAsString();
        List<ErrorDto> errorDtos = Arrays.stream(objectMapper.readValue(jsonResult, ErrorDto[].class)).toList();

        List<ErrorDto> expectedErrorDtos = List.of(
                new ErrorDto("order", null, null, "Limit order must have a price, market order should not have a price")
        );
        assertThat(errorDtos, containsInAnyOrder(expectedErrorDtos.toArray()));
    }
}
