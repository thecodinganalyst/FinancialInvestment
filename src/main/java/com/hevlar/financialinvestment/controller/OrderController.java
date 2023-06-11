package com.hevlar.financialinvestment.controller;

import com.hevlar.financialinvestment.model.Order;
import com.hevlar.financialinvestment.service.OrderBookService;
import com.hevlar.financialinvestment.validation.ErrorDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orderBooks/{orderBookId}/orders")
public class OrderController {

    private final OrderBookService orderBookService;

    public OrderController(OrderBookService orderBookService){
        this.orderBookService = orderBookService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Order addOrder(@PathVariable("orderBookId") Long orderBookId, @RequestBody @Valid Order order){
        return this.orderBookService.addOrder(orderBookId, order);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<ErrorDto> handleValidationExceptions(MethodArgumentNotValidException ex){
        return ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(ErrorDto::fromObjectError)
                .toList();
    }
}
