package com.hevlar.financialinvestment.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "OrderEntry")
public class Order {
    @Id
    @GeneratedValue
    Long orderId;
    ZonedDateTime entryDate;
    String instrumentId;
    Integer quantity;
    OrderType orderType;
    BigDecimal price;
    @ManyToOne
    @JoinColumn
    OrderBook orderBook;

    public Order(ZonedDateTime entryDate, String instrumentId, Integer quantity, OrderType orderType, BigDecimal price, OrderBook orderBook){
        this.entryDate = entryDate;
        this.instrumentId = instrumentId;
        this.quantity = quantity;
        this.orderType = orderType;
        this.price = price;
        this.orderBook = orderBook;
    }

    public Order(ZonedDateTime entryDate, String instrumentId, Integer quantity, OrderType orderType, OrderBook orderBook){
        this.entryDate = entryDate;
        this.instrumentId = instrumentId;
        this.quantity = quantity;
        this.orderType = orderType;
        this.orderBook = orderBook;
    }
}
