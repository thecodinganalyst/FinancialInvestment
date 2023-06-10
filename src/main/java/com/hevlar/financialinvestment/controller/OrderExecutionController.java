package com.hevlar.financialinvestment.controller;

import com.hevlar.financialinvestment.model.OrderExecution;
import com.hevlar.financialinvestment.service.OrderBookService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orderBooks/{orderBookId}/executions")
public class OrderExecutionController {
    private final OrderBookService orderBookService;

    public OrderExecutionController(OrderBookService orderBookService){
        this.orderBookService = orderBookService;
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderExecution addExecution(@PathVariable("orderBookId") Long orderBookId, @RequestBody OrderExecution execution){
        return orderBookService.addExecution(orderBookId, execution);
    }
}
