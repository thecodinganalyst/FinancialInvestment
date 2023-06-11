package com.hevlar.financialinvestment.validation;

import com.hevlar.financialinvestment.model.Order;
import com.hevlar.financialinvestment.model.OrderType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class OrderValidator implements ConstraintValidator<ValidOrder, Order> {
    @Override
    public boolean isValid(Order value, ConstraintValidatorContext context) {
        if(value.getOrderType() == null) return false;
        return (value.getOrderType().equals(OrderType.LimitOrder) && value.getPrice() != null) ||
                (value.getOrderType().equals(OrderType.MarketOrder) && value.getPrice() == null);
    }
}
