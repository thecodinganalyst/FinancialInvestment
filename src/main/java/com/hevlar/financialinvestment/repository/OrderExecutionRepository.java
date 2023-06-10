package com.hevlar.financialinvestment.repository;

import com.hevlar.financialinvestment.model.OrderExecution;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderExecutionRepository extends JpaRepository<OrderExecution, Long> {
}
