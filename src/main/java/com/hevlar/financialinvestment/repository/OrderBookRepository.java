package com.hevlar.financialinvestment.repository;

import com.hevlar.financialinvestment.model.OrderBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderBookRepository extends JpaRepository<OrderBook, Long> {
}
