package com.hevlar.financialinvestment.validation;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

@AllArgsConstructor
@Data
public class ErrorDto{
    String objectName;
    String fieldName;
    String rejectedValue;
    String errorMessage;

    public static ErrorDto fromObjectError(ObjectError error){
        String field = error instanceof FieldError fieldError ? fieldError.getField() : null;
        String rejectedValue = error instanceof FieldError fieldError ? String.valueOf(fieldError.getRejectedValue()) : null;

        return new ErrorDto(
                error.getObjectName(),
                field,
                rejectedValue,
                error.getDefaultMessage());
    }
}
