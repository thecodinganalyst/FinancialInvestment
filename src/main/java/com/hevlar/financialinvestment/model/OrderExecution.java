package com.hevlar.financialinvestment.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
    OrderBook orderBook;
}
