package com.hevlar.financialinvestment.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class OrderBook {
    @Id
    @GeneratedValue
    Long orderBookId;
    OrderBookStatus status = OrderBookStatus.Open;

    @OneToMany(mappedBy = "orderBook", cascade = CascadeType.ALL)
    List<Order> orderList;

    @OneToMany(mappedBy = "orderBook", cascade = CascadeType.ALL)
    List<OrderExecution> executionList;
}
