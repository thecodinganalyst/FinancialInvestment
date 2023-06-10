package com.hevlar.financialinvestment.service;

import com.hevlar.financialinvestment.model.Order;
import com.hevlar.financialinvestment.model.OrderBook;
import com.hevlar.financialinvestment.model.OrderBookStatus;
import com.hevlar.financialinvestment.repository.OrderBookRepository;
import com.hevlar.financialinvestment.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@ExtendWith(MockitoExtension.class)
public class OrderBookServiceUnitTest {

    @Mock
    OrderBookRepository orderBookRepository;

    @Mock
    OrderRepository orderRepository;

    @InjectMocks
    OrderBookService orderBookService;

    @Test
    void givenOrderBookDoesntExist_whenAddOrder_thenThrow(){
        Mockito.when(orderBookRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> orderBookService.addOrder(1L, new Order()));
    }

    @Test
    void givenOrderBookIsClosed_whenAddOrder_thenThrow(){
        OrderBook orderBook = new OrderBook(1L, OrderBookStatus.Closed, List.of(), List.of());
        Order order = new Order();
        Mockito.when(orderBookRepository.findById(1L)).thenReturn(Optional.of(orderBook));
        assertThrows(IllegalStateException.class, () -> orderBookService.addOrder(1L, new Order()));
    }

    @Test
    void givenOrderBookExist_whenAddOrder_thenReturnOrder(){
        Order order = new Order();
        Mockito.when(orderBookRepository.findById(1L)).thenReturn(Optional.of(new OrderBook()));

        Mockito.when(orderRepository.save(any())).thenReturn(order);
        assertThat(orderBookService.addOrder(1L, order), is(order));
    }

    @Test
    void givenOrderBookDoesntExist_whenCloseOrderBook_thenThrow(){
        Mockito.when(orderBookRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> orderBookService.closeOrderBook(1L));
    }
}
