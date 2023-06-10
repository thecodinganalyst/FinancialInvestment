package com.hevlar.financialinvestment.controller;

import com.hevlar.financialinvestment.model.Order;
import com.hevlar.financialinvestment.service.OrderBookService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orderBooks/{orderBookId}/orders")
public class OrderController {

    private final OrderBookService orderBookService;

    public OrderController(OrderBookService orderBookService){
        this.orderBookService = orderBookService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Order addOrder(@PathVariable("orderBookId") Long orderBookId, @RequestBody Order order){
        return this.orderBookService.addOrder(orderBookId, order);
    }
}
