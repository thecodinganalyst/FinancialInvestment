package com.hevlar.financialinvestment.controller;

import com.hevlar.financialinvestment.model.OrderBook;
import com.hevlar.financialinvestment.service.OrderBookService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orderBooks")
public class OrderBookController {

    private final OrderBookService service;

    public OrderBookController(OrderBookService service){
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderBook openOrderBook(){
        return service.openOrderBook();
    }

    @PatchMapping("/{orderBookId}")
    public OrderBook closeOrderBook(@PathVariable("orderBookId") Long orderBookId){
        return service.closeOrderBook(orderBookId);
    }

}
