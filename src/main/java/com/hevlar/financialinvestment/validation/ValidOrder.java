package com.hevlar.financialinvestment.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

@Documented
@Target({TYPE, FIELD, PARAMETER, RECORD_COMPONENT})
@Constraint(validatedBy = OrderValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidOrder {
    String message() default "Limit order must have a price, market order should not have a price";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
