package com.hevlar.financialinvestment.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class OrderExecution{
    @Id
    @GeneratedValue
    Long orderExecutionId;

    Integer quantity;

    BigDecimal price;

    @ManyToOne
    @JoinColumn
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "orderBookId", scope = OrderBook.class)
    OrderBook orderBook;

    public OrderExecution(Integer quantity, BigDecimal price, OrderBook orderBook){
        this.quantity = quantity;
        this.price = price;
        this.orderBook = orderBook;
    }
}
