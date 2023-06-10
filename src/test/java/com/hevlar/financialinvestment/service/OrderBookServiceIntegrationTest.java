package com.hevlar.financialinvestment.service;

import com.hevlar.financialinvestment.model.Order;
import com.hevlar.financialinvestment.model.OrderBook;
import com.hevlar.financialinvestment.model.OrderBookStatus;
import com.hevlar.financialinvestment.model.OrderType;
import org.hibernate.internal.util.ZonedDateTimeComparator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class OrderBookServiceIntegrationTest {

    @Autowired
    OrderBookService service;

    @Test
    void whenCreate_thenReturnOrderBookWithStatusOpen() {
        OrderBook orderBook = service.openOrderBook();
        assertThat(orderBook.getOrderBookId(), is(notNullValue()));
        assertThat(orderBook.getStatus(), is(OrderBookStatus.Open));
    }

    @Test
    void whenAddOrder_thenReturnOrder(){
        OrderBook orderBook = service.openOrderBook();
        Order order = new Order(ZonedDateTime.now(), "Isin1", 10, OrderType.LimitOrder, new BigDecimal("100.00"), orderBook);
        Order result = service.addOrder(orderBook.getOrderBookId(), order);
        assertThat(result.getOrderId(), is(notNullValue()));
        assertThat(result.getInstrumentId(), is("Isin1"));
        assertThat(result.getQuantity(), is(10));
        assertThat(result.getPrice(), is(new BigDecimal("100.00")));
        assertThat(result.getOrderType(), is(OrderType.LimitOrder));
        assertThat(result.getOrderBook().getOrderBookId(), is(orderBook.getOrderBookId()));
    }

    @Test
    void whenCloseOrderBook_thenReturnOrderBookClosed(){
        OrderBook orderBook = service.openOrderBook();
        OrderBook result = service.closeOrderBook(orderBook.getOrderBookId());
        assertThat(result.getOrderBookId(), is(orderBook.getOrderBookId()));
        assertThat(result.getStatus(), is(OrderBookStatus.Closed));
    }

}
