package com.hevlar.financialinvestment.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.hevlar.financialinvestment.validation.ValidOrder;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
@ValidOrder
public class Order {
    @Id
    @GeneratedValue
    Long orderId;
    @NotNull(message = "entry date cannot be null")
    ZonedDateTime entryDate;
    @NotNull(message = "instrument id cannot be null")
    String instrumentId;
    @NotNull(message = "quantity cannot be null")
    Integer quantity;
    @NotNull(message = "order type cannot be null")
    OrderType orderType;
    BigDecimal price;
    @ManyToOne
    @JoinColumn
    @NotNull(message = "order book cannot be null")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "orderBookId", scope = OrderBook.class)
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
