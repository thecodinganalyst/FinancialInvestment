package com.hevlar.financialinvestment.service;

import com.hevlar.financialinvestment.model.OrderBookStatus;
import com.hevlar.financialinvestment.repository.OrderBookRepository;
import com.hevlar.financialinvestment.repository.OrderRepository;
import com.hevlar.financialinvestment.model.OrderBook;
import com.hevlar.financialinvestment.model.Order;
import org.aspectj.apache.bcel.generic.RET;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderBookService {

    private final OrderBookRepository orderBookRepository;
    private final OrderRepository orderRepository;

    public OrderBookService(OrderBookRepository orderBookRepository, OrderRepository orderRepository){
        this.orderBookRepository = orderBookRepository;
        this.orderRepository = orderRepository;
    }

    public OrderBook openOrderBook(){
        return orderBookRepository.save(new OrderBook());
    }

    public Order addOrder(Long orderBookId, Order order){
        Optional<OrderBook> orderBookExist = orderBookRepository.findById(orderBookId);
        if(orderBookExist.isEmpty()) throw new IllegalArgumentException("Order book id " + orderBookId + " does not exist");
        OrderBook orderBook = orderBookExist.get();
        if(orderBook.getStatus().equals(OrderBookStatus.Closed)) throw new IllegalStateException("Orders cannot be added because order book is closed");
        return orderRepository.save(order);
    }

    public OrderBook closeOrderBook(Long orderBookId){
        Optional<OrderBook> orderBookExist = orderBookRepository.findById(orderBookId);
        if(orderBookExist.isEmpty()) throw new IllegalArgumentException("Order book id " + orderBookId + " does not exist");
        OrderBook orderBook = orderBookExist.get();
        orderBook.setStatus(OrderBookStatus.Closed);
        orderBook = orderBookRepository.save(orderBook);
        return orderBook;
    }
}
